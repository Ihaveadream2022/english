package com.english.controller;

import com.english.model.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/placeholder")
public class PlaceholderController
{
    @GetMapping
    public JsonResponse get(@RequestParam String name)
    {
        System.out.println(name);

        return JsonResponse.success();
    }
}
