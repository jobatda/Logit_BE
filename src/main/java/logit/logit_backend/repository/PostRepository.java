package logit.logit_backend.repository;

import logit.logit_backend.domain.Meeting;
import logit.logit_backend.domain.Post;
import logit.logit_backend.domain.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long postId);
    List<Post> findByPostCategory(PostCategory postCategory);
}
