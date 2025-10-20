/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplatePage
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.definition.api.IRunTimePrintController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.PageSize;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.nr.definition.internal.impl.DesignPrintSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimePrintTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimePrintTemplateSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IPrintRuntimeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimePrintSettingService;
import com.jiuqi.nr.definition.internal.service.ParamStreamService;
import com.jiuqi.nr.definition.internal.service.RunTimePrintComTemDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimePrintTemplateDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimePrintTemplateSchemeDefineService;
import com.jiuqi.nr.definition.internal.stream.param.PrintSchemeListStream;
import com.jiuqi.nr.definition.internal.stream.param.PrintSchemeStream;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.definition.util.ExportLabelUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplatePage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunTimePrintController
implements IRunTimePrintController,
IPrintRunTimeController {
    private static final Logger logger = LoggerFactory.getLogger(RunTimePrintController.class);
    @Autowired
    private RunTimePrintComTemDefineService printComTemDefineService;
    @Autowired
    private IPrintRuntimeService printService;
    @Autowired
    private RunTimePrintTemplateSchemeDefineService printSchemeService;
    @Autowired
    private RunTimePrintTemplateDefineService printTemplateService;
    @Autowired
    private ParamStreamService paramStreamService;
    @Autowired
    private IRuntimePrintSettingService runtimePrintSettingService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private static final String NO_POSITION = "-1-1-1";

    @Override
    public PrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String printSchemeKey) {
        PrintTemplateSchemeDefine schemeDefine = this.printService.queryPrintTemplateSchemeDefine(printSchemeKey);
        return this.toPrintTemplateSchemeDefine(schemeDefine);
    }

    @Override
    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByTask(String taskKey) {
        List<PrintTemplateSchemeDefine> schemeDefines = this.printService.getAllPrintSchemeByTask(taskKey);
        ArrayList<PrintTemplateSchemeDefine> resultDefines = new ArrayList<PrintTemplateSchemeDefine>();
        for (PrintTemplateSchemeDefine schemeDefine : schemeDefines) {
            resultDefines.add(this.toPrintTemplateSchemeDefine(schemeDefine));
        }
        return resultDefines;
    }

    @Override
    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey) {
        List<PrintTemplateSchemeDefine> schemeDefines = this.printService.getAllPrintSchemeByFormScheme(formSchemeKey);
        ArrayList<PrintTemplateSchemeDefine> resultDefines = new ArrayList<PrintTemplateSchemeDefine>();
        for (PrintTemplateSchemeDefine schemeDefine : schemeDefines) {
            resultDefines.add(this.toPrintTemplateSchemeDefine(schemeDefine));
        }
        return resultDefines;
    }

    @Override
    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByFormSchemeWithoutBinary(String formSchemeKey) {
        return this.printService.getAllPrintSchemeByFormScheme(formSchemeKey);
    }

    @Override
    public PrintTemplateDefine queryPrintTemplateDefine(String printTemplateKey) {
        return this.getPrintTemplate(printTemplateKey);
    }

    @Override
    public PrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String printSchemeKey, String formKey) {
        return this.getPrintTemplateBySchemeAndForm(printSchemeKey, formKey);
    }

    @Override
    public List<PrintTemplateDefine> getAllPrintTemplateInScheme(String printSchemeKey) {
        return this.listPrintTemplateByScheme(printSchemeKey);
    }

    @Override
    public PrintSchemeStream getPrintTemplateScheme(String key) {
        try {
            PrintTemplateSchemeDefine schemeDefine = this.printService.queryPrintTemplateSchemeDefine(key);
            PrintTemplateSchemeDefine resultDefine = this.toPrintTemplateSchemeDefine(schemeDefine);
            return this.paramStreamService.getPrintSchemeStream(resultDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public PrintSchemeListStream listPrintTemplateSchemeByFormScheme(String formScheme) {
        try {
            List<PrintTemplateSchemeDefine> schemeDefines = this.printService.getAllPrintSchemeByFormScheme(formScheme);
            ArrayList<PrintTemplateSchemeDefine> resultDefines = new ArrayList<PrintTemplateSchemeDefine>();
            for (PrintTemplateSchemeDefine schemeDefine : schemeDefines) {
                resultDefines.add(this.toPrintTemplateSchemeDefine(schemeDefine));
            }
            return this.paramStreamService.getPrintSchemeListStream(resultDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public PrintSchemeAttributeDefine getPrintSchemeAttribute(PrintTemplateSchemeDefine printScheme) {
        try {
            return this.printService.getPrintSchemeAttribute(printScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public PrintTemplateDefine getPrintTemplate(String key) {
        try {
            PrintTemplateDefine templateDefine = this.printService.queryPrintTemplateDefine(key);
            PrintTemplateDefine resultDefine = this.toPrintTemplateDefine(templateDefine);
            return resultDefine;
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY, (Throwable)e);
        }
    }

    @Override
    public PrintTemplateDefine getPrintTemplateBySchemeAndForm(String printScheme, String formKey) {
        try {
            PrintTemplateDefine templateDefine = this.printService.queryPrintTemplateDefineBySchemeAndForm(printScheme, formKey);
            PrintTemplateDefine resultDefine = this.toPrintTemplateDefine(templateDefine);
            return resultDefine;
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<PrintTemplateDefine> listPrintTemplateByScheme(String printScheme) {
        try {
            List<PrintTemplateDefine> templateDefines = this.printService.getAllPrintTemplateInScheme(printScheme);
            ArrayList<PrintTemplateDefine> resultDefines = new ArrayList<PrintTemplateDefine>();
            for (PrintTemplateDefine templateDefine : templateDefines) {
                if (templateDefine == null) continue;
                resultDefines.add(this.toPrintTemplateDefine(templateDefine));
            }
            return resultDefines;
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY, (Throwable)e);
        }
    }

    @Override
    public boolean existCoverTemplateDocument(String printSchemeKey) {
        PrintTemplateSchemeDefine scheme = (PrintTemplateSchemeDefine)this.getPrintTemplateScheme(printSchemeKey).get();
        return null != scheme && null != scheme.getGatherCoverData() && 0 < scheme.getGatherCoverData().length;
    }

    @Override
    public ITemplateDocument getCoverTemplateDocument(String printSchemeKey) {
        PrintTemplateSchemeDefine scheme = (PrintTemplateSchemeDefine)this.getPrintTemplateScheme(printSchemeKey).get();
        if (null != scheme.getGatherCoverData() && scheme.getGatherCoverData().length > 0) {
            return PrintElementUtils.toTemplateDocument(scheme.getGatherCoverData());
        }
        return PrintElementUtils.newTemplateDocument();
    }

    private ITemplateDocument getCommonTemplateDocument(String printSchemeKey) {
        PrintComTemDefine define = this.printComTemDefineService.getByKey(printSchemeKey);
        if (null != define && null != define.getTemplateData() && define.getTemplateData().length > 0) {
            return PrintElementUtils.toTemplateDocument(define.getTemplateData());
        }
        PrintTemplateSchemeDefine scheme = (PrintTemplateSchemeDefine)this.getPrintTemplateScheme(printSchemeKey).get();
        return PrintElementUtils.newTemplateDocument(this.getPrintSchemeAttribute(scheme));
    }

    private ITemplateDocument getCommonTemplateDocument(String printSchemeKey, String commonCode) {
        PrintComTemDefine define = this.printComTemDefineService.getBySchemeAndCode(printSchemeKey, commonCode);
        if (null != define && null != define.getTemplateData() && define.getTemplateData().length > 0) {
            return PrintElementUtils.toTemplateDocument(define.getTemplateData());
        }
        return null;
    }

    @Override
    public ITemplateDocument getTemplateDocumentBySchemeAndForm(String printSchemeKey, String formKey) {
        PrintTemplateDefine define = this.getPrintTemplateBySchemeAndForm(printSchemeKey, formKey);
        if (null != define) {
            return PrintElementUtils.toTemplateDocument(define, () -> {
                ITemplateDocument common = this.getCommonTemplateDocument(printSchemeKey, define.getComTemCode());
                if (null == common) {
                    common = this.getCommonTemplateDocument(printSchemeKey);
                }
                return common;
            }, () -> {
                BigDataDefine style = this.runTimeViewController.getFormStyle(define.getFormKey(), null);
                return null == style ? null : Grid2Data.bytesToGrid((byte[])style.getData());
            });
        }
        PrintTemplateSchemeDefine scheme = (PrintTemplateSchemeDefine)this.getPrintTemplateScheme(printSchemeKey).get();
        BigDataDefine style = this.runTimeViewController.getFormStyle(formKey, scheme.getFormSchemeKey());
        Grid2Data grid2Data = null == style ? null : Grid2Data.bytesToGrid((byte[])style.getData());
        return PrintElementUtils.toTemplateDocument(this.getCommonTemplateDocument(printSchemeKey), grid2Data);
    }

    @Override
    public List<WordLabelDefine> listRelativePositionLabel(String printSchemeKey, String formKey) {
        ITemplateDocument document = this.getTemplateDocumentBySchemeAndForm(printSchemeKey, formKey);
        if (null == document) {
            return Collections.emptyList();
        }
        ArrayList<ReportLabelTemplateObject> list = new ArrayList<ReportLabelTemplateObject>();
        for (ITemplatePage page : document.getPages()) {
            for (ITemplateElement element : page.getTemplateElements()) {
                if (!(element instanceof ReportLabelTemplateObject)) continue;
                list.add((ReportLabelTemplateObject)element);
            }
        }
        list.sort((t1, t2) -> Double.compare(ExportLabelUtils.getDoubleOrder(t1, t2), ExportLabelUtils.getDoubleOrder(t2, t1)));
        ArrayList<WordLabelDefine> labels = new ArrayList<WordLabelDefine>();
        for (ReportLabelTemplateObject element : list) {
            WordLabelDefine label = ExportLabelUtils.convertWorldLabel(element);
            if (NO_POSITION.equals(label.getLocationCode())) continue;
            labels.add(label);
        }
        return labels;
    }

    @Override
    public PrintTemplateAttributeDefine getPrintTemplateAttribute(PrintTemplateDefine printTemplate) {
        try {
            return this.printService.getPrintTemplateAttribute(printTemplate);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY, (Throwable)e);
        }
    }

    @Override
    public PrintSettingDefine getDefaultPrintSettingDefine(String printSchemeKey, String formKey) {
        DesignPrintSettingDefineImpl define = new DesignPrintSettingDefineImpl();
        define.setPrintSchemeKey(printSchemeKey);
        define.setFormKey(formKey);
        define.setPageSize(PageSize.A4_PAPER);
        define.setLandscape(false);
        define.setTopMargin(2.54);
        define.setBottomMargin(2.54);
        define.setLeftMargin(1.91);
        define.setRightMargin(1.91);
        define.setHorizontallyCenter(false);
        define.setVerticallyCenter(false);
        define.setLeftToRight(false);
        return define;
    }

    @Override
    public PrintSettingDefine getPrintSettingDefine(String printSchemeKey, String formKey) {
        return this.runtimePrintSettingService.query(printSchemeKey, formKey);
    }

    @Override
    public List<PrintSettingDefine> listPrintSettingDefine(String printSchemeKey) {
        return this.runtimePrintSettingService.list(printSchemeKey);
    }

    @Override
    public List<PrintComTemDefine> listPrintComTemByScheme(String printScheme) {
        return this.printComTemDefineService.listByScheme(printScheme);
    }

    private PrintTemplateSchemeDefine toPrintTemplateSchemeDefine(PrintTemplateSchemeDefine schemeDefine) {
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

    private void setPrintSchemeData(RunTimePrintTemplateSchemeDefineImpl resultDefine) {
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
        if (resultDefine.isAutoRefreshForm()) {
            ITemplateDocument document = PrintElementUtils.toTemplateDocument(resultDefine, () -> {
                ITemplateDocument common = this.getCommonTemplateDocument(resultDefine.getPrintSchemeKey(), resultDefine.getComTemCode());
                if (null == common) {
                    common = this.getCommonTemplateDocument(resultDefine.getPrintSchemeKey());
                }
                return common;
            }, () -> {
                BigDataDefine style = this.runTimeViewController.getFormStyle(resultDefine.getFormKey(), null);
                return null == style ? null : Grid2Data.bytesToGrid((byte[])style.getData());
            });
            resultDefine.setTemplateData(PrintElementUtils.toByteArray(document));
        }
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
        resultDefine.setAutoRefreshForm(templateDefine.isAutoRefreshForm());
        resultDefine.setFormUpdateTime(templateDefine.getFormUpdateTime());
        resultDefine.setComTemCode(templateDefine.getComTemCode());
    }
}

