/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.ocsp;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import java.text.ParseException;
import java.util.Date;

public interface IBasicOCSPResponse
extends IASN1Encodable {
    public Date getProducedAtDate() throws ParseException;
}

