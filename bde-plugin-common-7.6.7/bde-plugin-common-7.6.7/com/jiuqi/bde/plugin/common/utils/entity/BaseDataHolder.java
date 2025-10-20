/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.bde.plugin.common.utils.entity;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.Map;

public class BaseDataHolder {
    private Map<String, Map<String, BaseDataDO>> baseDataTableNameToBaseDataInfoMap;

    public BaseDataHolder(Map<String, Map<String, BaseDataDO>> baseDataTableNameToBaseDataInfoMap) {
        this.baseDataTableNameToBaseDataInfoMap = baseDataTableNameToBaseDataInfoMap;
    }

    public Map<String, Map<String, BaseDataDO>> getBaseDataTableNameToBaseDataInfoMap() {
        return this.baseDataTableNameToBaseDataInfoMap;
    }

    public void setBaseDataTableNameToBaseDataInfoMap(Map<String, Map<String, BaseDataDO>> baseDataTableNameToBaseDataInfoMap) {
        this.baseDataTableNameToBaseDataInfoMap = baseDataTableNameToBaseDataInfoMap;
    }
}

