/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.exceptions;

public class ITextException
extends RuntimeException {
    public ITextException() {
        super("Unknown ITextException.");
    }

    public ITextException(String message) {
        super(message);
    }

    public ITextException(Throwable cause) {
        super("Unknown ITextException.", cause);
    }

    public ITextException(String message, Throwable cause) {
        super(message, cause);
    }
}

