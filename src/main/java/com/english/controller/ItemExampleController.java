package com.english.controller;

import com.english.entity.Item;
import com.english.entity.ItemExample;
import com.english.model.JsonResponse;
import com.english.model.request.ItemQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.model.request.UpdateItemExampleBody;
import com.english.service.impl.ItemExampleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item-examples")
public class ItemExampleController {

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
        Integer page = 1;
        Integer pageSize = 5;
        boolean continueFlag = false;
        QueryCondition queryCondition = new ItemQueryCondition();
        do {
            queryCondition.setPageNo(page);
            queryCondition.setPageSize(pageSize);
            Map<String, Object> data = itemExampleService.pageList(queryCondition);
            List<ItemExample> list = (List<ItemExample>) data.get("list");
            if (list.size() > 0) {
                itemExampleService.writeJSONFile(list, page);
            }
            page++;
            continueFlag = list.size() > 0;
        } while (continueFlag);
    }
}
