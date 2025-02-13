package logit.logit_backend.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseDayForm {

    @JsonProperty("카테고리")
    private String category;

    @JsonProperty("장소명")
    private String placeName;

    @JsonProperty("주소")
    private String address;

    @JsonProperty("imgUrl")
    private String imgUrl; // null 가능

}
