/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.tsp;

import com.itextpdf.commons.bouncycastle.tsp.AbstractTSPException;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampRequest;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampResponse;
import java.math.BigInteger;
import java.util.Date;

public interface ITimeStampResponseGenerator {
    public ITimeStampResponse generate(ITimeStampRequest var1, BigInteger var2, Date var3) throws AbstractTSPException;
}

