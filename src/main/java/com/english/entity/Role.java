package com.english.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Role extends Base
{
    private Long id;

    @NotBlank(message = "name不能为空")
    @Size(min = 0, max = 32, message = "name长度不能超过32个字符")
    private String name;

    @NotBlank(message = "key不能为空")
    @Size(min = 0, max = 32, message = "key长度不能超过32个字符")
    private String key;

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
}
