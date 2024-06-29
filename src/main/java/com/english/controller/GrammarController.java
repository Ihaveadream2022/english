package com.english.controller;

import com.english.entity.Grammar;
import com.english.model.JsonResponse;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.GrammarQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.service.GrammarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grammars")
public class GrammarController {

    private final Logger logger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    GrammarService grammarService;

    @GetMapping
    public JsonResponse list(GrammarQueryCondition grammarQueryCondition) {

        Map<String, Object> data = grammarService.pageList(grammarQueryCondition);

        return JsonResponse.success(data);
    }

    @PostMapping()
    public JsonResponse insert(@Validated @RequestBody Grammar grammar) {
        if (grammar.getName() != null && grammar.getName().trim().length() != 0 && grammarService.exist(grammar)) {
            return JsonResponse.error("The grammar has been exist");
        }
        Long rows = grammarService.insert(grammar);
        if (rows > 0) {
            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @PutMapping("/{id}")
    public JsonResponse update(@PathVariable Long id, @Validated @RequestBody Grammar grammar) {
        grammar.setId(id);
        if (grammar.getName() != null && grammar.getName().trim().length() != 0 && grammarService.exist(grammar)) {
            return JsonResponse.error("The grammar has been exist");
        }

        Long rows = grammarService.update(grammar);
        if (rows > 0) {
            return JsonResponse.success();
        }

        return JsonResponse.error();
    }

    @DeleteMapping( "/{id}")
    public JsonResponse delete(@PathVariable Long id) {
        DeleteRequestBody deleteRequestBody = new DeleteRequestBody();
        deleteRequestBody.setId(id);

        Long rows = grammarService.delete(deleteRequestBody);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    @GetMapping("/generate")
    @SuppressWarnings("unchecked")
    public void generate() {
        int page = 1;
        int pageSize = 10;
        boolean continueFlag = false;
        QueryCondition grammarQueryCondition = new GrammarQueryCondition();
        do {
            grammarQueryCondition.setPageNo(page);
            grammarQueryCondition.setPageSize(pageSize);
            Map<String, Object> data = grammarService.pageList(grammarQueryCondition);
            List<Grammar> list = (List<Grammar>) data.get("list");
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    grammarService.generate(list.get(i), i+1);
                }
            }
            page++;
            continueFlag = list.size() > 0;
        } while (continueFlag);
    }
}
