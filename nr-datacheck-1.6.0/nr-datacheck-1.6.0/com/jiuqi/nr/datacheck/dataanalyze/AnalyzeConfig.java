/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.dataanalyze;

import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeModel;
import com.jiuqi.nr.datacheck.dataanalyze.CheckCondition;
import com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType;
import java.util.List;

public class AnalyzeConfig {
    private CheckCondition checkRequires;
    private OrgSelectType orgSelectType = OrgSelectType.UCURRENTALLSUB;
    private List<AnalyzeModel> models;

    public List<AnalyzeModel> getModels() {
        return this.models;
    }

    public void setModels(List<AnalyzeModel> models) {
        this.models = models;
    }

    public CheckCondition getCheckRequires() {
        return this.checkRequires;
    }

    public void setCheckRequires(CheckCondition checkRequires) {
        this.checkRequires = checkRequires;
    }

    public OrgSelectType getOrgSelectType() {
        return this.orgSelectType;
    }

    public void setOrgSelectType(OrgSelectType orgSelectType) {
        this.orgSelectType = orgSelectType;
    }
}

