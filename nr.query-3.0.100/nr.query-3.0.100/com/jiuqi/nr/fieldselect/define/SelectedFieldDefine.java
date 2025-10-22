/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fieldselect.define;

import com.jiuqi.nr.fieldselect.service.impl.FieldSelectLinksData;

public class SelectedFieldDefine
extends FieldSelectLinksData {
    private boolean isHidden;
    private boolean isSelectByField;
    private String taskId;
    private String formSchemeId;
    private String regionKey;
    private String formKey;
    private boolean isCustom = false;
    private String customvalue;

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public boolean isSelectByField() {
        return this.isSelectByField;
    }

    public void setSelectByField(boolean selectByField) {
        this.isSelectByField = selectByField;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public boolean getCustom() {
        return this.isCustom;
    }

    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public String getCustomValue() {
        return this.customvalue;
    }

    public void setCustomValue(String customValue) {
        this.customvalue = customValue;
    }
}

