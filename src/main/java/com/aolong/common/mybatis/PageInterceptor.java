/**
 * 
 */
package com.aolong.common.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.CallableStatementHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.aolong.common.model.FilterModel;
import com.aolong.common.model.PageModel;
import com.aolong.common.model.PageParamsModel;
import com.aolong.common.model.SortModel;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {

    private static final String MYSQL = "com.mysql.jdbc.Driver";
    private static final String MSSQL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    private String dialect = MYSQL;  

	public Object intercept(Invocation invocation) throws Throwable {
		
		RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
        if ( ReflectUtil.getFieldValue(statement, "delegate").getClass().toString().equals(CallableStatementHandler.class.toString()) ){
            return invocation.proceed();
        }
        PreparedStatementHandler handler = (PreparedStatementHandler) ReflectUtil.getFieldValue(statement, "delegate");
		
        ParameterHandler parameters = (ParameterHandler)ReflectUtil.getFieldValue(handler, "parameterHandler");
		Object oParams = parameters.getParameterObject();
		
    	if (oParams instanceof Map<?, ?>)
    	{
			@SuppressWarnings("unchecked")
			Map<String,Object> pMap = (Map<String,Object>)oParams;
			if (!pMap.containsKey("pageParams") || !pMap.containsKey("pageModel"))
			{
				return invocation.proceed();
			}
    	}
    	else {
    		return invocation.proceed();
		}
		
    	Connection connection = (Connection) invocation.getArgs()[0];  
		MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(handler, "mappedStatement"); 
        BoundSql boundSql = statement.getBoundSql();

        setPageTotal(oParams, boundSql,connection, mappedStatement);
        if (MSSQL.equals(this.dialect)){
            setPageMssql(oParams,boundSql);
        }else if (MYSQL.equals(this.dialect)){
            setPageMysql(oParams,boundSql);
        }

		return invocation.proceed();
	}
	
	private void setPageTotal(Object oParams, BoundSql boundSql, Connection connection,MappedStatement mappedStatement) throws Throwable
	{
		@SuppressWarnings("unchecked")
		Map<String,Object> pMap = (Map<String,Object>)oParams;
		PageParamsModel pageParams = (PageParamsModel)(pMap.get("pageParams"));
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from ( ");
		sbSql.append(boundSql.getSql());
		sbSql.append(") _t ");
    	
    	StringBuffer sbFilter = new StringBuffer();
		for(FilterModel e : pageParams.getFilterList())
		{
			sbFilter.append(" and ");
			sbFilter.append(e.getField());
			sbFilter.append(e.getOperator());
			sbFilter.append(e.getValue());
		}
		if (sbFilter.length() > 0)
		{
			sbSql.append(" where 1 = 1 ");
			sbSql.append(sbFilter);
		}

		ReflectUtil.setFieldValue(boundSql, "sql", sbSql.toString());
		
        StringBuffer sbCountSql = new StringBuffer();
        sbCountSql.append("SELECT COUNT(0) FROM ( ");
        sbCountSql.append(sbSql.toString());
        sbCountSql.append( ") AS tmp ");  
        String countSql = sbCountSql.toString();
        PreparedStatement countStmt = connection.prepareStatement(countSql);  
        setParameters(countStmt, mappedStatement, boundSql, oParams);  
        ResultSet rs = countStmt.executeQuery();  
        int count = 0;  
        if (rs.next()) {  
            count = rs.getInt(1);  
        }  
        rs.close();  
        countStmt.close();  
        
        PageModel pageMode = (PageModel)(pMap.get("pageModel"));
        pageMode.setTotal(count);
	}
	
    private void setPageMssql(Object oParams, BoundSql boundSql) throws Throwable {
    	
    	@SuppressWarnings("unchecked")
		Map<String,Object> pMap = (Map<String,Object>)oParams;
		PageParamsModel pageParams = (PageParamsModel)(pMap.get("page"));

    	List<SortModel> sortList = pageParams.getSortList();
		StringBuffer sbOrder = new StringBuffer();
    	if (sortList.size() > 0)
    	{
    		sbOrder.append(" order by ");
			for(int i = 0; i < sortList.size(); i++)
			{
				 SortModel e = sortList.get(i);
				 sbOrder.append(e.getField());
				 sbOrder.append(" ");
				 sbOrder.append(e.getDirection());
				if (i < sortList.size() - 1) {
					sbOrder.append(", " );
				}
			}
    	}
    	else 
    	{
    	    sbOrder.append(" order by CURRENT_TIMESTAMP ");
		}
    	
    	String sql = boundSql.getSql();
    	StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append("select * from ( ");
		pagingSelect.append("select * ");
		pagingSelect.append(", ROW_NUMBER() OVER ( "); 
		pagingSelect.append(sbOrder.toString());
		pagingSelect.append(" ) AS RowNo ");
		pagingSelect.append(" from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) _tableTmp ) _table where _table.RowNo <= ");
		pagingSelect.append(pageParams.getLimit() + pageParams.getStart());
		pagingSelect.append(" and _table.RowNo  > ");
		pagingSelect.append(pageParams.getStart());

		ReflectUtil.setFieldValue(boundSql, "sql", pagingSelect.toString());
	}

    private void setPageMysql(Object oParams, BoundSql boundSql) throws Throwable {
        
        @SuppressWarnings("unchecked")
        Map<String,Object> pMap = (Map<String,Object>)oParams;
        PageParamsModel pageParams = (PageParamsModel)(pMap.get("pageParams"));
        
        List<SortModel> sortList = pageParams.getSortList();
        StringBuffer sbOrder = new StringBuffer();
        if (sortList.size() > 0)
        {
            sbOrder.append(" order by ");
            for(int i = 0; i < sortList.size(); i++)
            {
                 SortModel e = sortList.get(i);
                 sbOrder.append(e.getField());
                 sbOrder.append(" ");
                 sbOrder.append(e.getDirection());
                if (i < sortList.size() - 1) {
                    sbOrder.append(", " );
                }
            }
        }

        String sql = boundSql.getSql();
        StringBuffer pagingSelect = new StringBuffer();
        pagingSelect.append("select * from ( ");
        pagingSelect.append(sql);
        pagingSelect.append(" ) _tableTmp ");
        pagingSelect.append(sbOrder.toString());
        pagingSelect.append(" limit ");
        pagingSelect.append(pageParams.getStart());
        pagingSelect.append(",");
        pagingSelect.append(pageParams.getLimit());

        ReflectUtil.setFieldValue(boundSql, "sql", pagingSelect.toString());
    }

    private void setParameters(PreparedStatement ps,  
            MappedStatement mappedStatement, BoundSql boundSql,  
            Object parameterObject) throws SQLException {  
        ErrorContext.instance().activity("setting parameters")  
                .object(mappedStatement.getParameterMap().getId());  
        List<ParameterMapping> parameterMappings = boundSql  
                .getParameterMappings();  
        if (parameterMappings != null) {  
            Configuration configuration = mappedStatement.getConfiguration();  
            TypeHandlerRegistry typeHandlerRegistry = configuration  
                    .getTypeHandlerRegistry();  
            MetaObject metaObject = parameterObject == null ? null  
                    : configuration.newMetaObject(parameterObject);  
            for (int i = 0; i < parameterMappings.size(); i++) {  
                ParameterMapping parameterMapping = parameterMappings.get(i);  
                if (parameterMapping.getMode() != ParameterMode.OUT) {  
                    Object value;  
                    String propertyName = parameterMapping.getProperty();  
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);  
                    if (parameterObject == null) {  
                        value = null;  
                    } else if (typeHandlerRegistry  
                            .hasTypeHandler(parameterObject.getClass())) {  
                        value = parameterObject;  
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {  
                        value = boundSql.getAdditionalParameter(propertyName);  
                    } else if (propertyName  
                            .startsWith(ForEachSqlNode.ITEM_PREFIX)  
                            && boundSql.hasAdditionalParameter(prop.getName())) {  
                        value = boundSql.getAdditionalParameter(prop.getName());  
                        if (value != null) {  
                            value = configuration.newMetaObject(value)  
                                    .getValue(  
                                            propertyName.substring(prop  
                                                    .getName().length()));  
                        }  
                    } else {  
                        value = metaObject == null ? null : metaObject  
                                .getValue(propertyName);  
                    }  
                    @SuppressWarnings("unchecked")  
                    TypeHandler<Object> typeHandler = (TypeHandler<Object>) parameterMapping  
                            .getTypeHandler();  
                    if (typeHandler == null) {  
                        throw new ExecutorException(  
                                "There was no TypeHandler found for parameter "  
                                        + propertyName + " of statement "  
                                        + mappedStatement.getId());  
                    }  
                    typeHandler.setParameter(ps, i + 1, value,  
                            parameterMapping.getJdbcType());  
                }  
            }  
        }  
    }
    

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	    //this.dialect = properties.getProperty("dialect","mysql");
	}

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

}