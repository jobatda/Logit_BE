package logit.logit_backend.controller.form;

import logit.logit_backend.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetPostImgForm {
    private Long postId;
    private List<String> postContentImage;

    public GetPostImgForm(Long postId, List<String> postContentImage) {
        this.postId = postId;
        this.postContentImage = postContentImage;
    }
}
