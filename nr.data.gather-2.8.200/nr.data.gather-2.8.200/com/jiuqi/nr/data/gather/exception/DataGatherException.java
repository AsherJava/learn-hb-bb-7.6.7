/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.data.gather.exception;

import com.jiuqi.nr.common.exception.NrCommonException;

public class DataGatherException
extends NrCommonException {
    public DataGatherException(String errorCode) {
        super(errorCode);
    }

    public DataGatherException(String errorCode, String[] datas) {
        super(errorCode, datas);
    }
}

