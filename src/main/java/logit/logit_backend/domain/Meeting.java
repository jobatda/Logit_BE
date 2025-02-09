package logit.logit_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id", nullable = false)
    private Long meetingId;

    @Column(name = "meeting_host_id", nullable = false)
    private String meetingHostId;

    @Column(name = "meeting_title", nullable = false)
    private String meetingTitle;

    @Column(name = "meeting_content", nullable = false)
    private String meetingContent;

    @Column(name = "meeting_start_date", nullable = false)
    private LocalDate meetingStartDate;

    @Column(name = "meeting_end_date", nullable = false)
    private LocalDate meetingEndDate;

    @Column(name = "meeting_location", nullable = false)
    private String meetingLocation;

    @Column(name = "meeting_now_cnt", nullable = false)
    private int meetingNowCnt = 1;

    @Column(name = "meeting_max_cnt", nullable = false)
    private int meetingMaxCnt;

    @Column(name = "meeting_content_img")
    private String meetingContentImg;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<UserMeeting> userMeetings = new ArrayList<>();
}
