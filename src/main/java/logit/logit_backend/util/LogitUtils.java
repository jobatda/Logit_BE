package logit.logit_backend.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static java.nio.file.Files.readAllBytes;

public class LogitUtils {

    public static List<String> encodeImagesBase64(String imageField) throws IOException {
        List<String> images = new ArrayList<>();
        String[] imagePaths;
        String Base64EncodedImage;

        imagePaths = imageField.split("\n");
        for (String imagePath : imagePaths) {
            byte[] image = readAllBytes(new File(imagePath).toPath());
            Base64EncodedImage = Base64.getEncoder().encodeToString(image);
            images.add(Base64EncodedImage);
        }

        return images;
    }
}
