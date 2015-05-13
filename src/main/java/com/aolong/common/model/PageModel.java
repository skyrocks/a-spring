package com.aolong.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aolong.common.util.JsonUtil;

/**
 * 分页处理实体
 * 
 */
public class PageModel
{

    private List<? extends Object> data = new ArrayList<Object>();
    private int total;
    private boolean success = true;
    private Map<String,? extends Object> extend;

    /**
     * 序列化分页实体为json
     * 
     * @return
     * @throws Exception
     */
    public String toString()
    {
        Map<String, Object> _map = new HashMap<String, Object>();

        _map.put("success", true);
        _map.put("total", total);
        _map.put("data", data);
        
        if (extend != null){
            _map.put("extend", extend);
        }

        return JsonUtil.toJson(_map);
    }

    public List<? extends Object> getData()
    {
        return data;
    }

    public void setData(List<? extends Object> data)
    {
        this.data = data;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public Map<String,? extends Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String,? extends Object> extend) {
        this.extend = extend;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
