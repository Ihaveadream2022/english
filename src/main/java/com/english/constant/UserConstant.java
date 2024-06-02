package com.english.constant;

public class UserConstant
{
    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 32;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 2;
    public static final int PASSWORD_MAX_LENGTH = 32;

    /**
     * 用户状态 0正常 1禁用 2删除
     */
    public static final int STATUS_ACTIVE = 0;
    public static final int STATUS_FORBIDDEN = 1;
    public static final int STATUS_DELETED = 2;
}
