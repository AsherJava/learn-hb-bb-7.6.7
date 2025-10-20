/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.jcajce;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;
import com.itextpdf.commons.bouncycastle.cert.AbstractCertIOException;
import com.itextpdf.commons.bouncycastle.cert.IX509CertificateHolder;
import com.itextpdf.commons.bouncycastle.operator.IContentSigner;

public interface IJcaX509v3CertificateBuilder {
    public IX509CertificateHolder build(IContentSigner var1);

    public IJcaX509v3CertificateBuilder addExtension(IASN1ObjectIdentifier var1, boolean var2, IASN1Encodable var3) throws AbstractCertIOException;
}

