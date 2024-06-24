package com.english.model;

import java.util.List;

public class SynonymHtml {

    private String meaning;

    private List<SynonymHtmlItem> items;

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public List<SynonymHtmlItem> getItems() {
        return items;
    }

    public void setItems(List<SynonymHtmlItem> items) {
        this.items = items;
    }
}