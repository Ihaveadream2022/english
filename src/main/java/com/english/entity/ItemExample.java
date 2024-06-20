package com.english.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

public class ItemExample {

    private Long id;

    @NotBlank(message = "name is required")
    @Length(min = 1, max = 64, message = "name length is between 0 and 64")
    private String name;

    @NotBlank(message = "key is required")
    @Length(min = 1, max = 64, message = "key length is between 0 and 64")
    private String key;

    @NotBlank(message = "example is required")
    @Length(min = 1, max = 1024, message = "example length is between 0 and 1024")
    private String example;

    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}