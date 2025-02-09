package logit.logit_backend.service;

import logit.logit_backend.controller.form.UpdateUserForm;
import logit.logit_backend.domain.User;
import logit.logit_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
