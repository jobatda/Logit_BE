package logit.logit_backend.controller.form;

import logit.logit_backend.domain.UserSex;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    private String userName;
    private Integer userAge;
    private UserSex userSex;
    private String userImage;
    private String mbti;
    private boolean isHost;

    public MemberForm(String userName, Integer userAge, UserSex userSex, String userImage, String mbti, boolean isHost) {
        this.userName = userName;
        this.userAge = userAge;
        this.userSex = userSex;
        this.userImage = userImage;
        this.mbti = mbti;
        this.isHost = isHost;
    }
}
