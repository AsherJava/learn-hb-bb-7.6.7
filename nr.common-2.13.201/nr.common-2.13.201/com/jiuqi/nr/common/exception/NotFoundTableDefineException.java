/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.exception;

import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NrCommonException;

public class NotFoundTableDefineException
extends NrCommonException {
    public NotFoundTableDefineException(String errorCode, String[] datas) {
        super(errorCode, datas);
    }

    public NotFoundTableDefineException(String[] datas) {
        super(ExceptionCodeCost.NOTFOUND_TABLEDEFINE, datas);
    }
}

