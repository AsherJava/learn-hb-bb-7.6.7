/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.exception;

import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NrCommonException;

public class AppSelectException
extends NrCommonException {
    public AppSelectException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public AppSelectException(String[] datas) {
        super(ExceptionCodeCost.EXCEPTION_APP_SELECT, datas);
    }
}

