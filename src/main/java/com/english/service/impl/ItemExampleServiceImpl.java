package com.english.service.impl;

import com.english.entity.ItemExample;
import com.english.exception.ServiceRuntimeException;
import com.english.mapper.ItemExampleMapper;
import com.english.model.request.QueryCondition;
import com.english.service.ItemExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ItemExampleServiceImpl implements ItemExampleService {

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
}

