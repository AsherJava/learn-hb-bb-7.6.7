/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.workbench;

import com.jiuqi.bi.dataset.report.ReportDataProviderFactory;
import org.springframework.stereotype.Component;

@Component
public class ReportDataWBProvderFactory
extends ReportDataProviderFactory {
    @Override
    public String getType() {
        return "ReportDataSet_workbench";
    }
}

