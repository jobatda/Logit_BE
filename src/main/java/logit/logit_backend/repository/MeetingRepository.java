package logit.logit_backend.repository;


import logit.logit_backend.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Optional<Meeting> findByMeetingId(Long meetingId);
    List<Meeting> findByMeetingTitleContaining(String meetingTitle);
}
