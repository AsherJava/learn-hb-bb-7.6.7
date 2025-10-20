/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cms;

import com.itextpdf.commons.bouncycastle.cms.AbstractCMSException;
import com.itextpdf.commons.bouncycastle.cms.IRecipient;
import com.itextpdf.commons.bouncycastle.cms.IRecipientId;

public interface IRecipientInformation {
    public byte[] getContent(IRecipient var1) throws AbstractCMSException;

    public IRecipientId getRID();
}

