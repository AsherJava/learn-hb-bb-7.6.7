/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataservice.core.access;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UnitPermission
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, DimensionValue> masterKey;
    private List<String> resourceIds;

    public Map<String, DimensionValue> getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(Map<String, DimensionValue> masterKey) {
        this.masterKey = masterKey;
    }

    public List<String> getResourceIds() {
        return this.resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }
}

