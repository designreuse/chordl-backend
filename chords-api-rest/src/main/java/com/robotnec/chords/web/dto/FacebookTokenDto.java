package com.robotnec.chords.web.dto;

import lombok.*;

/**
 * @author zak <zak@robotnec.com>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FacebookTokenDto {
    private String name;
    private String id;
    private String accessToken;
    private String userID;
    private String expiresIn;
    private String signedRequest;
}
