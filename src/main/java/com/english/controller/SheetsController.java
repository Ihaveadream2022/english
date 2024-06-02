package com.english.controller;

import com.english.model.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SheetsController
{
    @GetMapping(value = "/sheets")
    public JsonResponse createSheets()
    {
        return JsonResponse.success();
    }
}