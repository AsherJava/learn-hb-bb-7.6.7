/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.ess;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.ess.IESSCertIDv2;

public interface ISigningCertificateV2
extends IASN1Encodable {
    public IESSCertIDv2[] getCerts();
}

