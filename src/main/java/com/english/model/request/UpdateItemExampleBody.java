package com.english.model.request;

import com.english.entity.ItemExample;
import java.util.List;

public class UpdateItemExampleBody {

    private String name;

    private List<ItemExample> examples;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemExample> getExamples() {
        return examples;
    }

    public void setExamples(List<ItemExample> examples) {
        this.examples = examples;
    }
}
