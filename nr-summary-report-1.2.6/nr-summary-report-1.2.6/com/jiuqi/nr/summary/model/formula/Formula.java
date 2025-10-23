/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.formula;

import java.io.Serializable;
import java.util.Date;

public class Formula
implements Serializable {
    private String key;
    private String solutionId;
    private String rptId;
    private String fmCode;
    private boolean calc;
    private boolean check;
    private int checkType;
    private String expression;
    private String desc;
    private Date modifyTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSolutionId() {
        return this.solutionId;
    }

    public void setSolutionId(String solutionId) {
        this.solutionId = solutionId;
    }

    public String getRptId() {
        return this.rptId;
    }

    public void setRptId(String rptId) {
        this.rptId = rptId;
    }

    public String getFmCode() {
        return this.fmCode;
    }

    public void setFmCode(String fmCode) {
        this.fmCode = fmCode;
    }

    public boolean isCalc() {
        return this.calc;
    }

    public void setCalc(boolean calc) {
        this.calc = calc;
    }

    public boolean isCheck() {
        return this.check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getCheckType() {
        return this.checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

