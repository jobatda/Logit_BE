package logit.logit_backend.repository;

import logit.logit_backend.domain.CoursePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoursePlanRepository extends JpaRepository<CoursePlan, Long> {
    @Query("SELECT p FROM Course p WHERE p.courseId = :courseId")
    List<CoursePlan> findByCourseId(@Param("courseId") Long courseId);
}
