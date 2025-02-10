package logit.logit_backend.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetUserMeetingForm {

    private List<MemberForm> members;
    private Integer nowCnt;
    private Integer maxCnt;

    public GetUserMeetingForm(List<MemberForm> members, Integer nowCnt, Integer maxCnt) {
        this.members = members;
        this.nowCnt = nowCnt;
        this.maxCnt = maxCnt;
    }
}
