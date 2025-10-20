/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ref.intf;

import com.jiuqi.va.biz.ref.intf.RefDataObject;
import java.util.Map;

public class RefDataEntry
implements Map.Entry<String, Object> {
    private final RefDataObject o;
    private final String key;

    public RefDataEntry(RefDataObject o, String key) {
        this.o = o;
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Object getValue() {
        if (this.key.equals("isNull")) {
            return this.o.isNull();
        }
        if (this.key.equals("name")) {
            return this.o.getName();
        }
        if (this.key.equals("title")) {
            return this.o.getTitle();
        }
        if (this.key.equals("showTitle")) {
            return this.o.getShowTitle();
        }
        if (this.o.isNull()) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    @Override
    public Object setValue(Object value) {
        throw new UnsupportedOperationException();
    }
}

