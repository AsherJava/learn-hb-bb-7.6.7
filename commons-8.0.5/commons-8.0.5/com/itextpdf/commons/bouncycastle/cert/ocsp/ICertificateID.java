/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.ocsp;

import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;
import com.itextpdf.commons.bouncycastle.asn1.x509.IAlgorithmIdentifier;
import com.itextpdf.commons.bouncycastle.cert.IX509CertificateHolder;
import com.itextpdf.commons.bouncycastle.cert.ocsp.AbstractOCSPException;
import com.itextpdf.commons.bouncycastle.operator.IDigestCalculatorProvider;
import java.math.BigInteger;

public interface ICertificateID {
    public IASN1ObjectIdentifier getHashAlgOID();

    public IAlgorithmIdentifier getHashSha1();

    public boolean matchesIssuer(IX509CertificateHolder var1, IDigestCalculatorProvider var2) throws AbstractOCSPException;

    public BigInteger getSerialNumber();
}

