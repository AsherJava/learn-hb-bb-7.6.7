/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 */
package com.jiuqi.nr.analysisreport.authority.bean;

import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.nr.analysisreport.authority.common.AnalysisReportResourceType;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportDefineImpl;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportGroupDefineImpl;

public class AnalysisReportResource
implements Identifiable {
    private String id;
    private String title;
    private String type;

    public AnalysisReportResource() {
        this.id = null;
        this.type = null;
        this.title = null;
    }

    public AnalysisReportResource(String id, String type, String title) {
        this.id = id;
        this.type = type;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void analysisReportGroupToResoucce(AnalysisReportGroupDefineImpl analysisReportGroup) {
        this.id = analysisReportGroup.getKey();
        this.title = analysisReportGroup.getTitle();
        this.type = AnalysisReportResourceType.GROUP;
    }

    public void analysisReportToResoucce(AnalysisReportDefineImpl analysisReport) {
        this.id = analysisReport.getKey();
        this.title = analysisReport.getTitle();
        this.type = AnalysisReportResourceType.TEMPLATE;
    }
}

