/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.impl.ReportState;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class StateCacheKey {
    private String cacheKey;
    private DimensionValueSet dimensionValueSet;
    private String formSchemeKey;
    private String formKey;
    private String groupKey;
    private boolean getUser;

    public String getCacheKey() {
        return this.cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public boolean isGetUser() {
        return this.getUser;
    }

    public void setGetUser(boolean getUser) {
        this.getUser = getUser;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.cacheKey == null ? 0 : this.cacheKey.hashCode());
        result = 31 * result + (this.dimensionValueSet == null ? 0 : this.dimensionValueSet.hashCode());
        result = 31 * result + (this.formKey == null ? 0 : this.formKey.hashCode());
        result = 31 * result + (this.formSchemeKey == null ? 0 : this.formSchemeKey.hashCode());
        result = 31 * result + (this.getUser ? 1231 : 1237);
        result = 31 * result + (this.groupKey == null ? 0 : this.groupKey.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        StateCacheKey other = (StateCacheKey)obj;
        if (this.cacheKey == null ? other.cacheKey != null : !this.cacheKey.equals(other.cacheKey)) {
            return false;
        }
        if (this.dimensionValueSet == null ? other.dimensionValueSet != null : !this.dimensionValueSet.equals((Object)other.dimensionValueSet)) {
            return false;
        }
        if (this.formKey == null ? other.formKey != null : !this.formKey.equals(other.formKey)) {
            return false;
        }
        if (this.formSchemeKey == null ? other.formSchemeKey != null : !this.formSchemeKey.equals(other.formSchemeKey)) {
            return false;
        }
        if (this.getUser != other.getUser) {
            return false;
        }
        return !(this.groupKey == null ? other.groupKey != null : !this.groupKey.equals(other.groupKey));
    }

    public String toString() {
        return "StateCacheKey [cacheKey=" + this.cacheKey + ", dimensionValueSet=" + this.dimensionValueSet + ", formSchemeKey=" + this.formSchemeKey + ", formKey=" + this.formKey + ", groupKey=" + this.groupKey + ", getUser=" + this.getUser + "]";
    }
}

