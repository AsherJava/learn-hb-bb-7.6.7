/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Primitive;
import java.io.Closeable;
import java.io.IOException;

public interface IASN1InputStream
extends Closeable {
    public IASN1Primitive readObject() throws IOException;

    @Override
    public void close() throws IOException;
}

