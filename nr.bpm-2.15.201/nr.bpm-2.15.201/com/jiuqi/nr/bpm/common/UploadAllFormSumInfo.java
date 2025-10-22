/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import com.jiuqi.nr.bpm.common.UploadSumNew;
import java.util.Map;

public class UploadAllFormSumInfo
extends UploadSumNew {
    private String formId;
    private String formGroupId;
    private String formGroupTitle;
    private String formTitle;
    private Map<String, String> keyTitle;

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormGroupId() {
        return this.formGroupId;
    }

    public void setFormGroupId(String formGroupId) {
        this.formGroupId = formGroupId;
    }

    public String getFormGroupTitle() {
        return this.formGroupTitle;
    }

    public void setFormGroupTitle(String formGroupTitle) {
        this.formGroupTitle = formGroupTitle;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public Map<String, String> getKeyTitle() {
        return this.keyTitle;
    }

    public void setKeyTitle(Map<String, String> keyTitle) {
        this.keyTitle = keyTitle;
    }
}

