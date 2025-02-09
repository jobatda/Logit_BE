package logit.logit_backend.repository;

import logit.logit_backend.domain.UserMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMeetingRepository extends JpaRepository<UserMeeting, Long> {
}
