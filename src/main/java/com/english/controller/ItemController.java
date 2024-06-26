package com.english.controller;

import com.english.entity.Item;
import com.english.entity.ItemExample;
import com.english.entity.ItemTts;
import com.english.model.JsonResponse;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.ItemQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.service.ItemService;
import com.english.service.impl.ItemExampleServiceImpl;
import com.english.service.impl.ItemServiceImpl;
import com.english.service.impl.ItemTtsServiceImpl;
import com.english.manager.ThreadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;

    @Autowired
    ItemExampleServiceImpl itemExampleService;

    @Autowired
    ItemTtsServiceImpl itemAudioService;

    @GetMapping
    public JsonResponse list(ItemQueryCondition itemQueryCondition) {

        Map<String, Object> data = itemService.pageList(itemQueryCondition);

        return JsonResponse.success(data);
    }

    @PostMapping()
    public JsonResponse insert(@Validated @RequestBody Item item) {
        if (item.getName() != null && item.getName().trim().length() != 0 && itemService.exist(item)) {
            return JsonResponse.error("The item has been exist");
        }
        Long rows = itemService.insert(item);
        if (rows > 0) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    itemAudioService.dealSpeech(item.getTts());
                }
            };
            ThreadManager.getInstance().execute(timerTask);

            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @PutMapping("/{id}")
    public JsonResponse update(@PathVariable Long id, @Validated @RequestBody Item item) {

        item.setId(id);

        if (item.getName() != null && item.getName().trim().length() != 0 && itemService.exist(item)) {
            return JsonResponse.error("The item has been exist");
        }

        Long rows = itemService.update(item);
        if (rows > 0) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    itemAudioService.dealSpeech(item.getTts());
                }
            };
            ThreadManager.getInstance().execute(timerTask);

            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @DeleteMapping( "/{id}")
    public JsonResponse delete(@PathVariable Long id) {
        DeleteRequestBody deleteRequestBody = new DeleteRequestBody();
        deleteRequestBody.setId(id);

        Long rows = itemService.delete(deleteRequestBody);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    @GetMapping("/generate")
    @SuppressWarnings("unchecked")
    public void generate() {
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
                itemService.writeJSONFile(list, page);
            }
            page++;
            continueFlag = list.size() > 0;
        } while (continueFlag);

        int index = 1;
        Integer examplePage = 1;
        Integer examplePageSize = 10;
        boolean exampleContinueFlag = false;
        QueryCondition exampleQueryCondition = new QueryCondition();
        do {
            exampleQueryCondition.setPageNo(examplePage);
            exampleQueryCondition.setPageSize(examplePageSize);
            Map<String, Object> data2 = itemExampleService.pageList(exampleQueryCondition);
            List<ItemExample> itemExampleList = (List<ItemExample>) data2.get("list");
            if (itemExampleList.size() > 0) {
                for (ItemExample itemExample : itemExampleList) {
                    if (itemExample.getExamples() != null) {
                        itemExampleService.writeJSONFile(itemExample, index);
                        index++;
                    }
                }
            }
            examplePage++;
            exampleContinueFlag = itemExampleList.size() > 0;
        } while (exampleContinueFlag);
    }
}
