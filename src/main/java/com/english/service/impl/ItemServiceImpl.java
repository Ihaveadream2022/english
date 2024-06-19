package com.english.service.impl;

import com.english.entity.Item;
import com.english.entity.ItemTts;
import com.english.entity.User;
import com.english.mapper.ItemMapper;
import com.english.model.ItemHtml;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import com.english.service.ItemService;
import com.english.manager.ThreadManager;
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
public class ItemServiceImpl implements ItemService {

    private final Logger serviceLogger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    ItemMapper itemMapper;

    public HashMap<String, Object> pageList(QueryCondition queryCondition) {
        Integer a = queryCondition.getOffset();
        HashMap<String, Object> data = new HashMap<>();

        List<Item> list = itemMapper.selectLimited(queryCondition);
        Long total = itemMapper.count(queryCondition);

        data.put("list", list);
        data.put("total", total);
        data.put("pageSize", queryCondition.getPageSize());
        data.put("pageNo", queryCondition.getPageNo());

        return data;
    }

    public Item findByName(String name) {
        return itemMapper.findByName(name);
    }

    @Override
    @Transactional
    public Long insert(Item item) {
        return itemMapper.insert(item);
    }

    @Override
    @Transactional
    public Long update(Item item) {
        return itemMapper.update(item);
    }

    @Override
    public Boolean exist(Item item) {
        long wordId = item.getId() == null? -1L : item.getId();
        Item one = itemMapper.findByName(item.getName());
        return one != null && one.getId() != wordId;
    }

    @Override
    @Transactional
    public Long delete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getId() != null) {
            return itemMapper.delete(deleteRequestBody);
        }

        return 0L;
    }

    @Override
    @Transactional
    public Long batchDelete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getIds() != null && deleteRequestBody.getIds().length > 0) {
            return itemMapper.batchDelete(deleteRequestBody);
        }

        return 0L;
    }

    @Override
    public void generateJSONFile(List<Item> itemList, Integer index) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    List<ItemHtml> list = new ArrayList<>();
                    for (Item item : itemList) {
                        ItemHtml itemHtml = new ItemHtml();
                        itemHtml.setEn(item.getName());
                        itemHtml.setCn(item.getCommon());
                        if (item.getTts().getId() != null) {
                            itemHtml.setTts(item.getTts().getSpeech());
                        }
                        list.add(itemHtml);
                    }
                    String filePath = String.format("%s/html/json/%s.json", System.getProperty("user.dir"), index);
                    File file = new File(filePath);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(file, list);

                    serviceLogger.info(String.format("JSON file [%s] has been created.", filePath));
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        };
        ThreadManager.getInstance().execute(timerTask);
    }
}
