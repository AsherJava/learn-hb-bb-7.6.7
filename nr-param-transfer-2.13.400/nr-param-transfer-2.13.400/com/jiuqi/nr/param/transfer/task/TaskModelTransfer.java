/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.param.transfer.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.FormulaGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.singlemap.SingleMappingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.TaskDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.TaskInfoDTO;
import com.jiuqi.nr.param.transfer.definition.service.SingleMappingService;
import com.jiuqi.nr.param.transfer.task.util.TaskUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.SingleConfigInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TaskModelTransfer
implements IModelTransfer {
    Logger logger = LoggerFactory.getLogger(TaskModelTransfer.class);
    @Autowired
    private DefinitionModelTransfer definitionModelTransfer;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private SingleMappingService singleMappingService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static String TASK = "TASK";
    private static String FORMSCHEME = "FORMSCHEME";
    private static String FORMGROUP = "FORMGROUP";
    private static String FORM = "FORM";
    private static String FORMULASCHEME = "FORMULASCHEME";
    private static String PRINTSCHEME = "PRINTSCHEME";
    private static String FORMULA = "FORMULA";
    private static String PRINTTEMPLATE = "PRINTTEMPLATE";
    private static String MAPPING = "MAPPING";

    public TaskModelTransfer() {
        DefinitionModelTransfer.moduleRegister(this.objectMapper);
    }

    @Transactional(rollbackFor={Exception.class})
    public void importModel(IImportContext context, byte[] bytes) throws TransferException {
        String targetGuid = context.getTargetGuid();
        TransferGuid parse = TransferGuidParse.parseId(targetGuid);
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    this.importTaskGroupBusiness(key, bytes);
                    break;
                }
                case TASK: {
                    this.importTaskBusiness(context, key, bytes);
                    break;
                }
                default: {
                    throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + (Object)((Object)transferNodeType));
                }
            }
        }
        catch (IOException e) {
            throw new TransferException("\u89e3\u6790\u5931\u8d25", (Throwable)e);
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    public MetaExportModel exportModel(IExportContext iExportContext, String s) throws TransferException {
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        MetaExportModel model = new MetaExportModel();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    model.setData(this.getTaskGroupBusiness(key));
                    break;
                }
                case TASK: {
                    model.setData(this.getTaskBusiness(iExportContext, key));
                    break;
                }
                default: {
                    throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + (Object)((Object)transferNodeType));
                }
            }
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u6253\u5305\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
        if (model.getData() == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + parse);
        }
        return model;
    }

    private byte[] getTaskBusiness(IExportContext context, String key) throws Exception {
        HashMap<String, List<byte[]>> taskBusinessObj = new HashMap<String, List<byte[]>>();
        DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(key);
        this.logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1 " + designTaskDefine.getTitle());
        byte[] taskBusiness = this.definitionModelTransfer.getTaskBusiness(context, designTaskDefine.getKey());
        this.setDataObj(taskBusinessObj, TASK, taskBusiness);
        List formSchemeDefines = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
        for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
            Object formGroupDefine2;
            this.logger.info("\u62a5\u8868\u65b9\u6848 " + formSchemeDefine.getTitle());
            byte[] formSchemeBusiness = this.definitionModelTransfer.getFormSchemeBusiness(context, formSchemeDefine.getKey());
            this.setDataObj(taskBusinessObj, FORMSCHEME, formSchemeBusiness);
            List formGroupDefines = this.designTimeViewController.queryRootGroupsByFormScheme(formSchemeDefine.getKey());
            for (Object formGroupDefine2 : formGroupDefines) {
                this.logger.info("\u62a5\u8868\u5206\u7ec4 " + formGroupDefine2.getTitle());
                byte[] formGroupBusiness = this.definitionModelTransfer.getFormGroupBusiness(context, formGroupDefine2.getKey());
                this.setDataObj(taskBusinessObj, FORMGROUP, formGroupBusiness);
            }
            List formDefines = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(formSchemeDefine.getKey());
            formGroupDefine2 = formDefines.iterator();
            while (formGroupDefine2.hasNext()) {
                DesignFormDefine formDefine = (DesignFormDefine)formGroupDefine2.next();
                this.logger.info("\u62a5\u8868 " + formDefine.getTitle());
                byte[] formBusiness = this.definitionModelTransfer.getFormBusiness(context, formDefine.getKey());
                this.setDataObj(taskBusinessObj, FORM, formBusiness);
            }
            List formulaSchemeDefines = this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
            for (DesignFormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
                this.logger.info("\u516c\u5f0f\u65b9\u6848 " + formulaSchemeDefine.getTitle());
                Object formulaSchemeBusiness = this.definitionModelTransfer.getFormulaSchemeBusiness(context, formulaSchemeDefine.getKey());
                this.setDataObj(taskBusinessObj, FORMULASCHEME, (byte[])formulaSchemeBusiness);
                for (DesignFormDefine formDefine : formDefines) {
                    byte[] formulaFormBusiness = this.definitionModelTransfer.getFormulaFormBusiness(context, formulaSchemeDefine.getKey() + FormulaGuidParse.INTER_TABLE_FORMULA + formDefine.getKey());
                    this.setDataObj(taskBusinessObj, FORMULA, formulaFormBusiness);
                }
                byte[] formulaFormBJBusiness = this.definitionModelTransfer.getFormulaFormBusiness(context, formulaSchemeDefine.getKey() + FormulaGuidParse.INTER_TABLE_FORMULA + FormulaGuidParse.INTER_TABLE_FORMULA_KEY);
                this.setDataObj(taskBusinessObj, FORMULA, formulaFormBJBusiness);
            }
            List printTemplateSchemeDefines = this.printDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeDefine.getKey());
            Set formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
            for (DesignPrintTemplateSchemeDefine printTemplateSchemeDefine : printTemplateSchemeDefines) {
                this.logger.info("\u6253\u5370\u65b9\u6848 " + printTemplateSchemeDefine.getTitle());
                byte[] printSchemeBusiness = this.definitionModelTransfer.getPrintSchemeBusiness(context, printTemplateSchemeDefine.getKey());
                this.setDataObj(taskBusinessObj, PRINTSCHEME, printSchemeBusiness);
                List printTemplateDefines = this.printDesignTimeController.getAllPrintTemplateInScheme(printTemplateSchemeDefine.getKey(), false);
                HashSet<String> existFormSet = new HashSet<String>();
                for (DesignPrintTemplateDefine printTemplateDefine : printTemplateDefines) {
                    String formKey = printTemplateDefine.getFormKey();
                    if (!formKeys.contains(formKey) || existFormSet.contains(formKey)) continue;
                    byte[] printTemplateBusiness = this.definitionModelTransfer.getPrintTemplateBusiness(context, printTemplateDefine.getKey());
                    this.setDataObj(taskBusinessObj, PRINTTEMPLATE, printTemplateBusiness);
                    existFormSet.add(formKey);
                }
            }
            List<SingleConfigInfo> mappingConfigInfos = this.singleMappingService.getMappingConfigInfoByFormScheme(formSchemeDefine.getKey());
            for (SingleConfigInfo mappingConfigInfo : mappingConfigInfos) {
                this.logger.info("\u6620\u5c04\u65b9\u6848 " + mappingConfigInfo.getConfigName());
                byte[] mappingDefineBusiness = this.definitionModelTransfer.getMappingDefineBusiness(mappingConfigInfo.getConfigKey());
                this.setDataObj(taskBusinessObj, MAPPING, mappingDefineBusiness);
            }
        }
        this.logger.info("\u4efb\u52a1\u5bfc\u51fa\u5b8c\u6210 " + designTaskDefine.getTitle());
        return TaskUtil.changeMapToByte(taskBusinessObj);
    }

    private void setDataObj(Map<String, List<byte[]>> taskBusinessObj, String type, byte[] data) {
        if (null == taskBusinessObj.get(type)) {
            ArrayList<byte[]> list = new ArrayList<byte[]>();
            list.add(data);
            taskBusinessObj.put(type, list);
        } else {
            taskBusinessObj.get(type).add(data);
        }
    }

    private byte[] getTaskGroupBusiness(String key) throws IOException, TransferException {
        return null;
    }

    public void importTaskBusiness(IImportContext contextc, String key, byte[] bytes) throws IOException, TransferException {
        List<byte[]> mappingBytes;
        List<byte[]> printTempBytes;
        Object pringschemeByte;
        List<byte[]> pringschemeBytes;
        Object formulaByte;
        List<byte[]> formulaBytes;
        Object formulaSchemeByte;
        Object formByte;
        Object formGroupByte;
        Object formschemeByte2;
        DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(key);
        Map<String, List<byte[]>> stringListMap = TaskUtil.changeByteToMap(bytes);
        if (null != designTaskDefine) {
            this.doDeleteBusiness(designTaskDefine, stringListMap);
        }
        this.logger.info("task\u6a21\u5757\u5f00\u59cb\u5bfc\u5165\u4efb\u52a1");
        List<byte[]> taskBytes = stringListMap.get(TASK);
        for (byte[] byArray : taskBytes) {
            TaskDTO taskDTO = TaskDTO.valueOf(byArray, this.objectMapper);
            TaskInfoDTO taskInfo = taskDTO.getTaskInfo();
            taskInfo.setUpdateTime(new Date());
            byte[] bytes1 = taskDTO.toBytes(this.objectMapper);
            this.taskMessage("\u4efb\u52a1TASK", taskInfo.getKey());
            this.definitionModelTransfer.importTaskBusiness(contextc, taskInfo.getKey(), bytes1);
        }
        List<byte[]> formschemeBytes = stringListMap.get(FORMSCHEME);
        for (Object formschemeByte2 : formschemeBytes) {
            FormSchemeDTO formSchemeDTO = FormSchemeDTO.valueOf((byte[])formschemeByte2, this.objectMapper);
            FormSchemeInfoDTO formSchemeInfo = formSchemeDTO.getFormSchemeInfo();
            this.taskMessage("\u62a5\u8868\u65b9\u6848FORMSCHEME", formSchemeInfo.getKey());
            this.definitionModelTransfer.importFormSchemeBusiness(contextc, formSchemeInfo.getKey(), (byte[])formschemeByte2);
        }
        List<byte[]> list = stringListMap.get(FORMGROUP);
        formschemeByte2 = list.iterator();
        while (formschemeByte2.hasNext()) {
            formGroupByte = (byte[])formschemeByte2.next();
            FormGroupDTO formGroupDTO = FormGroupDTO.valueOf((byte[])formGroupByte, this.objectMapper);
            FormGroupInfoDTO formGroupInfo = formGroupDTO.getFormGroupInfo();
            this.taskMessage("\u62a5\u8868\u5206\u7ec4FORMGROUP", formGroupInfo.getKey());
            this.definitionModelTransfer.importFormGroupBusiness(contextc, formGroupInfo.getKey(), (byte[])formGroupByte);
        }
        List<byte[]> formBytes = stringListMap.get(FORM);
        formGroupByte = formBytes.iterator();
        while (formGroupByte.hasNext()) {
            formByte = (byte[])formGroupByte.next();
            FormDTO formDTO = FormDTO.valueOf((byte[])formByte, this.objectMapper);
            FormInfoDTO formInfo = formDTO.getFormInfo();
            this.taskMessage("\u62a5\u8868FORM", formInfo.getKey());
            this.definitionModelTransfer.importFormBusiness(contextc, formInfo.getKey(), (byte[])formByte);
        }
        List<byte[]> formulaSchemeBytes = stringListMap.get(FORMULASCHEME);
        if (null != formulaSchemeBytes) {
            formByte = formulaSchemeBytes.iterator();
            while (formByte.hasNext()) {
                formulaSchemeByte = (byte[])formByte.next();
                FormulaSchemeDTO formulaSchemeDTO = FormulaSchemeDTO.valueOf((byte[])formulaSchemeByte, this.objectMapper);
                FormulaSchemeInfoDTO formulaSchemeInfo = formulaSchemeDTO.getFormulaSchemeInfo();
                this.taskMessage("\u516c\u5f0f\u65b9\u6848FORMULASCHEME", formulaSchemeInfo.getKey());
                this.definitionModelTransfer.importFormulaSchemeBusiness(contextc, formulaSchemeInfo.getKey(), (byte[])formulaSchemeByte);
            }
        }
        if (null != (formulaBytes = stringListMap.get(FORMULA))) {
            formulaSchemeByte = formulaBytes.iterator();
            while (formulaSchemeByte.hasNext()) {
                formulaByte = (byte[])formulaSchemeByte.next();
                FormulaSchemeDTO formulaSchemeDTO = FormulaSchemeDTO.valueOf(formulaByte, this.objectMapper);
                List<FormulaDTO> formulas = formulaSchemeDTO.getFormulas();
                if (null == formulas || formulas.size() == 0) continue;
                String formKey = formulas.get(0).getFormKey();
                String formulaSchemeKey = formulas.get(0).getFormulaSchemeKey();
                if (formKey == null) {
                    formKey = FormulaGuidParse.INTER_TABLE_FORMULA_KEY;
                }
                this.taskMessage("\u62a5\u8868\u516c\u5f0fFORMULA", formulaSchemeKey + FormulaGuidParse.INTER_TABLE_FORMULA + formKey);
                this.definitionModelTransfer.importFormulaFormBusiness(contextc, formulaSchemeKey + FormulaGuidParse.INTER_TABLE_FORMULA + formKey, (byte[])formulaByte);
            }
        }
        if (null != (pringschemeBytes = stringListMap.get(PRINTSCHEME))) {
            formulaByte = pringschemeBytes.iterator();
            while (formulaByte.hasNext()) {
                pringschemeByte = (byte[])formulaByte.next();
                PrintTemplateSchemeDTO printTemplateSchemeDTO = PrintTemplateSchemeDTO.valueOf((byte[])pringschemeByte, this.objectMapper);
                PrintTemplateSchemeInfoDTO printTemplateSchemeInfo = printTemplateSchemeDTO.getPrintTemplateSchemeInfo();
                this.taskMessage("\u6253\u5370\u65b9\u6848PRINTSCHEME", printTemplateSchemeInfo.getKey());
                this.definitionModelTransfer.importPrintSchemeBusiness(contextc, printTemplateSchemeInfo.getKey(), (byte[])pringschemeByte);
            }
        }
        if (null != (printTempBytes = stringListMap.get(PRINTTEMPLATE))) {
            pringschemeByte = printTempBytes.iterator();
            while (pringschemeByte.hasNext()) {
                byte[] printTempByte = (byte[])pringschemeByte.next();
                PrintTemplateDTO printTemplateDTO = PrintTemplateDTO.valueOf(printTempByte, this.objectMapper);
                this.taskMessage("\u6253\u5370\u6a21\u677fPRINTTEMPLATE", printTemplateDTO.getKey());
                this.definitionModelTransfer.importPrintTemplateBusiness(contextc, printTemplateDTO.getKey(), printTempByte);
            }
        }
        if (null != (mappingBytes = stringListMap.get(MAPPING))) {
            for (byte[] mappingByte : mappingBytes) {
                SingleMappingDTO singleMappingDTO = SingleMappingDTO.valueOf(mappingByte, this.objectMapper);
                if (singleMappingDTO == null) continue;
                this.taskMessage("\u6620\u5c04MAPPING", singleMappingDTO.getConfigInfo().getConfigKey());
                this.definitionModelTransfer.importMappingDefineBusiness(contextc, singleMappingDTO.getConfigInfo().getConfigKey(), mappingByte);
            }
        }
        this.logger.info("\u4efb\u52a1\u5bfc\u5165\u5b8c\u6210");
    }

    private void doDeleteBusiness(DesignTaskDefine designTaskDefine, Map<String, List<byte[]>> stringListMap) throws TransferException {
        try {
            List formSchemeDefines = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
            List<byte[]> schemeBytes = stringListMap.get(FORMSCHEME);
            ArrayList<String> importFormSchemeKeys = new ArrayList<String>();
            ArrayList<String> deleteFormSchemeKeys = new ArrayList<String>();
            for (byte[] schemeByte : schemeBytes) {
                FormSchemeDTO formSchemeDTO = FormSchemeDTO.valueOf(schemeByte, this.objectMapper);
                FormSchemeInfoDTO formSchemeInfo = formSchemeDTO.getFormSchemeInfo();
                importFormSchemeKeys.add(formSchemeInfo.getKey());
            }
            List<byte[]> formGroupBytes = stringListMap.get(FORMGROUP);
            ArrayList<String> importFormGroupKeys = new ArrayList<String>();
            ArrayList<String> deleteFormGroupKeys = new ArrayList<String>();
            if (null != formGroupBytes) {
                for (byte[] formGroupByte : formGroupBytes) {
                    FormGroupDTO formGroupDTO = FormGroupDTO.valueOf(formGroupByte, this.objectMapper);
                    FormGroupInfoDTO formGroupInfo = formGroupDTO.getFormGroupInfo();
                    importFormGroupKeys.add(formGroupInfo.getKey());
                }
            }
            List<byte[]> formBytes = stringListMap.get(FORM);
            ArrayList<String> importFormKeys = new ArrayList<String>();
            ArrayList<String> deleteFormKeys = new ArrayList<String>();
            if (null != formBytes) {
                for (byte[] formByte : formBytes) {
                    FormDTO formDTO = FormDTO.valueOf(formByte, this.objectMapper);
                    FormInfoDTO formInfo = formDTO.getFormInfo();
                    importFormKeys.add(formInfo.getKey());
                }
            }
            List<byte[]> formulaSchemeBytes = stringListMap.get(FORMULASCHEME);
            ArrayList<String> importFormulaSchemeKeys = new ArrayList<String>();
            ArrayList<String> deleteFormulaSchemeKeys = new ArrayList<String>();
            if (null != formulaSchemeBytes) {
                for (byte[] formulaSchemeByte : formulaSchemeBytes) {
                    FormulaSchemeDTO formulaSchemeDTO = FormulaSchemeDTO.valueOf(formulaSchemeByte, this.objectMapper);
                    FormulaSchemeInfoDTO formulaSchemeInfo = formulaSchemeDTO.getFormulaSchemeInfo();
                    importFormulaSchemeKeys.add(formulaSchemeInfo.getKey());
                }
            }
            List<byte[]> printSchemeBytes = stringListMap.get(PRINTSCHEME);
            ArrayList<String> importPrintSchemeKeys = new ArrayList<String>();
            ArrayList<String> deletePrintSchemeKeys = new ArrayList<String>();
            if (null != printSchemeBytes) {
                for (byte[] printSchemeByte : printSchemeBytes) {
                    PrintTemplateSchemeDTO printTemplateDTO = PrintTemplateSchemeDTO.valueOf(printSchemeByte, this.objectMapper);
                    PrintTemplateSchemeInfoDTO printTemplateSchemeInfo = printTemplateDTO.getPrintTemplateSchemeInfo();
                    importPrintSchemeKeys.add(printTemplateSchemeInfo.getKey());
                }
            }
            List<byte[]> printTemplateBytes = stringListMap.get(PRINTTEMPLATE);
            ArrayList<String> importPrintTemplateKeys = new ArrayList<String>();
            ArrayList<String> deletePrintTemplateKeys = new ArrayList<String>();
            if (null != printTemplateBytes) {
                for (byte[] printTemplateByte : printTemplateBytes) {
                    PrintTemplateDTO printTemplateDTO = PrintTemplateDTO.valueOf(printTemplateByte, this.objectMapper);
                    importPrintTemplateKeys.add(printTemplateDTO.getKey());
                }
            }
            for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                Object formGroupDefine2;
                if (!importFormSchemeKeys.contains(formSchemeDefine.getKey())) {
                    deleteFormSchemeKeys.add(formSchemeDefine.getKey());
                    continue;
                }
                List formGroupDefines = this.designTimeViewController.queryRootGroupsByFormScheme(formSchemeDefine.getKey());
                for (Object formGroupDefine2 : formGroupDefines) {
                    if (importFormGroupKeys.contains(formGroupDefine2.getKey())) continue;
                    deleteFormGroupKeys.add(formGroupDefine2.getKey());
                }
                List formDefines = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(formSchemeDefine.getKey());
                formGroupDefine2 = formDefines.iterator();
                while (formGroupDefine2.hasNext()) {
                    DesignFormDefine formDefine = (DesignFormDefine)formGroupDefine2.next();
                    if (importFormKeys.contains(formDefine.getKey())) continue;
                    deleteFormKeys.add(formDefine.getKey());
                }
                List formulaSchemeDefines = this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
                for (DesignFormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
                    if (importFormulaSchemeKeys.contains(formulaSchemeDefine.getKey())) continue;
                    deleteFormulaSchemeKeys.add(formulaSchemeDefine.getKey());
                }
                List printTemplateSchemeDefines = this.printDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeDefine.getKey());
                for (DesignPrintTemplateSchemeDefine printTemplateSchemeDefine : printTemplateSchemeDefines) {
                    if (!importPrintSchemeKeys.contains(printTemplateSchemeDefine.getKey())) {
                        deletePrintSchemeKeys.add(printTemplateSchemeDefine.getKey());
                    }
                    List printTemplateDefines = this.printDesignTimeController.getAllPrintTemplateInScheme(printTemplateSchemeDefine.getKey());
                    for (DesignPrintTemplateDefine printTemplateDefine : printTemplateDefines) {
                        if (importPrintTemplateKeys.contains(printTemplateDefine.getKey())) continue;
                        deletePrintTemplateKeys.add(printTemplateDefine.getKey());
                    }
                }
            }
            for (String deleteKey : deleteFormSchemeKeys) {
                this.logger.info("\u6267\u884c\u65b9\u6848\u6e05\u9664" + deleteKey);
                this.designTimeViewController.deleteFormSchemeDefine(deleteKey);
            }
            for (String deleteFormGroupKey : deleteFormGroupKeys) {
                this.logger.info("\u6267\u884c\u62a5\u8868\u5206\u7ec4\u6e05\u9664" + deleteFormGroupKey);
                this.designTimeViewController.deleteFormGroup(deleteFormGroupKey);
            }
            for (String deleteFormKey : deleteFormKeys) {
                this.logger.info("\u6267\u884c\u62a5\u8868\u6e05\u9664" + deleteFormKey);
                this.designTimeViewController.deleteFormDefine(deleteFormKey);
            }
            for (String deleteFormulaSchemeKey : deleteFormulaSchemeKeys) {
                this.logger.info("\u6267\u884c\u516c\u5f0f\u65b9\u6848\u6e05\u9664" + deleteFormulaSchemeKey);
                this.formulaDesignTimeController.deleteFormulaSchemeDefine(deleteFormulaSchemeKey);
            }
            for (String deletePrintSchemeKey : deletePrintSchemeKeys) {
                this.logger.info("\u6267\u884c\u6253\u5370\u65b9\u6848\u6e05\u9664" + deletePrintSchemeKey);
                this.printDesignTimeController.deletePrintTemplateSchemeDefine(deletePrintSchemeKey);
            }
            for (String deletePrintTemplateKey : deletePrintTemplateKeys) {
                this.logger.info("\u6267\u884c\u6253\u5370\u6a21\u677f\u6e05\u9664" + deletePrintTemplateKey);
                this.printDesignTimeController.deletePrintTemplateDefine(deletePrintTemplateKey);
            }
        }
        catch (Exception e) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    private void importTaskGroupBusiness(String key, byte[] bytes) throws IOException, TransferException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\u53c2\u6570\u5bfc\u5165\u5355\u673a\u7248\u4efb\u52a1\u7684\u4efb\u52a1\u5206\u7ec4\u5c5e\u6027\uff0c\u5176\u503c\u662f\u7a7a\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\uff01");
        }
    }

    private void taskMessage(String resourceTypeName, String key) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("task\u6a21\u5757\u5f00\u59cb\u5bfc\u5165%s \uff0ckey\u662f\uff1a%s \uff01", resourceTypeName, key));
        }
    }
}

