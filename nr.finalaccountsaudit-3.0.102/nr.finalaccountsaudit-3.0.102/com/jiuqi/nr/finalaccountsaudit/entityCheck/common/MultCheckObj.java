/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MultCheckType;
import java.io.Serializable;

public class MultCheckObj
implements Serializable {
    private int dqdwCount;
    private int yzdwCount;
    private int jsdwCount;
    private int ywdwCount;
    private int xzdwCount;
    private boolean isTrue;
    private MultCheckType checkType;
    private String message;

    public MultCheckObj() {
    }

    public boolean isTrue() {
        return this.isTrue;
    }

    public void setTrue(boolean aTrue) {
        this.isTrue = aTrue;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDqdwCount() {
        return this.dqdwCount;
    }

    public void setDqdwCount(int dqdwCount) {
        this.dqdwCount = dqdwCount;
    }

    public int getYzdwCount() {
        return this.yzdwCount;
    }

    public void setYzdwCount(int yzdwCount) {
        this.yzdwCount = yzdwCount;
    }

    public int getJsdwCount() {
        return this.jsdwCount;
    }

    public void setJsdwCount(int jsdwCount) {
        this.jsdwCount = jsdwCount;
    }

    public int getYwdwCount() {
        return this.ywdwCount;
    }

    public void setYwdwCount(int ywdwCount) {
        this.ywdwCount = ywdwCount;
    }

    public int getXzdwCount() {
        return this.xzdwCount;
    }

    public void setXzdwCount(int xzdwCount) {
        this.xzdwCount = xzdwCount;
    }

    public MultCheckObj(int dqdwCount, int yzdwCount, int jsdwCount, int ywdwCount, int xzdwCount, boolean isTrue, String message) {
        this.dqdwCount = dqdwCount;
        this.yzdwCount = yzdwCount;
        this.jsdwCount = jsdwCount;
        this.ywdwCount = ywdwCount;
        this.xzdwCount = xzdwCount;
        this.isTrue = isTrue;
        this.message = message;
    }

    public MultCheckObj(int dqdwCount, int yzdwCount, int jsdwCount, int ywdwCount, int xzdwCount) {
        this.dqdwCount = dqdwCount;
        this.yzdwCount = yzdwCount;
        this.jsdwCount = jsdwCount;
        this.ywdwCount = ywdwCount;
        this.xzdwCount = xzdwCount;
    }

    public MultCheckType getCheckType() {
        return this.checkType;
    }

    public void setCheckType(MultCheckType checkType) {
        this.checkType = checkType;
    }
}

