package com.english.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequestBody
{
    /**
     * 用户名
     */
    @Size(min = 2, max = 32, message = "用户名长度为2-32")
    private String username;

    /**
     * 密码
     */
    @Size(min = 2, max = 32, message = "密码长度为2-32")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * UUID标识
     */
    @NotBlank(message = "UUID不能为空")
    private String uuid;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
