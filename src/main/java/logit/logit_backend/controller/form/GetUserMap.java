package logit.logit_backend.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetUserMap {
    private String region;
    private String background;

    public GetUserMap(String region, String background) {
        this.region = region;
        this.background = background;
    }
}
