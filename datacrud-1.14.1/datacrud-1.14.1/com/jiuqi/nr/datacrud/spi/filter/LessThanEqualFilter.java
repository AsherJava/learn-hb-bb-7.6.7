/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.spi.filter;

import com.jiuqi.nr.datacrud.spi.filter.LinkFilter;

public class LessThanEqualFilter
extends LinkFilter {
    public LessThanEqualFilter(String link, String value) {
        super(link, value);
    }

    @Override
    protected String operator() {
        return "<=";
    }
}

