package logit.logit_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Column(name = "post_content", nullable = false)
    private String content;

    @Column(name = "post_date", nullable = false)
    private LocalDateTime date; // 작성시간

    @Enumerated(EnumType.STRING)
    @Column(name = "post_category" ,nullable = false)
    private Category category;

    @Column(name = "post_location")
    private String location;

    @Column(name = "post_travel_num", nullable = false)
    private int travelNum;

    public enum Category{ // 피드, 축제, 체험
        feed, festival, experience
    }
}
