/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.ocsp;

import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;
import com.itextpdf.commons.bouncycastle.asn1.x509.IExtension;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IReq;
import java.io.IOException;

public interface IOCSPReq {
    public byte[] getEncoded() throws IOException;

    public IReq[] getRequestList();

    public IExtension getExtension(IASN1ObjectIdentifier var1);
}

