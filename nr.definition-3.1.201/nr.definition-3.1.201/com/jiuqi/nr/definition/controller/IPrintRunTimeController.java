/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import java.util.List;

public interface IPrintRunTimeController {
    public PrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String var1);

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByTask(String var1);

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String var1);

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByFormSchemeWithoutBinary(String var1);

    public PrintTemplateDefine queryPrintTemplateDefine(String var1);

    public PrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String var1, String var2);

    public List<PrintTemplateDefine> getAllPrintTemplateInScheme(String var1);

    public PrintSchemeAttributeDefine getPrintSchemeAttribute(PrintTemplateSchemeDefine var1);

    public PrintTemplateAttributeDefine getPrintTemplateAttribute(PrintTemplateDefine var1);
}

