/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.exception;

import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NrCommonException;

public class NotFoundAsyncTaskException
extends NrCommonException {
    public NotFoundAsyncTaskException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public NotFoundAsyncTaskException(String[] datas) {
        super(ExceptionCodeCost.NOTFOUND_ASYNCTASK, datas);
    }
}

