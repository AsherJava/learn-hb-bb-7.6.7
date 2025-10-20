/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.ocsp;

import com.itextpdf.commons.bouncycastle.cert.ocsp.AbstractOCSPException;
import java.io.IOException;

public interface IOCSPResp {
    public byte[] getEncoded() throws IOException;

    public int getStatus();

    public Object getResponseObject() throws AbstractOCSPException;

    public int getSuccessful();
}

