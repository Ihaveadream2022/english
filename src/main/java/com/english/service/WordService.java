package com.english.service;

import com.english.entity.Word;
import com.english.model.request.QueryCondition;

import java.util.HashMap;

public interface WordService
{
    public HashMap<String, Object> pageList(QueryCondition queryCondition);

    public Boolean wordExist(Word word);

    public Word findByWord(String word);

    public Long insert(Word word);
}