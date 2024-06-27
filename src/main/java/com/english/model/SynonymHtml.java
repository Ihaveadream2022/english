package com.english.model;

import java.util.List;

public class SynonymHtml {

    private String meaning;

    private List<ItemHtml> itemHtmlList;

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public List<ItemHtml> getItemHtmlList() {
        return itemHtmlList;
    }

    public void setItemHtmlList(List<ItemHtml> itemHtmlList) {
        this.itemHtmlList = itemHtmlList;
    }
}