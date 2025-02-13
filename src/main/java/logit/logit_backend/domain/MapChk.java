package logit.logit_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter @Setter
public class MapChk {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_chk_id", nullable = false)
    private Long mapChkId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_login_id", nullable = false)
//    private User user;

    @Column(name = "map_chk_area")
    private String mapChkArea;

    @Column(name = "map_chk_insert")
    private String mapChkInsert;
}

