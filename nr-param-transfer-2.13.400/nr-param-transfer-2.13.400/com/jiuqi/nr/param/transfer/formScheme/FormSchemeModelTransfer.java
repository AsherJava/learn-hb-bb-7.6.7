/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonFactoryBuilder
 *  com.fasterxml.jackson.core.StreamReadConstraints
 *  com.fasterxml.jackson.core.StreamReadConstraints$Builder
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.PrintSettingDefine
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.service.IMCParamService
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.param.transfer.formScheme;

import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.service.IMCParamService;
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
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.MultCheckParamDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintSettingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.singlemap.SingleMappingDTO;
import com.jiuqi.nr.param.transfer.definition.service.SingleMappingService;
import com.jiuqi.nr.param.transfer.formScheme.dto.FormSchemeFullDTO;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FormSchemeModelTransfer
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeModelTransfer.class);
    @Autowired
    private DefinitionModelTransfer definitionModelTransfer;
    @Autowired
    private com.jiuqi.nr.definition.controller.IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimeViewController apiDesignTimeViewController;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private SingleMappingService singleMappingService;
    @Autowired
    private IParamLevelManager paramLevelManager;
    @Autowired(required=false)
    private IMCParamService iMCParamService;
    private final ObjectMapper objectMapper;
    private static final String TASK = "TASK";
    private static final String FORM_SCHEME = "FORM_SCHEME";
    private static final String FORM_GROUP = "FORM_GROUP";
    private static final String FORM = "FORM";
    private static final String FORMULA_SCHEME = "FORMULA_SCHEME";
    private static final String FORMULA = "FORMULA";
    private static final String PRINT_SCHEME = "PRINT_SCHEME";
    private static final String PRINT_TEMPLATE = "PRINT_TEMPLATE";
    private static final String PRINT_SETTING = "PRINT_SETTING";
    private static final String MAPPING = "MAPPING";
    private static final int NOT_LEVEL = 0;
    private static final int ONE_LEVEL = 1;
    private static final int SECOND_LEVEL = 2;
    private static final String S_NOT_LEVEL = "0";
    private static final String S_ONE_LEVEL = "1";
    private static final String S_SECOND_LEVEL = "2";

    public FormSchemeModelTransfer() {
        logger.info("FormSchemeModelTransfer\u521d\u59cb\u5316objectMapper");
        JsonFactoryBuilder b = new JsonFactoryBuilder();
        StreamReadConstraints.Builder builder = StreamReadConstraints.builder();
        builder.maxStringLength(Integer.MAX_VALUE);
        StreamReadConstraints streamReadConstraints = builder.build();
        b.streamReadConstraints(streamReadConstraints);
        this.objectMapper = new ObjectMapper(b.build());
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
                    this.importTaskGroupBusiness(context, key, bytes);
                    break;
                }
                case TASK: {
                    this.importTaskBusiness(context, key, bytes);
                    break;
                }
                case FORM_SCHEME: {
                    this.importFormSchemeBusiness(context, key, bytes);
                    break;
                }
                case CUSTOM_DATA: {
                    this.importCustomDataBusiness(context, key, bytes);
                    break;
                }
                default: {
                    throw new TransferException("\u4e0d\u5b58\u5728\u7684\u8d44\u6e90\u7c7b\u578b\uff1a" + (Object)((Object)transferNodeType));
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
                    model.setData(this.getTaskGroupBusiness(iExportContext, key));
                    break;
                }
                case TASK: {
                    model.setData(this.getTaskBusiness(iExportContext, key));
                    break;
                }
                case FORM_SCHEME: {
                    model.setData(this.getFormSchemeBusiness(iExportContext, key));
                    break;
                }
                case CUSTOM_DATA: {
                    model.setData(this.getCustomDataBusiness(key));
                    break;
                }
                default: {
                    throw new TransferException("\u4e0d\u5b58\u5728\u7684\u8d44\u6e90\u7c7b\u578b\uff1a" + (Object)((Object)transferNodeType));
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

    private byte[] getTaskGroupBusiness(IExportContext context, String key) throws IOException, TransferException {
        return this.definitionModelTransfer.getTaskGroupBusiness(context, key);
    }

    private byte[] getTaskBusiness(IExportContext context, String key) throws Exception {
        DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(key);
        if (designTaskDefine == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1\uff1a{} ", (Object)designTaskDefine.getTitle());
        byte[] taskBusiness = this.definitionModelTransfer.getTaskBusiness(context, designTaskDefine.getKey());
        logger.info("\u4efb\u52a1\uff1a{}\u5bfc\u51fa\u5b8c\u6210 ", (Object)designTaskDefine.getTitle());
        return taskBusiness;
    }

    private byte[] getFormSchemeBusiness(IExportContext context, String key) throws Exception {
        HashMap<String, List<Object>> formSchemeBusinessObjs = new HashMap<String, List<Object>>();
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(key);
        if (formSchemeDefine == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        logger.info("\u5f00\u59cb\u5bfc\u51fa\u62a5\u8868\u65b9\u6848\uff1a{} ", (Object)formSchemeDefine.getTitle());
        FormSchemeDTO formSchemeBusinessDTO = this.definitionModelTransfer.getFormSchemeBusinessDTO(context, formSchemeDefine.getKey());
        String formSchemeString = this.objectMapper.writeValueAsString((Object)formSchemeBusinessDTO);
        this.setDataObjs(formSchemeBusinessObjs, FORM_SCHEME, formSchemeString);
        List formGroupDefines = this.designTimeViewController.queryRootGroupsByFormScheme(formSchemeDefine.getKey());
        for (Object formGroupDefine : formGroupDefines) {
            logger.info("\u62a5\u8868\u5206\u7ec4\uff1a{} ", (Object)formGroupDefine.getTitle());
            FormGroupDTO formGroupBusinessDTO = this.definitionModelTransfer.getFormGroupBusinessDTO(context, formGroupDefine.getKey());
            String formGroupString = this.objectMapper.writeValueAsString((Object)formGroupBusinessDTO);
            this.setDataObjs(formSchemeBusinessObjs, FORM_GROUP, formGroupString);
        }
        List formDefines = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(formSchemeDefine.getKey());
        for (Object formDefine : formDefines) {
            logger.info("\u62a5\u8868\uff1a{} ", (Object)formDefine.getTitle());
            FormDTO formDTO = this.definitionModelTransfer.getFormBusinessDTO(context, formDefine.getKey());
            String formString = this.objectMapper.writeValueAsString((Object)formDTO);
            this.setDataObjs(formSchemeBusinessObjs, FORM, formString);
        }
        List formulaSchemeDefines = this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
        for (DesignFormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            logger.info("\u516c\u5f0f\u65b9\u6848\uff1a{} ", (Object)formulaSchemeDefine.getTitle());
            FormulaSchemeDTO formulaSchemeBusinessDTO = this.definitionModelTransfer.getFormulaSchemeBusinessDTO(context, formulaSchemeDefine.getKey());
            String formulaSchemeString = this.objectMapper.writeValueAsString((Object)formulaSchemeBusinessDTO);
            this.setDataObjs(formSchemeBusinessObjs, FORMULA_SCHEME, formulaSchemeString);
            for (DesignFormDefine formDefine : formDefines) {
                FormulaSchemeDTO formulaFormBusinessDTO = this.definitionModelTransfer.getFormulaFormBusinessDTO(context, formulaSchemeDefine.getKey() + FormulaGuidParse.INTER_TABLE_FORMULA + formDefine.getKey());
                String formulaFormString = this.objectMapper.writeValueAsString((Object)formulaFormBusinessDTO);
                this.setDataObjs(formSchemeBusinessObjs, FORMULA, formulaFormString);
            }
            FormulaSchemeDTO formulaFormBusinessDTO = this.definitionModelTransfer.getFormulaFormBusinessDTO(context, formulaSchemeDefine.getKey() + FormulaGuidParse.INTER_TABLE_FORMULA + FormulaGuidParse.INTER_TABLE_FORMULA_KEY);
            String formulaSchemeString000 = this.objectMapper.writeValueAsString((Object)formulaFormBusinessDTO);
            this.setDataObjs(formSchemeBusinessObjs, FORMULA, formulaSchemeString000);
        }
        Set formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        List printTemplateSchemeDefines = this.printDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeDefine.getKey());
        for (DesignPrintTemplateSchemeDefine printTemplateSchemeDefine : printTemplateSchemeDefines) {
            logger.info("\u6253\u5370\u65b9\u6848\uff1a{} ", (Object)printTemplateSchemeDefine.getTitle());
            PrintTemplateSchemeDTO printSchemeBusinessDTO = this.definitionModelTransfer.getPrintSchemeBusinessDTO(context, printTemplateSchemeDefine.getKey());
            String printTemplateSchemeString = this.objectMapper.writeValueAsString((Object)printSchemeBusinessDTO);
            this.setDataObjs(formSchemeBusinessObjs, PRINT_SCHEME, printTemplateSchemeString);
            List printTemplateDefines = this.printDesignTimeController.getAllPrintTemplateInScheme(printTemplateSchemeDefine.getKey(), false);
            HashSet<String> existFormSet = new HashSet<String>();
            for (DesignPrintTemplateDefine printTemplateDefine : printTemplateDefines) {
                String formKey = printTemplateDefine.getFormKey();
                if (!formKeys.contains(formKey) || existFormSet.contains(formKey)) continue;
                PrintTemplateDTO printTemplateBusinessDTO = this.definitionModelTransfer.getPrintTemplateBusinessDTO(context, printTemplateDefine.getKey());
                String printTemplateString = this.objectMapper.writeValueAsString((Object)printTemplateBusinessDTO);
                this.setDataObjs(formSchemeBusinessObjs, PRINT_TEMPLATE, printTemplateString);
                existFormSet.add(formKey);
            }
            List designPrintSettingDefines = this.designTimePrintController.listPrintSettingDefine(printTemplateSchemeDefine.getKey());
            HashSet<String> existFormSet1 = new HashSet<String>();
            if (!CollectionUtils.isEmpty(designPrintSettingDefines)) continue;
            for (DesignPrintSettingDefine designPrintSettingDefine : designPrintSettingDefines) {
                String formKey = designPrintSettingDefine.getFormKey();
                if (!formKeys.contains(formKey) || existFormSet1.contains(formKey)) continue;
                PrintSettingDTO printSettingDTO = PrintSettingDTO.valueOf((PrintSettingDefine)designPrintSettingDefine);
                String printSettingString = this.objectMapper.writeValueAsString((Object)printSettingDTO);
                this.setDataObjs(formSchemeBusinessObjs, PRINT_SETTING, printSettingString);
                existFormSet1.add(formKey);
            }
        }
        List<SingleConfigInfo> mappingConfigInfos = this.singleMappingService.getMappingConfigInfoByFormScheme(formSchemeDefine.getKey());
        for (SingleConfigInfo mappingConfigInfo : mappingConfigInfos) {
            logger.info("\u6620\u5c04\u65b9\u6848\uff1a{} ", (Object)mappingConfigInfo.getConfigName());
            SingleMappingDTO mappingDefineBusinessDTO = this.definitionModelTransfer.getMappingDefineBusinessDTO(mappingConfigInfo.getConfigKey());
            String singleMappingString = this.objectMapper.writeValueAsString((Object)mappingDefineBusinessDTO);
            this.setDataObjs(formSchemeBusinessObjs, MAPPING, singleMappingString);
        }
        logger.info("\u62a5\u8868\u65b9\u6848\uff1a{} \u5bfc\u5165\u5b8c\u6210 ", (Object)formSchemeDefine.getTitle());
        return this.objectMapper.writeValueAsBytes(formSchemeBusinessObjs);
    }

    private byte[] getCustomDataBusiness(String key) throws Exception {
        return this.definitionModelTransfer.getCustomData(key);
    }

    private void setDataObjs(Map<String, List<Object>> taskBusinessObj, String type, Object data) {
        if (null == taskBusinessObj.get(type)) {
            ArrayList<Object> list = new ArrayList<Object>();
            list.add(data);
            taskBusinessObj.put(type, list);
        } else {
            taskBusinessObj.get(type).add(data);
        }
    }

    private void importTaskGroupBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        this.definitionModelTransfer.importTaskGroupBusiness(context, key, bytes);
        if (logger.isDebugEnabled()) {
            logger.debug("\u53c2\u6570\u5bfc\u5165\u5168\u91cf\u4efb\u52a1\u7684\u4efb\u52a1\u5206\u7ec4\u5bfc\u5165\u5b8c\u6210\uff01");
        }
    }

    public void importTaskBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        this.definitionModelTransfer.importTaskBusiness(context, key, bytes);
        if (logger.isDebugEnabled()) {
            logger.debug("\u53c2\u6570\u5bfc\u5165\u5168\u91cf\u4efb\u52a1\u5bfc\u5165\u5b8c\u6210\uff01");
        }
    }

    private void importFormSchemeBusiness(IImportContext context, String formSchemeKey, byte[] bytes) throws IOException, TransferException {
        int srcPacketLevel = context.getSrcPacketLevel();
        int thisPacketLeve = this.paramLevelManager.getLevel().getValue();
        if (srcPacketLevel != 0 && thisPacketLeve != 0 && srcPacketLevel > thisPacketLeve) {
            logger.error("\u5168\u91cf\u5bfc\u5165\u62a5\u8868\u65b9\u6848\u6765\u6e90\u670d\u52a1\u7ea7\u6b21\u5fc5\u987b\u9ad8\u4e8e\u5f53\u524d\u670d\u52a1\uff01\u6765\u6e90\u670d\u52a1\u7ea7\u6b21\u662f{}\uff0c\u5f53\u524d\u670d\u52a1\u7ea7\u6b21\u662f{}", (Object)srcPacketLevel, (Object)thisPacketLeve);
            throw new TransferException("\u5168\u91cf\u5bfc\u5165\u62a5\u8868\u65b9\u6848\u6765\u6e90\u670d\u52a1\u7ea7\u6b21\u5fc5\u987b\u9ad8\u4e8e\u5f53\u524d\u670d\u52a1\uff01");
        }
        Map resourceListMap = (Map)this.objectMapper.readValue(bytes, (TypeReference)new TypeReference<Map<String, List<Object>>>(){});
        FormSchemeFullDTO formSchemeFullDTO = this.analysis(resourceListMap);
        this.handleImportFormScheme(context, formSchemeKey, formSchemeFullDTO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void handleImportFormScheme(IImportContext context, String formSchemeKey, FormSchemeFullDTO formSchemeFullDTO) throws TransferException {
        int srcPacketLevel = context.getSrcPacketLevel();
        this.doDeleteBusiness(srcPacketLevel, formSchemeKey, formSchemeFullDTO);
        this.doImportFormScheme(formSchemeFullDTO, context);
    }

    /*
     * WARNING - void declaration
     */
    private void doImportFormScheme(FormSchemeFullDTO formSchemeFullDTO, IImportContext context) throws TransferException {
        List<SingleMappingDTO> list;
        List<PrintSettingDTO> list2;
        List<PrintTemplateDTO> list3;
        List<PrintTemplateSchemeDTO> list4;
        List<FormulaSchemeDTO> list5;
        logger.info("\u5f00\u59cb\u62a5\u8868\u65b9\u6848\u7684\u5168\u91cf\u5bfc\u5165");
        List<FormSchemeDTO> allFormSchemeDTOs = formSchemeFullDTO.getAllFormSchemeDTOs();
        for (FormSchemeDTO formSchemeDTO : allFormSchemeDTOs) {
            String string = formSchemeDTO.getFormSchemeInfo().getKey();
            this.debugeMessage("\u62a5\u8868\u65b9\u6848FORM_SCHEME", string);
            this.definitionModelTransfer.importFormSchemeBusinessObj(context, string, formSchemeDTO, false);
        }
        List<FormGroupDTO> allFormGroupDTOs = formSchemeFullDTO.getAllFormGroupDTOs();
        for (FormGroupDTO formGroupDTO : allFormGroupDTOs) {
            String string = formGroupDTO.getFormGroupInfo().getKey();
            this.debugeMessage("\u62a5\u8868\u5206\u7ec4FORM_GROUP", string);
            this.definitionModelTransfer.importFormGroupBusinessObj(context, string, formGroupDTO, false);
        }
        List<FormDTO> list6 = formSchemeFullDTO.getAllFormDTOs();
        for (FormDTO formDTO : list6) {
            String string = formDTO.getFormInfo().getKey();
            this.debugeMessage("\u62a5\u8868FORM", string);
            this.definitionModelTransfer.importFormBusinessObj(context, string, formDTO, false);
        }
        List<FormulaSchemeDTO> list7 = formSchemeFullDTO.getAllFormulaSchemeDTOs();
        if (null != list7) {
            for (FormulaSchemeDTO formulaSchemeDTO : list7) {
                String string = formulaSchemeDTO.getFormulaSchemeInfo().getKey();
                this.debugeMessage("\u516c\u5f0f\u65b9\u6848FORMULA_SCHEME", string);
                this.definitionModelTransfer.importFormulaSchemeBusinessObj(context, string, formulaSchemeDTO, false);
            }
        }
        if (null != (list5 = formSchemeFullDTO.getAllFormFormulaDTOs())) {
            for (FormulaSchemeDTO formulaSchemeDTO : list5) {
                void var11_37;
                List<FormulaDTO> list8 = formulaSchemeDTO.getFormulas();
                if (null == list8 || list8.isEmpty()) continue;
                String string = list8.get(0).getFormKey();
                String formulaSchemeKey = list8.get(0).getFormulaSchemeKey();
                if (string == null) {
                    String string2 = FormulaGuidParse.INTER_TABLE_FORMULA_KEY;
                }
                this.debugeMessage("\u62a5\u8868\u516c\u5f0fFORMULA", formulaSchemeKey + FormulaGuidParse.INTER_TABLE_FORMULA + (String)var11_37);
                this.definitionModelTransfer.importFormulaFormBusinessObj(context, formulaSchemeKey + FormulaGuidParse.INTER_TABLE_FORMULA + (String)var11_37, formulaSchemeDTO, false);
            }
        }
        if (null != (list4 = formSchemeFullDTO.getAllPrintTemplateSchemeDTOs())) {
            for (PrintTemplateSchemeDTO printTemplateSchemeDTO : list4) {
                String string = printTemplateSchemeDTO.getPrintTemplateSchemeInfo().getKey();
                this.debugeMessage("\u6253\u5370\u65b9\u6848PRINT_SCHEME", string);
                this.definitionModelTransfer.importPrintSchemeBusinessObj(context, string, printTemplateSchemeDTO, false);
            }
        }
        if (null != (list3 = formSchemeFullDTO.getAllPrintTemplateDTOs())) {
            for (PrintTemplateDTO printTemplateDTO : list3) {
                this.debugeMessage("\u6253\u5370\u6a21\u677fPRINT_TEMPLATE", printTemplateDTO.getKey());
                this.definitionModelTransfer.importPrintTemplateBusinessObj(context, printTemplateDTO.getKey(), printTemplateDTO, false);
            }
        }
        if (null != (list2 = formSchemeFullDTO.getAllPrintSettingDTOs())) {
            for (PrintSettingDTO printSettingDTO : list2) {
                this.debugeMessage("\u6253\u5370\u8bbe\u7f6ePRINT_TEMPLATE", printSettingDTO.getFormKey());
                String key = printSettingDTO.getPrintSchemeKey() + FormulaGuidParse.INTER_TABLE_FORMULA + printSettingDTO.getFormKey();
                this.definitionModelTransfer.importPrintSettingBusinessObj(context, key, printSettingDTO, false);
            }
        }
        if (null != (list = formSchemeFullDTO.getAllSingleMappingDTOs())) {
            for (SingleMappingDTO singleMappingDTO : list) {
                if (singleMappingDTO == null) continue;
                this.debugeMessage("\u6620\u5c04MAPPING", singleMappingDTO.getConfigInfo().getConfigKey());
                this.definitionModelTransfer.importMappingDefineBusinessObj(context, singleMappingDTO.getConfigInfo().getConfigKey(), singleMappingDTO);
            }
        }
        logger.info("\u62a5\u8868\u65b9\u6848\u5168\u91cf\u5bfc\u5165\u5b8c\u6210");
    }

    private FormSchemeFullDTO analysis(Map<String, List<Object>> stringListMap) throws TransferException {
        FormSchemeFullDTO formSchemeFullDTO = new FormSchemeFullDTO();
        try {
            List<Object> printSchemeStringObjs;
            List<Object> formulaStringObjs;
            List<Object> formulaSchemeStringObjs;
            List<Object> formStringObjs;
            List<Object> schemeStringObjs = stringListMap.get(FORM_SCHEME);
            ArrayList<FormSchemeDTO> formSchemeDTOList = new ArrayList<FormSchemeDTO>();
            for (Object schemeStringObj : schemeStringObjs) {
                String formSchemeString = schemeStringObj.toString();
                FormSchemeDTO formSchemeDTO = (FormSchemeDTO)this.objectMapper.readValue(formSchemeString, FormSchemeDTO.class);
                formSchemeDTOList.add(formSchemeDTO);
            }
            formSchemeFullDTO.setAllFormSchemeDTOs(formSchemeDTOList);
            List<Object> formGroupStringObjs = stringListMap.get(FORM_GROUP);
            if (null != formGroupStringObjs) {
                ArrayList<FormGroupDTO> allFormGroupDtos = new ArrayList<FormGroupDTO>();
                for (Object formGroupStringObj : formGroupStringObjs) {
                    String formGroupString = formGroupStringObj.toString();
                    FormGroupDTO formGroupDTO = (FormGroupDTO)this.objectMapper.readValue(formGroupString, FormGroupDTO.class);
                    allFormGroupDtos.add(formGroupDTO);
                }
                formSchemeFullDTO.setAllFormGroupDTOs(allFormGroupDtos);
            }
            if (null != (formStringObjs = stringListMap.get(FORM))) {
                ArrayList<FormDTO> allFormDTOs = new ArrayList<FormDTO>();
                for (Object formStringObj : formStringObjs) {
                    String formString = formStringObj.toString();
                    FormDTO formDTO = (FormDTO)this.objectMapper.readValue(formString, FormDTO.class);
                    allFormDTOs.add(formDTO);
                }
                formSchemeFullDTO.setAllFormDTOs(allFormDTOs);
            }
            if (null != (formulaSchemeStringObjs = stringListMap.get(FORMULA_SCHEME))) {
                ArrayList<FormulaSchemeDTO> allFormulaSchemeDtos = new ArrayList<FormulaSchemeDTO>();
                for (Object formulaSchemeStringObj : formulaSchemeStringObjs) {
                    String formulaSchemeString = formulaSchemeStringObj.toString();
                    FormulaSchemeDTO formulaSchemeDTO = (FormulaSchemeDTO)this.objectMapper.readValue(formulaSchemeString, FormulaSchemeDTO.class);
                    allFormulaSchemeDtos.add(formulaSchemeDTO);
                }
                formSchemeFullDTO.setAllFormulaSchemeDTOs(allFormulaSchemeDtos);
            }
            if (null != (formulaStringObjs = stringListMap.get(FORMULA))) {
                ArrayList<FormulaSchemeDTO> allFormFormulaDtos = new ArrayList<FormulaSchemeDTO>();
                for (Object formulaStringObj : formulaStringObjs) {
                    String formulaString = formulaStringObj.toString();
                    FormulaSchemeDTO formulaSchemeDTO = (FormulaSchemeDTO)this.objectMapper.readValue(formulaString, FormulaSchemeDTO.class);
                    allFormFormulaDtos.add(formulaSchemeDTO);
                }
                formSchemeFullDTO.setAllFormFormulaDTOs(allFormFormulaDtos);
            }
            if (null != (printSchemeStringObjs = stringListMap.get(PRINT_SCHEME))) {
                ArrayList<PrintTemplateSchemeDTO> allPrintTemplateSchemeDtos = new ArrayList<PrintTemplateSchemeDTO>();
                for (Object printSchemeStringObj : printSchemeStringObjs) {
                    String string = printSchemeStringObj.toString();
                    PrintTemplateSchemeDTO printTemplateDTO = (PrintTemplateSchemeDTO)this.objectMapper.readValue(string, PrintTemplateSchemeDTO.class);
                    allPrintTemplateSchemeDtos.add(printTemplateDTO);
                }
                formSchemeFullDTO.setAllPrintTemplateSchemeDTOs(allPrintTemplateSchemeDtos);
            }
            List<Object> printTemplateStringObjs = stringListMap.get(PRINT_TEMPLATE);
            ArrayList<PrintTemplateDTO> importPrintTemplateDTO = new ArrayList<PrintTemplateDTO>();
            if (null != printTemplateStringObjs) {
                for (Object e : printTemplateStringObjs) {
                    String printTemplateString = e.toString();
                    PrintTemplateDTO printTemplateDTO = (PrintTemplateDTO)this.objectMapper.readValue(printTemplateString, PrintTemplateDTO.class);
                    importPrintTemplateDTO.add(printTemplateDTO);
                }
                formSchemeFullDTO.setAllPrintTemplateDTOs(importPrintTemplateDTO);
            }
            List<Object> printSettingStringObjs = stringListMap.get(PRINT_SETTING);
            ArrayList<PrintSettingDTO> arrayList = new ArrayList<PrintSettingDTO>();
            if (null != printSettingStringObjs) {
                for (Object e : printSettingStringObjs) {
                    String printSettingString = e.toString();
                    PrintSettingDTO printSettingDTO = (PrintSettingDTO)this.objectMapper.readValue(printSettingString, PrintSettingDTO.class);
                    arrayList.add(printSettingDTO);
                }
                formSchemeFullDTO.setAllPrintSettingDTOs(arrayList);
            }
            List<Object> mappingStringObjs = stringListMap.get(MAPPING);
            ArrayList<SingleMappingDTO> arrayList2 = new ArrayList<SingleMappingDTO>();
            if (null != mappingStringObjs) {
                for (Object mappingStringObject : mappingStringObjs) {
                    String mappingString = mappingStringObject.toString();
                    SingleMappingDTO singleMappingDTO = (SingleMappingDTO)this.objectMapper.readValue(mappingString, SingleMappingDTO.class);
                    arrayList2.add(singleMappingDTO);
                }
                formSchemeFullDTO.setAllSingleMappingDTOs(arrayList2);
            }
        }
        catch (Exception e) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
        return formSchemeFullDTO;
    }

    private void doDeleteBusiness(int srcPacketLevel, String formSchemeKey, FormSchemeFullDTO formSchemeFullDTO) throws TransferException {
        try {
            int thisPacketLevel = this.paramLevelManager.getLevel().getValue();
            int levelType = 0;
            if (thisPacketLevel == 0) {
                this.designTimeViewController.deleteFormSchemeDefine(formSchemeKey);
                if (this.iMCParamService != null) {
                    this.iMCParamService.deleteSchemeByFormScheme(formSchemeKey);
                }
            } else if (0 == srcPacketLevel) {
                this.doDelete(formSchemeKey, String.valueOf(thisPacketLevel), srcPacketLevel, 0);
            } else if (thisPacketLevel == srcPacketLevel) {
                this.doDelete(formSchemeKey, String.valueOf(thisPacketLevel), srcPacketLevel, 0);
                levelType = 1;
            } else if (srcPacketLevel < thisPacketLevel) {
                this.doDelete(formSchemeKey, String.valueOf(thisPacketLevel), srcPacketLevel, 1);
                levelType = 2;
            }
            this.setLevel(formSchemeFullDTO, srcPacketLevel, levelType);
        }
        catch (Exception e) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    private void setLevel(FormSchemeFullDTO formSchemeFullDTO, int srcPacketLevel, int type) {
        List<FormSchemeDTO> formSchemeDTOList = formSchemeFullDTO.getAllFormSchemeDTOs();
        List<FormGroupInfoDTO> importFormGroupInfoDTO = formSchemeFullDTO.getAllFormGroupDTOs().stream().map(FormGroupDTO::getFormGroupInfo).collect(Collectors.toList());
        List<FormInfoDTO> importFormInfoDTO = formSchemeFullDTO.getAllFormDTOs().stream().map(FormDTO::getFormInfo).collect(Collectors.toList());
        List<FormulaSchemeInfoDTO> importFormulaSchemeInfoDTO = formSchemeFullDTO.getAllFormulaSchemeDTOs().stream().map(FormulaSchemeDTO::getFormulaSchemeInfo).collect(Collectors.toList());
        List<FormulaSchemeDTO> allFormFormulaDTOs = formSchemeFullDTO.getAllFormFormulaDTOs();
        ArrayList<FormulaDTO> importFormulaDTO = new ArrayList<FormulaDTO>();
        if (!CollectionUtils.isEmpty(allFormFormulaDTOs)) {
            for (FormulaSchemeDTO allFormFormulaDTO : allFormFormulaDTOs) {
                if (CollectionUtils.isEmpty(allFormFormulaDTO.getFormulas())) continue;
                importFormulaDTO.addAll(allFormFormulaDTO.getFormulas());
            }
        }
        List<PrintTemplateSchemeInfoDTO> importPrintTemplateSchemeInfoDTO = formSchemeFullDTO.getAllPrintTemplateSchemeDTOs().stream().map(PrintTemplateSchemeDTO::getPrintTemplateSchemeInfo).collect(Collectors.toList());
        List<PrintTemplateDTO> importPrintTemplateDTO = formSchemeFullDTO.getAllPrintTemplateDTOs();
        List<PrintSettingDTO> importPrintSettingDTOs = formSchemeFullDTO.getAllPrintSettingDTOs();
        if (type == 0) {
            for (FormSchemeDTO formSchemeDTO : formSchemeDTOList) {
                formSchemeDTO.getFormSchemeInfo().setOwnerLevelAndId(S_NOT_LEVEL);
                List<MultCheckParamDTO> multCheckParamDTOS = formSchemeDTO.getMultCheckParamDTOS();
                if (CollectionUtils.isEmpty(multCheckParamDTOS)) continue;
                multCheckParamDTOS.forEach(a -> a.setLevel(S_NOT_LEVEL));
            }
            importFormGroupInfoDTO.forEach(a -> a.setOwnerLevelAndId(S_NOT_LEVEL));
            importFormInfoDTO.forEach(a -> a.setOwnerLevelAndId(S_NOT_LEVEL));
            importFormulaSchemeInfoDTO.forEach(a -> a.setOwnerLevelAndId(S_NOT_LEVEL));
            importFormulaDTO.forEach(a -> a.setOwnerLevelAndId(S_NOT_LEVEL));
            importPrintTemplateSchemeInfoDTO.forEach(a -> a.setOwnerLevelAndId(S_NOT_LEVEL));
            importPrintTemplateDTO.forEach(a -> a.setOwnerLevelAndId(S_NOT_LEVEL));
            importPrintSettingDTOs.forEach(a -> a.setOwnerLevelAndId(S_NOT_LEVEL));
        } else if (type == 1 || type == 2) {
            String desLevel = type == 2 ? String.valueOf(srcPacketLevel) : S_NOT_LEVEL;
            for (FormSchemeDTO formSchemeDTO : formSchemeDTOList) {
                List<MultCheckParamDTO> multCheckParamDTOS;
                if (this.insertCheckLevel(formSchemeDTO.getFormSchemeInfo().getOwnerLevelAndId(), srcPacketLevel, type)) {
                    formSchemeDTO.getFormSchemeInfo().setOwnerLevelAndId(desLevel);
                }
                if (CollectionUtils.isEmpty(multCheckParamDTOS = formSchemeDTO.getMultCheckParamDTOS())) continue;
                multCheckParamDTOS.forEach(a -> {
                    if (this.insertCheckLevel(formSchemeDTO.getFormSchemeInfo().getOwnerLevelAndId(), srcPacketLevel, type)) {
                        a.setLevel(desLevel);
                    }
                });
            }
            importFormGroupInfoDTO.forEach(a -> {
                if (this.insertCheckLevel(a.getOwnerLevelAndId(), srcPacketLevel, type)) {
                    a.setOwnerLevelAndId(desLevel);
                }
            });
            importFormInfoDTO.forEach(a -> {
                if (this.insertCheckLevel(a.getOwnerLevelAndId(), srcPacketLevel, type)) {
                    a.setOwnerLevelAndId(desLevel);
                }
            });
            importFormulaSchemeInfoDTO.forEach(a -> {
                if (this.insertCheckLevel(a.getOwnerLevelAndId(), srcPacketLevel, type)) {
                    a.setOwnerLevelAndId(desLevel);
                }
            });
            importFormulaDTO.forEach(a -> {
                if (this.insertCheckLevel(a.getOwnerLevelAndId(), srcPacketLevel, type)) {
                    a.setOwnerLevelAndId(desLevel);
                }
            });
            importPrintTemplateSchemeInfoDTO.forEach(a -> {
                if (this.insertCheckLevel(a.getOwnerLevelAndId(), srcPacketLevel, type)) {
                    a.setOwnerLevelAndId(desLevel);
                }
            });
            importPrintTemplateDTO.forEach(a -> {
                if (this.insertCheckLevel(a.getOwnerLevelAndId(), srcPacketLevel, type)) {
                    a.setOwnerLevelAndId(desLevel);
                }
            });
            importPrintSettingDTOs.forEach(a -> {
                if (this.insertCheckLevel(a.getOwnerLevelAndId(), srcPacketLevel, type)) {
                    a.setOwnerLevelAndId(desLevel);
                }
            });
        }
    }

    private boolean insertCheckLevel(String defineLevel, int srcPacketLevel, int type) {
        if (type == 1) {
            return !StringUtils.hasText(defineLevel) || Integer.parseInt(defineLevel) == 0 || srcPacketLevel - Integer.parseInt(defineLevel) <= 0;
        }
        if (type == 2) {
            return !StringUtils.hasText(defineLevel) || Integer.parseInt(defineLevel) == 0 || srcPacketLevel - Integer.parseInt(defineLevel) < 0;
        }
        return false;
    }

    private void doDelete(String formSchemeKey, String thisPacketLevel, int srcPacketLevel, int type) throws Exception {
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(formSchemeKey);
        List<Object> deleteFormGroupKeys = new ArrayList();
        List<Object> deleteFormKeys = new ArrayList();
        List<Object> deletePrintSchemeKeys = new ArrayList();
        ArrayList deletePrintTemplateKeys = new ArrayList();
        List<Object> deleteFormulaSchemeKeys = new ArrayList();
        ArrayList deleteFormulaKeys = new ArrayList();
        List<Object> deleteMultcheckSchemeKeys = new ArrayList();
        if (formSchemeDefine != null) {
            List formDefines = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(formSchemeDefine.getKey());
            List formGroupDefines = this.designTimeViewController.queryRootGroupsByFormScheme(formSchemeDefine.getKey());
            List formulaSchemeDefines = this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
            List printTemplateSchemeDefines = this.printDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeDefine.getKey());
            if (this.iMCParamService != null) {
                List multCheckSchemes = this.iMCParamService.query(formSchemeKey);
                deleteMultcheckSchemeKeys = multCheckSchemes.stream().filter(a -> this.deleteCheckLevel(a.getLevel(), thisPacketLevel, srcPacketLevel, type)).map(MultcheckScheme::getKey).collect(Collectors.toList());
            }
            deleteFormKeys = formDefines.stream().filter(a -> this.deleteCheckLevel(a.getOwnerLevelAndId(), thisPacketLevel, srcPacketLevel, type)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            deleteFormGroupKeys = formGroupDefines.stream().filter(a -> this.deleteCheckLevel(a.getOwnerLevelAndId(), thisPacketLevel, srcPacketLevel, type)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            deleteFormulaSchemeKeys = formulaSchemeDefines.stream().filter(a -> this.deleteCheckLevel(a.getOwnerLevelAndId(), thisPacketLevel, srcPacketLevel, type)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            deletePrintSchemeKeys = printTemplateSchemeDefines.stream().filter(a -> this.deleteCheckLevel(a.getOwnerLevelAndId(), thisPacketLevel, srcPacketLevel, type)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            for (DesignFormulaSchemeDefine designFormulaSchemeDefine : formulaSchemeDefines) {
                if (deleteFormulaSchemeKeys.contains(designFormulaSchemeDefine.getKey())) continue;
                List allFormulasInScheme = this.formulaDesignTimeController.getAllFormulasInScheme(designFormulaSchemeDefine.getKey());
                deleteFormulaKeys.addAll(allFormulasInScheme.stream().filter(a -> this.deleteCheckLevel(a.getOwnerLevelAndId(), thisPacketLevel, srcPacketLevel, type)).map(IBaseMetaItem::getKey).collect(Collectors.toList()));
            }
            for (DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine : printTemplateSchemeDefines) {
                if (deletePrintSchemeKeys.contains(designPrintTemplateSchemeDefine.getKey())) continue;
                List printTemplateDefines = this.printDesignTimeController.getAllPrintTemplateInScheme(designPrintTemplateSchemeDefine.getKey());
                deletePrintTemplateKeys.addAll(printTemplateDefines.stream().filter(a -> this.deleteCheckLevel(a.getOwnerLevelAndId(), thisPacketLevel, srcPacketLevel, type)).map(IBaseMetaItem::getKey).collect(Collectors.toList()));
            }
            this.apiDesignTimeViewController.deleteForm(deleteFormKeys.toArray(new String[0]));
            logger.info("\u5168\u91cf\u5bfc\u5165\u62a5\u8868\u62a5\u8868\u6e05\u9664\uff1a{}", (Object)deleteFormKeys);
            for (String string : deleteFormGroupKeys) {
                List forms = this.designTimeViewController.queryAllSoftFormDefinesInGroup(string);
                if (!CollectionUtils.isEmpty(forms)) {
                    logger.info("\u5168\u91cf\u5bfc\u5165\u62a5\u8868\u5206\u7ec4\u6e05\u9664\uff1a{} \u4e0b\u8fd8\u6709\u62a5\u8868\uff0c\u6682\u4e0d\u6e05\u9664", (Object)string);
                    continue;
                }
                logger.info("\u5168\u91cf\u5bfc\u5165\u6267\u884c\u62a5\u8868\u5206\u7ec4\u6e05\u9664\uff1a{} ", (Object)string);
                this.designTimeViewController.deleteFormGroup(string);
            }
            for (String string : deleteFormulaSchemeKeys) {
                logger.info("\u5168\u91cf\u5bfc\u5165\u6267\u884c\u516c\u5f0f\u65b9\u6848\u6e05\u9664\uff1a{} ", (Object)string);
                this.formulaDesignTimeController.deleteFormulaSchemeDefine(string);
            }
            logger.info("\u5168\u91cf\u5bfc\u5165\u6267\u884c\u516c\u5f0f\u6e05\u9664\u6e05\u9664\u6389\u7684\u516c\u5f0f\u6570\u91cf\u4e3a\uff1a{}", (Object)deleteFormulaKeys.size());
            this.formulaDesignTimeController.deleteFormulaDefines(deleteFormulaKeys.toArray(new String[0]));
            for (String string : deletePrintSchemeKeys) {
                logger.info("\u5168\u91cf\u5bfc\u5165\u6267\u884c\u6253\u5370\u65b9\u6848\u6e05\u9664\uff1a{} ", (Object)string);
                this.printDesignTimeController.deletePrintTemplateSchemeDefine(string);
                this.printDesignTimeController.deletePrintTemplateDefineByScheme(string);
            }
            logger.info("\u5168\u91cf\u5bfc\u5165\u62a5\u8868\u65b9\u6848\u6e05\u9664\u7efc\u5408\u5ba1\u6838\u65b9\u6848\uff1a{}", (Object)deleteMultcheckSchemeKeys);
            this.iMCParamService.batchDeleteSchemeByKeys(deleteMultcheckSchemeKeys);
            logger.info("\u5168\u91cf\u5bfc\u5165\u6267\u884c\u6253\u5370\u6a21\u677f\u6e05\u9664\uff1a{} ", (Object)deletePrintTemplateKeys);
            this.designTimePrintController.deletePrintTemplate(deletePrintTemplateKeys.toArray(new String[0]));
        }
    }

    private boolean deleteCheckLevel(String defineLevel, String thisPacketLevel, int srcPacketLevel, int type) {
        if (type == 0) {
            return !StringUtils.hasText(defineLevel) || S_NOT_LEVEL.equals(defineLevel) || thisPacketLevel.equals(defineLevel);
        }
        if (type == 1) {
            return StringUtils.hasText(defineLevel) && !S_NOT_LEVEL.equals(defineLevel) && srcPacketLevel - Integer.parseInt(defineLevel) <= 0;
        }
        return false;
    }

    private void importCustomDataBusiness(IImportContext context, String key, byte[] bytes) throws TransferException, IOException {
        this.definitionModelTransfer.importCustomData(context, key, bytes);
        if (logger.isDebugEnabled()) {
            logger.debug("\u53c2\u6570\u5bfc\u5165\u5168\u91cf\u4efb\u52a1\u5bfc\u5165\u5b8c\u6210\uff01");
        }
    }

    private void debugeMessage(String resourceTypeName, String key) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u62a5\u8868\u65b9\u6848\u5168\u91cf\u5bfc\u5165\u5f00\u59cb\u5bfc\u5165%s \uff0ckey\u662f\uff1a%s \uff01", resourceTypeName, key));
        }
    }
}

