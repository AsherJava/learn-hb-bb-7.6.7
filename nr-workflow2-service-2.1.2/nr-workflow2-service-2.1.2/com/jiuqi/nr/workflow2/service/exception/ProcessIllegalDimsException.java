/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.exception;

import com.jiuqi.nr.workflow2.service.exception.OperateStateCode;

public class ProcessIllegalDimsException
extends RuntimeException {
    public ProcessIllegalDimsException(OperateStateCode operateStateCode) {
        super(operateStateCode.toString());
    }

    public ProcessIllegalDimsException(OperateStateCode operateStateCode, Throwable cause) {
        super(operateStateCode.toString(), cause);
    }
}

