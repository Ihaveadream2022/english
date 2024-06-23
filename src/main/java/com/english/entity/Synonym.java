package com.english.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

public class Synonym {

    private Long id;

    @NotBlank(message = "meaning is not blank")
    @Length(min=1, max=64, message="meaning length is 1-64")
    private String meaning;

    @NotBlank(message = "items is not blank")
    @Length(min=1, max=10240, message="items is not blank")
    private String items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
