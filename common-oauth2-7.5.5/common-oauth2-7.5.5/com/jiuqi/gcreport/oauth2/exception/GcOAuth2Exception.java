/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.oauth2.exception;

public class GcOAuth2Exception
extends RuntimeException {
    static final long serialVersionUID = -8207224895475307692L;

    public GcOAuth2Exception() {
    }

    public GcOAuth2Exception(String message) {
        super(message);
    }

    public GcOAuth2Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public GcOAuth2Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GcOAuth2Exception(Throwable cause) {
        super(cause);
    }
}

