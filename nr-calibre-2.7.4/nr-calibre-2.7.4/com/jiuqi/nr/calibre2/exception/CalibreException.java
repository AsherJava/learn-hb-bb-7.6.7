/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.calibre2.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum CalibreException implements ErrorEnum
{
    ENTITY_QUERY("E-01", "\u5b9e\u4f53\u67e5\u8be2\u5f02\u5e38");

    private String code;
    private String message;

    private CalibreException(String code, String message) {
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

