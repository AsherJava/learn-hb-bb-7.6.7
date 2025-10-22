/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.exception.BpmException;

public class ProcessEngineNotFound
extends BpmException {
    private static final long serialVersionUID = 2633916646675449012L;

    public ProcessEngineNotFound(ProcessEngine.ProcessEngineType engineType) {
        super(String.format("can not find process engine %s.", engineType.toString()));
    }
}

