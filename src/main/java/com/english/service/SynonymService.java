package com.english.service;

import com.english.entity.Synonym;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.HashMap;

public interface SynonymService {

    public HashMap<String, Object> pageList(QueryCondition queryCondition);

    public Boolean exist(Synonym synonym);

    public Synonym findByMeaning(String meaning);

    public Long insert(Synonym synonym);

    public Long update(Synonym synonym);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);

    public void writeJSONFile(Synonym synonym, Integer index);
}