package logit.logit_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_content", nullable = false)
    private String postContent;

    @Column(name = "post_content_image")
    private String postContentImage;

    @CreatedDate
    @Column(name = "post_date", nullable = false)
    private LocalDateTime postDate; // 작성시간

    @Enumerated(EnumType.STRING)
    @Column(name = "post_category" ,nullable = false)
    private PostCategory postCategory;

    @Column(name = "post_location")
    private String postLocation;

    @Column(name = "post_travel_num")
    private int postTravelNum;
}
