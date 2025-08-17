package com.market.controller;

import com.market.common.CaptchaResponse;
import com.market.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CaptchaController {

    @Autowired
    private CaptchaUtil captchaUtil;

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/captcha")
    public CaptchaResponse captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaResponse captchaResponse = captchaUtil.generateCaptcha(request, response);
        return captchaResponse;
    }


}
