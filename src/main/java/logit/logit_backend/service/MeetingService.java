package logit.logit_backend.service;

import logit.logit_backend.controller.form.CreateMeetingForm;
import logit.logit_backend.controller.form.GetMeetingForm;
import logit.logit_backend.domain.Meeting;
import logit.logit_backend.domain.User;
import logit.logit_backend.domain.UserMeeting;

import logit.logit_backend.repository.MeetingRepository;
import logit.logit_backend.repository.UserMeetingRepository;
import logit.logit_backend.repository.UserRepository;

import logit.logit_backend.util.LogitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class MeetingService {
    private final UserRepository userRepository;
    private final UserMeetingRepository userMeetingRepository;
    private final MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository,
                          UserRepository userRepository,
                          UserMeetingRepository userMeetingRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.userMeetingRepository = userMeetingRepository;
    }

    public Meeting create(CreateMeetingForm form) {
        User user = userRepository.findByUserLoginId(form.getMeetingHostId()).orElseThrow();
        Meeting meeting = new Meeting();
        UserMeeting userMeeting = new UserMeeting();

        // Meeting 테이블 생성
        if (form.getMeetingHostId() != null &&
                form.getMeetingTitle() != null &&
                form.getMeetingContent() != null &&
                form.getMeetingStartDate() != null &&
                form.getMeetingEndDate() != null &&
                form.getMeetingLocation() != null &&
                form.getMeetingMaxCnt() != null) {
            meeting.setMeetingHostId(form.getMeetingHostId());
            meeting.setMeetingTitle(form.getMeetingTitle());
            meeting.setMeetingContent(form.getMeetingContent());
            meeting.setMeetingStartDate(form.getMeetingStartDate());
            meeting.setMeetingEndDate(form.getMeetingEndDate());
            meeting.setMeetingLocation(form.getMeetingLocation());
            meeting.setMeetingMaxCnt(form.getMeetingMaxCnt());
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "필드가 잘못되었습니다.");
        }
        meetingRepository.save(meeting);

        // UserMeeting 테이블 생성
        userMeeting.setUser(user);
        userMeeting.setMeeting(meeting);
        if (form.getUserMeetingMbti() != null) {
            userMeeting.setUserMeetingMbti(form.getUserMeetingMbti());
        }
        userMeeting.setUserMeetingStatus(true);
        userMeetingRepository.save(userMeeting);

        return meeting;
    }

    public void updateImages(Meeting meeting, List<MultipartFile> meetingImages, String UPLOAD_DIR) throws IOException {
        String imagePath;
        List<String> imagePaths = new ArrayList<>();
        Long meetingId = meeting.getMeetingId();

        // image 에 대한 모든 경로를 String 으로 저장
        // 구분자는 "\n"
        if (meetingImages != null && !meetingImages.isEmpty()) {
            for (MultipartFile image : meetingImages) {
                if (image != null && !image.isEmpty()) {
                    imagePath = UPLOAD_DIR + meetingId + "_" + image.getOriginalFilename();
                    image.transferTo(new File(imagePath));
                    imagePaths.add(imagePath);
                }
            }
            meeting.setMeetingContentImage(String.join("\n", imagePaths));
            meetingRepository.save(meeting);
        }
    }

    public List<GetMeetingForm> getAllMeetings() throws IOException {
        List<Meeting> meetings = meetingRepository.findAll();
        List<GetMeetingForm> allMeetings = new ArrayList<>();

        if (meetings.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "찾을 수 없습니다");
        }
        for (Meeting m : meetings) {
            String imageField = m.getMeetingContentImage();
            List<String> images = List.of();

            if (imageField != null && !imageField.isEmpty()) {
                images = LogitUtils.encodeImagesBase64(imageField);
            }
            allMeetings.add(new GetMeetingForm(m, images));
        }

        return allMeetings;
    }

    public List<GetMeetingForm> getMeetingsByTitle(String title) throws IOException {
        List<Meeting> meetings = meetingRepository.findByMeetingTitleContaining(title);
        List<GetMeetingForm> allMeetings = new ArrayList<>();

        if (meetings.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "찾을 수 없습니다");
        }
        for (Meeting m : meetings) {
            String imageField = m.getMeetingContentImage();
            List<String> images = List.of();

            if (imageField != null && !imageField.isEmpty()) {
                images = LogitUtils.encodeImagesBase64(imageField);
            }
            allMeetings.add(new GetMeetingForm(m, images));
        }

        return allMeetings;
    }

    public GetMeetingForm getMeetingById(Long meetingId) throws IOException {
        Meeting meeting = meetingRepository.findByMeetingId(meetingId).orElseThrow();
        String imageField = meeting.getMeetingContentImage();

        if (imageField != null && !imageField.isEmpty()) {
            return new GetMeetingForm(meeting, LogitUtils.encodeImagesBase64(imageField));
        }
        return new GetMeetingForm(meeting, List.of());
    }
}
