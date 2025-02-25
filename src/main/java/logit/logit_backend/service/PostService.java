package logit.logit_backend.service;

import logit.logit_backend.controller.form.CreatePostForm;
import logit.logit_backend.controller.form.GetMeetingForm;
import logit.logit_backend.controller.form.GetPostForm;
import logit.logit_backend.controller.form.GetPostImgForm;
import logit.logit_backend.domain.Meeting;
import logit.logit_backend.domain.Post;
import logit.logit_backend.domain.PostCategory;
import logit.logit_backend.domain.User;
import logit.logit_backend.repository.PostRepository;
import logit.logit_backend.repository.UserRepository;
import logit.logit_backend.util.LogitUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static java.nio.file.Files.readAllBytes;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post create(CreatePostForm form, String loginId) {
        Post post = new Post();
        User user = userRepository.findByUserLoginId(loginId).orElseThrow();

        if (form.getPostCategory() != null &&
                form.getPostLocation() != null &&
                form.getPostTitle() != null &&
                form.getPostContent() != null) {
            post.setPostCategory(form.getPostCategory());
            post.setPostLocation(form.getPostLocation());
            post.setPostTitle(form.getPostTitle());
            post.setPostContent(form.getPostContent());
        }
        if (form.getPostLocation() != null) {
            post.setPostLocation(form.getPostLocation());
        }
        post.setUser(user);
        postRepository.save(post);

        return post;
    }

    public void updateImages(Post post, List<MultipartFile> postImages, String UPLOAD_DIR) throws IOException {
        String imagePath;
        List<String> imagePaths = new ArrayList<>();
        Long postId = post.getPostId();

        // image 에 대한 모든 경로를 String 으로 저장
        // 구분자는 "\n"
        if (postImages != null && !postImages.isEmpty()) {
            for (MultipartFile image : postImages) {
                if (image != null && !image.isEmpty()) {
                    imagePath = UPLOAD_DIR + postId + "_" + image.getOriginalFilename();
                    image.transferTo(new File(imagePath));
                    imagePaths.add(imagePath);
                }
            }
            post.setPostContentImage(String.join("\n", imagePaths));
            postRepository.save(post);
        }
    }

    // 카테고리별 post 조회
    public List<GetPostForm> getPostByCategory(PostCategory category) throws IOException{
        List<Post> PostCategoryList = postRepository.findByPostCategory(category);
        List<GetPostForm> allPostsCategory = new ArrayList<>();

        if(PostCategoryList.isEmpty()){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "찾을 수 없습니다");
        }
        for (Post post : PostCategoryList) {
            String imageField = post.getPostContentImage();
            List<String> images = List.of();

            if (imageField != null && !imageField.isEmpty()) {
                images = LogitUtils.encodeImagesBase64(imageField);
            }

            allPostsCategory.add(new GetPostForm(
                    post,
                    images,
                    post.getUser()));
        }
        return allPostsCategory;
    }

    public GetPostForm getPostByPostId(Long postId) throws IOException{
        Post post = postRepository.findByPostId(postId).orElseThrow();
        String imageField = post.getPostContentImage();

        if (imageField != null && !imageField.isEmpty()) {
            return new GetPostForm(post, LogitUtils.encodeImagesBase64(imageField), post.getUser());
        }

        return new GetPostForm(post, List.of());
    }

    public List<GetPostImgForm> getPostImgByUserId(Long userLoginId) throws IOException{
        List<Post> PostList = postRepository.getPostImgByUserId(userLoginId);
        List<GetPostImgForm> allPostImg = new ArrayList<>();

        if(PostList.isEmpty()){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "찾을 수 없습니다");
        }
        for (Post post : PostList) {
            String imagePath = post.getPostContentImage();
            List<String> images = List.of();

            if (imagePath != null && !imagePath.isEmpty()) {
                images = LogitUtils.encodeImagesBase64(imagePath);
            }

            allPostImg.add(new GetPostImgForm(post.getPostId(), images));
        }
        return allPostImg;
    }

    // 특정 검색어 제목 모임 조회
    public List<GetPostForm> getPostsByTitle(String title) throws IOException {
        List<Post> posts = postRepository.findByPostTitleContaining(title);
        List<GetPostForm> allPosts = new ArrayList<>();

        if (posts.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "찾을 수 없습니다");
        }
        for (Post ps : posts) {
            String imageField = ps.getPostContentImage();
            List<String> images = List.of();

            if (imageField != null && !imageField.isEmpty()) {
                images = LogitUtils.encodeImagesBase64(imageField);
            }
            allPosts.add(new GetPostForm(ps, images));
        }

        return allPosts;
    }


}