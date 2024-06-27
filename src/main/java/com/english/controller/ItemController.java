package com.english.controller;

import com.english.entity.Item;
import com.english.model.JsonResponse;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.ItemQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.service.impl.ItemExampleServiceImpl;
import com.english.service.impl.ItemServiceImpl;
import com.english.service.impl.ItemTtsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;

    @Autowired
    ItemExampleServiceImpl itemExampleService;

    @Autowired
    ItemTtsServiceImpl itemTtsService;

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
            itemTtsService.createAudio(item.getTts());

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
            itemTtsService.createAudio(item.getTts());

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
        int indexExample = 1;
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
                for (Item item: list) {
                    if (item.getExample() != null && item.getExample().getExamples() != null) {
                        itemExampleService.writeJSONFile(item, indexExample);
                        indexExample++;
                    }
                    if (item.getTts().getAudio() != null) {
                        itemTtsService.writeBinaryFile(item.getTts());
                    }
                }
            }
            page++;
            continueFlag = list.size() > 0;
        } while (continueFlag);
    }
}
