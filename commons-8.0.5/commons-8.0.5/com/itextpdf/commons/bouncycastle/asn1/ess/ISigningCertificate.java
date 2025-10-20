/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.ess;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.ess.IESSCertID;

public interface ISigningCertificate
extends IASN1Encodable {
    public IESSCertID[] getCerts();
}

