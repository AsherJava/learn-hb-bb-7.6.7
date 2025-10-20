/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.exception;

import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NrCommonException;

public class AppSaveException
extends NrCommonException {
    public AppSaveException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public AppSaveException(String[] datas) {
        super(ExceptionCodeCost.EXCEPTION_APP_SAVE, datas);
    }
}

