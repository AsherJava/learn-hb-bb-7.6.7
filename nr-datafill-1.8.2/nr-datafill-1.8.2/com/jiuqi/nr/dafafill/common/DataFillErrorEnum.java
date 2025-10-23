/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.dafafill.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum DataFillErrorEnum implements ErrorEnum
{
    DF_ERROR_AUTHORITY("000", "operationNoPermission"),
    DF_ERROR_SERIALIZE("001", "serializationFail"),
    DF_ERROR_CODE_NULL("002", "emptyCode"),
    DF_ERROR_CODE_REPEAT("003", "sameCode"),
    DF_ERROR_TITLE_REPEAT("004", "sameTitle"),
    DG_ERROR_NOT_EXIST("100", "noFolder"),
    DG_ERROR_NOT_DELETE("200", "noDeleFolder");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return String.format("nr.dataFill.%s", this.message);
    }

    private DataFillErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

