package com.english.mapper;

import com.english.entity.Word;
import com.english.model.request.QueryCondition;

import java.util.List;

public interface WordMapper
{
    public List<Word> selectLimited(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);

    public Word findByWord(String word);

    public Long insert(Word word);
}