package logit.logit_backend.controller;

import logit.logit_backend.controller.form.CreateMeetingForm;
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
    public ResponseEntity<String> createMeeting(
            @ModelAttribute CreateMeetingForm form,
            @RequestPart(value = "meetingImage", required = false) List<MultipartFile> meetingImages) {
        try {

            Meeting meeting = meetingService.create(form);
            meetingService.updateImages(meeting, meetingImages, UPLOAD_DIR);

            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created meeting");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
