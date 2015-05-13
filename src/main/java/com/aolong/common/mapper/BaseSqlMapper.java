package com.aolong.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BaseSqlMapper extends SqlMapper {
	
	public <T> void add(T model) throws DataAccessException;
    
    public <T> void modify(T model) throws DataAccessException;
    
    public <T> void remove(Object[] ids) throws DataAccessException;
    
    public <T> T findById(String id) throws DataAccessException;
    
    public <T> List<T> findAll() throws DataAccessException;
    
    /**
     * @param params 基于分页查询时 map包括基本元素 pageEntity
     * @return
     * @throws DataAccessException
     */
    public <T> List<T> findAll(Map<String, Object> params) throws DataAccessException;
}
