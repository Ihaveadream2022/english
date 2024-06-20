package com.english.mapper;

import com.english.entity.ItemExample;
import com.english.model.request.QueryCondition;
import java.util.List;

public interface ItemExampleMapper {

    public Long batchDelete(String name);

    public Long batchInsert(List<ItemExample> itemExampleList);

    public List<ItemExample> selectLimited(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);
}