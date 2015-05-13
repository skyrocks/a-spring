package com.aolong.common.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aolong.common.model.PageModel;
import com.aolong.common.model.PageParamsModel;

@Repository
public class CommonDao extends SqlSessionDaoSupport
{
    @Autowired(required=false)
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        super.setSqlSessionFactory(sqlSessionFactory);
    }
    
	public <T> T selectOne(String method) 
	{
	    return this.getSqlSession().selectOne(method);
	} 
	
	public <T> T selectOne(String method, Object params) 
    {
	    return this.getSqlSession().selectOne(method, params);
    }
	
	public <T> List<T> selectList(String method) 
	{
	    return this.getSqlSession().selectList(method);
	} 
	
	public <T> List<T> selectList(String method,Object params) 
	{
	    return this.getSqlSession().selectList(method, params);
	} 
	
	public <T> PageModel selectPage(String method, PageParamsModel pageParams, Map<String, Object> params)
	{
	    PageModel page = new PageModel();
        params.put("pageParams", pageParams);
        params.put("pageModel",page);
        List<T> list = this.getSqlSession().selectList(method,params );
        page.setData(list);
        return page;
	}
	
	public <T> PageModel selectPage(String method,PageParamsModel pageParams)
	{
	    Map<String, Object> _map = new HashMap<String, Object>();
        return this.selectPage(method, pageParams, _map);
	}
	
	public int insert(String method, Object params)
	{
	    return this.getSqlSession().insert(method, params);
	}
	
	public int update(String method, Object params)
	{
	    return this.getSqlSession().update(method, params);
	}
	
	public int delete(String method, Object params)
	{
        return this.getSqlSession().delete(method, params);
	}

}
