package com.example.library.dto.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenRequestDto {
    @JsonProperty("refresh_token")
    String refreshToken;
}
