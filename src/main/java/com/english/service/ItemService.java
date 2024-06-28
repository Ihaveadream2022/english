package com.english.service;

import com.english.entity.Item;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.HashMap;
import java.util.List;

public interface ItemService {
    public HashMap<String, Object> pageList(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);

    public Boolean exist(Item item);

    public Item findByName(String name);

    public Long insert(Item item);

    public Long update(Item item);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);

    public void writeJSONFile(List<Item> itemList, Integer index);
}