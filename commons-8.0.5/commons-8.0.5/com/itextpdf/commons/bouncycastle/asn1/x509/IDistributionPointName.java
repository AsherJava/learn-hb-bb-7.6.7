/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.x509;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;

public interface IDistributionPointName
extends IASN1Encodable {
    public int getType();

    public IASN1Encodable getName();

    public int getFullName();
}

