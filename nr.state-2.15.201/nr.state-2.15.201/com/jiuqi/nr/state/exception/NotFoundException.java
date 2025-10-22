/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.state.exception;

import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NrCommonException;

public class NotFoundException
extends NrCommonException {
    private static final long serialVersionUID = 1L;

    public NotFoundException(String errorCode, String[] datas) {
        super(errorCode, datas);
    }

    public NotFoundException(String[] datas) {
        super(ExceptionCodeCost.NOTFOUND_FORMSHCEME, datas);
    }
}

