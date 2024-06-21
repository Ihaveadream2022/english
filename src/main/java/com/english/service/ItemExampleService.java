package com.english.service;

import com.english.entity.Item;
import com.english.entity.ItemExample;
import java.util.List;

public interface ItemExampleService {

    public void update(String name, List<ItemExample> itemExampleList);

    public void writeJSONFile(Item item, Integer index);
}