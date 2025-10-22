/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FormInfo
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FormInfo;
import java.io.Serializable;

public class FormInfoDTO
implements FormInfo,
Serializable {
    private static final long serialVersionUID = 1L;
    private String formKey;
    private String formCode;
    private String formTitle;
    private String formGroupCode;
    private String formGroupTitle;

    public FormInfoDTO(String formKey, String formCode, String formTitle) {
        this.formKey = formKey;
        this.formCode = formCode;
        this.formTitle = formTitle;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormGroupCode() {
        return this.formGroupCode;
    }

    public void setFormGroupCode(String formGroupCode) {
        this.formGroupCode = formGroupCode;
    }

    public String getFormGroupTitle() {
        return this.formGroupTitle;
    }

    public void setFormGroupTitle(String formGroupTitle) {
        this.formGroupTitle = formGroupTitle;
    }
}

