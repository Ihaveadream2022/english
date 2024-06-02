package com.english.service.impl;

import com.english.entity.Word;
import com.english.mapper.WordMapper;
import com.english.model.request.QueryCondition;
import com.english.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class WordServiceImpl implements WordService
{
    @Autowired
    WordMapper wordMapper;

    public HashMap<String, Object> pageList(QueryCondition queryCondition)
    {
        HashMap<String, Object> data = new HashMap<>();

        List<Word> list = wordMapper.selectLimited(queryCondition);
        Long total = wordMapper.count(queryCondition);

        data.put("list", list);
        data.put("total", total);
        data.put("pageSize", queryCondition.getPageSize());
        data.put("pageNo", queryCondition.getPageNo());

        return data;
    }

    public Word findByWord(String word)
    {
        return wordMapper.findByWord(word);
    }

    @Override
    @Transactional
    public Long insert(Word word)
    {
        return wordMapper.insert(word);
    }

    @Override
    public Boolean wordExist(Word word)
    {
        Long wordId = word.getId() == null? -1L : word.getId();
        Word one = wordMapper.findByWord(word.getWord());
        return one != null && one.getId().longValue() != wordId.longValue();
    }
}
