/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Primitive;
import java.text.ParseException;
import java.util.Date;

public interface IASN1GeneralizedTime
extends IASN1Primitive {
    public Date getDate() throws ParseException;
}

