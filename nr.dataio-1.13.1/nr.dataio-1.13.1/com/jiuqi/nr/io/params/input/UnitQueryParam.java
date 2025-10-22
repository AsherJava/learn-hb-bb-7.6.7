/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.dataentity_ext.dto.PageInfo
 */
package com.jiuqi.nr.io.params.input;

import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.PageInfo;
import java.util.List;

public class UnitQueryParam {
    private String dwSchemeKey;
    private EntityDataType[] types;
    private PageInfo pageInfo;
    private List<String> entityIds;

    public String getDwSchemeKey() {
        return this.dwSchemeKey;
    }

    public void setDwSchemeKey(String dwSchemeKey) {
        this.dwSchemeKey = dwSchemeKey;
    }

    public EntityDataType[] getTypes() {
        return this.types;
    }

    public void setTypes(EntityDataType[] types) {
        this.types = types;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<String> getEntityIds() {
        return this.entityIds;
    }

    public void setEntityIds(List<String> entityIds) {
        this.entityIds = entityIds;
    }
}

