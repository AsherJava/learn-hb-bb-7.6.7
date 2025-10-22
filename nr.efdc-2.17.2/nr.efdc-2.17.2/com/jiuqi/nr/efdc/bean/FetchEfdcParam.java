/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.bean;

import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import java.util.Map;

public class FetchEfdcParam {
    private QueryObjectImpl queryObj;
    private Map<String, String> dimMap;
    private String entityId;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public QueryObjectImpl getQueryObj() {
        return this.queryObj;
    }

    public void setQueryObj(QueryObjectImpl queryObj) {
        this.queryObj = queryObj;
    }

    public Map<String, String> getDimMap() {
        return this.dimMap;
    }

    public void setDimMap(Map<String, String> dimMap) {
        this.dimMap = dimMap;
    }
}

