package logit.logit_backend.service;

import logit.logit_backend.controller.form.CreateCourseForm;
import logit.logit_backend.controller.form.GetCourseForm;
import logit.logit_backend.domain.Course;
import logit.logit_backend.domain.User;
import logit.logit_backend.repository.CourseRepository;
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
import java.util.List;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Course createCourse(CreateCourseForm form, String loginId) {
        Course course = new Course();
        User user = userRepository.findByUserLoginId(loginId).orElseThrow();

        if (form.getCourseTitle() != null &&
            form.getCourseArea() != null &&
            form.getCoursePeriod() != null &&
            form.getCourseCreDate() != null &&
            form.getCourseTheme() != null) {
            course.setCourseTitle(form.getCourseTitle());
            course.setCourseArea(form.getCourseArea());
            course.setCoursePeriod(form.getCoursePeriod());
            course.setCourseCreDate(form.getCourseCreDate());
            course.setCourseTheme(form.getCourseTheme());
        }
        course.setUser(user);
        courseRepository.save(course);

        return course;
    }

    public void updateImages(Course course, List<MultipartFile> courseImages, String UPLOAD_DIR) throws IOException {
        String imagePath;
        List<String> imagePaths = new ArrayList<>();
        Long courseId = course.getCourseId();

        // image 에 대한 모든 경로를 String 으로 저장
        // 구분자는 "\n"
        if (courseImages != null && !courseImages.isEmpty()) {
            for (MultipartFile image : courseImages) {
                if (image != null && !image.isEmpty()) {
                    imagePath = UPLOAD_DIR + courseId + "_" + image.getOriginalFilename();
                    image.transferTo(new File(imagePath));
                    imagePaths.add(imagePath);
                }
            }
            course.setCourseImage(String.join("\n", imagePaths));
            courseRepository.save(course);
        }
    }

    public List<GetCourseForm> getCourseAll() throws IOException{
        List<Course> courses = courseRepository.findAll();
        List<GetCourseForm> CourseAll = new ArrayList<>();

        if (courses.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "찾을 수 없습니다");
        }

        for (Course c : courses) {
            String imageField = c.getCourseImage();
            List<String> images = List.of();

            if (imageField != null && !imageField.isEmpty()) {
                images = LogitUtils.encodeImagesBase64(imageField);
            }
            CourseAll.add(new GetCourseForm(c, images));
        }
        return CourseAll;
    }
}
