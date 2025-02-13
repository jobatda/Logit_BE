package logit.logit_backend.service;

import logit.logit_backend.controller.form.GetUserMap;
import logit.logit_backend.controller.form.UpdateUserForm;
import logit.logit_backend.controller.form.UpdateUserMapForm;
import logit.logit_backend.domain.User;
import logit.logit_backend.repository.UserRepository;
import logit.logit_backend.util.LogitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.readAllBytes;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void update(UpdateUserForm form, String imagePath, String userLoginId) {
        User user = userRepository.findByUserLoginId(userLoginId).orElseThrow();

        if (form.getUserAge() != null) {
            user.setUserAge(form.getUserAge());
        }
        if (form.getUserSex() != null) {
            user.setUserSex(form.getUserSex());
        }
        if (form.getUserStatusMsg() != null) {
            user.setUserStatusMsg(form.getUserStatusMsg());
        }
        if (form.getUserTemperature() != null) {
            user.setUserTemperature(form.getUserTemperature());
        }
        if (imagePath != null) {
            user.setUserImagePath(imagePath);
        }

        userRepository.save(user);
    }

    public void updateMap(String userLoginId, UpdateUserMapForm form, String UPLOAD_DIR) throws RuntimeException {
        User user = userRepository.findByUserLoginId(userLoginId).orElseThrow();
        String[] isColorOrImage = form.getBackground().split(",");
        String userMap = user.getUserMap();

        if (isColorOrImage.length == 1) {
            LogitUtils.saveUserMapColor(user, form, isColorOrImage[0]);
        } else if (isColorOrImage.length == 2) {
            LogitUtils.saveUserMapImage(user, form, isColorOrImage[1], UPLOAD_DIR);
        }

        userRepository.save(user);
    }

    public List<GetUserMap> getMap(String userLoginId) throws IOException {
        User user = userRepository.findByUserLoginId(userLoginId).orElseThrow();
        String[] userMapContents = user.getUserMap().split("\n");
        List<GetUserMap> userMapList = new ArrayList<>();

        for (String content : userMapContents) {
            String[] details = content.split(":");
            String region = details[0];
            String background = null;

            if (details[1].substring(0, 2).equals("/a")) {
                byte[] image = readAllBytes(new File(details[1]).toPath());
                background = Base64.getEncoder().encodeToString(image);
            }

            userMapList.add(new GetUserMap(region, background));
        }

        return userMapList;
    }
}
