/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.fielddatacrud.impl.dto;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Set;

public class AccessDTO {
    private Set<DimensionValueSet> accessMasterKeys;
    private Set<DimensionValueSet> noAccessMasterKeys;

    public Set<DimensionValueSet> getAccessMasterKeys() {
        return this.accessMasterKeys;
    }

    public void setAccessMasterKeys(Set<DimensionValueSet> accessMasterKeys) {
        this.accessMasterKeys = accessMasterKeys;
    }

    public Set<DimensionValueSet> getNoAccessMasterKeys() {
        return this.noAccessMasterKeys;
    }

    public void setNoAccessMasterKeys(Set<DimensionValueSet> noAccessMasterKeys) {
        this.noAccessMasterKeys = noAccessMasterKeys;
    }

    public String toString() {
        return "AccessDTO{accessMasterKeys=" + this.accessMasterKeys + ", noAccessMasterKeys=" + this.noAccessMasterKeys + '}';
    }
}

