package logit.logit_backend.domain;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "course_title",nullable = false)
    private String courseTitle;

    @Column(name = "course_area", nullable = false)
    private String courseArea;

    @Column(name = "course_image")
    private String courseImage;

    @Column(name = "course_period" ,nullable = false)
    private int coursePeriod;
    // 0 = 당일치기 , 1 = 1박 2일 , 2 = 2박 3일

    @Column(name = "course_cre_date", nullable = false)
    private LocalDate courseCreDate;

    @Column(name = "course_theme", nullable = false)
    private String courseTheme;

    @OneToMany(mappedBy = "courseId", fetch = FetchType.LAZY)
    private List<CoursePlan> coursePlans = new ArrayList<>();
}
