/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.summary.internal.entity;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.summary.api.DesignSummaryReport;
import com.jiuqi.nr.summary.internal.entity.SummaryReportDO;

@DBAnno.DBTable(dbTable="NR_SUMMARY_REPORT_DES")
public class DesignSummaryReportDO
extends SummaryReportDO
implements DesignSummaryReport {
    @Override
    public void setDesc(String desc) {
    }
}

