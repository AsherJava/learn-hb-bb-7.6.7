/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.dto;

import com.jiuqi.nr.definition.option.dto.InnerField;
import com.jiuqi.nr.definition.option.dto.ReferEntity;
import java.util.Map;

public class DimensionGroupDTO {
    private String entityId;
    private String groupKey;
    private String groupValue;
    private String filterKey;
    private String filterValue;
    private Map<String, InnerField> innerFieldMap;
    private Map<String, ReferEntity> referEntityMap;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getGroupValue() {
        return this.groupValue;
    }

    public void setGroupValue(String groupValue) {
        this.groupValue = groupValue;
    }

    public String getFilterKey() {
        return this.filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public String getFilterValue() {
        return this.filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public Map<String, ReferEntity> getReferEntityMap() {
        return this.referEntityMap;
    }

    public void setReferEntityMap(Map<String, ReferEntity> referEntityMap) {
        this.referEntityMap = referEntityMap;
    }

    public Map<String, InnerField> getInnerFieldMap() {
        return this.innerFieldMap;
    }

    public void setInnerFieldMap(Map<String, InnerField> innerFieldMap) {
        this.innerFieldMap = innerFieldMap;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}

