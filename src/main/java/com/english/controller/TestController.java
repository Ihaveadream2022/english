package com.english.controller;

import com.english.manager.ThreadManager;
import com.english.util.FileUtil;
import com.english.util.encrypt.Digest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.TimeZone;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.lang.Runnable;


@RequestMapping("/open")
@RestController
public class TestController
{
    @GetMapping(value = "/video", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getAudio(HttpServletResponse response) throws IOException {


        String input = Digest.digestMD5("画");
        String firstFour = input.substring(0, Math.min(input.length(), 4));
        String lastFour = input.substring(Math.max(input.length() - 4, 0));
        System.out.println(firstFour + lastFour);


//        String downloadFileName = "download2.mp3";
//        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
//        response.setHeader("Content-disposition", "attachment; filename=" + downloadFileName);
//        response.setHeader("download-filename", downloadFileName);
//        response.setContentType("audio/mpeg");

        //FileUtil.writeBytes("abc.mp3", response.getOutputStream());

        FileOutputStream fos = new FileOutputStream("output_file.mp3");
        String bs = "//MoxAAMyFokAU8QAMsZzk7Lmo3NsLYIecZdAjgKgTBGN6vQxDFY8OA+s/8EAwJAQdLhjIQQBB3BAMCQMc+UDCqzm5aRhADj//MoxAcN+Jp8AZs4AEVxciA04TAgYx+pNsEZ3GUzAMFCIYbGlgARee/dLgMEdDmcgMMdGVHGEgTSpcdu/9HClxe7AGcrRg3H//MoxAoOeSrMAY9AADI/enPvnkVVvNTf9RzFKv6JUcz/Hv2YiSoqLB0LR0HwYLPufLgrgkxv////////0v//Z8UYFg5r0zWh//MoxAsOCPLQAc84APx/rVsyXizKOBeYhihAEi9M/+n9ScIGROt1Xn+c4j2ANun///IcMcT8o44AylX9aABQrf1CydbbRihf//MoxA0OYb7UAGtElA8yrqMg/jLr9S/7P1lDAFNgrHf0Mp////YxPhmA3bb//9GEhPS9HU6VEIEclf1mADqKX4yI79VBlkM5//MoxA4PWcbYAGqKlMSgODhIrTqb/MRvGCwKJ/Wd/3/R2/df6kJpUOiQmaFaz+8h/9gYrALH/LNIRKZECv0CyDSSWb6Y7vyo//MoxAsOop7UAJKKuBtrJkZoD40R0mLf/N/iUNlqYaikR3+rf0/2/kX9woev1I/8v9H/l/36aIPbhvjeqlq7w/cJweEVxUEB//MoxAsNERbQAIoOcMtdf4dhV5XWZEwOnmsiO9fp+dOqyIPSQ1CQ6Ku4a6P6TukFf//+swj66vWgXwANAY1RjUigrqHoYKJu//MoxBEMSIq8AJDETPqJtU0RO/JYhDSyLKv//TX/7fMrX0XUvL9psRsJmQ+LVZJIBBJJJJGBuUMLHaVGfR5y6xC9+F75go50//MoxBoMWCbuXghEAk7WsaKdveu4wN/do/71iSWNXeeui8FTarhYEyhR9g6ysPrFBlU+PRfwHNMP0/Xu08atkvH28n//vy6R//MoxCMK6AbAAADEADzXtCrWCVTQraavHZBXyDEc7jPFIBCp+KWKk6BOUFEJOvNGshJxgABw53V9TqfpPV+SkYEHEDWym/9///MoxDIM0QLMAHpKcPoVdWoG6PVko+G+V3JzX54MlVOTw45iYaUwQKYd57d1+kqKyjmCPWtP/9eXUQJ////p8jABF9A8O5Uz//MoxDkMAQ7cAGmEcMhdZUXJYcCUqOzQoMnzlM9FdEVSVKAAuwsQ7FSz////+t2oc48xFc4qoojWE222stu1wAHmgwscsBZZ//MoxEQNOc7cAFHKlHCJehjluAPUWnwQEY5zSsqv8OOrQTi4PB9nPU6Ub0+WFXiJ9qLqGACqn3GTf9nYxogBlLijwQhpBBG6//MoxEoM8PMuXjlEcuUTPumapxyycon6JjhB10lvsZ1mpcBAINIcEA5BJGm3JOsfgsx1Qdzyv+/SdBo4C1KHvPpujM98zt9z//MoxFEMQP7MAFBMcGqBWxxMH4Gn0i/T6v9wSPmBUnWy222OyWy2gbpiw8AbZSBsZ3tjrnbCTrdnnzJsbOU4TM4YRFGKKiMD//MoxFsMmPsGWGjMchokFJbDeeQsRB3Z+nIzpEiwJAqGQlb//u/6FQEBJBxJRIQAeBxLbLiBiSWUlEsloutaLcqCJCUSBqJo//MoxGMQkLsCX0swAoMl5JGRCjnkWSRyZ+BlJqKkQ01J0UkIJEGRq/ol4spEG+tv9aSkjYmDdlKSqWZGyX/uZLLpa3Lf/4HL//MoxFsXGe6M9Y2QAH1qTZEAf//+YcX///+q8NtciorRQfIHQfOHILQ9KBsaULWoqar+5P//63FqTEFNRTMuOTkuNaqqqqqq//MoxDkKYRmENcFAAKqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        FileUtil.writeBase64ToOutputStream(bs, fos);

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