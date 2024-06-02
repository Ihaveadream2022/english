package com.english.service;

import com.english.entity.Item;
import com.english.entity.ItemTts;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.HashMap;

public interface ItemTtsService
{
    public HashMap<String, Object> pageList(QueryCondition queryCondition);

    public Boolean exist(ItemTts item);

    public ItemTts findByName(String name);

    public Long insert(ItemTts item);

    public Long update(ItemTts item);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);

    public void dealSpeech(ItemTts itemTts);

    public void writeSpeech(ItemTts itemTts);
}