package logit.logit_backend.controller.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CreateCourseForm {
    private Long userId;
    private String courseTitle;
    private String courseArea;
    private String courseImage;
    private Integer coursePeriod;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate courseCreDate;
    private String courseTheme;
}
