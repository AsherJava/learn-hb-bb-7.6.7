/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.value;

import com.jiuqi.va.biz.impl.value.ValueGetterBase;
import com.jiuqi.va.biz.intf.value.ValueGetter;
import java.util.HashMap;
import java.util.Map;

public class MapGetterImpl
extends ValueGetterBase {
    protected final Map<String, Object> map;

    public MapGetterImpl() {
        this.map = new HashMap<String, Object>();
    }

    public MapGetterImpl(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public Object getValue(String name) {
        return this.map.get(name);
    }

    @Override
    public ValueGetter getObject(String name) {
        Object value = this.map.get(name);
        if (value instanceof Map) {
            return new MapGetterImpl((Map)value);
        }
        return super.getObject(name);
    }
}

