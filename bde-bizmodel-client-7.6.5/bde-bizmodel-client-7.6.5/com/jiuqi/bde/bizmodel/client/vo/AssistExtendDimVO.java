/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.vo;

import java.math.BigDecimal;

public class AssistExtendDimVO {
    private String id;
    private String assistDimCode;
    private String refField;
    private String code;
    private String name;
    private String matchRule;
    private Integer stopFlagNum;
    private BigDecimal ordinal;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssistDimCode() {
        return this.assistDimCode;
    }

    public void setAssistDimCode(String assistDimCode) {
        this.assistDimCode = assistDimCode;
    }

    public String getRefField() {
        return this.refField;
    }

    public void setRefField(String refField) {
        this.refField = refField;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatchRule() {
        return this.matchRule;
    }

    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
    }

    public Integer getStopFlagNum() {
        return this.stopFlagNum;
    }

    public void setStopFlagNum(Integer stopFlagNum) {
        this.stopFlagNum = stopFlagNum;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }
}

