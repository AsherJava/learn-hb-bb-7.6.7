/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.entity.engine.executors;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.definitions.TableRunInfo;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;

public class QueryInfo {
    private DimensionValueSet masterKeys;
    private TableRunInfo tableRunInfo;
    private EntityQueryParam queryParam;

    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        this.masterKeys = masterKeys;
    }

    public TableRunInfo getTableRunInfo() {
        return this.tableRunInfo;
    }

    public void setTableRunInfo(TableRunInfo tableRunInfo) {
        this.tableRunInfo = tableRunInfo;
    }

    public EntityQueryParam getQueryParam() {
        return this.queryParam;
    }

    public void setQueryParam(EntityQueryParam queryParam) {
        this.queryParam = queryParam;
    }
}

