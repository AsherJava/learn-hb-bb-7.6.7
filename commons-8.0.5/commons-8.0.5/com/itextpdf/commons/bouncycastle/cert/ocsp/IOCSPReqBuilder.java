/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.ocsp;

import com.itextpdf.commons.bouncycastle.asn1.x509.IExtensions;
import com.itextpdf.commons.bouncycastle.cert.ocsp.AbstractOCSPException;
import com.itextpdf.commons.bouncycastle.cert.ocsp.ICertificateID;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IOCSPReq;

public interface IOCSPReqBuilder {
    public IOCSPReqBuilder setRequestExtensions(IExtensions var1);

    public IOCSPReqBuilder addRequest(ICertificateID var1);

    public IOCSPReq build() throws AbstractOCSPException;
}

