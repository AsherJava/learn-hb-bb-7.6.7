/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common;

public class MatchingInfo {
    private int matchingType;
    private String currentFormula;
    private String relatedFormula;

    public int getMatchingType() {
        return this.matchingType;
    }

    public void setMatchingType(int matchingType) {
        this.matchingType = matchingType;
    }

    public String getCurrentFormula() {
        return this.currentFormula;
    }

    public void setCurrentFormula(String currentFormula) {
        this.currentFormula = currentFormula;
    }

    public String getRelatedFormula() {
        return this.relatedFormula;
    }

    public void setRelatedFormula(String relatedFormula) {
        this.relatedFormula = relatedFormula;
    }
}

