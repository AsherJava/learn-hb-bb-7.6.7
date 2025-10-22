/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.DataTransformUtil;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.internal.impl.print.PrintSchemeAttributeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.PrintTemplateAttributeDefineImpl;
import java.util.List;

public interface IPrintRuntimeService {
    public PrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String var1);

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByTask(String var1);

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String var1);

    public PrintTemplateDefine queryPrintTemplateDefine(String var1);

    public PrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String var1, String var2);

    public List<PrintTemplateDefine> getAllPrintTemplateInScheme(String var1);

    default public PrintSchemeAttributeDefine getPrintSchemeAttribute(PrintTemplateSchemeDefine printScheme) {
        PrintSchemeAttributeDefine define = null;
        define = null != printScheme && null != printScheme.getCommonAttribute() && printScheme.getCommonAttribute().length > 1 ? DataTransformUtil.deserialize(printScheme.getCommonAttribute(), PrintSchemeAttributeDefine.class) : new PrintSchemeAttributeDefineImpl();
        return define;
    }

    default public PrintTemplateAttributeDefine getPrintTemplateAttribute(PrintTemplateDefine printTemplate) {
        PrintTemplateAttributeDefine define = null;
        define = null != printTemplate && null != printTemplate.getLabelData() && printTemplate.getLabelData().length > 1 ? DataTransformUtil.deserialize(printTemplate.getLabelData(), PrintTemplateAttributeDefine.class) : new PrintTemplateAttributeDefineImpl();
        return define;
    }
}

