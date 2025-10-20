/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.tsp;

import com.itextpdf.commons.bouncycastle.cms.ISignerInformationVerifier;
import com.itextpdf.commons.bouncycastle.tsp.AbstractTSPException;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampTokenInfo;
import java.io.IOException;

public interface ITimeStampToken {
    public ITimeStampTokenInfo getTimeStampInfo();

    public void validate(ISignerInformationVerifier var1) throws AbstractTSPException;

    public byte[] getEncoded() throws IOException;
}

