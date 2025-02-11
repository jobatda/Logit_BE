package logit.logit_backend.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import logit.logit_backend.domain.PostCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class CreatePostForm {
    private Long userId;
    private String postTitle;
    private String postContent;
    private List<String> postContentImage;
    private LocalDateTime postDate; // 작성시간
    @Schema(example = "feed/festival/experience")
    private PostCategory postCategory;
    private String postLocation;
    private int postTravelNum;

}
