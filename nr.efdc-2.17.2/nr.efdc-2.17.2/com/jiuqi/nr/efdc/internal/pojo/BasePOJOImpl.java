/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.internal.pojo;

import com.jiuqi.nr.efdc.pojo.BasePOJO;
import java.util.HashMap;
import java.util.Map;

public class BasePOJOImpl
implements BasePOJO {
    private String key;
    private String title;
    private String code;
    private Map<String, Object> valueMap = new HashMap<String, Object>();

    public void setValueMap(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Map<String, Object> getValueMap() {
        return this.valueMap;
    }

    @Override
    public Object getFieldValue(String key) {
        return this.valueMap.get(key);
    }

    public void setFieldValue(String key, Object title) {
        if (key == null) {
            throw new RuntimeException("\u952e\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        this.valueMap.put(key, title);
    }
}

