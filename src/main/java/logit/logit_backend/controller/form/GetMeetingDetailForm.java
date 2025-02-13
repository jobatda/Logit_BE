package logit.logit_backend.controller.form;

import logit.logit_backend.domain.Meeting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetMeetingDetailForm {

    private Long meetingId;
    private List<MemberForm> members; // 참여 인원
    private Integer nowCnt;
    private Integer maxCnt;

    public GetMeetingDetailForm(List<MemberForm> members, Meeting meeting) {
        this.members = members;
        this.nowCnt = meeting.getMeetingNowCnt();
        this.maxCnt = meeting.getMeetingMaxCnt();
    }
}
