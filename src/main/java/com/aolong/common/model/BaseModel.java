package com.aolong.common.model;

import com.aolong.common.util.JsonUtil;

public class BaseModel
{
    
	public String toJson(){
        return JsonUtil.toJson(this);
    }
	
    /**
     * 重写equals()必须遵循的准则：
     * • 对称性：如果x.equals(y)返回是“true”，那么y.equals(x)也应该返回是“true”。
     * • 反射性：x.equals(x)必须返回是“true”。
     * • 类推性：如果x.equals(y)返回是“true”，而且y.equals(z)返回是“true”，那么z.equals(x)也应该返回是“true”。
     * • 还有一致性：如果x.equals(y)返回是“true”，只要x和y内容一直不变，不管你重复x.equals(y)多少次，返回都是“true”。
     * • 任何情况下，x.equals(null)，永远返回是“false”；x.equals(和x不同类型的对象)永远返回是“false”。
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || this == null) return false;
        if (obj.getClass() != this.getClass()) return false;

        return this.toString().equals(obj.toString());
    }

}
