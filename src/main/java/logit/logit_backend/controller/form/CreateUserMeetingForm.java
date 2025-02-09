package logit.logit_backend.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateUserMeetingForm {

    private String userLoginId;
    private Long meetingId;
    private boolean userMeetingStatus;
    private String userMeetingMbti;

}
