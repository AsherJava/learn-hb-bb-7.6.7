/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz.bean;

public class MdCodeDataTime {
    private String mdCode;
    private String validDataTime;

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getValidDataTime() {
        return this.validDataTime;
    }

    public void setValidDataTime(String validDataTime) {
        this.validDataTime = validDataTime;
    }

    public String toString() {
        return "{mdCode='" + this.mdCode + '\'' + ", validDataTime='" + this.validDataTime + '\'' + '}';
    }
}

