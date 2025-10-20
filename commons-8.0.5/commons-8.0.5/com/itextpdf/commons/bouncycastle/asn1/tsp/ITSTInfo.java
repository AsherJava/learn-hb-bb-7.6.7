/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.asn1.tsp;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.tsp.IMessageImprint;
import java.text.ParseException;
import java.util.Date;

public interface ITSTInfo
extends IASN1Encodable {
    public IMessageImprint getMessageImprint();

    public Date getGenTime() throws ParseException;
}

