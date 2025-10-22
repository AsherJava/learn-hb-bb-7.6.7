/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.ValidateResult;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;

public class FieldValidateResult {
    private FieldDefine fieldDefine;
    private List<ValidateResult> validateResult;

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(FieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public List<ValidateResult> getValidateResult() {
        return this.validateResult;
    }

    public void setValidateResult(List<ValidateResult> validateResult) {
        this.validateResult = validateResult;
    }
}

