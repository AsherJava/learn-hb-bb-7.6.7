/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.ParameterModelItem;
import java.util.List;

public class ParameterModelWrapper {
    private ParameterModelItem periodParam;
    private ParameterModelItem masterParam;
    private List<ParameterModelItem> sceneParams;

    public ParameterModelItem getPeriodParam() {
        return this.periodParam;
    }

    public void setPeriodParam(ParameterModelItem periodParam) {
        this.periodParam = periodParam;
    }

    public ParameterModelItem getMasterParam() {
        return this.masterParam;
    }

    public void setMasterParam(ParameterModelItem masterParam) {
        this.masterParam = masterParam;
    }

    public List<ParameterModelItem> getSceneParams() {
        return this.sceneParams;
    }

    public void setSceneParams(List<ParameterModelItem> sceneParams) {
        this.sceneParams = sceneParams;
    }
}

