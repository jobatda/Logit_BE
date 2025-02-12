package logit.logit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import logit.logit_backend.controller.form.CreateCoursePlanForm;
import logit.logit_backend.controller.form.GetCourseForm;
import logit.logit_backend.controller.form.GetCoursePlanForm;
import logit.logit_backend.domain.CoursePlan;
import logit.logit_backend.service.CoursePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/course-plan")
public class CoursePlanController {
    private final CoursePlanService coursePlanService;
    private final String UPLOAD_DIR = "/app/uploads/image/course/";

    @Autowired
    public CoursePlanController(CoursePlanService coursePlanService) {
        this.coursePlanService = coursePlanService;
    }

    @Operation(summary = "Create CoursePlan", description = " AI플래너 세부일정 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"plan_id\": \"1\" }")
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
    @PostMapping(value = "/{courseId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createCoursePlan(
            @PathVariable Long courseId,
            @ModelAttribute CreateCoursePlanForm form,
            @RequestPart(value = "coursePlanImages", required = false) List<MultipartFile> coursePlanImages){
        try {

            CoursePlan coursePlan = coursePlanService.createCoursePlan(form, courseId);
            coursePlanService.updateImages(coursePlan, coursePlanImages, UPLOAD_DIR);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("planId", coursePlan.getPlanId()));
        } catch(NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch(IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Get coursePlan home", description = "courseId에 해당하는 플랜 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCoursePlanForm.class))
                    )),
            @ApiResponse(responseCode = "404", description = "AI플래너 게시물이 생성되지 않았습니다.",
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
    @GetMapping(value = "{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchCoursePlanByCourseId(@PathVariable Long courseId){
        try {
            List<GetCoursePlanForm> CoursePlans = coursePlanService.searchCoursePlanByCourseId(courseId);

            return ResponseEntity.ok(CoursePlans);
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
}
