package logit.logit_backend.controller.form;

import logit.logit_backend.domain.PostCategory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreatePostForm {
    private String postTitle;
    private String postContent;
    private PostCategory postCategory;
    private String postLocation;
}
