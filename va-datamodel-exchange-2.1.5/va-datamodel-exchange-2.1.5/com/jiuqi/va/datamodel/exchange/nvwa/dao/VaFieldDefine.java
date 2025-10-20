/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.dao;

import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelFieldDefine;

public class VaFieldDefine {
    private String id;
    private String code;
    private String name;
    private DataModelFieldDefine.DbType type;
    private int[] size;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public DataModelFieldDefine.DbType getType() {
        return this.type;
    }

    public void setType(DataModelFieldDefine.DbType type) {
        this.type = type;
    }

    public int[] getSize() {
        return this.size;
    }

    public void setSize(int[] size) {
        this.size = size;
    }
}

