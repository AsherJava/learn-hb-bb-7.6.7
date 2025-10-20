/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import java.util.List;
import java.util.Map;

public class FixedAdaptSettingDTO {
    private String adaptFormula;
    private String wildcardFormula;
    private String logicFormula;
    private String memo;
    private String description;
    private Boolean stopFlag;
    private Map<String, List<Map<String, Object>>> bizModelFormula;

    public String getAdaptFormula() {
        return this.adaptFormula;
    }

    public void setAdaptFormula(String adaptFormula) {
        this.adaptFormula = adaptFormula;
    }

    public String getWildcardFormula() {
        return this.wildcardFormula;
    }

    public void setWildcardFormula(String wildcardFormula) {
        this.wildcardFormula = wildcardFormula;
    }

    public String getLogicFormula() {
        return this.logicFormula;
    }

    public void setLogicFormula(String logicFormula) {
        this.logicFormula = logicFormula;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Map<String, List<Map<String, Object>>> getBizModelFormula() {
        return this.bizModelFormula;
    }

    public void setBizModelFormula(Map<String, List<Map<String, Object>>> bizModelFormula) {
        this.bizModelFormula = bizModelFormula;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Boolean stopFlag) {
        this.stopFlag = stopFlag;
    }
}

