/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Primitive;
import java.io.Closeable;
import java.io.IOException;

public interface IASN1OutputStream
extends Closeable {
    public void writeObject(IASN1Primitive var1) throws IOException;

    @Override
    public void close() throws IOException;
}

