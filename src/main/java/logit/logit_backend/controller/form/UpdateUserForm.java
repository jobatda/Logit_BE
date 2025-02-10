package logit.logit_backend.controller.form;

import logit.logit_backend.domain.UserSex;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserForm {

    private String userStatusMsg;
    private Integer userAge;
    private UserSex userSex;
    private Integer userTemperature;

}