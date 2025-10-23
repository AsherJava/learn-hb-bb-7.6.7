/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.api;

import com.jiuqi.nr.summary.api.BasicGetter;
import com.jiuqi.nr.summary.api.Ordered;
import com.jiuqi.nr.summary.model.report.SequenceType;

public interface SummaryReport
extends BasicGetter,
Ordered {
    public String getSummarySolutionKey();

    public SequenceType getSequenceType();

    public String getFilter();

    public String getConfig();

    public String getGridData();

    public String getPageConfig();
}

