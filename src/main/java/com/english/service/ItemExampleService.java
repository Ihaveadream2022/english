package com.english.service;

import com.english.entity.Item;
import com.english.entity.ItemExample;

public interface ItemExampleService {
    public ItemExample findByName(String name);

    public Long insert(ItemExample itemExample);

    public Long update(ItemExample itemExample);

    public void writeJSONFile(Item item, Integer index);
}

