/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.pkcs;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.x509.IAlgorithmIdentifier;
import java.math.BigInteger;

public interface IRSASSAPSSParams
extends IASN1Encodable {
    public IAlgorithmIdentifier getHashAlgorithm();

    public IAlgorithmIdentifier getMaskGenAlgorithm();

    public BigInteger getSaltLength();

    public BigInteger getTrailerField();
}

