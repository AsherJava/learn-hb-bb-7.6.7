/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import java.io.Serializable;

public class LookupKeyItem
implements Serializable {
    private static final long serialVersionUID = 8879551137451709747L;
    private String refKey;
    private String valueKey;
    private boolean isEntityView;

    public LookupKeyItem() {
    }

    public LookupKeyItem(String refKey, String valueKey, boolean isEntityView) {
        this.refKey = refKey;
        this.valueKey = valueKey;
        this.isEntityView = isEntityView;
    }

    public String getRefKey() {
        return this.refKey;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }

    public String getValueKey() {
        return this.valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public boolean isEntityView() {
        return this.isEntityView;
    }

    public void setEntityView(boolean isEntityView) {
        this.isEntityView = isEntityView;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.isEntityView ? 1231 : 1237);
        result = 31 * result + (this.refKey == null ? 0 : this.refKey.hashCode());
        result = 31 * result + (this.valueKey == null ? 0 : this.valueKey.hashCode());
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
        LookupKeyItem other = (LookupKeyItem)obj;
        if (this.isEntityView != other.isEntityView) {
            return false;
        }
        if (this.refKey == null ? other.refKey != null : !this.refKey.equals(other.refKey)) {
            return false;
        }
        return !(this.valueKey == null ? other.valueKey != null : !this.valueKey.equals(other.valueKey));
    }
}

