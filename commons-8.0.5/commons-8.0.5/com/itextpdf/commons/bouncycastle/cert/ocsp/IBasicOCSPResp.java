/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.ocsp;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;
import com.itextpdf.commons.bouncycastle.cert.IX509CertificateHolder;
import com.itextpdf.commons.bouncycastle.cert.ocsp.AbstractOCSPException;
import com.itextpdf.commons.bouncycastle.cert.ocsp.ISingleResp;
import com.itextpdf.commons.bouncycastle.operator.IContentVerifierProvider;
import java.io.IOException;
import java.util.Date;

public interface IBasicOCSPResp {
    public ISingleResp[] getResponses();

    public boolean isSignatureValid(IContentVerifierProvider var1) throws AbstractOCSPException;

    public IX509CertificateHolder[] getCerts();

    public byte[] getEncoded() throws IOException;

    public Date getProducedAt();

    public IASN1Encodable getExtensionParsedValue(IASN1ObjectIdentifier var1);
}

