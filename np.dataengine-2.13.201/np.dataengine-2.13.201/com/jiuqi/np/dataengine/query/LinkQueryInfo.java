/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.query;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LinkQueryInfo {
    private String linkAlias;
    private Set<String> unKnownLinkUnits = new HashSet<String>();
    private Map<String, Map<Object, Object>> relationDim1V1Map = new HashMap<String, Map<Object, Object>>();

    public LinkQueryInfo(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public Set<String> getUnKnownLinkUnits() {
        return this.unKnownLinkUnits;
    }

    public Map<String, Map<Object, Object>> getRelationDim1V1Map() {
        return this.relationDim1V1Map;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.linkAlias == null ? 0 : this.linkAlias.hashCode());
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
        LinkQueryInfo other = (LinkQueryInfo)obj;
        return !(this.linkAlias == null ? other.linkAlias != null : !this.linkAlias.equals(other.linkAlias));
    }

    public String toString() {
        return "LinkQueryInfo [linkAlias=" + this.linkAlias + ", unKnownLinkUnits=" + this.unKnownLinkUnits + ", relationDim1V1Map=" + this.relationDim1V1Map + "]";
    }
}

