package logit.logit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import logit.logit_backend.controller.form.CreateUserMeetingForm;
import logit.logit_backend.controller.form.GetMeetingDetailForm;
import logit.logit_backend.service.UserMeetingService;
import logit.logit_backend.domain.UserMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user-meeting")
@Tag(name = "UserMeeting", description = "번개신청 API")
public class UserMeetingController {
    private final UserMeetingService userMeetingService;

    @Autowired
    public UserMeetingController(UserMeetingService userMeetingService) {
        this.userMeetingService = userMeetingService;
    }

    @Operation(summary = "Submit meeting", description = "여행 번개 게시물1 - 신청하기 버튼")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"userMeetingId\": \"user123\" }")
                    )),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
    }) // Swagger 문서 작성
    @PostMapping ("/submit")
    public ResponseEntity<Map<String, Object>> createUserMeeting(
            @RequestBody CreateUserMeetingForm createUserMeetingForm) {
        try {

            UserMeeting userMeeting = userMeetingService.create(createUserMeetingForm);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("userMeetingId", userMeeting.getUserMeetingId()));

        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Get meeting members", description = "여행 번개 게시물2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetMeetingDetailForm.class)
                    )),
            @ApiResponse(responseCode = "404", description = "번개를 찾을수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"message\" }")
                    )),
    }) // Swagger 문서 작성
    @GetMapping(value = "/{meetingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMembers(@PathVariable Long meetingId) {
        try {

            GetMeetingDetailForm form = userMeetingService.getMembersByMeetingId(meetingId);
            return ResponseEntity.ok(form);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
