package logit.logit_backend.controller;

import logit.logit_backend.controller.form.CreatePostForm;
import logit.logit_backend.domain.Post;
import logit.logit_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequestMapping("/post")
@RestController
public class PostController {
    private final PostService postService;
    private final String UPLOAD_DIR = "/app/uploads/image/post/";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/{loginId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createPost(
            @PathVariable String loginId,
            @RequestPart(value = "postImage", required = false) List<MultipartFile> postImages,
            @ModelAttribute CreatePostForm form) {
        try {

            Post post = postService.create(form, loginId);
            postService.updateImages(post, postImages, UPLOAD_DIR);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("postId: ", post.getPostId()));
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error: ", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error: ", e.getMessage()));
        }

    }
}
