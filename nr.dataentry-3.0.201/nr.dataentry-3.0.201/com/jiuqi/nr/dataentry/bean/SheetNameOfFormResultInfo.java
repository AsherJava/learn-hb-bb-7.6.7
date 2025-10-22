/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.definition.facade.FormDefine;

public class SheetNameOfFormResultInfo {
    private boolean resultType = false;
    private FormDefine formDefine;
    private String noFormInfo;

    public boolean isResultType() {
        return this.resultType;
    }

    public void setResultType(boolean resultType) {
        this.resultType = resultType;
    }

    public FormDefine getFormDefine() {
        return this.formDefine;
    }

    public void setFormDefine(FormDefine formDefine) {
        this.formDefine = formDefine;
    }

    public String getNoFormInfo() {
        return this.noFormInfo;
    }

    public void setNoFormInfo(String noFormInfo) {
        this.noFormInfo = noFormInfo;
    }
}

