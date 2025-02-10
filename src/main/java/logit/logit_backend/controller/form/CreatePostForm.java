package logit.logit_backend.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import logit.logit_backend.domain.PostCategory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreatePostForm {
    private String postTitle;
    private String postContent;
    @Schema(example = "feed/festival/experience")
    private PostCategory postCategory;
    private String postLocation;
}
