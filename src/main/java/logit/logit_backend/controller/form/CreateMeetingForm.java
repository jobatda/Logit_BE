package logit.logit_backend.controller.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CreateMeetingForm {

    private String meetingHostId;
    private String meetingTitle;
    private String meetingContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate meetingStartDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate meetingEndDate;

    private String meetingLocation;
    private Integer meetingNowCnt;
    private Integer meetingMaxCnt;
    private String userMeetingMbti;

}
