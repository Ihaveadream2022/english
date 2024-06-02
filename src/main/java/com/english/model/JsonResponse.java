package com.english.model;

import java.util.HashMap;

public class JsonResponse extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "message";
    public static final String DATA_TAG = "data";

    public JsonResponse() {}

    public JsonResponse(int code, String msg, Object data)
    {
        super.put(MSG_TAG, msg);
        super.put(CODE_TAG, code);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    public static JsonResponse success()
    {
        return new JsonResponse(1, "操作成功", null);
    }

    public static JsonResponse success(String msg)
    {
        return new JsonResponse(1, msg, null);
    }

    public static JsonResponse success(Object data)
    {
        return new JsonResponse(1, "操作成功", data);
    }

    public static JsonResponse error()
    {
        return new JsonResponse(0, "操作失败", null);
    }

    public static JsonResponse error(String msg)
    {
        return new JsonResponse(0, msg, null);
    }

    public static JsonResponse warn(String msg)
    {
        return new JsonResponse(2, msg, null);
    }
}
