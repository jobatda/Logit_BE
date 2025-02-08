package logit.logit_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Meeting {

    @Id @GeneratedValue
    @Column(name = "meeting_id", nullable = false)
    private Long id;

    @Column(name = "meeting_host_id", nullable = false)
    private Long hostId;

    @Column(name = "meeting_title", nullable = false)
    private String title;

    @Column(name = "meeting_content", nullable = false)
    private String content;

    @Column(name = "meeting_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "meeting_location", nullable = false)
    private String location;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<UserMeeting> userMeetings = new ArrayList<>();
}
