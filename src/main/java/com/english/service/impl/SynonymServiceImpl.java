package com.english.service.impl;

import com.english.entity.ItemTts;
import com.english.entity.Synonym;
import com.english.exception.GlobalExceptionHandler;
import com.english.manager.ThreadManager;
import com.english.mapper.SynonymMapper;
import com.english.model.ItemHtml;
import com.english.model.SynonymHtml;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import com.english.service.SynonymService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

@Service
public class SynonymServiceImpl implements SynonymService {

    private final Logger frameworkLogger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final Logger serviceLogger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    SynonymMapper synonymMapper;

    @Autowired
    ItemTtsServiceImpl itemTtsService;

    public HashMap<String, Object> pageList(QueryCondition queryCondition) {
        HashMap<String, Object> data = new HashMap<>();

        List<Synonym> list = synonymMapper.selectLimited(queryCondition);
        Long total = synonymMapper.count(queryCondition);

        data.put("list", list);
        data.put("total", total);
        data.put("pageSize", queryCondition.getPageSize());
        data.put("pageNo", queryCondition.getPageNo());

        return data;
    }

    @Override
    public Synonym findByMeaning(String meaning) {
        return synonymMapper.findByMeaning(meaning);
    }

    @Override
    @Transactional
    public Long insert(Synonym synonym) {
        return synonymMapper.insert(synonym);
    }

    @Override
    @Transactional
    public Long update(Synonym synonym) {
        return synonymMapper.update(synonym);
    }

    @Override
    public Boolean exist(Synonym synonym) {
        long synonymId = synonym.getId() == null? -1L : synonym.getId();
        Synonym one = synonymMapper.findByMeaning(synonym.getMeaning());
        return one != null && one.getId() != synonymId;
    }

    @Override
    @Transactional
    public Long delete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getId() != null) {
            return synonymMapper.delete(deleteRequestBody);
        }

        return 0L;
    }

    @Override
    @Transactional
    public Long batchDelete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getIds() != null && deleteRequestBody.getIds().length > 0) {
            return synonymMapper.batchDelete(deleteRequestBody);
        }

        return 0L;
    }

    public void writeJSONFile(Synonym synonym, Integer index) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String filePath = String.format("%s/html/json/synonym-%s.json", System.getProperty("user.dir"), index);
                    File file = new File(filePath);
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<ItemHtml> list = objectMapper.readValue(synonym.getItems(), new TypeReference<List<ItemHtml>>() {});
                    list.forEach(v->{
                        ItemTts itemTts = itemTtsService.findByName(v.getEn());
                        if (itemTts != null && itemTts.getAudio() != null) {
                            v.setTts(itemTts.getAudio());
                        }
                    });
                    SynonymHtml synonymHtml = new SynonymHtml();
                    synonymHtml.setMeaning(synonym.getMeaning());
                    synonymHtml.setItemHtmlList(list);
                    objectMapper.writeValue(file, synonymHtml);
                    serviceLogger.info(String.format("JSON file [%s] has been created.", filePath));
                } catch (Exception e) {
                    frameworkLogger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        };
        ThreadManager.getInstance().execute(timerTask);
    }
}
