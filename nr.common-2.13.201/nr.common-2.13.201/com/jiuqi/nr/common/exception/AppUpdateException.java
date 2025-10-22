/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.exception;

import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NrCommonException;

public class AppUpdateException
extends NrCommonException {
    public AppUpdateException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public AppUpdateException(String[] datas) {
        super(ExceptionCodeCost.EXCEPTION_APP_UPDATE, datas);
    }
}

