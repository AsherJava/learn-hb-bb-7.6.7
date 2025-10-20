/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf;

import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;

public class CustomFetchField {
    private String code;
    private String name;
    private AggregateFuncEnum func;
    private ResultColumnType colType;

    public CustomFetchField(String code, String name, AggregateFuncEnum func, ResultColumnType colType) {
        this.code = code;
        this.name = name;
        this.func = func;
        this.colType = colType;
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

    public AggregateFuncEnum getFunc() {
        return this.func;
    }

    public void setFunc(AggregateFuncEnum func) {
        this.func = func;
    }

    public ResultColumnType getColType() {
        return this.colType;
    }

    public void setColType(ResultColumnType colType) {
        this.colType = colType;
    }

    public String toString() {
        return "CustomFetchField [code=" + this.code + ", name=" + this.name + ", func=" + this.func + ", colType=" + (Object)((Object)this.colType) + "]";
    }
}

