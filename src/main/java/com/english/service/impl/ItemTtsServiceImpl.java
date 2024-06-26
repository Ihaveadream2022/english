package com.english.service.impl;

import com.english.entity.ItemTts;
import com.english.exception.ServiceRuntimeException;

import com.english.mapper.ItemTtsMapper;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import com.english.service.ItemTtsService;
import com.english.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
public class ItemTtsServiceImpl implements ItemTtsService {

    private static final Logger serviceLogger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    ItemTtsMapper itemTtsMapper;

    public HashMap<String, Object> pageList(QueryCondition queryCondition) {
        Integer a = queryCondition.getOffset();
        HashMap<String, Object> data = new HashMap<>();

        List<ItemTts> list = itemTtsMapper.selectLimited(queryCondition);
        Long total = itemTtsMapper.count(queryCondition);

        data.put("list", list);
        data.put("total", total);
        data.put("pageSize", queryCondition.getPageSize());
        data.put("pageNo", queryCondition.getPageNo());

        return data;
    }

    public ItemTts findByName(String name) {
        return itemTtsMapper.findByName(name);
    }

    @Override
    @Transactional
    public Long insert(ItemTts itemTts) {
        boolean isExist = exist(itemTts);
        if (isExist) {
            throw new ServiceRuntimeException("The item tts has been existed.");
        }
        return itemTtsMapper.insert(itemTts);
    }

    @Override
    @Transactional
    public Long update(ItemTts itemTts) {
        boolean isExist = exist(itemTts);
        if (isExist) {
            throw new ServiceRuntimeException("The item tts has been existed.");
        }
        return itemTtsMapper.update(itemTts);
    }

    @Override
    public Boolean exist(ItemTts itemTts) {
        long wordId = itemTts.getId() == null? -1L : itemTts.getId();
        ItemTts one = itemTtsMapper.findByName(itemTts.getName());
        return one != null && one.getId() != wordId;
    }

    @Override
    @Transactional
    public Long delete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getId() != null) {
            return itemTtsMapper.delete(deleteRequestBody);
        }

        return 0L;
    }

    @Override
    @Transactional
    public Long batchDelete(DeleteRequestBody deleteRequestBody) {
        if (deleteRequestBody.getIds() != null && deleteRequestBody.getIds().length > 0) {
            return itemTtsMapper.batchDelete(deleteRequestBody);
        }

        return 0L;
    }

    @Override
    public void dealSpeech(ItemTts itemTts) {
        try {
            // Create audio
            String scriptPath = "python " + System.getProperty("user.dir") + "/scripts/english.py";
            String itemName = itemTts.getName();
            String savePath = System.getProperty("user.dir") + "/" + itemName.replace(" ","_") + ".mp3";
            String command = scriptPath + " \"" + itemName + "\" " + "\"" + savePath + "\"" ;
            Process process = Runtime.getRuntime().exec(command);
            String cmdOutput = null;
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((cmdOutput = stdInput.readLine()) != null) {
                serviceLogger.info(cmdOutput);
            }
            stdInput.close();
            process.waitFor();

             // Read audio and convert to Base64 String
            Path path = Paths.get(savePath);
            byte[] bytes = Files.readAllBytes(path);
            String base64String = Base64.getEncoder().encodeToString(bytes);

            // Delete the audio
            FileUtil.deleteFile(savePath);

            // Update item speech
            if (base64String != null) {
                itemTts.setSpeech(base64String);
                itemTtsMapper.update(itemTts);
            }
        } catch (Exception e) {
            throw new ServiceRuntimeException(e.getMessage());
        }
    }

    @Override
    public void writeSpeech(ItemTts itemTts) {
        String savePath = System.getProperty("user.dir") + "/audio/" + itemTts.getName().replace(" ","_") + ".mp3";
        try (FileOutputStream fos = new FileOutputStream(savePath)) {
            FileUtil.writeBase64ToOutputStream(itemTts.getSpeech(), fos);
            serviceLogger.info("Write successfully: " + savePath);
        } catch (IOException e) {
            throw new ServiceRuntimeException(e.getMessage());
        }
    }
}
