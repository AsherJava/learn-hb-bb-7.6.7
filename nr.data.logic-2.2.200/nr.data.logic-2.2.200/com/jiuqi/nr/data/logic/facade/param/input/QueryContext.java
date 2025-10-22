/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

public class QueryContext {
    private String ckrTableName;
    private String ckdTableName;
    private String ckrTableAlia;
    private String ckdTableAlia;
    private boolean allCheck;

    public String getCkrTableName() {
        return this.ckrTableName;
    }

    public void setCkrTableName(String ckrTableName) {
        this.ckrTableName = ckrTableName;
    }

    public String getCkdTableName() {
        return this.ckdTableName;
    }

    public void setCkdTableName(String ckdTableName) {
        this.ckdTableName = ckdTableName;
    }

    public String getCkrTableAlia() {
        return this.ckrTableAlia;
    }

    public void setCkrTableAlia(String ckrTableAlia) {
        this.ckrTableAlia = ckrTableAlia;
    }

    public String getCkdTableAlia() {
        return this.ckdTableAlia;
    }

    public void setCkdTableAlia(String ckdTableAlia) {
        this.ckdTableAlia = ckdTableAlia;
    }

    public boolean isAllCheck() {
        return this.allCheck;
    }

    public void setAllCheck(boolean allCheck) {
        this.allCheck = allCheck;
    }
}

