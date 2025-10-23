/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FullCoverageImportBusinessNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IConfigTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IDataTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IMetaFinder
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IPublisher
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.bi.transfer.engine.intf.impl.DefaultImportContext
 *  com.jiuqi.bi.transfer.engine.model.GuidMapperBean
 *  com.jiuqi.bi.transfer.engine.model.NameMapperBean
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.des.LevelLoader
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.mapping2.service.MappingTransferService
 *  org.jdom2.Element
 */
package com.jiuqi.nr.param.transfer.definition;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FullCoverageImportBusinessNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IConfigTransfer;
import com.jiuqi.bi.transfer.engine.intf.IDataTransfer;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IMetaFinder;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.intf.IPublisher;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.bi.transfer.engine.intf.impl.DefaultImportContext;
import com.jiuqi.bi.transfer.engine.model.GuidMapperBean;
import com.jiuqi.bi.transfer.engine.model.NameMapperBean;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.des.LevelLoader;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.mapping2.service.MappingTransferService;
import com.jiuqi.nr.param.transfer.ChangeObj;
import com.jiuqi.nr.param.transfer.FormChangeObj;
import com.jiuqi.nr.param.transfer.FormulaChangeObj;
import com.jiuqi.nr.param.transfer.ParamTransferConfig;
import com.jiuqi.nr.param.transfer.TransferConsts;
import com.jiuqi.nr.param.transfer.Utils;
import com.jiuqi.nr.param.transfer.datascheme.IDesignDataSchemeCacheProxy;
import com.jiuqi.nr.param.transfer.datascheme.TransferIdParse;
import com.jiuqi.nr.param.transfer.definition.DataSchemeVisitor;
import com.jiuqi.nr.param.transfer.definition.DefinitionBusinessManager;
import com.jiuqi.nr.param.transfer.definition.DefinitionFolderManager;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.FormulaGuidParse;
import com.jiuqi.nr.param.transfer.definition.IDesignTimeCacheProxy;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.definition.dao.DataTableByFormDefineDao;
import com.jiuqi.nr.param.transfer.definition.dto.TaskBusinessNode;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintSettingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.singlemap.SingleMappingDTO;
import com.jiuqi.nr.param.transfer.definition.spi.IFormSchemeRelateBusiness;
import com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer;
import com.jiuqi.nr.param.transfer.formScheme.FormSchemeModelTransfer;
import com.jiuqi.nr.param.transfer.formScheme.dto.FormSchemeFullDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DefinitionTransferFactory
extends TransferFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefinitionTransferFactory.class);
    @Autowired
    private DefinitionModelTransfer definitionModelTransfer;
    @Autowired
    private DefinitionFolderManager definitionFolderManager;
    @Autowired
    private DefinitionBusinessManager definitionBusinessManager;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private LevelLoader levelLoader;
    @Autowired
    private IDesignDataSchemeCacheProxy iDesignDataSchemeCacheProxy;
    @Autowired
    private IDesignTimeCacheProxy iDesignTimeCacheProxy;
    @Autowired
    private MappingTransferService mappingTransferService;
    @Autowired(required=false)
    private List<TaskTransfer> taskTransfers;
    @Autowired(required=false)
    private List<IFormSchemeRelateBusiness> formSchemeRelateBusiness;
    @Autowired
    private DataTableByFormDefineDao dataTableByFormDefineDao;
    @Autowired
    private FormSchemeModelTransfer formSchemeModelTransfer;

    public String getId() {
        return "DEFINITION_TRANSFER_FACTORY_ID";
    }

    public String getTitle() {
        return "\u4efb\u52a1";
    }

    public String getModuleId() {
        return "com.jiuqi.nr";
    }

    public boolean supportFullCoverageImport() {
        return true;
    }

    public void fullCoverageImportModel(IImportContext context, List<FullCoverageImportBusinessNode> nodes) throws TransferException {
        FormSchemeDTO formSchemeDTO;
        List customBusinessNodes;
        List list;
        List<TransferNodeType> allTransferNodeType = Arrays.asList(TransferNodeType.values());
        allTransferNodeType.sort(Comparator.comparing(TransferNodeType::getValue));
        HashMap<TransferNodeType, List> resourceMap = new HashMap<TransferNodeType, List>();
        DefaultImportContext importContext = new DefaultImportContext((ITransferContext)context);
        BeanUtils.copyProperties(context, importContext);
        for (FullCoverageImportBusinessNode fullCoverageImportBusinessNode : nodes) {
            String guid = fullCoverageImportBusinessNode.getBusinessNode().getGuid();
            TransferGuid parse = TransferGuidParse.parseId(guid);
            resourceMap.computeIfAbsent(parse.getTransferNodeType(), key -> new ArrayList()).add(fullCoverageImportBusinessNode);
        }
        List taskGroupBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.TASK_GROUP);
        if (!CollectionUtils.isEmpty(taskGroupBusinessNodes)) {
            for (Object taskGroupBusinessNode : taskGroupBusinessNodes) {
                importContext.setTargetGuid(taskGroupBusinessNode.getBusinessNode().getGuid());
                this.definitionModelTransfer.importModel((IImportContext)importContext, taskGroupBusinessNode.getData());
            }
        }
        if (!CollectionUtils.isEmpty(list = (List)resourceMap.get((Object)TransferNodeType.TASK))) {
            for (Object taskBusinessNode : list) {
                importContext.setTargetGuid(taskBusinessNode.getBusinessNode().getGuid());
                this.definitionModelTransfer.importModel((IImportContext)importContext, taskBusinessNode.getData());
            }
        }
        if (!CollectionUtils.isEmpty(customBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.CUSTOM_DATA))) {
            for (FullCoverageImportBusinessNode customBusinessNode : customBusinessNodes) {
                importContext.setTargetGuid(customBusinessNode.getBusinessNode().getGuid());
                this.definitionModelTransfer.importModel((IImportContext)importContext, customBusinessNode.getData());
            }
        }
        HashMap<String, FormSchemeDTO> formSchemeResourceMap = new HashMap<String, FormSchemeDTO>();
        HashMap<String, List> formGroupResourceMap = new HashMap<String, List>();
        HashMap<String, String> formulaSchemeToFormSchemeKey = new HashMap<String, String>();
        HashMap<String, List> formulaSchemeResourceMap = new HashMap<String, List>();
        HashMap<String, List> printTemplateSchemeResourceMap = new HashMap<String, List>();
        HashMap<String, List> formResourceMap = new HashMap<String, List>();
        HashMap<String, List> formulaResourceMap = new HashMap<String, List>();
        HashMap<String, List> printTemplateResourceMap = new HashMap<String, List>();
        HashMap<String, List> printSettingResourceMap = new HashMap<String, List>();
        HashMap<String, List> singleMapping = new HashMap<String, List>();
        try {
            List mappingDefineBusinessNodes;
            String formSchemeKey;
            List printSettingBusinessNodes;
            List printTemplateBusinessNodes;
            List formulaFormBusinessNodes;
            List formBusinessNodes;
            Object data;
            List formulaSchemeBusinessNodes;
            List formGroupBusinessNodes;
            List formSchemeBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.FORM_SCHEME);
            if (!CollectionUtils.isEmpty(formSchemeBusinessNodes)) {
                for (Object formSchemeBusinessNode : formSchemeBusinessNodes) {
                    byte[] data2 = formSchemeBusinessNode.getData();
                    formSchemeDTO = FormSchemeDTO.valueOf(data2, DefinitionModelTransfer.objectMapper);
                    String formSchemeKey2 = formSchemeDTO.getFormSchemeInfo().getKey();
                    formSchemeResourceMap.put(formSchemeKey2, formSchemeDTO);
                }
            }
            if (!CollectionUtils.isEmpty(formGroupBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.FORM_GROUP))) {
                for (Object formGroupBusinessNode : formGroupBusinessNodes) {
                    byte[] data3 = formGroupBusinessNode.getData();
                    FormGroupDTO formGroupDTO = FormGroupDTO.valueOf(data3, DefinitionModelTransfer.objectMapper);
                    String formSchemeKey3 = formGroupDTO.getFormGroupInfo().getFormSchemeKey();
                    formGroupResourceMap.computeIfAbsent(formSchemeKey3, key -> new ArrayList()).add(formGroupDTO);
                }
            }
            if (!CollectionUtils.isEmpty(formulaSchemeBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.FORMULA_SCHEME))) {
                for (FullCoverageImportBusinessNode formulaSchemeBusinessNode : formulaSchemeBusinessNodes) {
                    data = formulaSchemeBusinessNode.getData();
                    FormulaSchemeDTO formulaSchemeDTO = FormulaSchemeDTO.valueOf((byte[])data, DefinitionModelTransfer.objectMapper);
                    String formSchemeKey4 = formulaSchemeDTO.getFormulaSchemeInfo().getFormSchemeKey();
                    formulaSchemeResourceMap.computeIfAbsent(formSchemeKey4, key -> new ArrayList()).add(formulaSchemeDTO);
                    formulaSchemeToFormSchemeKey.put(formulaSchemeDTO.getFormulaSchemeInfo().getKey(), formSchemeKey4);
                }
            }
            HashMap<String, String> printSchemeToFormSchemeKey = new HashMap<String, String>();
            List printSchemeBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.PRINT_SCHEME);
            if (!CollectionUtils.isEmpty(printSchemeBusinessNodes)) {
                data = printSchemeBusinessNodes.iterator();
                while (data.hasNext()) {
                    FullCoverageImportBusinessNode printSchemeBusinessNode = (FullCoverageImportBusinessNode)data.next();
                    byte[] data4 = printSchemeBusinessNode.getData();
                    PrintTemplateSchemeDTO printTemplateSchemeDTO = PrintTemplateSchemeDTO.valueOf(data4, DefinitionModelTransfer.objectMapper);
                    String formSchemeKey5 = printTemplateSchemeDTO.getPrintTemplateSchemeInfo().getFormSchemeKey();
                    printTemplateSchemeResourceMap.computeIfAbsent(formSchemeKey5, key -> new ArrayList()).add(printTemplateSchemeDTO);
                    printSchemeToFormSchemeKey.put(printTemplateSchemeDTO.getPrintTemplateSchemeInfo().getKey(), formSchemeKey5);
                }
            }
            if (!CollectionUtils.isEmpty(formBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.FORM))) {
                for (Object formBusinessNode : formBusinessNodes) {
                    byte[] data5 = formBusinessNode.getData();
                    FormDTO formDTO = FormDTO.valueOf(data5, DefinitionModelTransfer.objectMapper);
                    String formSchemeKey6 = formDTO.getFormInfo().getFormScheme();
                    formResourceMap.computeIfAbsent(formSchemeKey6, key -> new ArrayList()).add(formDTO);
                }
            }
            if (!CollectionUtils.isEmpty(formulaFormBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.FORMULA_FORM))) {
                for (Object formulaFormBusinessNode : formulaFormBusinessNodes) {
                    TransferGuid parse = TransferGuidParse.parseId(formulaFormBusinessNode.getBusinessNode().getGuid());
                    String[] args = FormulaGuidParse.parseKey(parse.getKey());
                    String formulaSchemeKey = args[0];
                    String formSchemeKey7 = (String)formulaSchemeToFormSchemeKey.get(formulaSchemeKey);
                    byte[] data6 = formulaFormBusinessNode.getData();
                    FormulaSchemeDTO formulaSchemeDTO = FormulaSchemeDTO.valueOf(data6, DefinitionModelTransfer.objectMapper);
                    formulaResourceMap.computeIfAbsent(formSchemeKey7, key -> new ArrayList()).add(formulaSchemeDTO);
                }
            }
            if (!CollectionUtils.isEmpty(printTemplateBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.PRINT_TEMPLATE))) {
                for (Object printTemplateBusinessNode : printTemplateBusinessNodes) {
                    byte[] data7 = printTemplateBusinessNode.getData();
                    PrintTemplateDTO printTemplateDTO = PrintTemplateDTO.valueOf(data7, DefinitionModelTransfer.objectMapper);
                    String printSchemeKey = printTemplateDTO.getPrintSchemeKey();
                    String formSchemeKey8 = (String)printSchemeToFormSchemeKey.get(printSchemeKey);
                    printTemplateResourceMap.computeIfAbsent(formSchemeKey8, key -> new ArrayList()).add(printTemplateDTO);
                }
            }
            if (!CollectionUtils.isEmpty(printSettingBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.PRINT_SETTING))) {
                for (Object printSettingBusinessNode : printSettingBusinessNodes) {
                    byte[] data8 = printSettingBusinessNode.getData();
                    PrintSettingDTO printSettingDTO = PrintSettingDTO.valueOf(data8, DefinitionModelTransfer.objectMapper);
                    String printSchemeKey = printSettingDTO.getPrintSchemeKey();
                    formSchemeKey = (String)printSchemeToFormSchemeKey.get(printSchemeKey);
                    printSettingResourceMap.computeIfAbsent(formSchemeKey, key -> new ArrayList()).add(printSettingDTO);
                }
            }
            if (!CollectionUtils.isEmpty(mappingDefineBusinessNodes = (List)resourceMap.get((Object)TransferNodeType.MAPPING_DEFINE))) {
                for (FullCoverageImportBusinessNode mappingDefineBusinessNode : mappingDefineBusinessNodes) {
                    byte[] data9 = mappingDefineBusinessNode.getData();
                    SingleMappingDTO singleMappingDTO = SingleMappingDTO.valueOf(data9, DefinitionModelTransfer.objectMapper);
                    formSchemeKey = singleMappingDTO.getSingleMappingConfig().getSchemeKey();
                    singleMapping.computeIfAbsent(formSchemeKey, key -> new ArrayList()).add(singleMappingDTO);
                }
            }
        }
        catch (Exception e) {
            throw new TransferException("\u89e3\u6790\u5931\u8d25\u62a5\u8868\u65b9\u6848\u5168\u91cf\u5bfc\u5165\u8d44\u6e90\u89e3\u6790\u5931\u8d25");
        }
        for (Map.Entry entry : formSchemeResourceMap.entrySet()) {
            FormSchemeFullDTO formSchemeFull = new FormSchemeFullDTO();
            String formSchemeKey = (String)entry.getKey();
            formSchemeDTO = (FormSchemeDTO)entry.getValue();
            ArrayList<FormSchemeDTO> allFormSchemeDTOs = new ArrayList<FormSchemeDTO>();
            allFormSchemeDTOs.add(formSchemeDTO);
            formSchemeFull.setAllFormSchemeDTOs(allFormSchemeDTOs);
            ArrayList<FormGroupDTO> formGroupDTOS = formGroupResourceMap.get(formSchemeKey) != null ? (List)formGroupResourceMap.get(formSchemeKey) : new ArrayList();
            formSchemeFull.setAllFormGroupDTOs(formGroupDTOS);
            ArrayList<FormulaSchemeDTO> formulaSchemeDTOS = formulaSchemeResourceMap.get(formSchemeKey) != null ? (List)formulaSchemeResourceMap.get(formSchemeKey) : new ArrayList();
            formSchemeFull.setAllFormulaSchemeDTOs(formulaSchemeDTOS);
            ArrayList<PrintTemplateSchemeDTO> printTemplateSchemeDTOS = printTemplateSchemeResourceMap.get(formSchemeKey) != null ? (List)printTemplateSchemeResourceMap.get(formSchemeKey) : new ArrayList();
            formSchemeFull.setAllPrintTemplateSchemeDTOs(printTemplateSchemeDTOS);
            ArrayList<FormDTO> formDTOS = formResourceMap.get(formSchemeKey) != null ? (List)formResourceMap.get(formSchemeKey) : new ArrayList();
            formSchemeFull.setAllFormDTOs(formDTOS);
            ArrayList<FormulaSchemeDTO> formFormulaSchemeDTOS = formulaResourceMap.get(formSchemeKey) != null ? (List)formulaResourceMap.get(formSchemeKey) : new ArrayList();
            formSchemeFull.setAllFormFormulaDTOs(formFormulaSchemeDTOS);
            ArrayList<PrintTemplateDTO> printTemplateDTOS = printTemplateResourceMap.get(formSchemeKey) != null ? (List)printTemplateResourceMap.get(formSchemeKey) : new ArrayList();
            formSchemeFull.setAllPrintTemplateDTOs(printTemplateDTOS);
            ArrayList<PrintSettingDTO> printSettingDTOS = printSettingResourceMap.get(formSchemeKey) != null ? (List)printSettingResourceMap.get(formSchemeKey) : new ArrayList();
            formSchemeFull.setAllPrintSettingDTOs(printSettingDTOS);
            ArrayList<SingleMappingDTO> singleMappingDTOS = singleMapping.get(formSchemeKey) != null ? (List)singleMapping.get(formSchemeKey) : new ArrayList();
            formSchemeFull.setAllSingleMappingDTOs(singleMappingDTOS);
            this.formSchemeModelTransfer.handleImportFormScheme(context, formSchemeKey, formSchemeFull);
        }
    }

    public boolean supportExport(String s) {
        return true;
    }

    public IModelTransfer createModelTransfer(String s) {
        return this.definitionModelTransfer;
    }

    public IConfigTransfer createConfigTransfer() {
        return null;
    }

    public IPublisher createPublisher() {
        return null;
    }

    public IDataTransfer createDataTransfer(String s) {
        return null;
    }

    public boolean supportExportData(String s) {
        return false;
    }

    public List<NameMapperBean> handleMapper() {
        return null;
    }

    public List<GuidMapperBean> handleMapper(List<NameMapperBean> list) {
        return null;
    }

    public IMetaFinder createMetaFinder(String s) {
        return null;
    }

    public AbstractFolderManager getFolderManager() {
        return this.definitionFolderManager;
    }

    public AbstractBusinessManager getBusinessManager() {
        return this.definitionBusinessManager;
    }

    public String getModifiedTime(String s) throws TransferException {
        IMetaItem metaItem = this.definitionBusinessManager.getMetaItem(s);
        if (metaItem == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        Date updateTime = metaItem.getUpdateTime();
        return updateTime == null ? null : TransferConsts.DATE_FORMAT_1.get().format(updateTime);
    }

    public String getModifiedTime(ITransferContext context, String guid) throws TransferException {
        return this.getModifiedTime(guid);
    }

    public String getIcon() {
        return null;
    }

    public List<ResItem> getRelatedBusiness(String s) throws TransferException {
        TransferGuid parse = TransferGuidParse.parse(s);
        if (parse == null) {
            return Collections.emptyList();
        }
        ArrayList<ResItem> list = new ArrayList<ResItem>();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        String key = parse.getKey();
        Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
        if (transferNodeType == TransferNodeType.FORM) {
            logger.info("form: " + s);
            this.getRelatedBusinessOfTheForm(list, key);
        } else if (transferNodeType == TransferNodeType.TASK) {
            logger.info("task: " + s);
            this.getRelatedBusinessOfTheTask(list, key);
        } else if (transferNodeType == TransferNodeType.FORM_SCHEME) {
            logger.info("\u62a5\u8868\u65b9\u6848\u5173\u8054\u8d44\u6e90\uff0cform_scheme\u7684guid: " + s);
            this.getRelatedBusinessOfTheFormScheme(list, key);
        } else if (transferNodeType == TransferNodeType.PRINT_TEMPLATE) {
            logger.info("\u6253\u5370\u6a21\u677f: " + s);
            this.getRelatedBusinessOfThePrintTemplate(list, key);
        }
        return list;
    }

    public void getRelatedBusinessOfThePrintTemplate(List<ResItem> list, String key) throws TransferException {
        DesignPrintComTemDefine comTemDefine;
        DesignPrintTemplateDefine templateDefine = this.iDesignTimeCacheProxy.getPrintTemplateDefine(key);
        if (null == templateDefine) {
            return;
        }
        if (StringUtils.hasText(templateDefine.getComTemCode()) && null != (comTemDefine = this.designTimePrintController.getPrintComTemBySchemeAndCode(templateDefine.getPrintSchemeKey(), templateDefine.getComTemCode()))) {
            list.add(new ResItem(TransferGuidParse.toBusinessId(TransferNodeType.PRINT_COMTEM, comTemDefine.getKey()), TransferNodeType.PRINT_COMTEM.getTitle(), "DEFINITION_TRANSFER_FACTORY_ID"));
        }
    }

    public void getRelatedBusinessOfTheFormScheme(List<ResItem> list, String key) {
        List mappingByForm = this.mappingTransferService.getSchemeByForm(key);
        if (mappingByForm == null) {
            Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
            logger.info("\u62a5\u8868\u65b9\u6848: " + key + " \u6dfb\u52a0\u5173\u8054\u8d44\u6e90\uff0c\u65b0\u6620\u5c04\u67e5\u8be2\u7ed3\u679c\u4e3a\u7a7a");
        } else {
            for (String s : mappingByForm) {
                list.add(new ResItem(s, "SHCEME", "nvwa_mapping"));
            }
        }
        if (!CollectionUtils.isEmpty(this.formSchemeRelateBusiness)) {
            for (IFormSchemeRelateBusiness schemeRelateBusiness : this.formSchemeRelateBusiness) {
                List<ResItem> relatedBusinessForFormScheme = schemeRelateBusiness.getRelatedBusinessForFormScheme(key);
                if (CollectionUtils.isEmpty(relatedBusinessForFormScheme)) continue;
                list.addAll(relatedBusinessForFormScheme);
            }
        }
    }

    public void getRelatedBusinessOfTheTask(List<ResItem> list, String key) {
        DesignTaskDefine taskDefine = this.iDesignTimeCacheProxy.getTaskDefine(key);
        if (taskDefine == null) {
            return;
        }
        String dataSchemeKey = taskDefine.getDataScheme();
        if (!StringUtils.hasLength(dataSchemeKey)) {
            return;
        }
        DesignDataScheme dataScheme = this.iDesignDataSchemeCacheProxy.getDataScheme(dataSchemeKey);
        if (dataScheme == null) {
            return;
        }
        SchemeNode root = new SchemeNode(dataScheme.getKey(), NodeType.SCHEME.getValue());
        DataSchemeVisitor dataSchemeVisitor = new DataSchemeVisitor();
        dataSchemeVisitor.setIDesignDataSchemeCacheProxy(this.iDesignDataSchemeCacheProxy);
        this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)dataSchemeVisitor, Integer.valueOf(NodeType.SCHEME.getValue() | NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.MD_INFO.getValue()));
        List<ResItem> resItems = dataSchemeVisitor.getResItems();
        if (resItems != null) {
            list.addAll(resItems);
        }
    }

    private void getRelatedBusinessOfTheForm(List<ResItem> list, String key) throws TransferException {
        DesignFormDefine formDefine = this.iDesignTimeCacheProxy.getSoftFormDefine(key);
        if (formDefine == null) {
            return;
        }
        String formSchemeKey = formDefine.getFormScheme();
        try {
            Object extensionProp;
            if (formDefine.getFormType() == FormType.FORM_TYPE_INSERTANALYSIS && (extensionProp = formDefine.getExtensionProp("analysisGuid")) != null) {
                list.add(new ResItem(extensionProp.toString(), "com.jiuqi.nvwa.quickreport.business", "DataAnalysisResourceCategory_ID"));
            }
            this.formulaScheme(list, formSchemeKey, key);
            this.printScheme(list, formSchemeKey, key);
            this.dataLink(key, list);
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
    }

    private void printScheme(List<ResItem> list, String formSchemeKey, String formKey) throws Exception {
        ArrayList<DesignPrintTemplateSchemeDefine> printSchemes = this.iDesignTimeCacheProxy.getAllPrintSchemeByFormScheme(formSchemeKey);
        for (PrintTemplateSchemeDefine printTemplateSchemeDefine : printSchemes) {
            DesignPrintSettingDefine setting;
            DesignPrintComTemDefine comTemDefine;
            String printSchemeKey = printTemplateSchemeDefine.getKey();
            DesignPrintTemplateDefine templateDefine = this.printDesignTimeController.queryPrintTemplateDefineBySchemeAndForm(printSchemeKey, formKey, false);
            if (templateDefine == null) continue;
            ResItem resItem = new ResItem(TransferGuidParse.toBusinessId(TransferNodeType.PRINT_TEMPLATE, templateDefine.getKey()), TransferNodeType.PRINT_TEMPLATE.getTitle(), "DEFINITION_TRANSFER_FACTORY_ID");
            list.add(resItem);
            if (StringUtils.hasText(templateDefine.getComTemCode()) && null != (comTemDefine = this.designTimePrintController.getPrintComTemBySchemeAndCode(templateDefine.getPrintSchemeKey(), templateDefine.getComTemCode()))) {
                list.add(new ResItem(TransferGuidParse.toBusinessId(TransferNodeType.PRINT_COMTEM, comTemDefine.getKey()), TransferNodeType.PRINT_COMTEM.getTitle(), "DEFINITION_TRANSFER_FACTORY_ID"));
            }
            if ((setting = this.designTimePrintController.getPrintSettingDefine(printSchemeKey, formKey)) == null) continue;
            list.add(new ResItem(TransferGuidParse.toBusinessId(TransferNodeType.PRINT_SETTING, FormulaGuidParse.toFormulaId(printSchemeKey, formKey)), TransferNodeType.PRINT_SETTING.getTitle(), "DEFINITION_TRANSFER_FACTORY_ID"));
        }
    }

    private void formulaScheme(List<ResItem> list, String formSchemeKey, String formKey) {
        ArrayList<DesignFormulaSchemeDefine> formulaSchemes = this.iDesignTimeCacheProxy.getFormulaSchemeDefineBySchemeKey(formSchemeKey);
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemes) {
            ResItem resItem = new ResItem(TransferGuidParse.toBusinessId(TransferNodeType.FORMULA_FORM, FormulaGuidParse.toVNodeFormulaId(formulaSchemeDefine.getKey())), TransferNodeType.FORMULA_FORM.getTitle(), "DEFINITION_TRANSFER_FACTORY_ID");
            list.add(resItem);
            resItem = new ResItem(TransferGuidParse.toBusinessId(TransferNodeType.FORMULA_FORM, FormulaGuidParse.toFormulaId(formulaSchemeDefine.getKey(), formKey)), TransferNodeType.FORMULA_FORM.getTitle(), "DEFINITION_TRANSFER_FACTORY_ID");
            list.add(resItem);
        }
    }

    private void dataLinkTODO(String key, List<ResItem> list) {
        List links = this.designTimeViewController.getAllLinksInForm(key);
        HashSet<String> fieldKeySet = new HashSet<String>();
        for (DataLinkDefine link : links) {
            String fieldKey;
            DataLinkType type = link.getType();
            if (type != DataLinkType.DATA_LINK_TYPE_FIELD || (fieldKey = link.getLinkExpression()) == null) continue;
            fieldKeySet.add(fieldKey);
        }
        if (fieldKeySet.isEmpty()) {
            return;
        }
        List dataFields = this.designDataSchemeService.getDataFields(new ArrayList(fieldKeySet));
        if (dataFields.isEmpty()) {
            return;
        }
        Set tableKeySet = dataFields.stream().map(DataField::getDataTableKey).filter(Objects::nonNull).collect(Collectors.toSet());
        List dataTables = this.designDataSchemeService.getDataTables(new ArrayList(tableKeySet));
        if (dataTables.isEmpty()) {
            return;
        }
        for (DesignDataTable dataTable : dataTables) {
            String tableKey = dataTable.getKey();
            DataTableType tableType = dataTable.getDataTableType();
            if (tableType == null) continue;
            NodeType nodeType = NodeType.TABLE;
            switch (tableType) {
                case DETAIL: {
                    nodeType = NodeType.DETAIL_TABLE;
                    break;
                }
                case MULTI_DIM: {
                    nodeType = NodeType.MUL_DIM_TABLE;
                    break;
                }
                case ACCOUNT: {
                    nodeType = NodeType.ACCOUNT_TABLE;
                    break;
                }
                case MD_INFO: {
                    nodeType = NodeType.MD_INFO;
                    break;
                }
            }
            String businessId = TransferIdParse.toBusinessId(nodeType, tableKey);
            ResItem resItem = new ResItem(businessId, nodeType.getTitle(), "DATASCHEME_TRANSFER_FACTORY_ID");
            list.add(resItem);
        }
    }

    private void dataLink(String formKey, List<ResItem> list) {
        List<String> tableKeys = this.dataTableByFormDefineDao.getDataTableKeysByFormKey(formKey);
        ArrayList<DesignDataTable> dataTables = new ArrayList<DesignDataTable>();
        for (String tableKey : tableKeys) {
            DesignDataTable dataTable = this.iDesignDataSchemeCacheProxy.getDataTable(tableKey);
            if (dataTable == null) continue;
            dataTables.add(dataTable);
        }
        if (dataTables.isEmpty()) {
            return;
        }
        for (DesignDataTable dataTable : dataTables) {
            String tableKey = dataTable.getKey();
            DataTableType tableType = dataTable.getDataTableType();
            if (tableType == null) continue;
            NodeType nodeType = NodeType.TABLE;
            switch (tableType) {
                case DETAIL: {
                    nodeType = NodeType.DETAIL_TABLE;
                    break;
                }
                case MULTI_DIM: {
                    nodeType = NodeType.MUL_DIM_TABLE;
                    break;
                }
                case ACCOUNT: {
                    nodeType = NodeType.ACCOUNT_TABLE;
                    break;
                }
                case MD_INFO: {
                    nodeType = NodeType.MD_INFO;
                    break;
                }
            }
            String businessId = TransferIdParse.toBusinessId(nodeType, tableKey);
            ResItem resItem = new ResItem(businessId, nodeType.getTitle(), "DATASCHEME_TRANSFER_FACTORY_ID");
            list.add(resItem);
        }
    }

    public List<String> getDependenceFactoryIds() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("DATASCHEME_TRANSFER_FACTORY_ID");
        return list;
    }

    public int getOrder() {
        return -125;
    }

    public void sortImportBusinesses(List<BusinessNode> importBusinessNodes) {
        importBusinessNodes.sort(Comparator.nullsLast((o1, o2) -> {
            String type = o1.getType();
            String type1 = o2.getType();
            TransferNodeType nodeType = TransferNodeType.valueOf(type);
            TransferNodeType nodeType1 = TransferNodeType.valueOf(type1);
            return nodeType.getValue() - nodeType1.getValue();
        }));
    }

    public BusinessNode createBusinessNode() {
        return new TaskBusinessNode();
    }

    public void toDocumentExtra(Element element, BusinessNode businessNode) {
        TransferGuid taskNodeTransferGuid = this.getTaskNodeTransferGuid(businessNode);
        if (taskNodeTransferGuid == null) {
            return;
        }
        for (TaskTransfer taskTransfer : this.taskTransfers) {
            taskTransfer.toDocumentTaskExtra(taskNodeTransferGuid.getKey(), element);
        }
    }

    public void loadBusinessExtra(Element element, BusinessNode businessNode) {
        TransferGuid taskNodeTransferGuid = this.getTaskNodeTransferGuid(businessNode);
        if (taskNodeTransferGuid == null) {
            return;
        }
        if (businessNode instanceof TaskBusinessNode) {
            for (TaskTransfer taskTransfer : this.taskTransfers) {
                Object v = taskTransfer.loadBusinessTaskExtra(element);
                if (v == null) continue;
                ((TaskBusinessNode)businessNode).setValue(taskTransfer.getId(), v);
            }
        }
    }

    private TransferGuid getTaskNodeTransferGuid(BusinessNode businessNode) {
        if (this.taskTransfers == null) {
            return null;
        }
        String guid = businessNode.getGuid();
        TransferGuid parse = TransferGuidParse.parse(guid);
        if (parse == null) {
            return null;
        }
        String key = parse.getKey();
        if (key == null) {
            return null;
        }
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        if (transferNodeType != TransferNodeType.TASK) {
            return null;
        }
        return parse;
    }

    public void beforeImport(ITransferContext context, List<BusinessNode> businessNodes) throws TransferException {
        this.definitionModelTransfer.cleanFormChangeFormulaObjs();
        this.definitionModelTransfer.cleanFormChangeObj();
        this.doLogHelper(businessNodes);
        ParamTransferConfig.buildNpContext(context.getOperator());
    }

    private void doLogHelper(List<BusinessNode> businessNodes) {
        if (businessNodes != null && !businessNodes.isEmpty()) {
            LogHelper.info((String)"\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa", (String)"\u5bfc\u5165\u4efb\u52a1\u53c2\u6570", (String)"\u8fdb\u884c\u4e86\u4efb\u52a1\u8d44\u6e90\u7684\u5bfc\u5165\uff01");
        }
    }

    public String getImportDetailFileKey(ITransferContext context) {
        StringBuilder allMessage = new StringBuilder();
        Map<String, FormChangeObj> formChangeObj = this.definitionModelTransfer.getFormChangeObj();
        for (Map.Entry<String, FormChangeObj> formChangeForSchemeEntry : formChangeObj.entrySet()) {
            StringBuilder formMessageForScheme = new StringBuilder();
            FormChangeObj formChangeObj2 = formChangeForSchemeEntry.getValue();
            formMessageForScheme.append("\u62a5\u8868\u65b9\u6848 ").append(formChangeObj2.getFormSchemeTitle()).append("\r\n");
            if (!CollectionUtils.isEmpty(formChangeObj2.getAddForms())) {
                formMessageForScheme.append("\u4e0b\u65b0\u589e\u7684\u8868\u5355\u6709\uff1a");
                for (ChangeObj addForm : formChangeObj2.getAddForms()) {
                    formMessageForScheme.append(addForm.getTitle()).append("[").append(addForm.getCode()).append("]").append("\u3001");
                }
                formMessageForScheme.append("\r\n");
            }
            if (!CollectionUtils.isEmpty(formChangeObj2.getUpdateForms())) {
                formMessageForScheme.append("\u4e0b\u66f4\u65b0\u7684\u8868\u5355\u6709\uff1a");
                for (ChangeObj updateForm : formChangeObj2.getUpdateForms()) {
                    formMessageForScheme.append(updateForm.getTitle()).append("[").append(updateForm.getCode()).append("]").append("\u3001");
                }
                formMessageForScheme.append("\r\n");
            }
            formMessageForScheme.append("\r\n");
            allMessage.append((CharSequence)formMessageForScheme);
        }
        List<FormulaChangeObj> formChangeFormulaObjs = this.definitionModelTransfer.getFormChangeFormulaObjs();
        Map<String, List<FormulaChangeObj>> formSchemeToFormulaChange = formChangeFormulaObjs.stream().collect(Collectors.groupingBy(FormulaChangeObj::getFormulaSchemeKey));
        for (Map.Entry entry : formSchemeToFormulaChange.entrySet()) {
            List formSchemeToFormulaChangeValue = (List)entry.getValue();
            String formulaSchemeTitle = ((FormulaChangeObj)formSchemeToFormulaChangeValue.get(0)).getFormulaSchemeTitle();
            StringBuilder formulaMessageForScheme = new StringBuilder();
            formulaMessageForScheme.append("\u516c\u5f0f\u65b9\u6848 ").append(formulaSchemeTitle).append("\r\n");
            for (FormulaChangeObj formulaChangeObj : formSchemeToFormulaChangeValue) {
                formulaMessageForScheme.append("\u4e0b\u8868\u5355 ").append(formulaChangeObj.getFormTitle()).append("\r\n");
                if (!CollectionUtils.isEmpty(formulaChangeObj.getAddFormulas())) {
                    formulaMessageForScheme.append("\u65b0\u589e\u7684\u516c\u5f0f\u6709\uff1a");
                    for (ChangeObj addFormula : formulaChangeObj.getAddFormulas()) {
                        formulaMessageForScheme.append(addFormula.getCode()).append("\u3001");
                    }
                    formulaMessageForScheme.append("\r\n");
                }
                if (!CollectionUtils.isEmpty(formulaChangeObj.getUpdateFormulas())) {
                    formulaMessageForScheme.append("\u66f4\u65b0\u7684\u516c\u5f0f\u6709\uff1a");
                    for (ChangeObj addFormula : formulaChangeObj.getUpdateFormulas()) {
                        formulaMessageForScheme.append(addFormula.getCode()).append("\u3001");
                    }
                    formulaMessageForScheme.append("\r\n");
                }
                if (!CollectionUtils.isEmpty(formulaChangeObj.getDeleteFormulas())) {
                    formulaMessageForScheme.append("\u5220\u9664\u7684\u516c\u5f0f\u6709\uff1a");
                    for (ChangeObj addFormula : formulaChangeObj.getDeleteFormulas()) {
                        formulaMessageForScheme.append(addFormula.getCode()).append("\u3001");
                    }
                    formulaMessageForScheme.append("\r\n");
                }
                formulaMessageForScheme.append("\r\n");
            }
            formulaMessageForScheme.append("\r\n");
            allMessage.append((CharSequence)formulaMessageForScheme);
        }
        if (allMessage.length() == 0) {
            allMessage.append("\u62a5\u8868\u53c2\u6570\u65e0\u53d8\u5316");
        }
        String fileKey = "";
        try {
            fileKey = Utils.fileUpload("\u62a5\u8868\u540c\u6b65\u7ed3\u679c\u4fe1\u606f.txt", allMessage);
        }
        catch (Exception exception) {
            logger.error("\u53c2\u6570\u5bfc\u5165\u5199\u5165\u6587\u4ef6\u65f6\u51fa\u73b0\u9519\u8bef: " + exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
        return fileKey;
    }
}

