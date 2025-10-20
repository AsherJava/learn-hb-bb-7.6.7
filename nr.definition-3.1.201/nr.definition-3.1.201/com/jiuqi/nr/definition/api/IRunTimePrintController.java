/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.ITemplateDocument
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.internal.stream.param.PrintSchemeListStream;
import com.jiuqi.nr.definition.internal.stream.param.PrintSchemeStream;
import com.jiuqi.xg.process.ITemplateDocument;
import java.util.List;

public interface IRunTimePrintController {
    public PrintSchemeStream getPrintTemplateScheme(String var1);

    public PrintSchemeListStream listPrintTemplateSchemeByFormScheme(String var1);

    public PrintSchemeAttributeDefine getPrintSchemeAttribute(PrintTemplateSchemeDefine var1);

    public PrintTemplateDefine getPrintTemplate(String var1);

    public PrintTemplateDefine getPrintTemplateBySchemeAndForm(String var1, String var2);

    public List<PrintTemplateDefine> listPrintTemplateByScheme(String var1);

    public boolean existCoverTemplateDocument(String var1);

    public ITemplateDocument getCoverTemplateDocument(String var1);

    public ITemplateDocument getTemplateDocumentBySchemeAndForm(String var1, String var2);

    public List<WordLabelDefine> listRelativePositionLabel(String var1, String var2);

    public PrintTemplateAttributeDefine getPrintTemplateAttribute(PrintTemplateDefine var1);

    public PrintSettingDefine getDefaultPrintSettingDefine(String var1, String var2);

    public PrintSettingDefine getPrintSettingDefine(String var1, String var2);

    public List<PrintSettingDefine> listPrintSettingDefine(String var1);

    public List<PrintComTemDefine> listPrintComTemByScheme(String var1);
}

