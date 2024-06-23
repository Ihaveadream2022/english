package com.english.service;

import com.english.entity.Similar;
import com.english.entity.Synonym;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.HashMap;

public interface SimilarService {

    public HashMap<String, Object> pageList(QueryCondition queryCondition);

    public Boolean exist(Similar similar);

    public Similar findByItemIds(String itemIds);

    public Long insert(Similar similar);

    public Long update(Similar similar);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);

    public void writeJSONFile(Similar similar, Integer index);
}