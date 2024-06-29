package com.english.controller;

import com.english.entity.Item;
import com.english.entity.Synonym;
import com.english.model.JsonResponse;
import com.english.model.SynonymHtmlItem;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.ItemQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.model.request.SynonymQueryCondition;
import com.english.service.impl.SynonymServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/synonyms")
public class SynonymController {

    private final Logger logger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    SynonymServiceImpl synonymService;

    @GetMapping
    public JsonResponse list(SynonymQueryCondition synonymQueryCondition) {

        Map<String, Object> data = synonymService.pageList(synonymQueryCondition);

        return JsonResponse.success(data);
    }

    @PostMapping()
    public JsonResponse insert(@Validated @RequestBody Synonym synonym) {
        if (synonym.getMeaning() != null && synonym.getMeaning().trim().length() != 0 && synonymService.exist(synonym)) {
            return JsonResponse.error("The meaning has been exist");
        }
        Long rows = synonymService.insert(synonym);
        if (rows > 0) {
            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @PutMapping("/{id}")
    public JsonResponse update(@PathVariable Long id, @Validated @RequestBody Synonym synonym) {
        synonym.setId(id);
        if (synonym.getMeaning() != null && synonym.getMeaning().trim().length() != 0 && synonymService.exist(synonym)) {
            return JsonResponse.error("The grammar has been exist");
        }
        Long rows = synonymService.update(synonym);
        if (rows > 0) {
            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @DeleteMapping( "/{id}")
    public JsonResponse delete(@PathVariable Long id) {
        DeleteRequestBody deleteRequestBody = new DeleteRequestBody();
        deleteRequestBody.setId(id);

        Long rows = synonymService.delete(deleteRequestBody);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    @GetMapping("/generate")
    @SuppressWarnings("unchecked")
    public void generate() {
        int page = 1;
        int pageSize = 10;
        boolean continueFlag = false;
        SynonymQueryCondition synonymQueryCondition = new SynonymQueryCondition();
        do {
            synonymQueryCondition.setPageNo(page);
            synonymQueryCondition.setPageSize(pageSize);
            Map<String, Object> data = synonymService.pageList(synonymQueryCondition);
            List<Synonym> list = (List<Synonym>) data.get("list");
            for (int i = 0; i < list.size(); i++) {
                synonymService.writeJSONFile(list.get(i), (page - 1) * 10 + i + 1);
            }
            page++;
            continueFlag = list.size() > 0;
        } while (continueFlag);
    }
}
