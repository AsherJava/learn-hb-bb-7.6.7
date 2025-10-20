/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert;

import com.itextpdf.commons.bouncycastle.asn1.x509.IAuthorityKeyIdentifier;
import com.itextpdf.commons.bouncycastle.asn1.x509.ISubjectKeyIdentifier;
import com.itextpdf.commons.bouncycastle.asn1.x509.ISubjectPublicKeyInfo;

public interface IX509ExtensionUtils {
    public IAuthorityKeyIdentifier createAuthorityKeyIdentifier(ISubjectPublicKeyInfo var1);

    public ISubjectKeyIdentifier createSubjectKeyIdentifier(ISubjectPublicKeyInfo var1);
}

