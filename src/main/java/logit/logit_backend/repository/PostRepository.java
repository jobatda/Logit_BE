package logit.logit_backend.repository;

import logit.logit_backend.domain.Meeting;
import logit.logit_backend.domain.Post;
import logit.logit_backend.domain.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long postId);
    List<Post> findByPostCategory(PostCategory postCategory);

    @Query("SELECT p FROM Post p WHERE p.user.userId = :userId")
    List<Post> getPostImgByUserId(@Param("userId") Long userId);
}
