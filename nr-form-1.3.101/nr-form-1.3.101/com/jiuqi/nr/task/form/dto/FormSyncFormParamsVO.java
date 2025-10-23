/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import java.io.Serializable;

public class FormSyncFormParamsVO
implements Serializable {
    private String desFormTitle;
    private String desFormCode;
    private String desFormKey;
    private String srcFormKey;

    public String getDesFormTitle() {
        return this.desFormTitle;
    }

    public void setDesFormTitle(String desFormTitle) {
        this.desFormTitle = desFormTitle;
    }

    public String getDesFormCode() {
        return this.desFormCode;
    }

    public void setDesFormCode(String desFormCode) {
        this.desFormCode = desFormCode;
    }

    public String getDesFormKey() {
        return this.desFormKey;
    }

    public void setDesFormKey(String desFormKey) {
        this.desFormKey = desFormKey;
    }

    public String getSrcFormKey() {
        return this.srcFormKey;
    }

    public void setSrcFormKey(String srcFormKey) {
        this.srcFormKey = srcFormKey;
    }
}

