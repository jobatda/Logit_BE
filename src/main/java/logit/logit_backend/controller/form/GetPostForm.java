package logit.logit_backend.controller.form;

import logit.logit_backend.domain.Post;
import logit.logit_backend.domain.PostCategory;
import logit.logit_backend.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import static java.nio.file.Files.readAllBytes;

@Getter @Setter
public class GetPostForm {
    private String userLoginId;
    private String userImage;
    private Long postId;
    private String postTitle;
    private String postContent;
    private List<String> postContentImage;
    private LocalDateTime postDate; // 작성시간
    private String postLocation;
    private PostCategory postCategory;
    private int postTravelNum;


    public GetPostForm(Post post, List<String> images, User user) throws IOException {
        byte[] image = readAllBytes(new File(user.getUserImagePath()).toPath());

        this.userLoginId = user.getUserName();
        this.userImage = Base64.getEncoder().encodeToString(image);
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postContentImage = images;
        this.postDate = post.getPostDate();
        this.postLocation = post.getPostLocation();
        this.postCategory = post.getPostCategory();
        this.postTravelNum = post.getPostTravelNum();
    }

    public GetPostForm(Post post, List<String> images) {
        this.userLoginId = null;
        this.userImage = null;
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postContentImage = images;
        this.postDate = post.getPostDate();
        this.postLocation = post.getPostLocation();
        this.postCategory = post.getPostCategory();
        this.postTravelNum = post.getPostTravelNum();
    }

}
