/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.producer;

import com.itextpdf.commons.actions.confirmations.ConfirmedEventWrapper;
import java.util.List;

interface IPlaceholderPopulator {
    public String populate(List<ConfirmedEventWrapper> var1, String var2);
}

