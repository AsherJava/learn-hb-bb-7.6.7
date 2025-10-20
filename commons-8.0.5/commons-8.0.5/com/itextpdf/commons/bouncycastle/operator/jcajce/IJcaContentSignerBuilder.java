/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.operator.jcajce;

import com.itextpdf.commons.bouncycastle.operator.AbstractOperatorCreationException;
import com.itextpdf.commons.bouncycastle.operator.IContentSigner;
import java.security.PrivateKey;

public interface IJcaContentSignerBuilder {
    public IContentSigner build(PrivateKey var1) throws AbstractOperatorCreationException;

    public IJcaContentSignerBuilder setProvider(String var1);
}

