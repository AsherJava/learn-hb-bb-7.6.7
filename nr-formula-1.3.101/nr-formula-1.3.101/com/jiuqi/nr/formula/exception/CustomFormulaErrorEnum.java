/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.formula.exception;

import com.jiuqi.np.common.exception.ErrorEnum;
import java.util.StringJoiner;

public enum CustomFormulaErrorEnum implements ErrorEnum
{
    QUERY_UNIT("001", "\u67e5\u8be2\u5355\u4f4d\u5217\u8868\u5931\u8d25"),
    DATA_LIST("002", "\u67e5\u8be2\u516c\u5f0f\u5217\u8868\u5931\u8d25");

    private final String code;
    private final String message;

    private CustomFormulaErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return new StringJoiner(", ", CustomFormulaErrorEnum.class.getSimpleName() + "[", "]").add("code='" + this.code + "'").add("message='" + this.message + "'").toString();
    }
}

