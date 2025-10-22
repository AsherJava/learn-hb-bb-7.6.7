/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.annotation.exception;

import com.jiuqi.nr.common.exception.NrCommonException;

public class ErrorParamException
extends NrCommonException {
    public ErrorParamException(String errCode) {
        super(errCode);
    }

    public ErrorParamException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

