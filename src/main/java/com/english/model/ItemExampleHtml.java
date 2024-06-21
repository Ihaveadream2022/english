package com.english.model;

import com.english.entity.ItemExample;

import java.util.List;

public class ItemExampleHtml {

    private String name;

    private List<String[]> meanings;

    private List<ItemExample> examples;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String[]> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<String[]> meanings) {
        this.meanings = meanings;
    }

    public List<ItemExample> getExamples() {
        return examples;
    }

    public void setExamples(List<ItemExample> examples) {
        this.examples = examples;
    }
}