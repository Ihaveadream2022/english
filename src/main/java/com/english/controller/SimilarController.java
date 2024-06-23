package com.english.controller;

import com.english.entity.Similar;
import com.english.model.JsonResponse;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.GrammarQueryCondition;
import com.english.service.impl.SimilarServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/similars")
public class SimilarController {

    private final Logger logger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    SimilarServiceImpl similarService;

    @GetMapping
    public JsonResponse list(GrammarQueryCondition grammarQueryCondition) {

        Map<String, Object> data = similarService.pageList(grammarQueryCondition);

        return JsonResponse.success(data);
    }

    @PostMapping()
    public JsonResponse insert(@Validated @RequestBody Similar similar) {
        if (similar.getItemIds() != null && similar.getItemIds().trim().length() != 0 && similarService.exist(similar)) {
            return JsonResponse.error("The meaning has been exist");
        }
        Long rows = similarService.insert(similar);
        if (rows > 0) {
            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @PutMapping("/{id}")
    public JsonResponse update(@PathVariable Long id, @Validated @RequestBody Similar similar) {
        similar.setId(id);
        if (similar.getItemIds() != null && similar.getItemIds().trim().length() != 0 && similarService.exist(similar)) {
            return JsonResponse.error("The grammar has been exist");
        }
        Long rows = similarService.update(similar);
        if (rows > 0) {
            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @DeleteMapping( "/{id}")
    public JsonResponse delete(@PathVariable Long id) {
        DeleteRequestBody deleteRequestBody = new DeleteRequestBody();
        deleteRequestBody.setId(id);

        Long rows = similarService.delete(deleteRequestBody);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    @GetMapping("/generate")
    @SuppressWarnings("unchecked")
    public void generate() {
//        Integer page = 1;
//        Integer pageSize = 10;
//        boolean continueFlag = false;
//        QueryCondition grammarQueryCondition = new GrammarQueryCondition();
//        do {
//            grammarQueryCondition.setPageNo(page);
//            grammarQueryCondition.setPageSize(pageSize);
//            Map<String, Object> data = grammarService.pageList(grammarQueryCondition);
//            List<Grammar> list = (List<Grammar>) data.get("list");
//            if (list.size() > 0) {
//                for (int i = 0; i < list.size(); i++) {
//                    grammarService.generate(list.get(i), i+1);
//                }
//            }
//            page++;
//            continueFlag = list.size() > 0;
//        } while (continueFlag);
    }
}
