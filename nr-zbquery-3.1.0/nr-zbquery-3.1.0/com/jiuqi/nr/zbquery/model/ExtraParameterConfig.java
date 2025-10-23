/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import java.util.ArrayList;
import java.util.List;

public class ExtraParameterConfig {
    private ParameterCandidateValueMode candidateMode = ParameterCandidateValueMode.ALL;
    private List<String> candidateValue = new ArrayList<String>();

    public ParameterCandidateValueMode getCandidateMode() {
        return this.candidateMode;
    }

    public void setCandidateMode(ParameterCandidateValueMode candidateMode) {
        this.candidateMode = candidateMode;
    }

    public List<String> getCandidateValue() {
        return this.candidateValue;
    }

    public void setCandidateValue(List<String> candidateValue) {
        this.candidateValue = candidateValue;
    }
}

