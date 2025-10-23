/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.api;

import com.jiuqi.nr.summary.api.BasicSetter;
import com.jiuqi.nr.summary.api.OrderSetter;
import com.jiuqi.nr.summary.api.SummaryReport;
import com.jiuqi.nr.summary.model.report.SequenceType;

public interface DesignSummaryReport
extends SummaryReport,
BasicSetter,
OrderSetter {
    public void setSummarySolutionKey(String var1);

    public void setSequenceType(SequenceType var1);

    public void setFilter(String var1);

    public void setConfig(String var1);

    public void setGridData(String var1);
}

