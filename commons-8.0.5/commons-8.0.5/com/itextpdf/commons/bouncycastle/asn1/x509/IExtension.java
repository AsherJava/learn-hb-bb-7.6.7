/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.x509;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;

public interface IExtension
extends IASN1Encodable {
    public IASN1ObjectIdentifier getCRlDistributionPoints();

    public IASN1ObjectIdentifier getIssuingDistributionPoint();

    public IASN1ObjectIdentifier getAuthorityInfoAccess();

    public IASN1ObjectIdentifier getBasicConstraints();

    public IASN1ObjectIdentifier getKeyUsage();

    public IASN1ObjectIdentifier getExtendedKeyUsage();

    public IASN1ObjectIdentifier getAuthorityKeyIdentifier();

    public IASN1ObjectIdentifier getSubjectKeyIdentifier();

    public IASN1ObjectIdentifier getExpiredCertsOnCRL();
}

