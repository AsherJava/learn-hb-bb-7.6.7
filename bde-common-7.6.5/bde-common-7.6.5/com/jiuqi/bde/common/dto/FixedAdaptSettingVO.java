/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import java.util.List;
import java.util.Map;

public class FixedAdaptSettingVO {
    private String adaptFormula;
    private String wildcardFormula;
    private String logicFormula;
    private String memo;
    private String description;
    private Boolean stopFlag;
    private Map<String, List<FixedFetchSourceRowSettingVO>> bizModelFormula;

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, List<FixedFetchSourceRowSettingVO>> getBizModelFormula() {
        return this.bizModelFormula;
    }

    public void setBizModelFormula(Map<String, List<FixedFetchSourceRowSettingVO>> bizModelFormula) {
        this.bizModelFormula = bizModelFormula;
    }

    public Boolean getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Boolean stopFlag) {
        this.stopFlag = stopFlag;
    }
}

