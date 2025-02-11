package logit.logit_backend.controller.form;

import logit.logit_backend.domain.Meeting;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class GetMeetingForm {
    private Long meetingId;
    private String meetingTitle;
    private String meetingContent;
    private LocalDate meetingStartDate;
    private LocalDate meetingEndDate;
    private Integer meetingNowCnt;
    private Integer meetingMaxCnt;
    private String meetingLocation;
    private List<String> meetingContentImage;

    public GetMeetingForm(Meeting meeting, List<String> images) {
        this.meetingId = meeting.getMeetingId();
        this.meetingTitle = meeting.getMeetingTitle();
        this.meetingContent = meeting.getMeetingContent();
        this.meetingStartDate = meeting.getMeetingStartDate();
        this.meetingEndDate = meeting.getMeetingEndDate();
        this.meetingNowCnt = meeting.getMeetingNowCnt();
        this.meetingMaxCnt = meeting.getMeetingMaxCnt();
        this.meetingLocation = meeting.getMeetingLocation();
        this.meetingContentImage = images;
    }
}
