package logit.logit_backend.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import logit.logit_backend.domain.User;
import logit.logit_backend.domain.UserSex;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    private String userName;
    private Integer userAge;

    @Schema(example = "male/female")
    private UserSex userSex;
    private String userImage;
    private String mbti;

    @JsonProperty("isHost")
    private boolean isHost;

    public MemberForm(User user, String userImage, String mbti) {

        this.userName = user.getUserName();
        this.userAge = user.getUserAge();
        this.userSex = user.getUserSex();
        this.userImage = userImage;
        this.mbti = mbti;
        //this.isHost = isHost;
    }
}
