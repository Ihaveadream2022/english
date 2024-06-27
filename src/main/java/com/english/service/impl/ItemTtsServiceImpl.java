package com.english.service.impl;

import com.english.entity.ItemTts;
import com.english.exception.ServiceRuntimeException;
import com.english.manager.ThreadManager;
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
import java.util.TimerTask;

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
    public void createAudio(ItemTts itemTts) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    // Create audio
                    String scriptPath = "python " + System.getProperty("user.dir") + "/scripts/english.py";
                    String itemName = itemTts.getName();
                    String filePath = String.format("%s/html/audio/%s.mp3", System.getProperty("user.dir"), itemTts.getName().replace(" ","_"));
                    String command = scriptPath + " \"" + itemName + "\" " + "\"" + filePath + "\"" ;
                    Process process = Runtime.getRuntime().exec(command);
                    String cmdOutput = null;
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while ((cmdOutput = stdInput.readLine()) != null) {
                        serviceLogger.info(cmdOutput);
                    }
                    stdInput.close();
                    process.waitFor();

                    // Read audio and convert to Base64 String
                    Path path = Paths.get(filePath);
                    byte[] bytes = Files.readAllBytes(path);
                    String byteBase64 = Base64.getEncoder().encodeToString(bytes);

                    // Delete the audio
                     FileUtil.deleteFile(filePath);

                    // Update item audio
                    if (byteBase64 != null && byteBase64.length() > 0) {
                        itemTts.setAudio(byteBase64);
                        itemTtsMapper.update(itemTts);
                    }
                } catch (Exception e) {
                    throw new ServiceRuntimeException(e.getMessage());
                }
            }
        };
        ThreadManager.getInstance().execute(timerTask);
    }

    @Override
    public void writeBinaryFile(ItemTts itemTts) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String filePath = String.format("%s/html/audio/%s.mp3", System.getProperty("user.dir"), itemTts.getName().replace(" ","_"));
                try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                    byte[] bytes = Base64.getDecoder().decode(itemTts.getAudio());
                    fileOutputStream.write(bytes);
                    serviceLogger.info("Write binary file successfully: " + filePath);
                } catch (IOException e) {
                    throw new ServiceRuntimeException(e.getMessage());
                }
            }
        };
        ThreadManager.getInstance().execute(timerTask);
    }
}
