package com.robotnec.chords.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FacebookCheckTokenResponseDto {
    private Data data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Data {

        @JsonProperty("app_id")
        private String appId;
        private String application;
        private FacebookErrorDto error;

        @JsonProperty("expires_at")
        private long expiresAt;

        @JsonProperty("is_valid")
        private boolean isValid;

        @JsonProperty("issued_at")
        private long issued_at;
        private Object metadata;

        @JsonProperty("profile_id")
        private String profileId;
        private List<String> scopes;

        @JsonProperty("user_id")
        private String userId;
    }
}
