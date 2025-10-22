/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.model;

import com.jiuqi.bi.dataset.report.model.AdvancedConfig;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import java.util.ArrayList;
import java.util.List;

public class ReportDsModelDefine {
    private List<ReportExpField> fields = new ArrayList<ReportExpField>();
    private List<ReportDsParameter> parameters = new ArrayList<ReportDsParameter>();
    private String filter;
    private String filterDesc;
    private String gatherSchemeCode;
    private boolean showDetail;
    private AdvancedConfig config;
    private String reportTaskKey;

    public List<ReportExpField> getFields() {
        return this.fields;
    }

    public void setFields(List<ReportExpField> fields) {
        this.fields = fields;
    }

    public List<ReportDsParameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<ReportDsParameter> parameters) {
        this.parameters = parameters;
    }

    public String getFilter() {
        return this.filter;
    }

    public String getFilterDesc() {
        return this.filterDesc;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setFilterDesc(String filterDesc) {
        this.filterDesc = filterDesc;
    }

    public String getReportTaskKey() {
        return this.reportTaskKey;
    }

    public void setReportTaskKey(String reportTaskKey) {
        this.reportTaskKey = reportTaskKey;
    }

    public String getGatherSchemeCode() {
        return this.gatherSchemeCode;
    }

    public boolean isShowDetail() {
        return this.showDetail;
    }

    public void setGatherSchemeCode(String gatherSchemeCode) {
        this.gatherSchemeCode = gatherSchemeCode;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }

    public AdvancedConfig getConfig() {
        return this.config;
    }

    public void setConfig(AdvancedConfig config) {
        this.config = config;
    }
}

