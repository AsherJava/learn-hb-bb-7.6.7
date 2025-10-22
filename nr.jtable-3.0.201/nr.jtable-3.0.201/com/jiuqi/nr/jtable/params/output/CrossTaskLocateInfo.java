/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import java.io.Serializable;

public class CrossTaskLocateInfo
implements Serializable {
    private static final long serialVersionUID = 5197806675408028493L;
    private boolean haveAccess;
    private String message;
    private String formSchemeKey;
    private String taskTitle;
    private String formTitle;

    public CrossTaskLocateInfo() {
    }

    public CrossTaskLocateInfo(boolean haveAccess, String message, String formSchemeKey, String taskTitle, String formTitle) {
        this.haveAccess = haveAccess;
        this.message = message;
        this.formSchemeKey = formSchemeKey;
        this.taskTitle = taskTitle;
        this.formTitle = formTitle;
    }

    public boolean isHaveAccess() {
        return this.haveAccess;
    }

    public void setHaveAccess(boolean haveAccess) {
        this.haveAccess = haveAccess;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }
}

