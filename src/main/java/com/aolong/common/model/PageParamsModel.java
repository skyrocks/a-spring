package com.aolong.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aolong.common.util.JsonUtil;

public class PageParamsModel {

	private int start = 0;
	private int limit;
	private int page;
	private String sort;
	private String filter;
	private List<FilterModel> filterList = new ArrayList<FilterModel>();
	private List<SortModel> sortList = new ArrayList<SortModel>();
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
		
		if (this.sort != null && this.sort.length() > 0){
			this.sortList = JsonUtil.toList(this.getSort(), SortModel.class);
		}
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
		
		if (this.filter != null && this.filter.length() > 0)
		{
		    List<FilterModel> filterList = getFilterList();
            filterList.clear();
            
            @SuppressWarnings("rawtypes")
            List<Map> params = JsonUtil.toList(this.filter, Map.class);
            
            for(int i = 0; i < params.size(); i++)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> param = (Map<String, Object>)params.get(i);

                if ( !param.containsKey("type") || !param.containsKey("field")){
                    continue;
                }
                
                String type = param.get("type").toString();
                Object value = param.get("value");
                
                FilterModel model = new FilterModel();
                model.setField(param.get("field").toString());
                model.setValue(value);
                model.setOperator(" = ");
                model.setType(type);
                
                if (type.equals("string")) 
                {
                    model.setOperator(" like ");
                    model.setValue("'%"+value+"%'");
                } 
                if (type.equals("numeric") || type.equals("date")) 
                {
                    String comparison = param.get("comparison").toString();
                    if (comparison.equals("lt")){
                        model.setOperator(" < ");
                    } 
                    if (comparison.equals("gt")) {
                        model.setOperator(" > ");
                    } 
                    if (comparison.equals("eq")) {
                        model.setOperator(" = ");
                    }
                } 
                if (type.equals("date")){
                    model.setValue("'"+value+"'");
                }
                if (type.equals("list"))
                {
                    model.setOperator(" in ");
                    StringBuffer sbValue = new StringBuffer();
                    sbValue.append(" ( ");
                    
                    List<Object> objList = JsonUtil.toList(value.toString(), Object.class);
                    for(int j = 0; j < objList.size(); j++)
                    {
                        Object obj = objList.get(j);
                        if (obj instanceof Integer)
                        {
                            sbValue.append(obj);
                        }
                        if (obj instanceof String)
                        {
                            sbValue.append("'");
                            sbValue.append(obj);
                            sbValue.append("'");
                        }
                        if (j < objList.size() - 1)
                        {
                            sbValue.append(",");
                        }
                    }
                    sbValue.append(" ) ");
                    model.setValue(sbValue.toString());
                }
                if (type.equals("boolean"))
                {
                    model.setValue(Boolean.valueOf(value.toString()) ? 1 : 0 );
                }
                filterList.add(model);
            }
		}
	}
	public List<SortModel> getSortList() {
		return sortList;
	}
	public void setSortList(List<SortModel> sortList) {
		this.sortList = sortList;
	}
	public List<FilterModel> getFilterList() {
		return filterList;
	}
	public void setFilterList(List<FilterModel> filterList) {
		this.filterList = filterList;
	}
	public PageParamsModel addSort(String property, String direction)
	{
		boolean bExists = false;
		for (SortModel sortEntity : this.sortList)
		{
			if (sortEntity.getField().trim().toLowerCase().equals(property.trim().toLowerCase()))
			{
				bExists = true;
				break;
			}
		}
		if (!bExists)
		{
			SortModel e = new SortModel();
			e.setField(property);
			e.setDirection(direction);
			this.sortList.add(e);
		}
		return this;
	}
	public PageParamsModel addSort(String property)
	{
		return addSort(property," ASC ");
	}
	public PageParamsModel replaceField(String oldField,String newField)
	{
		for(FilterModel e : this.getFilterList())
		{
			if (e.getField().equals(oldField))
			{
				if (newField==null||"".equals(newField))
                {
                    this.getFilterList().remove(e);
                }
                else
                {
                    e.setField(newField);
                }
				break;
			}
		}
		for(SortModel e : this.getSortList())
		{
			if (e.getField().equals(oldField))
			{
				if (newField==null||"".equals(newField))
				{
				    this.getSortList().remove(e);  
				}
				else
				{
				    e.setField(newField);
				}
				break;
			}
		}
		return this;
	}
}
