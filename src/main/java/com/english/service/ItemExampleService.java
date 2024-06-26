package com.english.service;

import com.english.entity.ItemExample;
import java.util.List;

public interface ItemExampleService {
    public ItemExample findByName(String name);

    public Long insert(ItemExample itemExample);

    public Long update(ItemExample itemExample);

    public void writeJSONFile(ItemExample itemExample, Integer index);
}

