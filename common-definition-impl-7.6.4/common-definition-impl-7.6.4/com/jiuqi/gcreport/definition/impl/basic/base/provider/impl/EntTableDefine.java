/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.provider.impl;

import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntTableDefine {
    private String code;
    private String name;
    private String title;
    private int type;
    private String bizKey;
    private Map<String, EntFieldDefine> fields;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, EntFieldDefine> getFields() {
        return this.fields;
    }

    public void setFields(Map<String, EntFieldDefine> fields) {
        this.fields = fields;
    }

    public List<EntFieldDefine> getAllFields() {
        return new ArrayList<EntFieldDefine>(this.fields.values());
    }

    public EntFieldDefine getField(String fieldName) {
        return this.fields.get(fieldName);
    }

    public void addField(EntFieldDefine field) {
        if (this.fields == null) {
            this.fields = new HashMap<String, EntFieldDefine>();
        }
        this.fields.put(field.getName(), field);
    }

    public String getBizKey() {
        return this.bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

