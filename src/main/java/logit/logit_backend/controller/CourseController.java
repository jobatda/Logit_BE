package logit.logit_backend.controller;

import logit.logit_backend.controller.form.CreateCourseForm;
import logit.logit_backend.domain.Course;
import logit.logit_backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final String UPLOAD_DIR = "/app/uploads/image/course/";

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(value = "/{loginId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createCourse(
            @PathVariable String loginId,
            @ModelAttribute CreateCourseForm form,
            @RequestPart(value = "courseImages", required = false) List<MultipartFile> courseImages) {
        try {

            Course course = courseService.createCourse(form, loginId);
            courseService.updateImages(course, courseImages, UPLOAD_DIR);

            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created course");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
