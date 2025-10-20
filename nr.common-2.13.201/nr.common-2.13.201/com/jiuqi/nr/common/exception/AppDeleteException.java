/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.exception;

import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NrCommonException;

public class AppDeleteException
extends NrCommonException {
    public AppDeleteException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public AppDeleteException(String[] datas) {
        super(ExceptionCodeCost.EXCEPTION_APP_DELETE, datas);
    }
}

