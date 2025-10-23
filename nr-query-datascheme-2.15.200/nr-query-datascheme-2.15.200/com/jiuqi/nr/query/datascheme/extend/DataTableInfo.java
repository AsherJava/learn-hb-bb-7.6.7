/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTableInfo {
    private String key;
    private String code;
    private String title;
    private String groupKey;
    private String dataSchemeKey;
    private String description;
    private String expression;
    private String srcTableKey;
    private DataTableType dataTableType;
    private String srcType;
    private Map<String, String> dimFieldMap = new HashMap<String, String>();
    private List<String> srcFieldCodes = new ArrayList<String>();

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getSrcTableKey() {
        return this.srcTableKey;
    }

    public void setSrcTableKey(String srcTableKey) {
        this.srcTableKey = srcTableKey;
    }

    public DataTableType getDataTableType() {
        return this.dataTableType;
    }

    public void setDataTableType(DataTableType dataTableType) {
        this.dataTableType = dataTableType;
    }

    public Map<String, String> getDimFieldMap() {
        return this.dimFieldMap;
    }

    public String getSrcType() {
        return this.srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public List<String> getSrcFieldCodes() {
        return this.srcFieldCodes;
    }
}

