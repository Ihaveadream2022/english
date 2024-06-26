package com.english.service.impl;

import com.english.entity.Item;
import com.english.entity.ItemExample;
import com.english.exception.ServiceRuntimeException;
import com.english.manager.ThreadManager;
import com.english.mapper.ItemExampleMapper;
import com.english.model.ItemExampleHtml;
import com.english.model.KeyValue;
import com.english.model.request.QueryCondition;
import com.english.service.ItemExampleService;
import com.english.util.StringUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemExampleServiceImpl implements ItemExampleService {

    private final Logger logger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    ItemExampleMapper itemExampleMapper;

    public HashMap<String, Object> pageList(QueryCondition queryCondition) {
        HashMap<String, Object> data = new HashMap<>();
        List<ItemExample> list = itemExampleMapper.selectLimited(queryCondition);
        Long total = itemExampleMapper.count(queryCondition);
        data.put("list", list);
        data.put("total", total);
        data.put("pageSize", queryCondition.getPageSize());
        data.put("pageNo", queryCondition.getPageNo());

        return data;
    }

    @Override
    public ItemExample findByName(String name) {
        return itemExampleMapper.findByName(name);
    }

    @Override
    @Transactional
    public Long insert(ItemExample itemExample) {
        boolean isExist = exist(itemExample);
        if (isExist) {
            throw new ServiceRuntimeException("The item example has been existed.");
        }
        return itemExampleMapper.insert(itemExample);
    }

    @Override
    @Transactional
    public Long update(ItemExample itemExample) {
        boolean isExist = exist(itemExample);
        if (isExist) {
            throw new ServiceRuntimeException("The item example has been existed.");
        }
        return itemExampleMapper.update(itemExample);
    }

    public Boolean exist(ItemExample itemExample) {
        long id = itemExample.getId() == null? -1L : itemExample.getId();
        ItemExample one = itemExampleMapper.findByName(itemExample.getName());
        return one != null && one.getId() != id;
    }

    public void writeJSONFile(ItemExample itemExample, Integer index) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<KeyValue> keyValueList = objectMapper.readValue(itemExample.getExamples(), new TypeReference<List<KeyValue>>() {});
                    List<KeyValue> keyValueListFilter = keyValueList.stream().map(v->{
                        v.setValue(StringUtil.toHTML(v.getValue()));
                        return v;
                    }).collect(Collectors.toList());
                    ItemExampleHtml itemExampleHtml = new ItemExampleHtml();
                    itemExampleHtml.setName(itemExample.getItem().getName());
                    itemExampleHtml.setMeanings(splitMeaning(itemExample.getItem()));
                    itemExampleHtml.setExamples(keyValueListFilter);

                    String filePath = String.format("%s/html/json/item-example-%s.json", System.getProperty("user.dir"), index);
                    File file = new File(filePath);
                    objectMapper.writeValue(file, itemExampleHtml);
                    logger.info(String.format("JSON file [%s] has been created.", filePath));
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        };
        ThreadManager.getInstance().execute(timerTask);
    }

    public List<String[]> splitMeaning(Item item) {
        List<String[]> result = new ArrayList<>();
        List<String> pending = new ArrayList<>();
        if (item.getNoun() != null) pending.add(item.getNoun().replace("n.","n.;"));
        if (item.getVerb() != null) pending.add(item.getVerb().replace("v.","v.;"));
        if (item.getAdjective() != null) pending.add(item.getAdjective().replace("adj.","adj.;"));
        if (item.getAdverb() != null) pending.add(item.getAdverb().replace("adv.","adv.;"));
        if (item.getConjunction() != null) pending.add(item.getConjunction().replace("conj.","conj.;"));
        if (item.getPreposition() != null) pending.add(item.getPreposition().replace("prep.","prep.;"));
        if (item.getPronoun() != null) pending.add(item.getPronoun().replace("pron.","pron.;"));
        for (String str: pending) {
            result.add(str.split(";"));
        }
        return result;
    }
}

