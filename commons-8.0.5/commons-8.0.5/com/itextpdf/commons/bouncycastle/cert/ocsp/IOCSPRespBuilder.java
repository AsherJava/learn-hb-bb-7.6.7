/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert.ocsp;

import com.itextpdf.commons.bouncycastle.cert.ocsp.AbstractOCSPException;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IBasicOCSPResp;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IOCSPResp;

public interface IOCSPRespBuilder {
    public int getSuccessful();

    public IOCSPResp build(int var1, IBasicOCSPResp var2) throws AbstractOCSPException;
}

