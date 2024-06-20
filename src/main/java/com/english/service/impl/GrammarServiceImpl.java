package com.english.service.impl;

import com.english.entity.Grammar;
import com.english.exception.ServiceRuntimeException;
import com.english.mapper.GrammarMapper;
import com.english.model.GrammarHtml;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import com.english.service.GrammarService;
import com.english.manager.ThreadManager;
import com.english.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

@Service
public class GrammarServiceImpl implements GrammarService {

    private final Logger serviceLogger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    GrammarMapper grammarMapper;

    public HashMap<String, Object> pageList(QueryCondition queryCondition) {
        HashMap<String, Object> data = new HashMap<>();

        List<Grammar> list = grammarMapper.selectLimited(queryCondition);
        Long total = grammarMapper.count(queryCondition);

        data.put("list", list);
        data.put("total", total);
        data.put("pageSize", queryCondition.getPageSize());
        data.put("pageNo", queryCondition.getPageNo());

        return data;
    }

    public Grammar findByName(String name) {
        return grammarMapper.findByName(name);
    }

    @Override
    @Transactional
    public Long insert(Grammar grammar) {
        return grammarMapper.insert(grammar);
    }

    @Override
    @Transactional
    public Long update(Grammar grammar) {
        return grammarMapper.update(grammar);
    }

    @Override
    public Boolean exist(Grammar grammar) {
        long grammarId = grammar.getId() == null? -1L : grammar.getId();
        Grammar one = grammarMapper.findByName(grammar.getName());
        return one != null && one.getId() != grammarId;
    }

    @Override
    @Transactional
    public Long delete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getId() != null) {
            return grammarMapper.delete(deleteRequestBody);
        }

        return 0L;
    }

    @Override
    @Transactional
    public Long batchDelete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getIds() != null && deleteRequestBody.getIds().length > 0) {
            return grammarMapper.batchDelete(deleteRequestBody);
        }

        return 0L;
    }

    public void generate(Grammar grammar, Integer index) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    GrammarHtml grammarHtml = new GrammarHtml();
                    grammarHtml.setName(grammar.getName());
                    grammarHtml.setContent(StringUtil.toHTML(grammar.getContent()));

                    String path = String.format("%s/html/json/grammar-%s.json", System.getProperty("user.dir"), index);
                    File file = new File(path);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(file, grammarHtml);

                    serviceLogger.info(String.format("JSON file [%s] has been created.", path));
                } catch (Exception e) {
                    throw new ServiceRuntimeException(e.getMessage());
                }
            }
        };
        ThreadManager.getInstance().execute(timerTask);
    }
}
