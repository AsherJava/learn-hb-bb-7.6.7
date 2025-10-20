/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.ocsp;

import com.itextpdf.commons.bouncycastle.cert.ocsp.ICertificateStatus;
import java.util.Date;

public interface IRevokedStatus
extends ICertificateStatus {
    public Date getRevocationTime();
}

