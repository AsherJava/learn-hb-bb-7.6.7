/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.pkcs;

import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;

public interface IPKCSObjectIdentifiers {
    public IASN1ObjectIdentifier getIdAaSignatureTimeStampToken();

    public IASN1ObjectIdentifier getIdAaEtsSigPolicyId();

    public IASN1ObjectIdentifier getIdSpqEtsUri();

    public IASN1ObjectIdentifier getEnvelopedData();

    public IASN1ObjectIdentifier getData();
}

