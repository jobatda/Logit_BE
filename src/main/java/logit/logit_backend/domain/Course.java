package logit.logit_backend.domain;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "course_img")
    private String courseImg;

    @Column(name = "course_start_date", nullable = false)
    private LocalDateTime courseStartDate;

    @Column(name = "course_end_date", nullable = false)
    private LocalDateTime courseEndDate;

    @Column(name = "course_title", nullable = false)
    private String courseTitle;
}
