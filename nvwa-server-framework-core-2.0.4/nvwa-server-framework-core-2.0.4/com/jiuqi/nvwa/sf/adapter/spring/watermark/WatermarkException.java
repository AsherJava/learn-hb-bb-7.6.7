/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark;

public class WatermarkException
extends Exception {
    private static final long serialVersionUID = 1L;

    public WatermarkException() {
    }

    public WatermarkException(String message) {
        super(message);
    }

    public WatermarkException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public WatermarkException(String message, Throwable cause) {
        super(message, cause);
    }
}

