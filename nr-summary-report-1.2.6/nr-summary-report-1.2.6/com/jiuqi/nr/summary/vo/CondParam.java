/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

public class CondParam {
    private String soluKey;
    private boolean withPeriod = true;
    private boolean withMaster = true;
    private boolean withScene = true;

    public String getSoluKey() {
        return this.soluKey;
    }

    public void setSoluKey(String soluKey) {
        this.soluKey = soluKey;
    }

    public boolean isWithPeriod() {
        return this.withPeriod;
    }

    public void setWithPeriod(boolean withPeriod) {
        this.withPeriod = withPeriod;
    }

    public boolean isWithMaster() {
        return this.withMaster;
    }

    public void setWithMaster(boolean withMaster) {
        this.withMaster = withMaster;
    }

    public boolean isWithScene() {
        return this.withScene;
    }

    public void setWithScene(boolean withScene) {
        this.withScene = withScene;
    }
}

