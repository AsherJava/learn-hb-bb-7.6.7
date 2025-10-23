/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

public class ProcessEngineRegisterException
extends RuntimeException {
    private static final long serialVersionUID = -8937782017893954875L;

    public ProcessEngineRegisterException(String processEngineId, String message) {
        super("can not register process engine '" + processEngineId + "': " + message);
    }
}

