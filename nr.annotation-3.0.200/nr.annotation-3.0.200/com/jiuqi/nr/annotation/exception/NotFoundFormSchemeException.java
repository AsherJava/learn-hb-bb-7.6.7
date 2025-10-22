/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.annotation.exception;

import com.jiuqi.nr.common.exception.NrCommonException;

public class NotFoundFormSchemeException
extends NrCommonException {
    public NotFoundFormSchemeException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

