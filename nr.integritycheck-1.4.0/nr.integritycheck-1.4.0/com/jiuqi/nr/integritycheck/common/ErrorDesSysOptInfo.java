/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.common;

import java.io.Serializable;

public class ErrorDesSysOptInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int checkMinNum;
    private int checkMaxNum;
    private boolean hasChinese = false;

    public int getCheckMinNum() {
        return this.checkMinNum;
    }

    public void setCheckMinNum(int checkMinNum) {
        this.checkMinNum = checkMinNum;
    }

    public int getCheckMaxNum() {
        return this.checkMaxNum;
    }

    public void setCheckMaxNum(int checkMaxNum) {
        this.checkMaxNum = checkMaxNum;
    }

    public boolean isHasChinese() {
        return this.hasChinese;
    }

    public void setHasChinese(boolean hasChinese) {
        this.hasChinese = hasChinese;
    }
}

