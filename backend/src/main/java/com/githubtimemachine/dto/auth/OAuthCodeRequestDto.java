package com.githubtimemachine.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class OAuthCodeRequestDto {

    @NotBlank(message = "OAuth authorization code cannot be blank")
    private String code;

    public OAuthCodeRequestDto() {
    }

    public OAuthCodeRequestDto(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
