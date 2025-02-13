package logit.logit_backend.service;

import logit.logit_backend.controller.form.PostCourseForm;
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

import static logit.logit_backend.util.ParserUtils.parseDay;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Course createCourse(PostCourseForm form) {
        Course course = new Course();
        User user = userRepository.findByUserLoginId("id111").orElseThrow();
        String themes = "";

        course.setCourseDay1(parseDay(form.getOutput().get("Day 1")));
        course.setCourseDay2(parseDay(form.getOutput().get("Day 2")));
        course.setCourseDay3(parseDay(form.getOutput().get("Day 3")));
        course.setUser(user);
        course.setCourseLocation(form.getLocation());
        for (String theme : form.getThemes()) {
            themes += theme + ",";
        }
        course.setCourseThemes(themes);
        courseRepository.save(course);

        return course;
    }

//    public void updateImages(Course course, List<MultipartFile> courseImages, String UPLOAD_DIR) throws IOException {
//        String imagePath;
//        List<String> imagePaths = new ArrayList<>();
//        Long courseId = course.getCourseId();
//
//        // image 에 대한 모든 경로를 String 으로 저장
//        // 구분자는 "\n"
//        if (courseImages != null && !courseImages.isEmpty()) {
//            for (MultipartFile image : courseImages) {
//                if (image != null && !image.isEmpty()) {
//                    imagePath = UPLOAD_DIR + courseId + "_" + image.getOriginalFilename();
//                    image.transferTo(new File(imagePath));
//                    imagePaths.add(imagePath);
//                }
//            }
//            course.setCourseImage(String.join("\n", imagePaths));
//            courseRepository.save(course);
//        }
//    }

//    public List<GetCourseForm> getCourseAll() throws IOException{
//        List<Course> courses = courseRepository.findAll();
//        List<GetCourseForm> CourseAll = new ArrayList<>();
//
//        if (courses.isEmpty()) {
//            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "찾을 수 없습니다");
//        }
//
//        for (Course c : courses) {
//            Long courseId = c.getCourseId();
//            int planCnt = coursePlanRepository.countByCourseId_CourseId(courseId);
//
//            System.out.println("PlanCnt" + planCnt);
//
//            String imageField = c.getCourseImage();
//            List<String> images = List.of();
//
//            if (imageField != null && !imageField.isEmpty()) {
//                images = LogitUtils.encodeImagesBase64(imageField);
//            }
//            CourseAll.add(new GetCourseForm(c, images, planCnt));
//        }
//        return CourseAll;
//    }
}
