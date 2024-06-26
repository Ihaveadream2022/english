package com.english.model;

import java.util.List;

public class ItemExampleHtml {

    private String name;

    private List<String[]> meanings;

    private List<KeyValue> examples;

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

    public List<KeyValue> getExamples() {
        return examples;
    }

    public void setExamples(List<KeyValue> examples) {
        this.examples = examples;
    }
}