/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.exception;

import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NrCommonException;

public class AsyncTaskErrorException
extends NrCommonException {
    public AsyncTaskErrorException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public AsyncTaskErrorException(String[] datas) {
        super(ExceptionCodeCost.ERROR_ASYNCTASK, datas);
    }
}

