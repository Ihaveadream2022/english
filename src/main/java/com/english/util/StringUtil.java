package com.english.util;

public class StringUtil {

    // 如果字符串为空则返回默认值
    public static String valueDefault(String value, String defaultValue) {
        return !isEmpty(value)? value : defaultValue;
    }

    // 判断字符串是否为空
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    // To HTML
    public static String toHTML(String content) {
        content = content.replace("\r", "</p>");
        content = content.replace("\n", "<p>");
        content = content.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"); // abcdef
        content = content.replace("【", "<span class=\"main-point\">【");
        content = content.replace("】", "】</span>");
        content = content.replace("[", "<span class=\"missing\">[");
        content = content.replace("]", "]</span>");
        content = content + "</p>";

        return content;
    }

    // Replace Full-Width characters
    public static String replaceFullWidthString(String fullWithString) {
        if (fullWithString != null && !fullWithString.equals("")) {
            fullWithString = fullWithString.replace("，", ",");
            fullWithString = fullWithString.replace("。", ".");
            fullWithString = fullWithString.replace("；", ";");
            fullWithString = fullWithString.replace("）", ")");
            fullWithString = fullWithString.replace("（", "(");
            fullWithString = fullWithString.replace("、", ", ");
        }
        return fullWithString;
    }
}
