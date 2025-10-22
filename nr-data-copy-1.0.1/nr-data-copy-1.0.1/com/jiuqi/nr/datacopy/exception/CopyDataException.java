/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.datacopy.exception;

import com.jiuqi.nr.common.exception.NrCommonException;

public class CopyDataException
extends NrCommonException {
    public CopyDataException(String errorCode) {
        super(errorCode);
    }

    public CopyDataException(String errorCode, String[] data) {
        super(errorCode, data);
    }
}

