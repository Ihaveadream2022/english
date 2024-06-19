package com.english.controller;

import com.english.entity.ItemTts;
import com.english.model.request.ItemTtsQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.service.ItemTtsService;
import com.english.manager.ThreadManager;
import com.english.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequestMapping("/tts")
@RestController
public class ItemTtsController
{
    @Autowired
    ItemTtsService itemTtsService;

    @GetMapping("/get")
    public void getTts(@RequestParam String name, HttpServletResponse response) throws IOException
    {
        ItemTts itemTts = itemTtsService.findByName(name);
        if (itemTts != null && itemTts.getSpeech() != null) {
            String downloadFileName = "tts.mp3";
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
            response.setHeader("Content-disposition", "attachment; filename=" + downloadFileName);
            response.setHeader("download-filename", downloadFileName);
            response.setContentType("audio/mpeg");
            FileUtil.writeBase64ToOutputStream(itemTts.getSpeech(), response.getOutputStream());
        }
    }

    @GetMapping("/generate")
    @SuppressWarnings("unchecked")
    public void generate()
    {
        Integer page = 1;
        Integer pageSize = 10;
        boolean continueFlag = false;
        QueryCondition queryCondition = new ItemTtsQueryCondition();
        do {
            queryCondition.setPageNo(page);
            queryCondition.setPageSize(pageSize);
            Map<String, Object> data = itemTtsService.pageList(queryCondition);
            List<ItemTts> list = (List<ItemTts>) data.get("list");
            if (list.size() > 0) {
                List<ItemTts> itemTtsList = new ArrayList<>();
                for (int i=0; i< list.size(); i++) {
                    itemTtsList.add(list.get(i));
                    if (i != 0 && ((i%2) == 0)) {
                        List<ItemTts> proData = itemTtsList;
                        processSome(proData);
                        itemTtsList = new ArrayList<>();
                    }
                    if (i == (list.size() - 1) && ((list.size() % 3) != 0)) {
                        List<ItemTts> proData = itemTtsList;
                        processSome(proData);
                    }
                }
            }
            page++;
            continueFlag = list.size() > 0;
        } while (continueFlag);
    }

    public void processSome(List<ItemTts> itemTtsList)
    {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (ItemTts itemTts : itemTtsList) {
                    itemTtsService.writeSpeech(itemTts);
                }
            }
        };
        ThreadManager.getInstance().execute(timerTask);
    }
}
