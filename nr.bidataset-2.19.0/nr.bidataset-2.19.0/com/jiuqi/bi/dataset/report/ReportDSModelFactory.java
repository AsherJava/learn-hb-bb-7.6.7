/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 */
package com.jiuqi.bi.dataset.report;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.bi.dataset.report.builder.ReportDSModelBuilder;
import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDSModelFactory
extends DSModelFactory {
    public static final String TYPE = "ReportDataSet";
    public static final String TITLE = "\u62a5\u8868\u6570\u636e\u96c6";
    @Autowired
    private ReportDSModelBuilder modelBuilder;

    public DSModel createDataSetModel() {
        ReportDSModel reportDSModel = new ReportDSModel();
        reportDSModel.setModelBuilder(this.modelBuilder);
        return reportDSModel;
    }

    public String getType() {
        return TYPE;
    }
}

