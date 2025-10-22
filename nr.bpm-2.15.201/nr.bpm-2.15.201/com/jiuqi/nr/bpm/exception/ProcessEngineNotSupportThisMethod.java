/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.exception.BpmException;

public class ProcessEngineNotSupportThisMethod
extends BpmException {
    private static final long serialVersionUID = 5766557831547852688L;

    public ProcessEngineNotSupportThisMethod(ProcessEngine.ProcessEngineType engineType, String methodName) {
        super(String.format("%s process engine not support method %s.", engineType.toString(), methodName));
    }
}

