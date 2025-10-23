/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.exception;

public class ProcessNotEnableException
extends RuntimeException {
    public ProcessNotEnableException(String taskKey) {
        super("Report task \u300c" + taskKey + "\u300d has no process enabled !");
    }
}

