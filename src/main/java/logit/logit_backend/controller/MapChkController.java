package logit.logit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import logit.logit_backend.controller.form.CreateMapChkForm;
import logit.logit_backend.controller.form.CreateMeetingForm;
import logit.logit_backend.domain.MapChk;
import logit.logit_backend.service.MapChkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/mapchk")
@Tag(name = "MapChk", description = "지도 API")
public class MapChkController {

    private final MapChkService mapChkService;
    private final String UPLOAD_DIR = "/app/uploads/image/mapChk/";

    @Autowired
    public MapChkController(MapChkService mapChkService) {this.mapChkService = mapChkService;}

    @Operation(summary = "Create MapChk", description = "지도 이미지 or 색상 넣기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"mapChkId\": \"1\" }")
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createMapChk(
            @ModelAttribute CreateMapChkForm form,
            //@RequestPart Map<String, Object> formData,
            @RequestPart(value = "mapChkImage", required = false) MultipartFile mapChkImage) {
        try{

            MapChk mapChk = mapChkService.create(form , mapChkImage);

            if (mapChkImage != null && !mapChkImage.isEmpty() ) { // 이미지인 경우
                mapChkService.updateImage(mapChkImage,UPLOAD_DIR);
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("mapChkId", mapChk.getMapChkId()));
        }catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error: ", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error: ", e.getMessage()));
        }
    }
}
