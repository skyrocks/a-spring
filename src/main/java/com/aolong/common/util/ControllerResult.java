package com.aolong.common.util;

import java.util.HashMap;
import java.util.Map;

public class ControllerResult {

    private static final String SUCCESS_KEY = "success";
    private static final String DATA_KEY = "data";
    private static final String ERROR_KEY = "errDesc";

    /**
     * 取得执行成功的返回
     */
    public static String getSuccessResult()
    {
        return getJsonResult(null, true, "");
    }

    /**
     * 取得执行成功的返回
     */
    public static <T> String getSuccessResult(T model)
    {
        return getJsonResult(model, true, "");
    }

    /**
     * 取得执行失败的返回
     */
    public static String getErrorResult(String errDesc)
    {
        return getJsonResult(null, false, errDesc);
    }

    /**
     * 取得执行失败的返回
     */
    public static <T> String getErrorResult(T model, String errDesc)
    {
        return getJsonResult(model, false, errDesc);
    }

    public static <T> String getJsonResult(T model, boolean success, String errDesc)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(SUCCESS_KEY, success);
        map.put(DATA_KEY, (model==null)?"":model);
        map.put(ERROR_KEY, (errDesc==null)?"":errDesc);
        return JsonUtil.toJson(map);
    }
}
