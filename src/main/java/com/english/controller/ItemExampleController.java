package com.english.controller;

import com.english.entity.ItemExample;
import com.english.model.JsonResponse;
import com.english.service.impl.ItemExampleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item-examples")
public class ItemExampleController {
    
    @Autowired
    ItemExampleServiceImpl itemExampleService;

    @PutMapping("/{id}")
    public JsonResponse update(@PathVariable Long id, @Validated @RequestBody ItemExample itemExample) {

        itemExample.setId(id);

        Long rows = itemExampleService.update(itemExample);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }
}
