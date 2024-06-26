package com.english.mapper;

import com.english.entity.ItemExample;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import java.util.List;

public interface ItemExampleMapper {

    public List<ItemExample> selectLimited(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);

    public ItemExample findByName(String name);

    public Long insert(ItemExample itemExample);

    public Long update(ItemExample itemExample);

    public Long delete(DeleteRequestBody deleteRequestBody);
}