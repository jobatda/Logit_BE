package logit.logit_backend.service;

import logit.logit_backend.controller.form.CreateUserMeetingForm;
import logit.logit_backend.domain.Meeting;
import logit.logit_backend.domain.User;
import logit.logit_backend.domain.UserMeeting;
import logit.logit_backend.repository.MeetingRepository;
import logit.logit_backend.repository.UserMeetingRepository;
import logit.logit_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void create(CreateUserMeetingForm createUserMeetingForm) {
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
    }
}
