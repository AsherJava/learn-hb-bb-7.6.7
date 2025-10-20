/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.ess;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.x509.IAlgorithmIdentifier;

public interface IESSCertIDv2
extends IASN1Encodable {
    public IAlgorithmIdentifier getHashAlgorithm();

    public byte[] getCertHash();
}

