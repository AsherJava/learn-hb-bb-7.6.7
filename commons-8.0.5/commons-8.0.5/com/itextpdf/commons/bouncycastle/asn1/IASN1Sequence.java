/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.IASN1Primitive;
import java.util.Enumeration;

public interface IASN1Sequence
extends IASN1Primitive {
    public IASN1Encodable getObjectAt(int var1);

    public Enumeration getObjects();

    public int size();

    public IASN1Encodable[] toArray();
}

