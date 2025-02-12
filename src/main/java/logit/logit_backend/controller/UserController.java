package logit.logit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "유저 API")
public class UserController {

    private final UserService userService;
    private final String UPLOAD_DIR = "/app/uploads/image/user/";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Update user profile", description = "프로필_마이페이지 - 프로필 편집")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"userLoginId\": \"user123\" }")
            )),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"error\": \"message\" }")
            )),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"error\": \"message\" }")
            )),
    }) // Swagger 문서 작성
    @PatchMapping(value = "/{userLoginId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable String userLoginId,
            @RequestPart(value = "userImg", required = false) MultipartFile userImg,
            @ModelAttribute UpdateUserForm form) {
        try {
            String imgPath = null;

            if (userImg != null && !userImg.isEmpty()) {
                imgPath = UPLOAD_DIR + userLoginId + "_" + userImg.getOriginalFilename();
                userImg.transferTo(new File(imgPath));
            }
            userService.update(form, imgPath, userLoginId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("userLoginId", userLoginId));
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}