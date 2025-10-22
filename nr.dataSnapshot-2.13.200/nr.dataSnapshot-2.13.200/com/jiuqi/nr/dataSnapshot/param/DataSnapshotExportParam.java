/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.context.infc.INRContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataSnapshotExportParam
implements INRContext {
    private static final String BATCHEXPORT = "batchExport";
    private UUID syncTaskID;
    private String formKey;
    private List<String> formKeyList;
    private Boolean allForms;
    private double deviation;
    private String unitTitle;
    private Boolean showAllFieldCompareResult;
    private Boolean showDifferenceColor;
    private String contextEntityId;
    private String contextFilterExpression;

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public Boolean getShowAllFieldCompareResult() {
        return this.showAllFieldCompareResult;
    }

    public void setShowAllFieldCompareResult(Boolean showAllFieldCompareResult) {
        this.showAllFieldCompareResult = showAllFieldCompareResult;
    }

    public Boolean getShowDifferenceColor() {
        return this.showDifferenceColor;
    }

    public void setShowDifferenceColor(Boolean showDifferenceColor) {
        this.showDifferenceColor = showDifferenceColor;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public List<String> getExportForms() {
        if (this.allForms != null && this.allForms.booleanValue()) {
            return null;
        }
        ArrayList<String> forms = new ArrayList<String>();
        if (this.formKey == null || this.formKey.equals(BATCHEXPORT)) {
            return this.formKeyList;
        }
        forms.add(this.formKey);
        return forms;
    }

    public UUID getSyncTaskID() {
        return this.syncTaskID;
    }

    public double getDeviation() {
        return this.deviation;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public List<String> getFormKeyList() {
        return this.formKeyList;
    }

    public Boolean getAllForms() {
        return this.allForms;
    }

    public void setSyncTaskID(UUID syncTaskID) {
        this.syncTaskID = syncTaskID;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setFormKeyList(List<String> formKeyList) {
        this.formKeyList = formKeyList;
    }

    public void setAllForms(Boolean allForms) {
        this.allForms = allForms;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }
}

