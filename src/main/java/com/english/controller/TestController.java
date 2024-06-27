package com.english.controller;

import com.english.entity.Item;
import com.english.entity.ItemExample;
import com.english.entity.ItemTts;
import com.english.manager.ThreadManager;
import com.english.model.KeyValue;
import com.english.model.request.ItemQueryCondition;
import com.english.model.request.ItemTtsQueryCondition;
import com.english.model.request.QueryCondition;
import com.english.service.impl.ItemExampleServiceImpl;
import com.english.service.impl.ItemServiceImpl;
import com.english.service.impl.ItemTtsServiceImpl;
import com.english.util.FileUtil;
import com.english.util.encrypt.Digest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.lang.Runnable;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;

@RequestMapping("/open")
@RestController
public class TestController {


//    public int longestCommonSubsequence(String text1, String text2) {
//        int m = text1.length();
//        int n = text2.length();
//
//        // 创建一个二维数组来存储子问题的解
//        int[][] dp = new int[m + 1][n + 1];
//
//        // 填充 dp 表
//        for (int i = 1; i <= m; i++) {
//            for (int j = 1; j <= n; j++) {
//                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
//                    dp[i][j] = dp[i - 1][j - 1] + 1;
//                } else {
//                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
//                }
//            }
//        }
//
//        // 返回 LCS 的长度
//        return dp[m][n];
//    }

    public String[] calc(int total, int today) {
        if (total < 1 || total > 31000) {
            throw new IllegalArgumentException("Total Parameter is beyond the scope of 1-31000");
        }
        if (today < 1 || today > 31) {
            throw new IllegalArgumentException("Today Parameter is beyond the scope of 1-31");
        }
        String[] todayTodo = {"", "1.json", "100.json"};
        int todayIndex = today % 32;
        int todayItemsFrom = todayIndex * 1000 - 1000 + 1;
        int todayItemsEnd = Math.min(total, todayIndex * 1000);
        int todayFileFrom = todayIndex * 100 - 100 + 1;
        int todayFileEnd =  todayIndex * 100;

        todayTodo[0] = String.format("%s:%s-%s", today, todayFileEnd*10-1000, todayFileEnd*10);
        todayTodo[1] = String.format("%s.json", todayFileFrom);
        todayTodo[2] = String.format("%s.json", todayFileEnd);
        return todayTodo;
    }

    @Autowired
    ItemExampleServiceImpl itemExampleService;

    @Autowired
    ItemServiceImpl itemService;

    @Autowired
    ItemTtsServiceImpl itemTtsService;

    @GetMapping("/test")
    public void getAudio(@RequestParam Integer total, @RequestParam Integer today, @RequestParam String name, HttpServletResponse response) throws IOException {




//        response.setContentType("audio/mp3");
//
//
//        ItemTts itemTts = itemTtsService.findByName(name);
//
//        try (OutputStream outputStream = response.getOutputStream()) {
//            outputStream.write(itemTts.getAudio());
//        }

//        QueryCondition queryCondition = new ItemTtsQueryCondition();
//        queryCondition.setPageNo(1);
//        queryCondition.setPageSize(500);
//        itemTtsService.pageList(queryCondition);
//        Map<String, Object> data = itemTtsService.pageList(queryCondition);
//        List<ItemTts> list = (List<ItemTts>) data.get("list");
//        list.forEach(v->{
//            byte[] bytes = Base64.getDecoder().decode(v.getSpeech());
//            v.setAudio(bytes);
//            itemTtsService.update(v);
//        });



//        QueryCondition queryCondition = new ItemQueryCondition();
//        queryCondition.setPageNo(1);
//        queryCondition.setPageSize(500);
//        Map<String, Object> data = itemExampleService.pageList(queryCondition);
//        List<ItemExample> list = (List<ItemExample>) data.get("list");
//
//        Map<String,List<KeyValue>> map = new HashMap<String,List<KeyValue>>();
//
//        list.forEach(v-> {
//            List<KeyValue> strList = map.get(v.getName());
//            if (strList == null) {
//                strList = new ArrayList<KeyValue>();
//            }
//
//                KeyValue keyValue = new KeyValue();
////                keyValue.setKey(v.getKey());
////            keyValue.setValue(v.getExample());
////                strList.add(keyValue);
//
//            map.put(v.getName(), strList);
//
//        });

//        map.forEach((k,v)->{
//            try {
//                ObjectMapper objectMapper = new ObjectMapper();
//                String jsonStr = objectMapper.writeValueAsString(v);
//
//                System.out.println(k + ": " +jsonStr);
////                ItemExample itemExample = itemExampleService.findByName(k);
////                itemExample.setExamples(jsonStr);
////                itemExampleService.update(itemExample);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        });
//
//        System.out.println(map);




        //        String[] todayTodo = calc(total,today);

//        List<String> stringList = Arrays.asList("a","b","c","d","e","f","g");
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("<ul>");
//        for (int i = 0; i < stringList.size() -1; i++) {
//            if (i%2==0) stringBuilder.append("<li>"); // The bottom number is the frequency.
//            stringBuilder.append("<span>").append(stringList.get(i)).append("</span>");
//            if (i%2==1) stringBuilder.append("</li>");
//        }
//        stringBuilder.append("</ul>");
//
//        System.out.println(stringBuilder.toString());
//            int frequency = total / 1000;
//            if (frequency >= 1) {
//                todayTodo = today/frequency;
//                todayTodo = 1;
//            }

//response.getWriter().println(todayTodo[0] + ","+todayTodo[1]+","+todayTodo[2]);
        //System.out.println(todayTodo*100-100 + ".json,"+ todayTodo*100 + ".json");

//        if (total > 1000 && total <= 3000) {
//
//
//            System.out.println(today%3);
//
//        } else if (total <= 30000) {
//
//        } else if (total <= 60000) {
//
//        } else {
//
//        }




//        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
//        response.setHeader("Content-disposition", "attachment; filename=a.png");
//        response.setHeader("download-filename", "a.png");
//        response.setContentType("audio/mpeg");
//        byte[] dataBytes = Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAIAAAD91JpzAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAAEXRFWHRTb2Z0d2FyZQBTbmlwYXN0ZV0Xzt0AAAAWSURBVAiZY5w4sZaBgYGJgYGBgYEBABOyAaMvqaJ9AAAAAElFTkSuQmCC");
//        try (FileOutputStream fos = new FileOutputStream("a.png")) {
//            fos.write(dataBytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        FileOutputStream fileOutputStream = null;
//        try {
//            String base64Image = "iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAIAAAD91JpzAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAAEXRFWHRTb2Z0d2FyZQBTbmlwYXN0ZV0Xzt0AAAAWSURBVAiZY5w4sZaBgYGJgYGBgYEBABOyAaMvqaJ9AAAAAElFTkSuQmCC";
//            byte[] dataBytes = Base64.getDecoder().decode(base64Image);
//            fileOutputStream = new FileOutputStream("a.png");
//            fileOutputStream.write(dataBytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage(), e);
//        } finally {
//            if (fileOutputStream != null) {
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

//        FileWriter fileWriter = null;
//        try {
//            fileWriter = new FileWriter(new File("a.json"));
//            fileWriter.write("{\"key\":\"value\"}");
//        } catch (IOException e) {
//            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
//        } finally {
//            if (fileWriter != null) {
//                try {
//                    fileWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


//        List<String> stringList = Arrays.asList("a","b","c","d","e","f","g");
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("<ul>");
//
//        for (int i = 0; i < stringList.size() -1; i++) {
//            if (i%2==0) stringBuilder.append("<li>"); // The bottom number is the frequency.
//            stringBuilder.append("<span>").append(stringList.get(i)).append("</span>");
//            if (i%2==1) stringBuilder.append("</li>");
//        }
//
//        stringBuilder.append("</ul>");
//        System.out.println(stringBuilder.toString());

//        String text1 = a1;
//        String text2 = a2;
//        System.out.println("Length of LCS: " + longestCommonSubsequence(text1, text2)); // 输出：3

//        String text1 = "abcde";
//        String text2 = "ace";
//        System.out.println("Length of LCS: " + longestCommonSubsequence(text1, text2)); // 输出：3
//
//        text1 = "abc";
//        text2 = "abc";
//        System.out.println("Length of LCS: " + longestCommonSubsequence(text1, text2)); // 输出：3
//
//        text1 = "abc";
//        text2 = "def";
//        System.out.println("Length of LCS: " + longestCommonSubsequence(text1, text2)); // 输出：0


//        String input = Digest.digestMD5("画");
//        String firstFour = input.substring(0, Math.min(input.length(), 4));
//        String lastFour = input.substring(Math.max(input.length() - 4, 0));
//        System.out.println(firstFour + lastFour);


//        String downloadFileName = "download2.mp3";
//        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
//        response.setHeader("Content-disposition", "attachment; filename=" + downloadFileName);
//        response.setHeader("download-filename", downloadFileName);
//        response.setContentType("audio/mpeg");

        //FileUtil.writeBytes("abc.mp3", response.getOutputStream());

//        FileOutputStream fos = new FileOutputStream("output_file.mp3");
//        String bs = "//MoxAAMyFokAU8QAMsZzk7Lmo3NsLYIecZdAjgKgTBGN6vQxDFY8OA+s/8EAwJAQdLhjIQQBB3BAMCQMc+UDCqzm5aRhADj//MoxAcN+Jp8AZs4AEVxciA04TAgYx+pNsEZ3GUzAMFCIYbGlgARee/dLgMEdDmcgMMdGVHGEgTSpcdu/9HClxe7AGcrRg3H//MoxAoOeSrMAY9AADI/enPvnkVVvNTf9RzFKv6JUcz/Hv2YiSoqLB0LR0HwYLPufLgrgkxv////////0v//Z8UYFg5r0zWh//MoxAsOCPLQAc84APx/rVsyXizKOBeYhihAEi9M/+n9ScIGROt1Xn+c4j2ANun///IcMcT8o44AylX9aABQrf1CydbbRihf//MoxA0OYb7UAGtElA8yrqMg/jLr9S/7P1lDAFNgrHf0Mp////YxPhmA3bb//9GEhPS9HU6VEIEclf1mADqKX4yI79VBlkM5//MoxA4PWcbYAGqKlMSgODhIrTqb/MRvGCwKJ/Wd/3/R2/df6kJpUOiQmaFaz+8h/9gYrALH/LNIRKZECv0CyDSSWb6Y7vyo//MoxAsOop7UAJKKuBtrJkZoD40R0mLf/N/iUNlqYaikR3+rf0/2/kX9woev1I/8v9H/l/36aIPbhvjeqlq7w/cJweEVxUEB//MoxAsNERbQAIoOcMtdf4dhV5XWZEwOnmsiO9fp+dOqyIPSQ1CQ6Ku4a6P6TukFf//+swj66vWgXwANAY1RjUigrqHoYKJu//MoxBEMSIq8AJDETPqJtU0RO/JYhDSyLKv//TX/7fMrX0XUvL9psRsJmQ+LVZJIBBJJJJGBuUMLHaVGfR5y6xC9+F75go50//MoxBoMWCbuXghEAk7WsaKdveu4wN/do/71iSWNXeeui8FTarhYEyhR9g6ysPrFBlU+PRfwHNMP0/Xu08atkvH28n//vy6R//MoxCMK6AbAAADEADzXtCrWCVTQraavHZBXyDEc7jPFIBCp+KWKk6BOUFEJOvNGshJxgABw53V9TqfpPV+SkYEHEDWym/9///MoxDIM0QLMAHpKcPoVdWoG6PVko+G+V3JzX54MlVOTw45iYaUwQKYd57d1+kqKyjmCPWtP/9eXUQJ////p8jABF9A8O5Uz//MoxDkMAQ7cAGmEcMhdZUXJYcCUqOzQoMnzlM9FdEVSVKAAuwsQ7FSz////+t2oc48xFc4qoojWE222stu1wAHmgwscsBZZ//MoxEQNOc7cAFHKlHCJehjluAPUWnwQEY5zSsqv8OOrQTi4PB9nPU6Ub0+WFXiJ9qLqGACqn3GTf9nYxogBlLijwQhpBBG6//MoxEoM8PMuXjlEcuUTPumapxyycon6JjhB10lvsZ1mpcBAINIcEA5BJGm3JOsfgsx1Qdzyv+/SdBo4C1KHvPpujM98zt9z//MoxFEMQP7MAFBMcGqBWxxMH4Gn0i/T6v9wSPmBUnWy222OyWy2gbpiw8AbZSBsZ3tjrnbCTrdnnzJsbOU4TM4YRFGKKiMD//MoxFsMmPsGWGjMchokFJbDeeQsRB3Z+nIzpEiwJAqGQlb//u/6FQEBJBxJRIQAeBxLbLiBiSWUlEsloutaLcqCJCUSBqJo//MoxGMQkLsCX0swAoMl5JGRCjnkWSRyZ+BlJqKkQ01J0UkIJEGRq/ol4spEG+tv9aSkjYmDdlKSqWZGyX/uZLLpa3Lf/4HL//MoxFsXGe6M9Y2QAH1qTZEAf//+YcX///+q8NtciorRQfIHQfOHILQ9KBsaULWoqar+5P//63FqTEFNRTMuOTkuNaqqqqqq//MoxDkKYRmENcFAAKqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
//        FileUtil.writeBase64ToOutputStream(bs, fos);

//        try {
//            System.out.println(System.getProperty("user.dir"));
//            Path path = Paths.get("abc.mp3");
//            byte[] mp3Bytes = Files.readAllBytes(path);
//
//            return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(mp3Bytes);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//byte[] ts = null;
//        return ResponseEntity.ok()
//            .contentType(MediaType.APPLICATION_OCTET_STREAM)
//            .body(ts);

//        try {
//            System.out.println(System.getProperty("user.dir"));
//            Path path = Paths.get("abc.mp3");
//            byte[] mp3Bytes = Files.readAllBytes(path);
//
//            // 使用Base64编码
//            String base64String = Base64.getEncoder().encodeToString(mp3Bytes);
//
//            // 输出Base64字符串
//            System.out.println("MP3文件的Base64字符串：" + base64String);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }


//        BufferedReader br = new BufferedReader(new FileReader(""))


//        TimerTask timerTask = new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                try {
//                    Thread.sleep(5000);
//                    System.out.println(123);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        ThreadManager.getInstance().execute(timerTask);
//
//        TimerTask timerTask2 = new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                try {
//                    Thread.sleep(10000);
//                    System.out.println(666);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        ThreadManager.getInstance().execute(timerTask2);

        //Thread t2 = new Thread(myRunnable::stop);
       // t2.start();

        //ScheduledExecutorService scheduledExecutorService = new ScheduledExecutorService(10);

        //return new ModelAndView("video");
    }
}