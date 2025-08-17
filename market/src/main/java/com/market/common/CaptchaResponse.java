package com.market.common;


import lombok.Data;

@Data
public class CaptchaResponse {
    private String captcha;
    private String imageBase64;

    public CaptchaResponse(String text, String imageBase64) {
        this.captcha = text;
        this.imageBase64 = imageBase64;
    }
}
