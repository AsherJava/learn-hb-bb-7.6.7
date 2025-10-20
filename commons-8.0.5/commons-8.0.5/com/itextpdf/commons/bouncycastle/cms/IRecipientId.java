/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cms;

import com.itextpdf.commons.bouncycastle.cert.IX509CertificateHolder;

public interface IRecipientId {
    public boolean match(IX509CertificateHolder var1);
}

