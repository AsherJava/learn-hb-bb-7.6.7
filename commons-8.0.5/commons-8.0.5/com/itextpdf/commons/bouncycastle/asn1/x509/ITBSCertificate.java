/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.x509;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.IASN1Integer;
import com.itextpdf.commons.bouncycastle.asn1.x500.IX500Name;
import com.itextpdf.commons.bouncycastle.asn1.x509.ISubjectPublicKeyInfo;

public interface ITBSCertificate
extends IASN1Encodable {
    public ISubjectPublicKeyInfo getSubjectPublicKeyInfo();

    public IX500Name getIssuer();

    public IASN1Integer getSerialNumber();
}

