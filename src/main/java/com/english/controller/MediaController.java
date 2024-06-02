package com.english.controller;

import com.english.model.JsonResponse;
import com.english.service.impl.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@RestController
@RequestMapping("/medias")
public class MediaController
{
    @Autowired
    MediaService collectMediaService;

    @GetMapping()
    public JsonResponse one(@RequestParam(required = false, defaultValue = "") String url)
    {
        Object response = collectMediaService.httpGet(url);

        return JsonResponse.success(response);
    }
}
