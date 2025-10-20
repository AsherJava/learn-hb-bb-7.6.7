/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cms.jcajce;

import com.itextpdf.commons.bouncycastle.cms.ISignerInformationVerifier;
import com.itextpdf.commons.bouncycastle.operator.AbstractOperatorCreationException;
import java.security.cert.X509Certificate;

public interface IJcaSimpleSignerInfoVerifierBuilder {
    public IJcaSimpleSignerInfoVerifierBuilder setProvider(String var1);

    public ISignerInformationVerifier build(X509Certificate var1) throws AbstractOperatorCreationException;
}

