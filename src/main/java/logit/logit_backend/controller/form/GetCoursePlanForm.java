package logit.logit_backend.controller.form;

import logit.logit_backend.domain.CoursePlan;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class GetCoursePlanForm {
    private Long planId;
    private Long courseId;
    private List<String> planImage;
    private String planCategory;
    private String planName;
    private String planAddress;
    private int planDate;

    public GetCoursePlanForm(CoursePlan coursePlan, List<String> planImage) {
        this.planId = coursePlan.getPlanId();
        this.courseId = coursePlan.getCourseId().getCourseId();
        this.planImage = planImage;
        this.planCategory = coursePlan.getPlanCategory();
        this.planName = coursePlan.getPlanName();
        this.planAddress = coursePlan.getPlanAddress();
        this.planDate = coursePlan.getPlanDate();
    }

}
