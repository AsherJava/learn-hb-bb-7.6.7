/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.spi.filter;

import com.jiuqi.nr.datacrud.spi.filter.AverageFilter;

public class LessThanAverage
extends AverageFilter {
    public LessThanAverage(String link) {
        super(link, null);
    }

    @Override
    protected String operator() {
        return " < ";
    }
}

