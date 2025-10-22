/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.data.logic.exeception;

import com.jiuqi.nr.common.exception.NrCommonException;

public class LogicMappingException
extends NrCommonException {
    private static final long serialVersionUID = -9024002857916887229L;

    public LogicMappingException(String errorCode) {
        super(errorCode);
    }

    public LogicMappingException(String errorCode, String[] data) {
        super(errorCode, data);
    }
}

