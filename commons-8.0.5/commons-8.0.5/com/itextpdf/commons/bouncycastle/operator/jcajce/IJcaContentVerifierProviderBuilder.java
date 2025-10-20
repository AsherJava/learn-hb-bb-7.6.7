/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.operator.jcajce;

import com.itextpdf.commons.bouncycastle.operator.AbstractOperatorCreationException;
import com.itextpdf.commons.bouncycastle.operator.IContentVerifierProvider;
import java.security.PublicKey;

public interface IJcaContentVerifierProviderBuilder {
    public IJcaContentVerifierProviderBuilder setProvider(String var1);

    public IContentVerifierProvider build(PublicKey var1) throws AbstractOperatorCreationException;
}

