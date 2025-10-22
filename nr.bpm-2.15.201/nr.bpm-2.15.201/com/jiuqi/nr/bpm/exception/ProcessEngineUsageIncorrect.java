/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

class ProcessEngineUsageIncorrect
extends BpmException {
    private static final long serialVersionUID = 3268176930249148523L;

    public ProcessEngineUsageIncorrect(String usageSuggest, String methodName) {
        super(String.format("method %s use incorrect. %s", methodName, usageSuggest));
    }
}

