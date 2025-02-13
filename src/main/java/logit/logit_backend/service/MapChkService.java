package logit.logit_backend.service;

import jakarta.transaction.Transactional;
import logit.logit_backend.controller.form.CreateMapChkForm;
import logit.logit_backend.domain.MapChk;
import logit.logit_backend.domain.User;
import logit.logit_backend.repository.MapChkRepository;
import logit.logit_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
@Transactional
public class MapChkService {
    private final MapChkRepository mapChkRepository;
//    private final UserRepository userRepository;

    @Autowired
    public MapChkService(MapChkRepository mapChkRepository) {
        this.mapChkRepository = mapChkRepository;
//        this.userRepository = userRepository;
    }

    public MapChk create(CreateMapChkForm form , MultipartFile mapChkImage) {
        MapChk mapChk = new MapChk();
//        User user = userRepository.findByUserLoginId(loginId).orElseThrow();

        if (form.getMapChkArea() != null){
            mapChk.setMapChkArea(form.getMapChkArea());
        }

        if (mapChkImage == null && mapChkImage.isEmpty() && form.getMapChkInsert() != null) {
            mapChk.setMapChkInsert(form.getMapChkInsert());
        }else{
            mapChk.setMapChkInsert(mapChkImage.getOriginalFilename());
        }
//        mapChk.getUser(user);
        mapChkRepository.save(mapChk);
        return mapChk;
    }

    public void updateImage(MultipartFile mapChkImage, String UPLOAD_DIR) throws IOException {
        String imagePath;

        if (mapChkImage != null && !mapChkImage.isEmpty()) {
            imagePath = UPLOAD_DIR + "data:image/png;base64," + mapChkImage.getOriginalFilename();
            mapChkImage.transferTo(new File(imagePath));

        }
    }
}
