/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.formulaeditor.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FormulaEditorException implements ErrorEnum
{
    EDITOR_EXCEPTION_001("001", "\u67e5\u8be2\u53c2\u6570\u4e0d\u5b8c\u6574\uff0c\u67e5\u8be2\u5931\u8d25\uff01");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private FormulaEditorException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

