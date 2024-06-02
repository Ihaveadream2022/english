package com.english.controller;

import com.google.code.kaptcha.Producer;
import com.english.constant.CacheConstant;
import com.english.model.JsonResponse;
import com.english.service.RedisService;
import com.english.util.encode.Base64;
import com.english.util.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class CaptchaController
{
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    RedisService redisService;

    @RequestMapping("/captcha")
    public JsonResponse captcha()
    {
        String uuid = UUID.randomUUID().toString(true);
        String captchaKey = CacheConstant.CAPTCHA_KEY + uuid;
        String captchaString = null;
        String captchaCode = null;
        BufferedImage captchaImage = null;

        String captchaText = captchaProducerMath.createText();
        captchaString = captchaText.substring(0, captchaText.lastIndexOf("@"));
        captchaCode = captchaText.substring(captchaText.lastIndexOf("@") + 1);
        captchaImage = captchaProducerMath.createImage(captchaString);

        redisService.setValue(captchaKey, captchaCode, CacheConstant.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(captchaImage, "jpg", os);
        } catch (IOException e) {
            return JsonResponse.error(e.getMessage());
        }

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("uuid", uuid);
        data.put("image", Base64.encode(os.toByteArray()));

        return JsonResponse.success(data);
    }
}
