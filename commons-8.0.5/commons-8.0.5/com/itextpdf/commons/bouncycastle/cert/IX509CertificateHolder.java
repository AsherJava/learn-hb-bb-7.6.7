/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert;

import com.itextpdf.commons.bouncycastle.asn1.x509.IAlgorithmIdentifier;

public interface IX509CertificateHolder {
    public IAlgorithmIdentifier getSignatureAlgorithm();
}

