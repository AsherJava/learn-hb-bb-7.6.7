/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

public class UpgradeResultVO {
    private String item;
    private boolean state;
    private String msg;

    public UpgradeResultVO() {
    }

    public UpgradeResultVO(String item, boolean state, String msg) {
        this.item = item;
        this.state = state;
        this.msg = msg;
    }

    public String getItem() {
        return this.item;
    }

    public boolean isState() {
        return this.state;
    }

    public String getMsg() {
        return this.msg;
    }
}

