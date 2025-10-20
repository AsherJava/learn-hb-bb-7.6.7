/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.AbstractStatisticsEvent;

public abstract class AbstractStatisticsAggregator {
    public abstract void aggregate(AbstractStatisticsEvent var1);

    public abstract Object retrieveAggregation();

    public abstract void merge(AbstractStatisticsAggregator var1);
}

