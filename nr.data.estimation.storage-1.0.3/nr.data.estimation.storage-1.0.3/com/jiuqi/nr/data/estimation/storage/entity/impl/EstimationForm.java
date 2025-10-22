/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package com.jiuqi.nr.data.estimation.storage.entity.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationFormDeserializer;

@JsonDeserialize(using=EstimationFormDeserializer.class)
public class EstimationForm {
    private String formId;
    private String formType;

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormType() {
        return this.formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }
}

