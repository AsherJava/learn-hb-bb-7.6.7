/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.zbquery.common;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;

public enum ZBQueryErrorEnum implements ErrorEnum
{
    ZBQUERY_EXCEPTION_000("000", "zbquery.zbqueryError.enum000"),
    ZBQUERY_EXCEPTION_001("001", "zbquery.zbqueryError.enum001"),
    ZBQUERY_EXCEPTION_100("100", "zbquery.zbqueryError.enum100"),
    ZBQUERY_EXCEPTION_101("101", "zbquery.zbqueryError.enum101"),
    ZBQUERY_EXCEPTION_201("201", "zbquery.zbqueryError.enum201");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return ZBQueryI18nUtils.getMessage(this.message, new Object[0]);
    }

    private ZBQueryErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

