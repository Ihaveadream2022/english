package com.english.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.english.exception.ServiceRuntimeException;
import com.english.model.MediaResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MediaService
{
    public Object httpGet(String videoUrl)
    {
        // https%3A%2F%2Fwww.douyin.com%2Fvideo%2F7358390394943491340
        String url = "https://tenapi.cn/v2/video?url=" + videoUrl;

        // 1. 创建Okhttp对象. 用于发起请求
        OkHttpClient client = new OkHttpClient();

        // 2. 创建请求实例
        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("User-Agent" , "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        Request request = builder.build();

        // 3. 发起请求
        MediaResponse mediaResponse = null;
        try {
            Response response = client.newCall(request).execute();
            String responseJson = response.body().string();
            if (responseJson == null) {
                throw new ServiceRuntimeException("请求结果为空");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            mediaResponse = objectMapper.readValue(responseJson, MediaResponse.class);
            if (mediaResponse.getData() == null) {
                throw new ServiceRuntimeException("请求结果为空");
            }
        } catch (IOException e) {
            throw new ServiceRuntimeException("请求失败");
        }

        return mediaResponse.getData();
    }
}
