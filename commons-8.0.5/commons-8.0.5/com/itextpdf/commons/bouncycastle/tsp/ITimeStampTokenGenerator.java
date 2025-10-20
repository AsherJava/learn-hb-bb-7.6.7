/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.tsp;

import com.itextpdf.commons.bouncycastle.cert.jcajce.IJcaCertStore;
import com.itextpdf.commons.bouncycastle.tsp.AbstractTSPException;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampRequest;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampToken;
import java.math.BigInteger;
import java.util.Date;

public interface ITimeStampTokenGenerator {
    public void setAccuracySeconds(int var1);

    public void addCertificates(IJcaCertStore var1);

    public ITimeStampToken generate(ITimeStampRequest var1, BigInteger var2, Date var3) throws AbstractTSPException;
}

