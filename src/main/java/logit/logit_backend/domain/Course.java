package logit.logit_backend.domain;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_login_id", nullable = false)
    private User user;

    @Column(name = "meeeting_id_by_course")
    private String meeetingIdByCourse;

    @Column(name = "course_day1")
    private String courseDay1;

    @Column(name = "course_day2")
    private String courseDay2;

    @Column(name = "course_day3")
    private String courseDay3;

    @Column(name = "course_location")
    private String courseLocation;

    @Column(name = "course_themes")
    private String courseThemes;

    @CreatedDate
    @Column(name = "course_date", nullable = false)
    private LocalDateTime courseDate;
}
