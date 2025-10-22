/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.DUserActionParam;

public class DWorkflowUserAction {
    private String code;
    private String title;
    private String desc;
    private String icon;
    private DUserActionParam userActionParam;

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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DUserActionParam getUserActionParam() {
        return this.userActionParam;
    }

    public void setUserActionParam(DUserActionParam userActionParam) {
        this.userActionParam = userActionParam;
    }
}

