package logit.logit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import logit.logit_backend.controller.form.CreateUserMeetingForm;
import logit.logit_backend.controller.form.GetUserMeetingForm;
import logit.logit_backend.service.UserMeetingService;
import logit.logit_backend.domain.UserMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user-meeting")
@Tag(name = "UserMeeting", description = "UserMeeting API")
public class UserMeetingController {
    private final UserMeetingService userMeetingService;

    @Autowired
    public UserMeetingController(UserMeetingService userMeetingService) {
        this.userMeetingService = userMeetingService;
    }

    @Operation(summary = "Submit meeting", description = "번개 참여를 신청한다")
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

    @Operation(summary = "Get meeting members", description = "특정 번개에 참여한 유저정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetUserMeetingForm.class)
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

            GetUserMeetingForm form = userMeetingService.getMembersByMeetingId(meetingId);
            return ResponseEntity.ok(form);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
