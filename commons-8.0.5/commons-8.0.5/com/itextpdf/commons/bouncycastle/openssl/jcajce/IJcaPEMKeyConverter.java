/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.openssl.jcajce;

import com.itextpdf.commons.bouncycastle.asn1.pkcs.IPrivateKeyInfo;
import com.itextpdf.commons.bouncycastle.openssl.AbstractPEMException;
import java.security.PrivateKey;
import java.security.Provider;

public interface IJcaPEMKeyConverter {
    public IJcaPEMKeyConverter setProvider(Provider var1);

    public PrivateKey getPrivateKey(IPrivateKeyInfo var1) throws AbstractPEMException;
}

