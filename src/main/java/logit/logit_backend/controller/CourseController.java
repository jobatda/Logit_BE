package logit.logit_backend.controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import logit.logit_backend.controller.form.PostCourseForm;
import logit.logit_backend.domain.Course;
import logit.logit_backend.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/course")
@Tag(name = "Course", description = "여행로드맵 API")
public class CourseController {
    private final CourseService courseService;
    private final static Logger logger = LoggerFactory.getLogger(CourseController.class);
    private final String UPLOAD_DIR = "/app/uploads/image/course/";

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Create Course", description = " 마이페이지-AI여행플랜 목록생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"course_id\": \"1\" }")
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
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createCourse(
            @RequestBody PostCourseForm form) {
        try {

            Course course = courseService.createCourse(form);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("courseId", course.getCourseId()));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}

//    @Operation(summary = "Get course home", description = "마이페이지-AI여행플랜 목록 - 모든 플랜 가져오기")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공",
//                    content = @Content(
//                            mediaType = "application/json",
//                            array = @ArraySchema(schema = @Schema(implementation = GetCourseForm.class))
//                    )),
//            @ApiResponse(responseCode = "404", description = "AI여행플랜 게시물이 생성되지 않았습니다.",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(example = "{ \"error\": \"message\" }")
//                    )),
//            @ApiResponse(responseCode = "500", description = "서버 오류 발생",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(example = "{ \"error\": \"message\" }")
//                    )),
//    }) // Swagger 문서 작성
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getCourseAll(){
//        try {
//            List<GetCourseForm> CourseAll = courseService.getCourseAll();
//
//            return ResponseEntity.ok(CourseAll);
//        }catch (HttpClientErrorException e){
//            return ResponseEntity
//                    .status(e.getStatusCode())
//                    .body(Map.of("error", e.getMessage()));
//        }catch (IOException e) {
//            return ResponseEntity
//                    .internalServerError()
//                    .body(Map.of("error", e.getMessage()));
//        }
//    }
//}
