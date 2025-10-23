/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.datascheme.fix.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FixError implements ErrorEnum
{
    NVWA_FIX_ERROR("001", "\u4fee\u590d\u5973\u5a32\u6a21\u578b\u5f02\u5e38"),
    NVWA_DELETE_ERROR("002", "\u5220\u9664\u5973\u5a32\u6a21\u578b\u5f02\u5e38");

    private final String code;
    private final String message;

    private FixError(String code, String message) {
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

