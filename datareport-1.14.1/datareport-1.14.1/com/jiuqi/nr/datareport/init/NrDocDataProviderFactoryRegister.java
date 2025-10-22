/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.office.template.document.provider.DocumentDataProviderFactoryManager
 *  com.jiuqi.nvwa.office.template.document.provider.IDocumentSchemaDataProviderFactory
 *  com.jiuqi.nvwa.office.template.document.provider.ILegacyDocumentDataProviderFactory
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.datareport.init;

import com.jiuqi.nr.datareport.service.impl.NrFormDocumentSchemeDataProviderFactory;
import com.jiuqi.nr.datareport.service.impl.NrLegacyDocumentDataProviderFactory;
import com.jiuqi.nvwa.office.template.document.provider.DocumentDataProviderFactoryManager;
import com.jiuqi.nvwa.office.template.document.provider.IDocumentSchemaDataProviderFactory;
import com.jiuqi.nvwa.office.template.document.provider.ILegacyDocumentDataProviderFactory;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrDocDataProviderFactoryRegister
implements ModuleInitiator {
    @Autowired
    private NrFormDocumentSchemeDataProviderFactory nrFormDocumentSchemeDataProviderFactory;
    @Autowired
    private NrLegacyDocumentDataProviderFactory nrLegacyDocumentDataProviderFactory;

    public void init(ServletContext context) throws Exception {
        DocumentDataProviderFactoryManager.getInstance().registerSchemaDataProviderFactory((IDocumentSchemaDataProviderFactory)this.nrFormDocumentSchemeDataProviderFactory);
        DocumentDataProviderFactoryManager.getInstance().setLegacyDocumentDataProviderFactory((ILegacyDocumentDataProviderFactory)this.nrLegacyDocumentDataProviderFactory);
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

