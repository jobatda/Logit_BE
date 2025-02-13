package logit.logit_backend.util;

import logit.logit_backend.controller.form.UpdateUserMapForm;
import logit.logit_backend.domain.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static java.nio.file.Files.readAllBytes;

public class LogitUtils {

    public static List<String> encodeImagesBase64(String imageField) throws IOException {
        List<String> images = new ArrayList<>();
        String[] imagePaths;
        String[] result;
        String Base64EncodedImage;

        imagePaths = imageField.split("\n");
        for (String imagePath : imagePaths) {
            byte[] image = readAllBytes(new File(imagePath).toPath());
            Base64EncodedImage = Base64.getEncoder().encodeToString(image);
            result = Base64EncodedImage.split(",");
            images.add(result.length == 1 ? result[0] : result[1]);
        }

        return images;
    }

    public static void saveUserMapColor(User user, UpdateUserMapForm form, String mapColor) {
        String userMap = user.getUserMap();

        if (userMap == null) {
            user.setUserMap(
                    form.getRegion() + ":" + mapColor);
        } else {
            user.setUserMap(
                    userMap + "\n" + form.getRegion() + ":" + mapColor);
        }
    }

    public static void saveUserMapImage(User user,
                                        UpdateUserMapForm form,
                                        String base64File,
                                        String UPLOAD_DIR) {
        String filename = user.getUserLoginId() + "_" + form.getRegion() + ".png";
        byte[] imageBytes = Base64.getDecoder().decode(base64File);
        File file = new File(UPLOAD_DIR + filename);
        String userMap = user.getUserMap();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (userMap == null) {
            user.setUserMap(filename);
        } else {
            user.setUserMap(
                    userMap + "\n" + form.getRegion() + ":" + UPLOAD_DIR + filename
            );
        }
    }
}
