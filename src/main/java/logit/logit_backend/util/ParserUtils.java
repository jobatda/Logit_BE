package logit.logit_backend.util;

import logit.logit_backend.controller.form.CourseDayForm;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ParserUtils {

    private final static RestTemplate restTemplate = new RestTemplate();

    public static String parseDay(List<CourseDayForm> forms) {
        String day = "";

        for (CourseDayForm form : forms) {
            day += "category:" + form.getCategory();
            day += ",name:" + form.getPlaceName();
            day += ",address:" + form.getAddress();
            if (form.getImgUrl() != null && !form.getImgUrl().isEmpty()) {
                downloadImage(form.getImgUrl(),
                        "/app/uploads/image/course",
                        "id111_" + form.getAddress());
                day += ",imageUrl:" + "/app/uploads/image/course/id111_" + form.getAddress();
            } else {
                day += ",imageUrl:" + "/app/uploads/image/course/test.png";
            }
            day += "\n";
        }

        return day;
    }

    public static void downloadImage(String imageUrl, String saveDir, String fileName) {
        try {
            // URL에서 파일명 추출
            Path savePath = Paths.get(saveDir, fileName);

            // 이미지 다운로드
            byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
            if (imageBytes != null) {
                Files.write(savePath, imageBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
