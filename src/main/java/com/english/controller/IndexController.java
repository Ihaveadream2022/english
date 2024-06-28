package com.english.controller;

import com.english.model.JsonResponse;
import com.english.model.request.ItemQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    ItemServiceImpl itemService;

    @RequestMapping("/")
    public String index() {
        return "Welcome";
    }

    @GetMapping("/todo")
    public JsonResponse todo() {
        Map<String,Object> data = new HashMap<>();

        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalDateTime localDateTime = LocalDateTime.of(2020, month, day, 0, 0, 0);

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
        String dayStr = localDateTime.format(dayFormatter);
        int dayInt = dayStr.startsWith("0")? Integer.parseInt(dayStr.substring(1)): Integer.parseInt(dayStr);

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        String monthStr = localDateTime.format(monthFormatter);
        int monthInt = monthStr.startsWith("0")? Integer.parseInt(monthStr.substring(1)): Integer.parseInt(monthStr);

        ItemQueryCondition itemQueryCondition = new ItemQueryCondition();
        long total = itemService.count(itemQueryCondition);
        if (total > 1 && total <= 1000) {
            data.put("total", total);
            data.put("totalPage", 1);
            data.put("nowPage", 1);
            data.put("todo", String.format("%s月%s日: [%s,%s]: [%s,%s]", monthInt, dayInt, 1, 1000, "item-1.json", "item-100.json"));
        } else if (total <= 31000) {
            int page = (int) Math.ceil((double) total / 1000);
            int pageCurrent = ((dayInt - 1 ) % page) + 1;
            int itemRangeFrom = (pageCurrent - 1) * 1000 + 1;
            int itemRangeEnd = pageCurrent * 1000;
            int itemFileRangeFrom = (pageCurrent - 1) * 100 + 1;
            int itemFileRangeEnd =  pageCurrent * 100;
            data.put("total", total);
            data.put("totalPage", page);
            data.put("nowPage", pageCurrent);
            data.put("todo", String.format("%s月%s日: [%s,%s]: [%s.json,%s.json]", monthInt, dayInt, itemRangeFrom, itemRangeEnd, itemFileRangeFrom, itemFileRangeEnd));
        } else if (total <= 62000) {
            int dayShow = dayInt;
            if (monthInt % 2 == 0) {
                dayInt = dayInt + 31;
            }
            int page = (int) Math.ceil((double) total / 1000);
            int pageCurrent = ((dayInt - 1 ) % page) + 1;
            int itemRangeFrom = (pageCurrent - 1) * 1000 + 1;
            int itemRangeEnd = pageCurrent * 1000;
            int itemFileRangeFrom = (pageCurrent - 1) * 100 + 1;
            int itemFileRangeEnd =  pageCurrent * 100;
            data.put("total", total);
            data.put("totalPage", page);
            data.put("nowPage", pageCurrent);
            data.put("todo", String.format("%s月%s日: [%s,%s]: [%s.json,%s.json]", monthInt, dayShow, itemRangeFrom, itemRangeEnd, itemFileRangeFrom, itemFileRangeEnd));
        }
        return JsonResponse.success(data);
    }
}
