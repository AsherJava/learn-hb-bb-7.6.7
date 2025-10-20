/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cms.jcajce;

import com.itextpdf.commons.bouncycastle.cms.ISignerInfoGenerator;
import com.itextpdf.commons.bouncycastle.operator.AbstractOperatorCreationException;
import com.itextpdf.commons.bouncycastle.operator.IContentSigner;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public interface IJcaSignerInfoGeneratorBuilder {
    public ISignerInfoGenerator build(IContentSigner var1, X509Certificate var2) throws AbstractOperatorCreationException, CertificateEncodingException;
}

