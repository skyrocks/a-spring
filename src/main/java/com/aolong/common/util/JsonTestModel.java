package com.aolong.common.util;

import java.util.List;

public class JsonTestModel {

    private String name;
    private JsonTest2Model test2;
    private List<JsonTest2Model> test2s;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonTest2Model getTest2() {
        return test2;
    }

    public void setTest2(JsonTest2Model test2) {
        this.test2 = test2;
    }

    public List<JsonTest2Model> getTest2s() {
        return test2s;
    }

    public void setTest2s(List<JsonTest2Model> test2s) {
        this.test2s = test2s;
    }
    
}
