package com.english.service.impl;

import com.english.entity.Item;
import com.english.entity.ItemExample;
import com.english.manager.ThreadManager;
import com.english.mapper.ItemExampleMapper;
import com.english.model.ItemExampleHtml;
import com.english.model.request.QueryCondition;
import com.english.service.ItemExampleService;
import com.english.util.StringUtil;
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
    @Transactional
    public void update(String name, List<ItemExample> itemExampleList) {
        if (check(name, itemExampleList)) {
            itemExampleMapper.batchDelete(name);
            if (itemExampleList.size() > 0) {
                itemExampleMapper.batchInsert(itemExampleList);
            }
        }
    }

    public boolean check(String name, List<ItemExample> itemExampleList) {
        if (itemExampleList.size() > 0) {
            for (ItemExample itemExample : itemExampleList) {
                if (!itemExample.getName().equals(name)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void writeJSONFile(Item item, Integer index) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    ItemExampleHtml itemExampleHtml = new ItemExampleHtml();
                    itemExampleHtml.setName(item.getName());
                    itemExampleHtml.setMeanings(splitMeaning(item));
                    List<ItemExample> itemExampleList = item.getExamples().stream().map(v->{
                        v.setExample(StringUtil.toHTML(v.getExample()));
                        return v;
                    }).collect(Collectors.toList());
                    itemExampleHtml.setExamples(itemExampleList);
                    String filePath = String.format("%s/html/json/item-example-%s.json", System.getProperty("user.dir"), index);
                    File file = new File(filePath);
                    ObjectMapper objectMapper = new ObjectMapper();
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

