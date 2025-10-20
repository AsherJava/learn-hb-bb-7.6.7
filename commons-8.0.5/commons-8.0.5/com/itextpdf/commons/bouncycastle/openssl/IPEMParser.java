/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.openssl;

import java.io.Closeable;
import java.io.IOException;

public interface IPEMParser
extends Closeable {
    public Object readObject() throws IOException;
}

