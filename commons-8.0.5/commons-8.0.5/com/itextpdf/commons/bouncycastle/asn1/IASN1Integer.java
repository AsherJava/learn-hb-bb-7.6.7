/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Primitive;
import java.math.BigInteger;

public interface IASN1Integer
extends IASN1Primitive {
    public BigInteger getValue();
}

