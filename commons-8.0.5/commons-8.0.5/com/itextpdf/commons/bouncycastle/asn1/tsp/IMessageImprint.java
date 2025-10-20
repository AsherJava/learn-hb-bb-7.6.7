/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.tsp;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.x509.IAlgorithmIdentifier;

public interface IMessageImprint
extends IASN1Encodable {
    public byte[] getHashedMessage();

    public IAlgorithmIdentifier getHashAlgorithm();
}

