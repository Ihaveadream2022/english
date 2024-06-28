package com.english.model.enums;

public enum QueryOrder {

    ASC("ASC"), DESC("DESC");

    private final String value;

    QueryOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
