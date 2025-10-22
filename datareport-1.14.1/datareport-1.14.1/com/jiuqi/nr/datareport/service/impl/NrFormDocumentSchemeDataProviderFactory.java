/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.office.template.document.IDocumentSchemaDataProvider
 *  com.jiuqi.nvwa.office.template.document.provider.IDocumentSchemaDataProviderFactory
 */
package com.jiuqi.nr.datareport.service.impl;

import com.jiuqi.nr.datareport.service.impl.NrFormDocumentSchemeDataProvider;
import com.jiuqi.nvwa.office.template.document.IDocumentSchemaDataProvider;
import com.jiuqi.nvwa.office.template.document.provider.IDocumentSchemaDataProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrFormDocumentSchemeDataProviderFactory
implements IDocumentSchemaDataProviderFactory {
    @Autowired
    private NrFormDocumentSchemeDataProvider nrFormDocumentSchemeDataProvider;

    public IDocumentSchemaDataProvider create() {
        return this.nrFormDocumentSchemeDataProvider;
    }
}

