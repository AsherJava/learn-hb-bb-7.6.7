/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.tsp;

import com.itextpdf.commons.bouncycastle.asn1.cmp.IPKIFailureInfo;
import com.itextpdf.commons.bouncycastle.tsp.AbstractTSPException;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampRequest;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampToken;
import java.io.IOException;

public interface ITimeStampResponse {
    public void validate(ITimeStampRequest var1) throws AbstractTSPException;

    public IPKIFailureInfo getFailInfo();

    public ITimeStampToken getTimeStampToken();

    public String getStatusString();

    public byte[] getEncoded() throws IOException;
}

