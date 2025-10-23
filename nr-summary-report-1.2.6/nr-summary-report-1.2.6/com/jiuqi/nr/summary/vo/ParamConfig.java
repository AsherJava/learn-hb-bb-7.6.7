/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.util.List;

public class ParamConfig {
    private String name;
    private List<String> values;
    private String defaultValueMode = "none";
    private ParameterSelectMode selectMode = ParameterSelectMode.SINGLE;
    private ParameterCandidateValueMode candidateValueMode = ParameterCandidateValueMode.ALL;
    private List<String> candidateValue;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getDefaultValueMode() {
        return this.defaultValueMode;
    }

    public void setDefaultValueMode(String defaultValueMode) {
        this.defaultValueMode = defaultValueMode;
    }

    public ParameterSelectMode getSelectMode() {
        return this.selectMode;
    }

    public void setSelectMode(ParameterSelectMode selectMode) {
        this.selectMode = selectMode;
    }

    public ParameterCandidateValueMode getCandidateValueMode() {
        return this.candidateValueMode;
    }

    public void setCandidateValueMode(ParameterCandidateValueMode candidateValueMode) {
        this.candidateValueMode = candidateValueMode;
    }

    public List<String> getCandidateValue() {
        return this.candidateValue;
    }

    public void setCandidateValue(List<String> candidateValue) {
        this.candidateValue = candidateValue;
    }
}

