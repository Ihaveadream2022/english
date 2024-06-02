package com.english.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class User extends Base
{
    private static final long serialVersionUID = 1L;

    private static final long superId = 1L;

    private Long id;

    @NotBlank(message = "账号不能为空")
    @Size(min = 0, max = 32, message = "账号长度不能超过32个字符")
    private String username;

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 64, message = "邮箱长度不能超过64个字符")
    private String email;

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String phone;

    private String avatar;

    private byte gender;

    private String password;

    private String lastLoginIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    public User() {}

    public User(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Byte getGender()
    {
        return gender;
    }

    public void setGender(Byte gender)
    {
        this.gender = gender;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getLastLoginIp()
    {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp)
    {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime()
    {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @JsonIgnore
    public boolean isAdmin()
    {
        return this.id != null && User.superId == this.id;
    }

    @JsonIgnore
    public static boolean isAdmin(Long id)
    {
        return id != null && User.superId == id;
    }

    @Override
    public String toString()
    {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", avatar='" + avatar + '\'' +
            ", gender=" + gender +
            ", password='" + password + '\'' +
            ", lastLoginIp='" + lastLoginIp + '\'' +
            ", lastLoginTime=" + lastLoginTime +
            '}';
    }
}
