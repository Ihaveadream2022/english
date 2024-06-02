package com.english.controller;

import com.english.entity.Item;
import com.english.entity.ItemTts;
import com.english.model.ItemHtml;
import com.english.model.JsonResponse;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.ItemQueryCondition;
import com.english.model.request.ItemTtsQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.service.ItemService;
import com.english.service.impl.ItemTtsServiceImpl;
import com.english.thread.ThreadManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

@RestController
@RequestMapping("/items")
public class ItemController
{
    @Autowired
    ItemService itemService;

    @Autowired
    ItemTtsServiceImpl itemAudioService;

    @GetMapping
    public JsonResponse list(ItemQueryCondition itemQueryCondition)
    {
        Map<String, Object> data = itemService.pageList(itemQueryCondition);

        return JsonResponse.success(data);
    }

    @PostMapping()
    public JsonResponse insert(@Validated @RequestBody Item item)
    {
        if (item.getName() != null && item.getName().trim().length() != 0 && itemService.exist(item)) {
            return JsonResponse.error("The item has been exist");
        }
        Long rows = itemService.insert(item);
        if (rows > 0) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    ItemTts itemTts = new ItemTts();
                    itemTts.setName(item.getName());
                    itemAudioService.dealSpeech(itemTts);
                }
            };
            ThreadManager.getInstance().execute(timerTask);

            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @PutMapping("/{id}")
    public JsonResponse update(@PathVariable Long id, @Validated @RequestBody Item item)
    {
        item.setId(id);

        if (item.getName() != null && item.getName().trim().length() != 0 && itemService.exist(item)) {
            return JsonResponse.error("The item has been exist");
        }

        Long rows = itemService.update(item);
        if (rows > 0) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    ItemTts itemTts = new ItemTts();
                    itemTts.setId(item.getTts().getId());
                    itemTts.setName(item.getName());
                    itemAudioService.dealSpeech(itemTts);
                }
            };
            ThreadManager.getInstance().execute(timerTask);

            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @DeleteMapping( "/{id}")
    public JsonResponse delete(@PathVariable Long id)
    {
        DeleteRequestBody deleteRequestBody = new DeleteRequestBody();
        deleteRequestBody.setId(id);

        Long rows = itemService.delete(deleteRequestBody);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    @GetMapping("/generate")
    @SuppressWarnings("unchecked")
    public void generate()
    {
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
                processSome(list, page);
            }
            page++;
            continueFlag = list.size() > 0;
        } while (continueFlag);
    }

    public void processSome(List<Item> itemList, Integer page)
    {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                itemService.itemsToJsonFiles(itemList, page);
            }
        };
        ThreadManager.getInstance().execute(timerTask);
    }
}
