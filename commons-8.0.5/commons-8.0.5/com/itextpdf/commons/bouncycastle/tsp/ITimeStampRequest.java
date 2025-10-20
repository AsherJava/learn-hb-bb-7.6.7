/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.tsp;

import java.io.IOException;
import java.math.BigInteger;

public interface ITimeStampRequest {
    public byte[] getEncoded() throws IOException;

    public BigInteger getNonce();
}

