/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.io.Serializable;

public class StatusResultCacheKey
implements Serializable {
    private DimensionValueSet masterKey;
    private String formKey;

    public StatusResultCacheKey(DimensionValueSet masterKey, String formKey) {
        this.masterKey = masterKey;
        this.formKey = formKey;
    }

    public DimensionValueSet getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionValueSet masterKey) {
        this.masterKey = masterKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatusResultCacheKey)) {
            return false;
        }
        StatusResultCacheKey key = (StatusResultCacheKey)obj;
        return key.getMasterKey().equals((Object)this.masterKey) && key.getFormKey().equals(this.formKey);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.masterKey != null ? this.masterKey.hashCode() : 0);
        result = 31 * result + (this.formKey != null ? this.formKey.hashCode() : 0);
        return result;
    }
}

