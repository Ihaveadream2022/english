package com.english.controller;

import com.english.entity.ItemTts;
import com.english.exception.ServiceRuntimeException;
import com.english.service.impl.ItemTtsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@RequestMapping("/tts")
@RestController
public class ItemTtsController {

    @Autowired
    ItemTtsServiceImpl itemTtsService;

    @GetMapping("/get")
    public void getTts(@RequestParam String name, HttpServletResponse response) throws IOException {
        ItemTts itemTts = itemTtsService.findByName(name);
        if (itemTts != null && itemTts.getAudio() != null) {
            StringBuilder downloadFileName = new StringBuilder();
            downloadFileName.append(itemTts.getName()).append(".mp3");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
            response.setHeader("Content-disposition", "attachment; filename=" + downloadFileName.toString());
            response.setHeader("download-filename", downloadFileName.toString());
            response.setContentType("audio/mpeg");
            try (OutputStream outputStream = response.getOutputStream()) {
                byte[] bytes = Base64.getDecoder().decode(itemTts.getAudio());
                outputStream.write(bytes);
            } catch (Exception e) {
                throw new ServiceRuntimeException(e.getMessage());
            }
        }
    }
}
