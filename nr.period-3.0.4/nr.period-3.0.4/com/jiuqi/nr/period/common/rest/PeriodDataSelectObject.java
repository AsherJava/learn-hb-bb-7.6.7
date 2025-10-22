/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.rest;

import com.jiuqi.nr.period.modal.IPeriodRow;

public class PeriodDataSelectObject {
    private String code;
    private String title;
    private String group;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public static PeriodDataSelectObject defineToObject(IPeriodRow periodDataDefine) {
        PeriodDataSelectObject periodDataObject = new PeriodDataSelectObject();
        periodDataObject.setCode(periodDataDefine.getCode());
        periodDataObject.setTitle(periodDataDefine.getTitle());
        return periodDataObject;
    }
}

