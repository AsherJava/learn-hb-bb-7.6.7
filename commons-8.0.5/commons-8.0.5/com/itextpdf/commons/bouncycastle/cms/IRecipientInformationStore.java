/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.cms;

import com.itextpdf.commons.bouncycastle.cms.IRecipientId;
import com.itextpdf.commons.bouncycastle.cms.IRecipientInformation;
import java.util.Collection;

public interface IRecipientInformationStore {
    public Collection<IRecipientInformation> getRecipients();

    public IRecipientInformation get(IRecipientId var1);
}

