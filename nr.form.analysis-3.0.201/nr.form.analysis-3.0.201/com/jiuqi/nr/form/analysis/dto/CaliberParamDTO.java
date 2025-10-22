/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.dto;

public class CaliberParamDTO {
    private int posNum;
    private String condition;

    public int getPosNum() {
        return this.posNum;
    }

    public String getCondition() {
        return this.condition;
    }

    public CaliberParamDTO(int posNum, String condition) {
        this.posNum = posNum;
        this.condition = condition;
    }
}

