package com.english.model.request;

public class UserQueryCondition extends QueryCondition
{
    private String username;

    private String phone;

    private String email;

    private Integer gender;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserQueryCondition{" +
            "username='" + username + '\'' +
            ", phone='" + phone + '\'' +
            ", email='" + email + '\'' +
            ", gender=" + gender +
            "} " + super.toString();
    }
}
