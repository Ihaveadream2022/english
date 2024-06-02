package com.english.controller;

import com.english.entity.Word;
import com.english.model.JsonResponse;
import com.english.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/words")
public class WordController
{
    @Autowired
    WordService wordService;

    @PostMapping()
    public JsonResponse insert(@Validated @RequestBody Word word)
    {
        if (word.getWord() != null && word.getWord().trim().length() != 0 && wordService.wordExist(word)) {
            return JsonResponse.error("单词已存在");
        }

        Long rows = wordService.insert(word);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }
}
