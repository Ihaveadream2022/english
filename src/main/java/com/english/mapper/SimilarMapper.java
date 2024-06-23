package com.english.mapper;

import com.english.entity.Similar;
import com.english.entity.Synonym;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.List;

public interface SimilarMapper {

    public List<Similar> selectLimited(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);

    public Similar findByItemIds(String itemIds);

    public Long insert(Similar similar);

    public Long update(Similar similar);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);
}