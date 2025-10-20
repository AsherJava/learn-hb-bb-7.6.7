/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.exceptions;

import com.itextpdf.commons.exceptions.ITextException;

public class UnknownProductException
extends ITextException {
    public static final String UNKNOWN_PRODUCT = "Product {0} is unknown. Probably you have to register it.";

    public UnknownProductException(String message) {
        super(message);
    }
}

