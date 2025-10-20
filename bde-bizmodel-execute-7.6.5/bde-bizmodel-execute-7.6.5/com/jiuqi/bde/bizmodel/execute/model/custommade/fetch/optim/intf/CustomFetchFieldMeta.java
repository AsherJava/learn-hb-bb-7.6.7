/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf;

import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchField;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.common.base.BusinessRuntimeException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomFetchFieldMeta {
    private boolean includeCtField = false;
    private boolean includeAvgField = false;
    private Map<String, CustomFetchField> fetchFieldMap = new LinkedHashMap<String, CustomFetchField>();

    public boolean isIncludeCtField() {
        return this.includeCtField;
    }

    public void setIncludeCtField(boolean includeCtField) {
        this.includeCtField = includeCtField;
    }

    public boolean isIncludeAvgField() {
        return this.includeAvgField;
    }

    public void setIncludeAvgField(boolean includeAvgField) {
        this.includeAvgField = includeAvgField;
    }

    public Map<String, CustomFetchField> getFetchFieldMap() {
        return this.fetchFieldMap;
    }

    public CustomFetchField getFetchField(String fieldCode) {
        if (this.fetchFieldMap.get(fieldCode) == null) {
            throw new BusinessRuntimeException(String.format("\u53d6\u6570\u5b57\u6bb5\u3010%1$s\u3011\u6ca1\u6709\u5728\u9884\u671f\u7684\u67e5\u8be2\u7ed3\u679c\u5185\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u914d\u7f6e\u662f\u5426\u6b63\u786e", fieldCode));
        }
        return this.fetchFieldMap.get(fieldCode);
    }

    public ResultColumnType getFetchFieldType(String fieldCode) {
        return this.getFetchField(fieldCode).getColType();
    }

    public AggregateFuncEnum getFetchFieldFunc(String fieldCode) {
        return this.getFetchField(fieldCode).getFunc();
    }

    public void addFetchField(CustomFetchField fetchField) {
        this.fetchFieldMap.put(fetchField.getCode(), fetchField);
    }

    public String toString() {
        return "CustomFetchFieldMeta [includeCtField=" + this.includeCtField + ", includeAvgField=" + this.includeAvgField + ", fetchFieldMap=" + this.fetchFieldMap + "]";
    }
}

