/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

public class StreamException2
extends Exception {
    public static final String STREAM_READERROR = "Stream read error";
    public static final String STREAM_WRITEERROR = "Stream write error";
    public static final String STREAM_SIZEERROR = "Stream size error";
    public static final String STREAM_SEEKERROR = "Stream seek error";

    public StreamException2() {
    }

    public StreamException2(String message) {
        super(message);
    }

    public StreamException2(String message, Throwable cause) {
        super(message, cause);
    }

    public StreamException2(Throwable cause) {
        super(cause);
    }
}

