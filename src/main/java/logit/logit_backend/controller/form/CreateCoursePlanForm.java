package logit.logit_backend.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCoursePlanForm {
    private Long courseId;
    private String planImage;
    private String planCategory;
    private String planName;
    private String planAddress;
    private Integer planDate;
}
