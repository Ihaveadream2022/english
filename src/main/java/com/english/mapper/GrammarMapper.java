package com.english.mapper;

import com.english.entity.Grammar;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.List;

public interface GrammarMapper {

    public List<Grammar> selectLimited(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);

    public Grammar findByName(String name);

    public Long insert(Grammar grammar);

    public Long update(Grammar grammar);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);
}