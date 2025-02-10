package logit.logit_backend.controller;

import logit.logit_backend.controller.form.CreateUserMeetingForm;
import logit.logit_backend.service.UserMeetingService;
import logit.logit_backend.domain.UserMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user-meeting")
public class UserMeetingController {
    private final UserMeetingService userMeetingService;

    @Autowired
    public UserMeetingController(UserMeetingService userMeetingService) {
        this.userMeetingService = userMeetingService;
    }

    @PostMapping ("/submit")
    public ResponseEntity<Map<String, Object>> createUserMeeting(
            @RequestBody CreateUserMeetingForm createUserMeetingForm) {
        try {

            UserMeeting userMeeting = userMeetingService.create(createUserMeetingForm);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("user-meetingId", userMeeting.getUserMeetingId()));

        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
