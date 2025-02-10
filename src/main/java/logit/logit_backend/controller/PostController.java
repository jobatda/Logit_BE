package logit.logit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import logit.logit_backend.controller.form.CreatePostForm;
import logit.logit_backend.controller.form.GetMeetingForm;
import logit.logit_backend.controller.form.GetPostForm;
import logit.logit_backend.controller.form.GetPostImgForm;
import logit.logit_backend.domain.Post;
import logit.logit_backend.domain.PostCategory;
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
@Tag(name = "Post", description = "Post API")
public class PostController {
    private final PostService postService;
    private final String UPLOAD_DIR = "/app/uploads/image/post/";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Create post", description = "피드, 축제, 체험의 게시물을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"postId\": \"1\" }")
                    )),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
    }) // Swagger 문서 작성
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

    @Operation(summary = "getPostsByCategory", description = "category 별로 post 출력")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPostForm.class))
                    )),
            @ApiResponse(responseCode = "404", description = "category와 일치하는 게시물이 존재하지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
    }) // Swagger 문서 작성
    @GetMapping(value = {"/category/{category}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPostsByCategory(@PathVariable  PostCategory category) {
        try {
            List<GetPostForm> PostCategoryList = postService.getPostByCategory(category);

            return ResponseEntity.ok(PostCategoryList);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "getPostByPostId", description = "postId에 맞는 post글 호출")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPostForm.class))
                    )),
            @ApiResponse(responseCode = "404", description = "postId와 일치하는 게시물이 존재하지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
    }) // Swagger 문서 작성
    @GetMapping(value = "/postId/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPostByPostId(@PathVariable Long postId){
        try{
            GetPostForm getPostByPostIdOne = postService.getPostByPostId(postId);
            return ResponseEntity.ok(getPostByPostIdOne);

        }catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }


    @Operation(summary = "getPostImgByUserId", description = "마이페이지 내가올린 post(게시물)조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPostImgForm.class))
                    )),
            @ApiResponse(responseCode = "404", description = "userLoginId와 일치하는 게시물이 존재하지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
    }) // Swagger 문서 작성
    @GetMapping(value = {"/userLoginId/{userLoginId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPostImgByUserId(@PathVariable Long userLoginId ) {
        try {
            List<GetPostImgForm> getPostImgeList = postService.getPostImgByUserId(userLoginId);

            return ResponseEntity.ok(getPostImgeList);
        } catch (NoSuchElementException e) {
             System.out.println("NoSuchElementException");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));

        } catch (IOException e) {
            System.out.println("IOException");
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
