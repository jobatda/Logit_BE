package logit.logit_backend.controller.form;

import logit.logit_backend.domain.Post;
import logit.logit_backend.domain.PostCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class GetPostForm {
    private String userName;
    private String userImage;
    private Long postId;
    private String postTitle;
    private String postContent;
    private List<String> postContentImage;
    private LocalDateTime postDate; // 작성시간
    private String postLocation;
    private PostCategory postCategory;
    private int postTravelNum;


    public GetPostForm(Post post, String userName, String userImage, List<String> images) {
        this.userName = userName;
        this.userImage = userImage;
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
