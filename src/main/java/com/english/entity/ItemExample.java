package com.english.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

public class ItemExample {

    private Long id;

    @NotBlank(message = "name is required")
    @Length(min = 1, max = 64, message = "name length is between 1 and 64")
    private String name;

    @Length(min = 0, max = 10240, message = "examples length is between 0 and 10240")
    private String examples;

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

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}