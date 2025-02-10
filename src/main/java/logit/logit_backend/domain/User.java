package logit.logit_backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_login_id", nullable = false, unique = true)
    private String userLoginId;

    @Column(name = "user_login_pw", nullable = false)
    private String userLoginPw;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_image_path")
    private String userImagePath;

    @Column(name = "user_status_msg")
    private String userStatusMsg;

    @Column(name = "user_age")
    private int userAge;

    @Enumerated(EnumType.STRING) // Enum을 DB에 문자열로 저장
    @Column(name = "user_sex")
    private UserSex userSex;

    @Column(name = "user_temperature", nullable = false, columnDefinition = "INT DEFAULT 36")
    private int userTemperature = 36;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserMeeting> userMeetings = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();
}
