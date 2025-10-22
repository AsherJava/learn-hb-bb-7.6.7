/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean;

import java.io.Serializable;

public class ExplainInfoCheckResultItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String unitKey;
    private String unitCode;
    private String unitTitle;
    private String taskKey;
    private String taskTitle;
    private String formulaSchemeKey;
    private String formulaSchemeTitle;
    private String formSchemeKey;
    private String formSchemeTitle;
    private String formulaCode;
    private String formulaKey;
    private String formulaExp;
    private String period;
    private String desc;
    private int minLen;
    private int maxLen;
    private String userName;

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
        this.unitCode = unitKey;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public String getFormulaExp() {
        return this.formulaExp;
    }

    public void setFormulaExp(String formulaExp) {
        this.formulaExp = formulaExp;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMinLen() {
        return this.minLen;
    }

    public void setMinLen(int minLen) {
        this.minLen = minLen;
    }

    public int getMaxLen() {
        return this.maxLen;
    }

    public void setMaxLen(int maxLen) {
        this.maxLen = maxLen;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
        this.unitKey = unitCode;
    }
}

