package com.english.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

public class Grammar {

    private Long id;

    private Byte sort;

    @NotBlank(message = "name is required")
    @Length(min = 0, max = 64, message = "name length is between 0 and 64")
    private String name;

    @Length(min = 0, max = 10240, message = "content length is between 0 and 10240")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}