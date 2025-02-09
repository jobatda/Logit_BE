package logit.logit_backend.controller;

import logit.logit_backend.controller.form.UpdateUserForm;
import logit.logit_backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final String UPLOAD_DIR = "/app/uploads/image/user/";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping(value = "/{loginId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUser(
            @PathVariable String loginId,
            @RequestPart(value = "userImg", required = false) MultipartFile userImg,
            @ModelAttribute UpdateUserForm form) {
        try {
            String imgPath = null;

            if (userImg != null && !userImg.isEmpty()) {
                imgPath = UPLOAD_DIR + loginId + "_" + userImg.getOriginalFilename();
                userImg.transferTo(new File(imgPath));
            }
            userService.update(form, imgPath, loginId);

            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated user");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}