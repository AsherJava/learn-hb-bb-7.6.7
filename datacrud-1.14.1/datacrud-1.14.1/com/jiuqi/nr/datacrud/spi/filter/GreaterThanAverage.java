/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.spi.filter;

import com.jiuqi.nr.datacrud.spi.filter.AverageFilter;

public class GreaterThanAverage
extends AverageFilter {
    public GreaterThanAverage(String link) {
        super(link, null);
    }

    @Override
    protected String operator() {
        return " > ";
    }
}

