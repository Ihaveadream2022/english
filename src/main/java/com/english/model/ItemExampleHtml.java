package com.english.model;

import java.util.List;

public class ItemExampleHtml {

    private String name;

    private String example;

    private String key;

    private List<String[]> meaning;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String[]> getMeaning() {
        return meaning;
    }

    public void setMeaning(List<String[]> meaning) {
        this.meaning = meaning;
    }
}
