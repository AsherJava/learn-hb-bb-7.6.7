/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.ocsp;

import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;

public interface IOCSPObjectIdentifiers {
    public IASN1ObjectIdentifier getIdPkixOcspBasic();

    public IASN1ObjectIdentifier getIdPkixOcspNonce();

    public IASN1ObjectIdentifier getIdPkixOcspNoCheck();

    public IASN1ObjectIdentifier getIdPkixOcspArchiveCutoff();
}

