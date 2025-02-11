package logit.logit_backend.service;

import jakarta.transaction.Transactional;
import logit.logit_backend.controller.form.CreateCoursePlanForm;
import logit.logit_backend.controller.form.GetCoursePlanForm;
import logit.logit_backend.controller.form.GetMeetingForm;
import logit.logit_backend.domain.Course;
import logit.logit_backend.domain.CoursePlan;
import logit.logit_backend.repository.CoursePlanRepository;
import logit.logit_backend.repository.CourseRepository;
import logit.logit_backend.util.LogitUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CoursePlanService {
    private final CoursePlanRepository coursePlanRepository;
    private final CourseRepository courseRepository;

    public CoursePlanService(CoursePlanRepository coursePlanRepository, CourseRepository courseRepository) {
        this.coursePlanRepository = coursePlanRepository;
        this.courseRepository = courseRepository;
    }

    public CoursePlan createCoursePlan(CreateCoursePlanForm form, Long courseId){
        CoursePlan coursePlan = new CoursePlan();
        Course course = courseRepository.findByCourseId(courseId).orElseThrow();

        if (form.getPlanCategory() != null &&
            form.getPlanName() != null &&
            form.getPlanAddress() != null &&
            form.getPlanDate() != null){
            coursePlan.setPlanCategory(form.getPlanCategory());
            coursePlan.setPlanName(form.getPlanName());
            coursePlan.setPlanAddress(form.getPlanAddress());
            coursePlan.setPlanDate(form.getPlanDate());
        }
        coursePlan.setCourseId(course);
        coursePlanRepository.save(coursePlan);
        return coursePlan;
    }

    public void updateImages(CoursePlan coursePlan, List<MultipartFile> coursePlanImages, String UPLOAD_DIR) throws IOException {
        String imagePath;
        List<String> imagePaths = new ArrayList<>();
        Long planId = coursePlan.getPlanId();

        // image 에 대한 모든 경로를 String 으로 저장
        // 구분자는 "\n"
        if(coursePlanImages != null && !coursePlanImages.isEmpty()){
            for (MultipartFile image : coursePlanImages) {
                if (image != null && !image.isEmpty()){
                    imagePath = UPLOAD_DIR + planId + "_" + image.getOriginalFilename();
                    image.transferTo(new File(imagePath));
                    imagePaths.add(imagePath);
                }
            }

            coursePlan.setPlanImage(String.join("\n", imagePaths));
            coursePlanRepository.save(coursePlan);
        }
    }

    public List<GetCoursePlanForm> searchCoursePlanByCourseId(Long courseId) throws IOException{
        List<CoursePlan> coursePlans = coursePlanRepository.findByCourseId(courseId);
        List<GetCoursePlanForm> allCoursePlans = new ArrayList<>();

        if (coursePlans.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "찾을 수 없습니다");
        }
        for (CoursePlan c : coursePlans) {
            String imageField = c.getPlanImage();
            List<String> images = List.of();

            if (imageField != null && !imageField.isEmpty()) {
                images = LogitUtils.encodeImagesBase64(imageField);
            }
            allCoursePlans.add(new GetCoursePlanForm(c, images));
        }

        return allCoursePlans;
    }
}

