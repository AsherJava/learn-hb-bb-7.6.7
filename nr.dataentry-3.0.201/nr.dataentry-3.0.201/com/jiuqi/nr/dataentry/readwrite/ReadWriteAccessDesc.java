/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.readwrite;

public class ReadWriteAccessDesc {
    private Boolean able;
    private String desc;

    public ReadWriteAccessDesc(Boolean able, String desc) {
        this.able = able;
        this.desc = desc;
    }

    public Boolean getAble() {
        return this.able;
    }

    public void setAble(Boolean able) {
        this.able = able;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

