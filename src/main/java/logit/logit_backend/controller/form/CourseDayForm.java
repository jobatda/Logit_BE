package logit.logit_backend.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseDayForm {

    private String 카테고리;
    private String 장소명;
    private String 주소;
    private String imgUrl; // null 가능

}
