/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.x509;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;

public interface IAlgorithmIdentifier
extends IASN1Encodable {
    public IASN1ObjectIdentifier getAlgorithm();

    public IASN1Encodable getParameters();
}

