/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AnalysisFormDTO {
    private AnalysisFormParamDefineImpl define;

    public AnalysisFormParamDefineImpl getDefine() {
        return this.define;
    }

    public void setDefine(AnalysisFormParamDefineImpl define) {
        this.define = define;
    }
}

