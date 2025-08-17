package com.market.utils;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.market.common.CaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 验证码工具类
 */
@Component
public class CaptchaUtil {

    @Autowired
    private DefaultKaptcha kaptcha;

    public CaptchaResponse generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String text = kaptcha.createText();

        // 设置响应头
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // 生成验证码图片并写入响应输出流
        BufferedImage image = kaptcha.createImage(text);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

        CaptchaResponse captchaResponse = new CaptchaResponse(text, imageBase64);
        return captchaResponse;
    }

    public boolean validateCaptcha(HttpServletRequest request, String inputCaptcha) {
        HttpSession session = request.getSession();
        String captcha = (String) session.getAttribute("captcha");
        return captcha != null && captcha.equalsIgnoreCase(inputCaptcha);
    }

}
