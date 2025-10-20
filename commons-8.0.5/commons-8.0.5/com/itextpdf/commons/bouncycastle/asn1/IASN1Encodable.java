/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Primitive;

public interface IASN1Encodable {
    public IASN1Primitive toASN1Primitive();

    public boolean isNull();
}

