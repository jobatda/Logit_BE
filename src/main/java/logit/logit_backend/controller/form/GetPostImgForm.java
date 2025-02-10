package logit.logit_backend.controller.form;

import logit.logit_backend.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetPostImgForm {
    private Long postId;
    private String postContentImage;

    public GetPostImgForm(Post post) {
        this.postId = post.getPostId();
        this.postContentImage = post.getPostContentImage();
    }
}
