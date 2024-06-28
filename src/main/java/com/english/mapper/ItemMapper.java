package com.english.mapper;

import com.english.entity.Item;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.List;

public interface ItemMapper {

    public List<Item> selectLimited(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);

    public Item findByName(String name);

    public Long insert(Item item);

    public Long update(Item item);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);
}