/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimePrintTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimePrintTemplateSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IPrintRuntimeService;
import com.jiuqi.nr.definition.internal.service.RunTimePrintTemplateDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimePrintTemplateSchemeDefineService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrintRunTimeWithAuthService {
    @Autowired
    private IPrintRuntimeService printService;
    @Autowired
    private RunTimePrintTemplateSchemeDefineService printSchemeService;
    @Autowired
    private RunTimePrintTemplateDefineService printTemplateService;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;

    public PrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String printSchemeKey) throws Exception {
        if (this.canReadPrintScheme(printSchemeKey)) {
            PrintTemplateSchemeDefine schemeDefine = this.printService.queryPrintTemplateSchemeDefine(printSchemeKey);
            PrintTemplateSchemeDefine resultDefine = this.toPrintTemplateSchemeDefine(schemeDefine);
            return resultDefine;
        }
        return null;
    }

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByTask(String taskKey) throws Exception {
        List<PrintTemplateSchemeDefine> schemeDefines = this.printService.getAllPrintSchemeByTask(taskKey);
        ArrayList<PrintTemplateSchemeDefine> resultDefines = new ArrayList<PrintTemplateSchemeDefine>();
        for (PrintTemplateSchemeDefine schemeDefine : schemeDefines) {
            if (!this.canReadPrintScheme(schemeDefine.getKey())) continue;
            resultDefines.add(this.toPrintTemplateSchemeDefine(schemeDefine));
        }
        return resultDefines;
    }

    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey) throws Exception {
        List<PrintTemplateSchemeDefine> schemeDefines = this.printService.getAllPrintSchemeByFormScheme(formSchemeKey);
        ArrayList<PrintTemplateSchemeDefine> resultDefines = new ArrayList<PrintTemplateSchemeDefine>();
        for (PrintTemplateSchemeDefine schemeDefine : schemeDefines) {
            if (!this.canReadPrintScheme(schemeDefine.getKey())) continue;
            resultDefines.add(this.toPrintTemplateSchemeDefine(schemeDefine));
        }
        return resultDefines;
    }

    public PrintTemplateDefine queryPrintTemplateDefine(String printTemplateKey) throws Exception {
        PrintTemplateDefine templateDefine = this.printService.queryPrintTemplateDefine(printTemplateKey);
        if (this.canReadPrintScheme(templateDefine.getPrintSchemeKey())) {
            PrintTemplateDefine resultDefine = this.toPrintTemplateDefine(templateDefine);
            return resultDefine;
        }
        return null;
    }

    public PrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String printSchemeKey, String formKey) throws Exception {
        if (this.canReadPrintScheme(printSchemeKey)) {
            PrintTemplateDefine templateDefine = this.printService.queryPrintTemplateDefineBySchemeAndForm(printSchemeKey, formKey);
            PrintTemplateDefine resultDefine = this.toPrintTemplateDefine(templateDefine);
            return resultDefine;
        }
        return null;
    }

    public List<PrintTemplateDefine> getAllPrintTemplateInScheme(String printSchemeKey) throws Exception {
        if (this.canReadPrintScheme(printSchemeKey)) {
            List<PrintTemplateDefine> templateDefines = this.printService.getAllPrintTemplateInScheme(printSchemeKey);
            ArrayList<PrintTemplateDefine> resultDefines = new ArrayList<PrintTemplateDefine>();
            for (PrintTemplateDefine templateDefine : templateDefines) {
                if (templateDefine == null) continue;
                resultDefines.add(this.toPrintTemplateDefine(templateDefine));
            }
            return resultDefines;
        }
        return Collections.emptyList();
    }

    public PrintSchemeAttributeDefine getPrintSchemeAttribute(PrintTemplateSchemeDefine printScheme) throws Exception {
        if (this.canReadPrintScheme(printScheme.getKey())) {
            return this.printService.getPrintSchemeAttribute(printScheme);
        }
        return null;
    }

    public PrintTemplateAttributeDefine getPrintTemplateAttribute(PrintTemplateDefine printTemplate) throws Exception {
        if (this.canReadPrintScheme(printTemplate.getPrintSchemeKey())) {
            return this.printService.getPrintTemplateAttribute(printTemplate);
        }
        return null;
    }

    private PrintTemplateSchemeDefine toPrintTemplateSchemeDefine(PrintTemplateSchemeDefine schemeDefine) throws Exception {
        if (schemeDefine == null) {
            return null;
        }
        RunTimePrintTemplateSchemeDefineImpl resultDefine = new RunTimePrintTemplateSchemeDefineImpl();
        this.clonePrintSchemeDefine(resultDefine, schemeDefine);
        this.setPrintSchemeData(resultDefine);
        return resultDefine;
    }

    private void clonePrintSchemeDefine(RunTimePrintTemplateSchemeDefineImpl resultDefine, PrintTemplateSchemeDefine schemeDefine) {
        resultDefine.setDescription(schemeDefine.getDescription());
        resultDefine.setFormSchemeKey(schemeDefine.getFormSchemeKey());
        resultDefine.setKey(schemeDefine.getKey());
        resultDefine.setOrder(schemeDefine.getOrder());
        resultDefine.setOwnerLevelAndId(schemeDefine.getOwnerLevelAndId());
        resultDefine.setTaskKey(schemeDefine.getTaskKey());
        resultDefine.setTitle(schemeDefine.getTitle());
        resultDefine.setUpdateTime(schemeDefine.getUpdateTime());
        resultDefine.setVersion(schemeDefine.getVersion());
    }

    private void setPrintSchemeData(RunTimePrintTemplateSchemeDefineImpl resultDefine) throws Exception {
        resultDefine.setGatherCoverData(this.printSchemeService.getPrintGatherData(resultDefine.getKey()));
        resultDefine.setCommonAttribute(this.printSchemeService.getPrintAttrData(resultDefine.getKey()));
    }

    private PrintTemplateDefine toPrintTemplateDefine(PrintTemplateDefine templateDefine) throws Exception {
        if (templateDefine == null) {
            return null;
        }
        RunTimePrintTemplateDefineImpl resultDefine = new RunTimePrintTemplateDefineImpl();
        this.clonePrintTemplateDefine(resultDefine, templateDefine);
        this.setPrintTemplateData(resultDefine);
        return resultDefine;
    }

    private void setPrintTemplateData(RunTimePrintTemplateDefineImpl resultDefine) throws Exception {
        resultDefine.setTemplateData(this.printTemplateService.getPrintTemplateData(resultDefine.getKey()));
        resultDefine.setLabelData(this.printTemplateService.getPrintLableData(resultDefine.getKey()));
    }

    private void clonePrintTemplateDefine(RunTimePrintTemplateDefineImpl resultDefine, PrintTemplateDefine templateDefine) {
        resultDefine.setDescription(templateDefine.getDescription());
        resultDefine.setFormKey(templateDefine.getFormKey());
        resultDefine.setKey(templateDefine.getKey());
        resultDefine.setOrder(templateDefine.getOrder());
        resultDefine.setOwnerLevelAndId(templateDefine.getOwnerLevelAndId());
        resultDefine.setPrintSchemeKey(templateDefine.getPrintSchemeKey());
        resultDefine.setTitle(templateDefine.getTitle());
        resultDefine.setUpdateTime(templateDefine.getUpdateTime());
        resultDefine.setVersion(templateDefine.getVersion());
    }

    private boolean canReadPrintScheme(String printSchemeKey) throws Exception {
        return this.authorityProvider.canReadPrintScheme(printSchemeKey);
    }
}

