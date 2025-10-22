/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachmentcheck.bean;

import java.util.HashMap;
import java.util.Map;

public class MapWrapper {
    private final Map<String, String> dimNameValueMap;

    public MapWrapper(Map<String, String> map) {
        this.dimNameValueMap = new HashMap<String, String>(map);
    }

    public Map<String, String> getDimNameValueMap() {
        return new HashMap<String, String>(this.dimNameValueMap);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MapWrapper that = (MapWrapper)o;
        return this.dimNameValueMap.equals(that.dimNameValueMap);
    }

    public int hashCode() {
        return this.dimNameValueMap.hashCode();
    }
}

