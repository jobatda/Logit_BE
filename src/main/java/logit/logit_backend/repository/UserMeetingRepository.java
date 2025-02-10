package logit.logit_backend.repository;

import logit.logit_backend.domain.UserMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMeetingRepository extends JpaRepository<UserMeeting, Long> {

    List<UserMeeting> findByMeetingId(Long meetingId);
}
