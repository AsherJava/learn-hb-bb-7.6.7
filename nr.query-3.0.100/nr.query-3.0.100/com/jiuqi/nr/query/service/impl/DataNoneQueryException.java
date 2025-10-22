/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQError
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.np.common.exception.JQError;

public class DataNoneQueryException
extends Exception
implements JQError {
    public String getErrorCode() {
        return "DQ-000";
    }

    public String getErrorMessage() {
        return "\u672a\u6267\u884c\u6570\u636e\u67e5\u8be2";
    }

    public Object getErrorData() {
        return "\u8bf7\u5148\u8c03\u7528getData\uff0c\u7136\u540e\u518d\u83b7\u53d6\u76f8\u5173\u4fe1\u606f\u3002";
    }
}

