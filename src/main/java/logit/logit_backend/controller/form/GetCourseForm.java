package logit.logit_backend.controller.form;

import logit.logit_backend.domain.Course;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class GetCourseForm {
    private Long courseId;
    private Long userId;
    private String courseTitle;
    private String courseArea;
    private List<String> courseImage;
    private int coursePeriod;
    private LocalDate courseCreDate;
    private String courseTheme;

    public GetCourseForm(Course course, List<String> courseImage) {
        this.courseId = course.getCourseId();
        this.userId = course.getUser().getUserId();
        this.courseTitle = course.getCourseTitle();
        this.courseArea = course.getCourseArea();
        this.courseImage = courseImage;
        this.coursePeriod = course.getCoursePeriod();
        this.courseCreDate = course.getCourseCreDate();
        this.courseTheme = course.getCourseTheme();
    }
}
