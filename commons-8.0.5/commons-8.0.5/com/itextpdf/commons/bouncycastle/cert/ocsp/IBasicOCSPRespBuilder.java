/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.ocsp;

import com.itextpdf.commons.bouncycastle.asn1.x509.IExtensions;
import com.itextpdf.commons.bouncycastle.cert.IX509CertificateHolder;
import com.itextpdf.commons.bouncycastle.cert.ocsp.AbstractOCSPException;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IBasicOCSPResp;
import com.itextpdf.commons.bouncycastle.cert.ocsp.ICertificateID;
import com.itextpdf.commons.bouncycastle.cert.ocsp.ICertificateStatus;
import com.itextpdf.commons.bouncycastle.operator.IContentSigner;
import java.util.Date;

public interface IBasicOCSPRespBuilder {
    public IBasicOCSPRespBuilder setResponseExtensions(IExtensions var1);

    public IBasicOCSPRespBuilder addResponse(ICertificateID var1, ICertificateStatus var2, Date var3, Date var4, IExtensions var5);

    public IBasicOCSPResp build(IContentSigner var1, IX509CertificateHolder[] var2, Date var3) throws AbstractOCSPException;
}

