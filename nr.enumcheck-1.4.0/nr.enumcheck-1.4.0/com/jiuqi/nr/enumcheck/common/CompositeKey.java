/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import java.util.HashMap;
import java.util.Map;

public class CompositeKey {
    private String strPart;
    private Map<String, String> mapPart;

    public CompositeKey(String strPart, Map<String, String> mapPart) {
        this.strPart = strPart;
        this.mapPart = new HashMap<String, String>(mapPart);
    }

    public String getStrPart() {
        return this.strPart;
    }

    public Map<String, String> getMapPart() {
        return this.mapPart;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CompositeKey that = (CompositeKey)o;
        if (!this.strPart.equals(that.strPart)) {
            return false;
        }
        return this.mapPart.equals(that.mapPart);
    }

    public int hashCode() {
        int result = this.strPart.hashCode();
        result = 31 * result + this.mapPart.hashCode();
        return result;
    }
}

