/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 */
package com.jiuqi.bi.dataset.report.workbench;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.report.ReportDSModelFactory;
import com.jiuqi.bi.dataset.report.workbench.ReportWBDsModel;
import org.springframework.stereotype.Component;

@Component
public class ReportWBDSModelFactory
extends ReportDSModelFactory {
    @Override
    public DSModel createDataSetModel() {
        return new ReportWBDsModel();
    }

    @Override
    public String getType() {
        return "ReportDataSet_workbench";
    }
}

