package logit.logit_backend.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import logit.logit_backend.domain.UserSex;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserForm {

    private String userStatusMsg;
    private Integer userAge;

    @Schema(example = "male/female")
    private UserSex userSex;
    private Integer userTemperature;

}