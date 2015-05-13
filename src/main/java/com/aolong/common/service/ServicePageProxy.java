package com.aolong.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aolong.common.mapper.BaseSqlMapper;
import com.aolong.common.model.PageModel;
import com.aolong.common.model.PageParamsModel;

public class ServicePageProxy {

	public static <T> PageModel findAll(BaseSqlMapper mapper,PageParamsModel pageParams)
	{
	    PageModel pageModel = new PageModel();

        Map<String, Object> params = createNewParams(pageModel, pageParams, null);
        
        List<T> list = mapper.findAll(params);
        
        pageModel.setData(list);
        
        return pageModel;
        
	}
	
	public static <T> PageModel findAll(BaseSqlMapper mapper,PageParamsModel pageParams, Map<String, Object> mapFilter)
    {
	    PageModel pageModel = new PageModel();
	    
	    Map<String, Object> params = createNewParams(pageModel, pageParams, mapFilter);
        
	    List<T> list = mapper.findAll(params);
        
        pageModel.setData(list);
        
        return pageModel;
        
    }
	
	public static Map<String, Object> createNewParams(PageModel pageModel,PageParamsModel pageParams, Map<String, Object> mapFilter)
	{
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageParams", pageParams);
        params.put("pageModel",pageModel);
        if (mapFilter != null)
        {
            for(String key : mapFilter.keySet())
            {
                params.put(key,mapFilter.get(key));
            }
        }
        
        return params;
	}
}
