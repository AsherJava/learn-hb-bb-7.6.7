/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.tsp;

import com.itextpdf.commons.bouncycastle.asn1.tsp.ITSTInfo;
import com.itextpdf.commons.bouncycastle.asn1.x509.IAlgorithmIdentifier;
import java.io.IOException;
import java.util.Date;

public interface ITimeStampTokenInfo {
    public IAlgorithmIdentifier getHashAlgorithm();

    public ITSTInfo toASN1Structure();

    public Date getGenTime();

    public byte[] getEncoded() throws IOException;
}

