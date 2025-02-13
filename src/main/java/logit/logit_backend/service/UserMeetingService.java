package logit.logit_backend.service;

import logit.logit_backend.controller.form.CreateUserMeetingForm;
import logit.logit_backend.controller.form.GetMeetingDetailForm;
import logit.logit_backend.controller.form.MemberForm;
import logit.logit_backend.domain.Meeting;
import logit.logit_backend.domain.User;
import logit.logit_backend.domain.UserMeeting;
import logit.logit_backend.repository.MeetingRepository;
import logit.logit_backend.repository.UserMeetingRepository;
import logit.logit_backend.repository.UserRepository;
import logit.logit_backend.util.LogitUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.readAllBytes;

@Service
@Transactional
public class UserMeetingService {
    private final UserMeetingRepository userMeetingRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

    public UserMeetingService(UserMeetingRepository userMeetingRepository, UserRepository userRepository, MeetingRepository meetingRepository) {
        this.userMeetingRepository = userMeetingRepository;
        this.userRepository = userRepository;
        this.meetingRepository = meetingRepository;
    }

    public UserMeeting create(CreateUserMeetingForm createUserMeetingForm) {
        UserMeeting userMeeting = new UserMeeting();
        User user = userRepository.findByUserLoginId(createUserMeetingForm.getUserLoginId()).orElseThrow();
        Meeting meeting = meetingRepository.findByMeetingId(createUserMeetingForm.getMeetingId()).orElseThrow();

        userMeeting.setUser(user);
        userMeeting.setMeeting(meeting);
        if (createUserMeetingForm.getUserMeetingMbti() != null) {
            userMeeting.setUserMeetingMbti(createUserMeetingForm.getUserMeetingMbti());
        }
        userMeeting.setUserMeetingStatus(false);
        userMeetingRepository.save(userMeeting);

        return userMeeting;
    }

    // meetingId 를 기반으로 연관된 유저 찾기
    public GetMeetingDetailForm getMembersByMeetingId(Long meetingId) throws IOException {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow();
        //List<UserMeeting> userMeetings = userMeetingRepository.findByMeeting(meeting);
        List<UserMeeting> userMeetings = userMeetingRepository.findByMeeting_MeetingId(meetingId);
        List<MemberForm> memberForms = new ArrayList<>();

        for (UserMeeting u : userMeetings) {
            User user = userRepository.findByUserLoginId(u.getUser().getUserLoginId()).orElseThrow();
            byte[] image  =readAllBytes(new File(user.getUserImagePath()).toPath());
            //byte[] image = readAllBytes(new File(LogitUtils.getUserImagePath()).toPath());

            memberForms.add(
                    new MemberForm(
                            user,
                            Base64.getEncoder().encodeToString(image),
                            u.getUserMeetingMbti(),
                            u.getUser().getUserLoginId().equals(meeting.getMeetingHostId())?true:false
                    )

            );
        }

        return new GetMeetingDetailForm(meetingId, memberForms, meeting);
    }
}
