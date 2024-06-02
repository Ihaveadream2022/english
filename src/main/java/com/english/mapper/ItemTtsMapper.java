package com.english.mapper;

import com.english.entity.ItemTts;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.List;

public interface ItemTtsMapper
{
    public List<ItemTts> selectLimited(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);

    public ItemTts findByName(String name);

    public Long insert(ItemTts item);

    public Long update(ItemTts item);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);
}