/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.x509;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.x509.IDistributionPointName;
import com.itextpdf.commons.bouncycastle.asn1.x509.IReasonFlags;

public interface IIssuingDistributionPoint
extends IASN1Encodable {
    public IDistributionPointName getDistributionPoint();

    public boolean onlyContainsUserCerts();

    public boolean onlyContainsCACerts();

    public boolean isIndirectCRL();

    public boolean onlyContainsAttributeCerts();

    public IReasonFlags getOnlySomeReasons();
}

