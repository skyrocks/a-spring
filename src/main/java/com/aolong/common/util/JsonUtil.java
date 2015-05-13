package com.aolong.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aolong.common.exception.BusinessException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static <T> String toJson(T model){
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(model);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
    public static <T> T toModel(String json, Class<T> classOfT){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return (T)objectMapper.readValue(json, classOfT);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        
    }
    
    public static <T> List<T> toList(String json, Class<T> classOfT){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(List.class,List.class, classOfT); 
            return objectMapper.readValue(json,javaType);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    
    public static void main(String[] arg){
        
        String namespace = "com.aolong.core.user.model.UserModel";
        System.out.println(namespace.replaceFirst(".model.", ".mapper.") .replaceFirst("Model$", "Mapper"));
        
        JsonTest2Model jsonModel200 = new JsonTest2Model();
        jsonModel200.setName("json model 200");
        JsonTest2Model jsonModel201 = new JsonTest2Model();
        jsonModel201.setName("json model 201");
        JsonTest2Model jsonModel202 = new JsonTest2Model();
        jsonModel202.setName("json model 202");
        List<JsonTest2Model> list = new ArrayList<JsonTest2Model>();
        list.add(jsonModel201);
        list.add(jsonModel202);
        JsonTestModel jsonModel = new JsonTestModel();
        jsonModel.setName("json model");
        jsonModel.setTest2(jsonModel200);
        jsonModel.setTest2s(list);
        
        String json = JsonUtil.toJson(jsonModel);
        System.out.println("model->json = " + json);
        
        JsonTestModel dd = JsonUtil.toModel(json, JsonTestModel.class);
        System.out.println("json->model = " + dd.getTest2().getName());
        System.out.println("json->model = " + dd.getTest2s().get(1).getName());
        
        
        JsonTest2Model jsonModel203 = new JsonTest2Model();
        jsonModel203.setName("json model 203");
        JsonTest2Model jsonModel204 = new JsonTest2Model();
        jsonModel204.setName("json model 204");
        JsonTest2Model jsonModel205 = new JsonTest2Model();
        jsonModel205.setName("json model 205");
        list = new ArrayList<JsonTest2Model>();
        list.add(jsonModel204);
        list.add(jsonModel205);
        JsonTestModel jsonModel_a = new JsonTestModel();
        jsonModel_a.setName("json model a");
        jsonModel_a.setTest2(jsonModel203);
        jsonModel_a.setTest2s(list);
        
        List<JsonTestModel> list_a = new ArrayList<JsonTestModel>();
        list_a.add(jsonModel);
        list_a.add(jsonModel_a);
        
        json = JsonUtil.toJson(list_a);
        System.out.println("List<model>->json = " + json);
     
        list_a = JsonUtil.toList(json, JsonTestModel.class);
        System.out.println("json->List<model> = " + list_a.get(1).getName());
        System.out.println("json->List<model> = " + list_a.get(1).getTest2().getName());
        System.out.println("json->List<model> = " + list_a.get(1).getTest2s().get(1).getName());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        System.out.println("map->json = " + JsonUtil.toJson(map));
        map = JsonUtil.toMap(JsonUtil.toJson(map));
        System.out.println("json->map  = " + map.get("key1").toString());
        
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("key3", "value3");
        map2.put("key4", "value4");
        
        List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
        listMap.add(map);
        listMap.add(map2);
        System.out.println("List<map>->json = " + JsonUtil.toJson(listMap));
        
        @SuppressWarnings("rawtypes")
        List<Map> mm = JsonUtil.toList(JsonUtil.toJson(listMap), Map.class);
        System.out.println("json -> List<map>= " + mm.get(1).get("key3"));
    }
}
