/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.office.template.document.ILegacyDocumentDataProvider
 *  com.jiuqi.nvwa.office.template.document.provider.ILegacyDocumentDataProviderFactory
 */
package com.jiuqi.nr.datareport.service.impl;

import com.jiuqi.nr.datareport.service.impl.NrLegacyDocumentDataProvider;
import com.jiuqi.nvwa.office.template.document.ILegacyDocumentDataProvider;
import com.jiuqi.nvwa.office.template.document.provider.ILegacyDocumentDataProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrLegacyDocumentDataProviderFactory
implements ILegacyDocumentDataProviderFactory {
    @Autowired
    private NrLegacyDocumentDataProvider nrLegacyDocumentDataProvider;

    public ILegacyDocumentDataProvider create() {
        return this.nrLegacyDocumentDataProvider;
    }
}

