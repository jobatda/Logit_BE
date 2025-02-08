package logit.logit_backend.domain;

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
    private Long id;

    @Column(name = "user_login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "user_login_pw", nullable = false)
    private String loginPw;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_img_path")
    private String imagePath;

    @Column(name = "user_status_msg")
    private String statusMsg;

    @Column(name = "user_age")
    private int age;

    @Enumerated(EnumType.STRING) // Enum을 DB에 문자열로 저장
    @Column(name = "user_sex")
    private UserSex userSex;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserMeeting> userMeetings = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();

    public enum UserSex{
        male, female
    }
}
