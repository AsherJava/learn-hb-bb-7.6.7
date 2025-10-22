/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import java.util.List;

public interface IPrintDesignTimeController {
    public DesignPrintTemplateSchemeDefine createPrintTemplateSchemeDefine();

    public String insertPrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine var1);

    public void updatePrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine var1);

    public void deletePrintTemplateSchemeDefine(String var1);

    public void exchangePrintTemplateSchemeOrder(String var1, String var2);

    public List<DesignPrintTemplateSchemeDefine> getAllPrintScheme();

    public DesignPrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String var1);

    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByTask(String var1);

    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String var1);

    public DesignPrintTemplateDefine createPrintTemplateDefine();

    public String insertPrintTemplateDefine(DesignPrintTemplateDefine var1);

    public void updatePrintTemplateDefine(DesignPrintTemplateDefine var1);

    public void deletePrintTemplateDefine(String var1);

    public void deletePrintTemplateDefineByScheme(String var1);

    public void deletePrintTemplateDefine(String var1, String ... var2);

    public void deletePrintTemplateDefineByForm(String ... var1);

    public DesignPrintTemplateDefine queryPrintTemplateDefine(String var1);

    public DesignPrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String var1, String var2);

    public List<DesignPrintTemplateDefine> getAllPrintTemplateInScheme(String var1);

    public WordLabelDefine createWordLabelDefine();

    public PrintSchemeAttributeDefine getPrintSchemeAttribute(DesignPrintTemplateSchemeDefine var1);

    public void setPrintSchemeAttribute(DesignPrintTemplateSchemeDefine var1, PrintSchemeAttributeDefine var2);

    public PrintTemplateAttributeDefine getPrintTemplateAttribute(DesignPrintTemplateDefine var1);

    public void setPrintTemplateAttribute(DesignPrintTemplateDefine var1, PrintTemplateAttributeDefine var2);

    public List<DesignPrintTemplateDefine> queryAllPrintTemplate(String var1);

    public int[] insertTemplates(DesignPrintTemplateDefine[] var1);

    public int[] updateTemplates(DesignPrintTemplateDefine[] var1);

    public DesignPrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String var1, boolean var2);

    public List<DesignPrintTemplateDefine> getAllPrintTemplateInScheme(String var1, boolean var2);

    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String var1, boolean var2);

    public DesignPrintTemplateDefine queryPrintTemplateDefine(String var1, boolean var2);

    public DesignPrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String var1, String var2, boolean var3);
}

