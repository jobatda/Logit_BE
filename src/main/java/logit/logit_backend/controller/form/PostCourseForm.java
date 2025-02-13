package logit.logit_backend.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class PostCourseForm {
    Map<String, List<CourseDayForm>> output;
    List<String> themes;
    String location;
}
