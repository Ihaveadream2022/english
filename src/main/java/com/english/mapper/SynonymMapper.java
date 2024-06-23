package com.english.mapper;

import com.english.entity.Synonym;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.List;

public interface SynonymMapper {

    public List<Synonym> selectLimited(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);

    public Synonym findByMeaning(String meaning);

    public Long insert(Synonym synonym);

    public Long update(Synonym synonym);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);
}