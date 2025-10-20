/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.openssl.jcajce;

import com.itextpdf.commons.bouncycastle.operator.AbstractOperatorCreationException;
import com.itextpdf.commons.bouncycastle.operator.IInputDecryptorProvider;
import java.security.Provider;

public interface IJceOpenSSLPKCS8DecryptorProviderBuilder {
    public IJceOpenSSLPKCS8DecryptorProviderBuilder setProvider(Provider var1);

    public IInputDecryptorProvider build(char[] var1) throws AbstractOperatorCreationException;
}

