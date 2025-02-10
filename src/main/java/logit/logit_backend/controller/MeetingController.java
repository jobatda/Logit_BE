package logit.logit_backend.controller;

import logit.logit_backend.controller.form.CreateMeetingForm;
import logit.logit_backend.controller.form.GetMeetingForm;
import logit.logit_backend.service.MeetingService;
import logit.logit_backend.domain.Meeting;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;
    private final String UPLOAD_DIR = "/app/uploads/image/meeting/";

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createMeeting(
            @ModelAttribute CreateMeetingForm form,
            @RequestPart(value = "meetingImage", required = false) List<MultipartFile> meetingImages) {
        try {

            Meeting meeting = meetingService.create(form);
            meetingService.updateImages(meeting, meetingImages, UPLOAD_DIR);


            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("meetingId", meeting.getMeetingId()));
        } catch (HttpClientErrorException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMeetingHome() {
        try {
            List<GetMeetingForm> allMeetings = meetingService.getAllMeetings();

            return ResponseEntity.ok(allMeetings);
        } catch (HttpClientErrorException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping(params = "meetingId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchMeetingById(@RequestParam Long meetingId) {
        try {
            GetMeetingForm meeting = meetingService.getMeetingById(meetingId);

            return ResponseEntity.ok(meeting);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping(params = "title", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchMeetingsByTitle(@RequestParam String title) {
        try {
            List<GetMeetingForm> meetings = meetingService.getMeetingsByTitle(title);

            return ResponseEntity.ok(meetings);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
