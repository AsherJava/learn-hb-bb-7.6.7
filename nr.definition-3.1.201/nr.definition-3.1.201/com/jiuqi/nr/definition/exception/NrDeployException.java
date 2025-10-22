/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.exception;

public class NrDeployException
extends RuntimeException {
    private static final long serialVersionUID = 5258486732208838802L;

    public NrDeployException() {
    }

    public NrDeployException(String message) {
        super(message);
    }

    public NrDeployException(String message, Throwable cause) {
        super(message, cause);
    }

    public NrDeployException(Throwable cause) {
        super(cause);
    }
}

