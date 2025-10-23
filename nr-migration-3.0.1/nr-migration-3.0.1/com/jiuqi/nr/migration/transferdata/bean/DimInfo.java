/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.migration.transferdata.bean.TransDimension;
import java.util.ArrayList;
import java.util.List;

public class DimInfo {
    private String name;
    private String entityId;
    private String value;
    private List<String> values = new ArrayList<String>();

    public DimInfo() {
    }

    public DimInfo(TransDimension dimension) {
        this.name = dimension.getName();
        this.entityId = dimension.getEntityId();
        this.value = dimension.getValue();
    }

    public DimInfo(String name, String entityId, String value) {
        this.name = name;
        this.entityId = entityId;
        this.value = value;
    }

    public DimInfo(String name, String entityId, List<String> values) {
        this.name = name;
        this.entityId = entityId;
        this.values = values;
    }

    public DimInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void addValue(String value) {
        if (!this.values.contains(value)) {
            this.values.add(value);
        }
    }
}

