/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.dafafill.owner.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum DFPrivateErrorEnum implements ErrorEnum
{
    DFP_ERROR_SERIALIZE("000", "\u5e8f\u5217\u5316\u5931\u8d25"),
    DFP_ERROR_JSON_PROCESS("001", "\u6a21\u677f\u683c\u5f0f\u5316\u5931\u8d25");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private DFPrivateErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

