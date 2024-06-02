package com.english.util;

public class StringUtil
{
    /**
     * 如果字符串为空则返回默认值
     * @param value
     * @param defaultValue
     * @return
     */
    public static String valueDefault(String value, String defaultValue)
    {
        return !isEmpty(value)? value : defaultValue;
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        return str == null || str.trim().length() == 0;
    }
}
