/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.ITemplateDocument
 */
package com.jiuqi.nr.definition.print.service.impl;

import com.jiuqi.nr.definition.api.IRunTimePrintController;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.print.service.IPrintLabelService;
import com.jiuqi.nr.definition.print.service.IPrintSchemeService;
import com.jiuqi.nr.definition.print.service.IPrintTemplateService;
import com.jiuqi.xg.process.ITemplateDocument;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintTemplateServiceImpl
implements IPrintSchemeService,
IPrintTemplateService,
IPrintLabelService {
    @Autowired
    private IRunTimePrintController runTimePrintController;

    @Override
    public ITemplateDocument loadNewRuntimeTempDoc(String printSchemeKey, String formKey) throws Exception {
        return this.runTimePrintController.getTemplateDocumentBySchemeAndForm(printSchemeKey, formKey);
    }

    @Override
    public ITemplateDocument loadSchemeTemplate(String printSchemeKey) throws Exception {
        return this.runTimePrintController.getCoverTemplateDocument(printSchemeKey);
    }

    @Override
    public boolean exsitSchemeTemplate(String printSchemeKey) throws Exception {
        return this.runTimePrintController.existCoverTemplateDocument(printSchemeKey);
    }

    @Override
    public List<WordLabelDefine> getLabelsWithLocation(String printSchemeKey, String formKey) {
        return this.runTimePrintController.listRelativePositionLabel(printSchemeKey, formKey);
    }
}

