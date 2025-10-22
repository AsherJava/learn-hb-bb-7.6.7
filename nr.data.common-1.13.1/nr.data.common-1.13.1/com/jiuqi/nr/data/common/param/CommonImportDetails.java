/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.common.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class CommonImportDetails {
    private DimensionCombination combination;
    private String formKey;
    private String formCode;
    private String formTitle;
    private String errorMessage;

    public DimensionCombination getCombination() {
        return this.combination;
    }

    public void setCombination(DimensionCombination combination) {
        this.combination = combination;
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

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public CommonImportDetails(DimensionCombination combination, String formKey, String formCode, String formTitle, String errorMessage) {
        this.combination = combination;
        this.formKey = formKey;
        this.formCode = formCode;
        this.formTitle = formTitle;
        this.errorMessage = errorMessage;
    }

    public CommonImportDetails() {
    }
}

