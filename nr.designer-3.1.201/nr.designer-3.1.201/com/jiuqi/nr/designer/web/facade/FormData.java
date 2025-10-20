/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormObj;

public class FormData {
    private FormObj formObject;
    private String enumDatas;
    private Object relatedForms;

    public FormObj getFormObject() {
        return this.formObject;
    }

    public void setFormObject(FormObj formObject) {
        this.formObject = formObject;
    }

    public String getEnumDatas() {
        return this.enumDatas;
    }

    public void setEnumDatas(String enumDatas) {
        this.enumDatas = enumDatas;
    }

    public Object getRelatedForms() {
        return this.relatedForms;
    }

    public void setRelatedForms(Object relatedForms) {
        this.relatedForms = relatedForms;
    }
}

