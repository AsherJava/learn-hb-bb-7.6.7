/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.dataentry.bean.impl.DimensionStateImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEntryContext
extends NRContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String funcId;
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private String formGroupKey;
    private Map<String, DimensionValue> dimensionSet;
    private String formulaSchemeKey;
    private String printSchemeKey;
    private boolean writeable = true;
    private String period;
    private int uploadState;
    private boolean formUpload;
    private String flowType;
    private List<DimensionStateImpl> dimensionStates = new ArrayList<DimensionStateImpl>();
    private Map<String, Object> variableMap = new HashMap<String, Object>();

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String task) {
        this.taskKey = task;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public boolean getWriteable() {
        return this.writeable;
    }

    public void setWriteable(boolean writeable) {
        this.writeable = writeable;
    }

    public String getFuncId() {
        return this.funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getUploadState() {
        return this.uploadState;
    }

    public void setUploadState(int uploadState) {
        this.uploadState = uploadState;
    }

    public boolean isFormUpload() {
        return this.formUpload;
    }

    public void setFormUpload(boolean formUpload) {
        this.formUpload = formUpload;
    }

    public List<DimensionStateImpl> getDimensionStates() {
        return this.dimensionStates;
    }

    public void setDimensionStates(List<DimensionStateImpl> dimensionStates) {
        this.dimensionStates = dimensionStates;
    }

    public String getFlowType() {
        return this.flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }
}

