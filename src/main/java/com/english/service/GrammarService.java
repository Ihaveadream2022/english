package com.english.service;

import com.english.entity.Grammar;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import java.util.HashMap;
import java.util.List;

public interface GrammarService {

    public HashMap<String, Object> pageList(QueryCondition queryCondition);

    public Boolean exist(Grammar grammar);

    public Grammar findByName(String name);

    public Long insert(Grammar grammar);

    public Long update(Grammar grammar);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);

    public void generate(Grammar grammar, Integer index);
}