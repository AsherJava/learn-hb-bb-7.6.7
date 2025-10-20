/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.jcajce;

import com.itextpdf.commons.bouncycastle.cert.IX509CertificateHolder;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public interface IJcaX509CertificateConverter {
    public X509Certificate getCertificate(IX509CertificateHolder var1) throws CertificateException;

    public IJcaX509CertificateConverter setProvider(Provider var1);
}

