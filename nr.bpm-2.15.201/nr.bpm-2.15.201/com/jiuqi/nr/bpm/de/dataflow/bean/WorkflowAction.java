/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;

public class WorkflowAction {
    private String code;
    private String title;
    private String desc;
    private String icon;
    private ActionParam actionParam;

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

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ActionParam getActionParam() {
        return this.actionParam;
    }

    public void setActionParam(ActionParam actionParam) {
        this.actionParam = actionParam;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

