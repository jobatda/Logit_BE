package logit.logit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import logit.logit_backend.controller.form.CreateCourseForm;
import logit.logit_backend.controller.form.GetCourseForm;
import logit.logit_backend.controller.form.GetMeetingForm;
import logit.logit_backend.domain.Course;
import logit.logit_backend.service.CourseService;
import logit.logit_backend.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final String UPLOAD_DIR = "/app/uploads/image/course/";
    private final MeetingService meetingService;

    @Autowired
    public CourseController(CourseService courseService, MeetingService meetingService) {
        this.courseService = courseService;
        this.meetingService = meetingService;
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
    @PostMapping(value = "/{loginId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createCourse(
            @PathVariable String loginId,
            @ModelAttribute CreateCourseForm form,
            @RequestPart(value = "courseImages", required = false) List<MultipartFile> courseImages) {
        try {

            Course course = courseService.createCourse(form, loginId);
            courseService.updateImages(course, courseImages, UPLOAD_DIR);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("courseId", course.getCourseId()));
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

    @Operation(summary = "Get course home", description = "마이페이지-AI여행플랜 목록 - 모든 플랜 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCourseForm.class))
                    )),
            @ApiResponse(responseCode = "404", description = "AI여행플랜 게시물이 생성되지 않았습니다.",
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCourseAll(){
        try {
            List<GetCourseForm> CourseAll = courseService.getCourseAll();

            return ResponseEntity.ok(CourseAll);
        }catch (HttpClientErrorException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("error", e.getMessage()));
        }catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
