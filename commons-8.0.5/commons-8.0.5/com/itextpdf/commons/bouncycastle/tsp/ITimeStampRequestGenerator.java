/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.tsp;

import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampRequest;
import java.math.BigInteger;

public interface ITimeStampRequestGenerator {
    public void setCertReq(boolean var1);

    public void setReqPolicy(String var1);

    public ITimeStampRequest generate(IASN1ObjectIdentifier var1, byte[] var2, BigInteger var3);
}

