/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.bill.intf.BillDefine;

public class BillViewPreviewDTO {
    private BillDefine define;
    private String triggerOrigin;
    private String viewName;

    public BillDefine getDefine() {
        return this.define;
    }

    public void setDefine(BillDefine define) {
        this.define = define;
    }

    public String getTriggerOrigin() {
        return this.triggerOrigin;
    }

    public void setTriggerOrigin(String triggerOrigin) {
        this.triggerOrigin = triggerOrigin;
    }

    public String getViewName() {
        return this.viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}

