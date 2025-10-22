/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.reportTag.InjectContext
 */
package com.jiuqi.nr.dataentry.report.rest.vo;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.report.rest.vo.ReportObj;
import com.jiuqi.nr.definition.reportTag.InjectContext;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DownloadReportParamsObj
implements Serializable {
    private static final long serialVersionUID = -4306841775684019330L;
    private List<ReportObj> reportObjList;
    private String taskKey;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private Map<String, DimensionValue> dimensionSet;
    private String dwmc;
    private String curPeriodTitle;
    private transient InjectContext injectContext;

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public String getDwmc() {
        return this.dwmc;
    }

    public String getCurPeriodTitle() {
        return this.curPeriodTitle;
    }

    public InjectContext getInjectContext() {
        return this.injectContext;
    }

    public List<ReportObj> getReportObjList() {
        return this.reportObjList;
    }

    public void setReportObjList(List<ReportObj> reportObjList) {
        this.reportObjList = reportObjList;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public void setCurPeriodTitle(String curPeriodTitle) {
        this.curPeriodTitle = curPeriodTitle;
    }

    public void setInjectContext(InjectContext injectContext) {
        this.injectContext = injectContext;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this.injectContext.getFormSchemeKey());
        out.writeObject(this.injectContext.getDimensionSet());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String cxtFsKey = (String)in.readObject();
        Map cxtDimensionSet = (Map)in.readObject();
        InjectContext cxt = new InjectContext();
        cxt.setFormSchemeKey(cxtFsKey);
        cxt.setDimensionSet(cxtDimensionSet);
        this.injectContext = cxt;
    }
}

