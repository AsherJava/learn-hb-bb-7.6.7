/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cert;

import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;
import com.itextpdf.commons.bouncycastle.cert.IX509CRLHolder;
import com.itextpdf.commons.bouncycastle.operator.IContentSigner;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

public interface IX509v2CRLBuilder {
    public IX509v2CRLBuilder addCRLEntry(BigInteger var1, Date var2, int var3);

    public IX509v2CRLBuilder addExtension(IASN1ObjectIdentifier var1, boolean var2, IASN1Encodable var3) throws IOException;

    public IX509v2CRLBuilder setNextUpdate(Date var1);

    public IX509CRLHolder build(IContentSigner var1);
}

