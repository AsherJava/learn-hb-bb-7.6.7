/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.examine.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ExamineErrorEnum implements ErrorEnum
{
    MD_INFO_001("001", "");

    private String code;
    private String message;

    private ExamineErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

