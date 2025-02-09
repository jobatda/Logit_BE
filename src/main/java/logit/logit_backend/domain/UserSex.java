package logit.logit_backend.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserSex {
    @JsonProperty("male") MALE,
    @JsonProperty("female") FEMALE
}
