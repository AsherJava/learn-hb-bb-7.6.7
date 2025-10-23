/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 */
package com.jiuqi.nr.task.web.param;

import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;

public class UnitTreeDataParam {
    private String entityId;
    private String parent;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent == null ? "-" : parent;
    }

    public String getTableName() {
        return BaseDataAdapterUtil.isBaseData((String)this.entityId) ? this.entityId.substring(0, this.entityId.indexOf("@")) : null;
    }
}

