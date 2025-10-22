/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.nr.common.exception.NrCommonException;

public class EtlAsyncTaskErrorException
extends NrCommonException {
    public EtlAsyncTaskErrorException(String errorCode) {
        super(errorCode);
    }

    public EtlAsyncTaskErrorException(String errorCode, String[] datas) {
        super(errorCode, datas);
    }
}

