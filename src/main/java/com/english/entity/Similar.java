package com.english.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

public class Similar {

    private Long id;

    @NotBlank(message="itemIds is not blank")
    @Length(min=1, max=512, message="itemIds length is 1-512")
    private String itemIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }
}
