package com.english.controller;

import com.english.manager.GitManager;
import com.english.model.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/git")
public class GitController {
    @GetMapping("/push")
    public JsonResponse pushToRemote() {

        GitManager.getInstance().push();

        return JsonResponse.success();
    }
}
