/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.x509;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.x509.IDistributionPointName;
import com.itextpdf.commons.bouncycastle.asn1.x509.IGeneralNames;
import com.itextpdf.commons.bouncycastle.asn1.x509.IReasonFlags;

public interface IDistributionPoint
extends IASN1Encodable {
    public IDistributionPointName getDistributionPoint();

    public IGeneralNames getCRLIssuer();

    public IReasonFlags getReasons();
}

