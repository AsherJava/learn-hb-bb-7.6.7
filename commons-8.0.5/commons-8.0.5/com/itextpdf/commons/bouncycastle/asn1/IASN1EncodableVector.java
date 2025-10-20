/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Primitive;
import com.itextpdf.commons.bouncycastle.asn1.cms.IAttribute;
import com.itextpdf.commons.bouncycastle.asn1.x509.IAlgorithmIdentifier;

public interface IASN1EncodableVector {
    public void add(IASN1Primitive var1);

    public void add(IAttribute var1);

    public void add(IAlgorithmIdentifier var1);

    public void addOptional(IASN1Primitive var1);

    public void addOptional(IAttribute var1);

    public void addOptional(IAlgorithmIdentifier var1);
}

