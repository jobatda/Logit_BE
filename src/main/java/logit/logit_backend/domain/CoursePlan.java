package logit.logit_backend.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CoursePlan {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseId;

    @Column(name = "plan_image")
    private String planImage;

    @Column(name = "plan_category", nullable = false)
    private String planCategory;
    // 여행지, 음식점, 카페

    @Column(name = "plan_name" , nullable = false)
    private String planName;

    @Column(name = "plan_address" , nullable = false)
    private String planAddress;

    @Column(name = "plan_date" , nullable = false)
    private int planDate;

}
