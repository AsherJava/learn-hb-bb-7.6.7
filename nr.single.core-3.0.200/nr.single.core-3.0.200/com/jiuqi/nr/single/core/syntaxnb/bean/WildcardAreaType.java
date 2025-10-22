/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;

public class WildcardAreaType
extends BaseCellDataType {
    private int taskTag;
    private String tableSign;
    private boolean isLan;
    private int number;

    public int getTaskTag() {
        return this.taskTag;
    }

    public String getTableSign() {
        return this.tableSign;
    }

    public boolean isLan() {
        return this.isLan;
    }

    public int getNumber() {
        return this.number;
    }

    public void setTaskTag(int taskTag) {
        this.taskTag = taskTag;
    }

    public void setTableSign(String tableSign) {
        this.tableSign = tableSign;
    }

    public void setLan(boolean isLan) {
        this.isLan = isLan;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

