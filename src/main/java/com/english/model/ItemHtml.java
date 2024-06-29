package com.english.model;

import java.util.List;

public class ItemHtml {

    private String en;

    private String cn;

    private String tts;

    private List<String[]> meanings;

    private List<KeyValue> examples;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getTts()
    {
        return tts;
    }

    public void setTts(String tts)
    {
        this.tts = tts;
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
