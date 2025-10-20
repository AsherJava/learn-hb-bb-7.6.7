/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import java.io.IOException;

public interface IASN1Primitive
extends IASN1Encodable {
    public byte[] getEncoded() throws IOException;

    public byte[] getEncoded(String var1) throws IOException;
}

