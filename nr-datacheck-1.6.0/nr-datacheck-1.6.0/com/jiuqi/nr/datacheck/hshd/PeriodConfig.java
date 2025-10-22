/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.hshd;

public class PeriodConfig {
    private Integer type;
    private String value;
    private String previousIssue;
    private String nextIssue;

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPreviousIssue() {
        return this.previousIssue;
    }

    public void setPreviousIssue(String previousIssue) {
        this.previousIssue = previousIssue;
    }

    public String getNextIssue() {
        return this.nextIssue;
    }

    public void setNextIssue(String nextIssue) {
        this.nextIssue = nextIssue;
    }
}

