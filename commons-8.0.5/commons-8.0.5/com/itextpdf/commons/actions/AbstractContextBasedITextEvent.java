/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.AbstractProductITextEvent;
import com.itextpdf.commons.actions.contexts.IMetaInfo;
import com.itextpdf.commons.actions.data.ProductData;

public abstract class AbstractContextBasedITextEvent
extends AbstractProductITextEvent {
    private IMetaInfo metaInfo;

    protected AbstractContextBasedITextEvent(ProductData productData, IMetaInfo metaInfo) {
        super(productData);
        this.metaInfo = metaInfo;
    }

    public Class<?> getClassFromContext() {
        return this.getClass();
    }

    public boolean setMetaInfo(IMetaInfo metaInfo) {
        if (this.metaInfo != null) {
            return false;
        }
        this.metaInfo = metaInfo;
        return true;
    }

    IMetaInfo getMetaInfo() {
        return this.metaInfo;
    }
}

