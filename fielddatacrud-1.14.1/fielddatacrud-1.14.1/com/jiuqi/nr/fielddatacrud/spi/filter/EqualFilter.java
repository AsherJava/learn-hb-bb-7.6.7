/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.spi.filter;

import com.jiuqi.nr.fielddatacrud.spi.filter.FieldFilter;

public class EqualFilter
extends FieldFilter {
    public EqualFilter(String fieldKey, String value) {
        super(fieldKey, value);
    }

    @Override
    protected String operator() {
        return "=";
    }
}

