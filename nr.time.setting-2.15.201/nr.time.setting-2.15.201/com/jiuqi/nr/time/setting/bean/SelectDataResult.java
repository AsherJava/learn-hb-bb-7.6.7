/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.time.setting.bean;

import java.util.List;

public class SelectDataResult {
    private List<String> canOperateList;
    private List<String> noOperateList;

    public List<String> getCanOperateList() {
        return this.canOperateList;
    }

    public void setCanOperateList(List<String> canOperateList) {
        this.canOperateList = canOperateList;
    }

    public List<String> getNoOperateList() {
        return this.noOperateList;
    }

    public void setNoOperateList(List<String> noOperateList) {
        this.noOperateList = noOperateList;
    }
}

