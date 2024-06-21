package com.english.controller;

import com.english.entity.Item;
import com.english.entity.ItemExample;
import com.english.model.JsonResponse;
import com.english.model.request.ItemQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.model.request.UpdateItemExampleBody;
import com.english.service.impl.ItemExampleServiceImpl;
import com.english.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item-examples")
public class ItemExampleController {

    @Autowired
    ItemServiceImpl itemService;
    
    @Autowired
    ItemExampleServiceImpl itemExampleService;

    @PostMapping
    public JsonResponse update(@RequestBody UpdateItemExampleBody updateItemExampleBody) {

        itemExampleService.update(updateItemExampleBody.getName(), updateItemExampleBody.getExamples());

        return JsonResponse.success();
    }

    @GetMapping("/generate")
    @SuppressWarnings("unchecked")
    public void generate() {
        int index = 1;
        Integer page = 1;
        Integer pageSize = 10;
        boolean continueFlag = false;
        QueryCondition queryCondition = new ItemQueryCondition();
        do {
            queryCondition.setPageNo(page);
            queryCondition.setPageSize(pageSize);
            Map<String, Object> data = itemService.pageList(queryCondition);
            List<Item> list = (List<Item>) data.get("list");
            if (list.size() > 0) {
                for (Item item : list) {
                    if (item.getExamples().size() > 0) {
                        itemExampleService.writeJSONFile(item, index);
                        index++;
                    }
                }
            }
            page++;
            continueFlag = list.size() > 0;
        } while (continueFlag);
    }
}
