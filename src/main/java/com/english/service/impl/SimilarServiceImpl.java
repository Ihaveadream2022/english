package com.english.service.impl;

import com.english.entity.Similar;
import com.english.entity.Synonym;
import com.english.mapper.SimilarMapper;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import com.english.service.SimilarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class SimilarServiceImpl implements SimilarService {

    private final Logger serviceLogger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    SimilarMapper similarMapper;

    public HashMap<String, Object> pageList(QueryCondition queryCondition) {
        HashMap<String, Object> data = new HashMap<>();

        List<Similar> list = similarMapper.selectLimited(queryCondition);
        Long total = similarMapper.count(queryCondition);

        data.put("list", list);
        data.put("total", total);
        data.put("pageSize", queryCondition.getPageSize());
        data.put("pageNo", queryCondition.getPageNo());

        return data;
    }

    @Override
    public Similar findByItemIds(String itemIds) {
        return similarMapper.findByItemIds(itemIds);
    }

    @Override
    public Boolean exist(Similar similar) {
        long similarId = similar.getId() == null? -1L : similar.getId();
        Similar one = similarMapper.findByItemIds(similar.getItemIds());
        return one != null && one.getId() != similarId;
    }

    @Override
    @Transactional
    public Long insert(Similar similar) {
        return similarMapper.insert(similar);
    }

    @Override
    @Transactional
    public Long update(Similar similar) {
        return similarMapper.update(similar);
    }

    @Override
    @Transactional
    public Long delete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getId() != null) {
            return similarMapper.delete(deleteRequestBody);
        }

        return 0L;
    }

    @Override
    @Transactional
    public Long batchDelete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getIds() != null && deleteRequestBody.getIds().length > 0) {
            return similarMapper.batchDelete(deleteRequestBody);
        }

        return 0L;
    }

    public void writeJSONFile(Similar similar, Integer index) {
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    GrammarHtml grammarHtml = new GrammarHtml();
//                    grammarHtml.setName(grammar.getName());
//                    grammarHtml.setContent(StringUtil.toHTML(grammar.getContent()));
//
//                    String path = String.format("%s/html/json/grammar-%s.json", System.getProperty("user.dir"), index);
//                    File file = new File(path);
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    objectMapper.writeValue(file, grammarHtml);
//
//                    serviceLogger.info(String.format("JSON file [%s] has been created.", path));
//                } catch (Exception e) {
//                    throw new ServiceRuntimeException(e.getMessage());
//                }
//            }
//        };
//        ThreadManager.getInstance().execute(timerTask);
    }
}
