/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.ocsp;

import com.itextpdf.commons.bouncycastle.cert.ocsp.ICertificateID;
import com.itextpdf.commons.bouncycastle.cert.ocsp.ICertificateStatus;
import java.util.Date;

public interface ISingleResp {
    public ICertificateID getCertID();

    public ICertificateStatus getCertStatus();

    public Date getNextUpdate();

    public Date getThisUpdate();
}

