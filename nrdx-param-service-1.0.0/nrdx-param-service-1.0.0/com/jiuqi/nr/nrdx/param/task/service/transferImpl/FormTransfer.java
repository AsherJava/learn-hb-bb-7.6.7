/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.conditionalstyle.controller.impl.DesignConditionalStyleController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.exception.DefinitonException
 *  com.jiuqi.nr.definition.facade.AnalysisFormParamDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 *  com.jiuqi.nr.definition.internal.service.DesignFormFoldingService
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 *  com.jiuqi.nr.filterTemplate.service.IFilterTemplateService
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisFormDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.ConditionalStyleDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.DataLinkMappingDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.EntityViewDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.FormFoldingDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.FormStyleDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.RegionTabSettingDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupLinkDTO
 *  com.jiuqi.nr.param.transfer.definition.service.AttachmentService
 *  com.jiuqi.nr.tds.Costs
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.nrdx.param.task.service.transferImpl;

import com.csvreader.CsvWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.conditionalstyle.controller.impl.DesignConditionalStyleController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.DefinitonException;
import com.jiuqi.nr.definition.facade.AnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.service.DesignFormFoldingService;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.dto.form.NrdxDataLinkDTO;
import com.jiuqi.nr.nrdx.param.task.dto.form.NrdxFormDTO;
import com.jiuqi.nr.nrdx.param.task.dto.form.NrdxFormGroupDTO;
import com.jiuqi.nr.nrdx.param.task.dto.form.NrdxFormReginDTO;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.nrdx.param.task.utils.UtilsService;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisFormDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.ConditionalStyleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.DataLinkMappingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.EntityViewDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormFoldingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormStyleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.RegionTabSettingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupLinkDTO;
import com.jiuqi.nr.param.transfer.definition.service.AttachmentService;
import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FormTransfer
extends AbstractParamTransfer {
    private static final Logger logger = LoggerFactory.getLogger(FormTransfer.class);
    public static final List<String> FORM_CSV_HEADER = Arrays.asList("key", "formCode", "title", "subTitle", "description", "serialNumber", "gather", "measureUnit", "secretLevel", "formType", "formCondition", "order", "version", "ownerLevelAndId", "updateTime", "formExtension", "masterEntitiesKey", "readOnlyCondition", "quoteType", "analysisForm", "ledgerForm", "fillInAutomaticallyDue", "formScheme", "updateUser", "fillGuide", "formStyles", "dataLinkMappings", "conditionalStyles", "formFoldings", "attachmentRules", "formGroupLinks", "analysisFormDTO", "desParamLanguageDTO");
    public static final List<String> FORM_REGION_CSV_HEADER = Arrays.asList("key", "code", "title", "formKey", "regionLeft", "regionRight", "regionTop", "regionBottom", "regionKind", "inputOrderFieldKey", "sortFieldsList", "rowsInFloatRegion", "gatherFields", "gatherSetting", "regionSettingKey", "filterCondition", "order", "version", "ownerLevelAndId", "updateTime", "maxRowCount", "canDeleteRow", "canInsertRow", "pageSize", "readOnlyCondition", "showGatherDetailRows", "showGatherDetailRowByOne", "showGatherSummaryRow", "allowDuplicateKey", "showAddress", "canFold", "hideZeroGatherFields", "regionSetting", "regionEnterNext", "levelSetting");
    public static final List<String> DATA_LINK_CSV_HEADER = Arrays.asList("key", "title", "regionKey", "linkExpression", "posX", "posY", "colNum", "rowNum", "editMode", "displayMode", "dataValidation", "captionFieldsString", "dropDownFieldsString", "order", "version", "ownerLevelAndId", "updateTime", "allowUndefinedCode", "allowNullAble", "allowNotLeafNodeRefer", "uniqueCode", "enumShowFullPath", "type", "enumTitleField", "enumLinkage", "enumCount", "formatProperties", "enumPos", "enumLinkageStatus", "entityViewID", "filterExpression", "ignorePermissions", "measureUnit", "entityView");
    private final ObjectMapper objectMapper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    UtilsService utilsService;
    @Autowired
    private DesignBigDataTableDao bigDataDao;
    @Autowired
    private DesignConditionalStyleController conditionalStyleController;
    @Autowired
    private DesignFormFoldingService formFoldingService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private IFilterTemplateService filterTemplateService;

    public FormTransfer() {
        logger.info("FormTransfer \u521d\u59cb\u5316objectMapper");
        this.objectMapper = new ObjectMapper();
        DefinitionModelTransfer.moduleRegister((ObjectMapper)this.objectMapper);
    }

    public void exportModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        String path = context.getPath();
        Costs.createPathIfNotExists((Path)new File(path).toPath());
        List<String> formKeys = param.getParamKeys();
        NrdxFormGroupDTO nrdxFormGroupDTO = new NrdxFormGroupDTO();
        this.exportFormCSV(formKeys, path);
        this.exportFormRegionCSV(formKeys, path);
        this.exportDataLinkCSV(formKeys, path);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportFormCSV(List<String> formKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + "FORM.CSV");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[FORM_CSV_HEADER.size()];
            for (int i = 0; i < FORM_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = FORM_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            List formDefines = this.runTimeViewController.queryFormsById(formKeys);
            for (FormDefine formDefine : formDefines) {
                List list;
                List conditionalStyles;
                List linkMappings;
                if (formDefine == null) continue;
                NrdxFormDTO nrdxFormDTO = NrdxFormDTO.valueOf(formDefine);
                if (formDefine.isAnalysisForm()) {
                    try {
                        AnalysisFormParamDefine define = this.runTimeViewController.queryAnalysisFormParamDefine(formDefine.getKey());
                        nrdxFormDTO.setAnalysisFormDTO(new ArrayList<AnalysisFormDTO>());
                        AnalysisFormDTO analysisFormDTO = new AnalysisFormDTO();
                        AnalysisFormParamDefineImpl impl = new AnalysisFormParamDefineImpl();
                        BeanUtils.copyProperties(define, impl);
                        analysisFormDTO.setDefine(impl);
                        nrdxFormDTO.getAnalysisFormDTO().add(analysisFormDTO);
                    }
                    catch (Exception define) {
                        // empty catch block
                    }
                }
                byte[] fillGuide = this.designTimeViewController.getFillGuide(formDefine.getKey());
                nrdxFormDTO.setFillGuide(fillGuide);
                List groupLinks = this.designTimeViewController.getFormGroupLinksByFormId(formDefine.getKey());
                if (!CollectionUtils.isEmpty(groupLinks)) {
                    List<FormGroupLinkDTO> links = groupLinks.stream().map(FormGroupLinkDTO::valueOf).collect(Collectors.toList());
                    nrdxFormDTO.setFormGroupLinks(links);
                }
                List<Object> bigData = new ArrayList();
                try {
                    bigData.addAll(this.bigDataDao.queryigDataDefines(formDefine.getKey(), "FORM_DATA"));
                    if (formDefine.getFormType() == FormType.FORM_TYPE_SURVEY) {
                        bigData.addAll(this.bigDataDao.queryigDataDefines(formDefine.getKey(), "BIG_SURVEY_DATA"));
                    }
                }
                catch (Exception e) {
                    throw new DefinitonException("\u83b7\u53d6\u8868\u6837\u5931\u8d25", (Throwable)e);
                }
                bigData = bigData.stream().filter(a -> a.getData() != null).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(bigData)) {
                    ArrayList<FormStyleDTO> list2 = new ArrayList<FormStyleDTO>();
                    nrdxFormDTO.setFormStyles(list2);
                    for (DesignBigDataTable designBigDataTable : bigData) {
                        FormStyleDTO formStyle = new FormStyleDTO();
                        formStyle.setCode(designBigDataTable.getCode());
                        if (designBigDataTable.getCode().equals("FORM_DATA")) {
                            Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])designBigDataTable.getData());
                            formStyle.setGrid2Data(grid2Data);
                        } else {
                            formStyle.setJsonData(DesignFormDefineBigDataUtil.bytesToString((byte[])designBigDataTable.getData()));
                        }
                        formStyle.setFormKey(formDefine.getKey());
                        formStyle.setLanguageType(designBigDataTable.getLang());
                        list2.add(formStyle);
                    }
                }
                if (!CollectionUtils.isEmpty(linkMappings = this.runTimeViewController.queryDataLinkMapping(formDefine.getKey()))) {
                    ArrayList<DataLinkMappingDTO> dataLinkMappingDTOS = new ArrayList<DataLinkMappingDTO>();
                    for (DataLinkMappingDefine linkMapping : linkMappings) {
                        DataLinkMappingDTO linkMappingDTO = new DataLinkMappingDTO();
                        linkMappingDTO.setId(linkMapping.getId());
                        linkMappingDTO.setFormKey(linkMapping.getFormKey());
                        linkMappingDTO.setLeftDataLinkKey(linkMapping.getLeftDataLinkKey());
                        linkMappingDTO.setRightDataLinkKey(linkMapping.getRightDataLinkKey());
                        dataLinkMappingDTOS.add(linkMappingDTO);
                    }
                    nrdxFormDTO.setDataLinkMappings(dataLinkMappingDTOS);
                }
                if (!CollectionUtils.isEmpty(conditionalStyles = this.conditionalStyleController.getAllCSInForm(formDefine.getKey()))) {
                    nrdxFormDTO.setConditionalStyles(conditionalStyles.stream().map(ConditionalStyleDTO::valueOf).collect(Collectors.toList()));
                }
                if (!CollectionUtils.isEmpty(list = this.formFoldingService.getByFormKey(formDefine.getKey()))) {
                    nrdxFormDTO.setFormFoldings(list.stream().map(FormFoldingDTO::valueOf).collect(Collectors.toList()));
                }
                List attachments = this.attachmentService.getByFormKey(formDefine.getKey());
                nrdxFormDTO.setAttachmentRules(attachments);
                DesParamLanguageDTO desParamLanguage = this.utilsService.getDesParamLanguage(formDefine.getKey(), LanguageResourceType.FORMTITLE, "2");
                if (desParamLanguage != null) {
                    nrdxFormDTO.setDesParamLanguageDTO(desParamLanguage);
                }
                this.exportFormDTO(csvWriter, nrdxFormDTO);
            }
        }
        catch (Exception exception) {
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
    }

    private void exportFormDTO(CsvWriter csvWriter, NrdxFormDTO nrdxFormDTO) throws Exception {
        String[] dataArray = new String[FORM_CSV_HEADER.size()];
        block70: for (int i = 0; i < FORM_CSV_HEADER.size(); ++i) {
            switch (FORM_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxFormDTO.getKey();
                    continue block70;
                }
                case "formCode": {
                    dataArray[i] = nrdxFormDTO.getFormCode();
                    continue block70;
                }
                case "title": {
                    dataArray[i] = nrdxFormDTO.getTitle();
                    continue block70;
                }
                case "subTitle": {
                    dataArray[i] = nrdxFormDTO.getSubTitle();
                    continue block70;
                }
                case "description": {
                    dataArray[i] = nrdxFormDTO.getDescription();
                    continue block70;
                }
                case "serialNumber": {
                    dataArray[i] = nrdxFormDTO.getSerialNumber();
                    continue block70;
                }
                case "gather": {
                    dataArray[i] = String.valueOf(nrdxFormDTO.isGather());
                    continue block70;
                }
                case "measureUnit": {
                    dataArray[i] = nrdxFormDTO.getMeasureUnit();
                    continue block70;
                }
                case "secretLevel": {
                    dataArray[i] = String.valueOf(nrdxFormDTO.getSecretLevel());
                    continue block70;
                }
                case "formType": {
                    dataArray[i] = nrdxFormDTO.getFormType().name();
                    continue block70;
                }
                case "formCondition": {
                    dataArray[i] = nrdxFormDTO.getFormCondition();
                    continue block70;
                }
                case "order": {
                    dataArray[i] = nrdxFormDTO.getOrder();
                    continue block70;
                }
                case "version": {
                    dataArray[i] = nrdxFormDTO.getVersion();
                    continue block70;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxFormDTO.getOwnerLevelAndId();
                    continue block70;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxFormDTO.getUpdateTime());
                    continue block70;
                }
                case "formExtension": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormDTO.getFormExtension());
                    continue block70;
                }
                case "masterEntitiesKey": {
                    dataArray[i] = nrdxFormDTO.getMasterEntitiesKey();
                    continue block70;
                }
                case "readOnlyCondition": {
                    dataArray[i] = nrdxFormDTO.getReadOnlyCondition();
                    continue block70;
                }
                case "quoteType": {
                    dataArray[i] = String.valueOf(nrdxFormDTO.isQuoteType());
                    continue block70;
                }
                case "analysisForm": {
                    dataArray[i] = String.valueOf(nrdxFormDTO.isAnalysisForm());
                    continue block70;
                }
                case "ledgerForm": {
                    dataArray[i] = String.valueOf(nrdxFormDTO.isLedgerForm());
                    continue block70;
                }
                case "fillInAutomaticallyDue": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormDTO.getFillInAutomaticallyDue());
                    continue block70;
                }
                case "formScheme": {
                    dataArray[i] = nrdxFormDTO.getFormScheme();
                    continue block70;
                }
                case "updateUser": {
                    dataArray[i] = nrdxFormDTO.getUpdateUser();
                    continue block70;
                }
                case "fillGuide": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormDTO.getFillGuide());
                    continue block70;
                }
                case "formStyles": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormDTO.getFormStyles());
                    continue block70;
                }
                case "dataLinkMappings": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormDTO.getDataLinkMappings());
                    continue block70;
                }
                case "conditionalStyles": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormDTO.getConditionalStyles());
                    continue block70;
                }
                case "formFoldings": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormDTO.getFormFoldings());
                    continue block70;
                }
                case "attachmentRules": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormDTO.getAttachmentRules());
                    continue block70;
                }
                case "formGroupLinks": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormDTO.getFormGroupLinks());
                    continue block70;
                }
                case "analysisFormDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormDTO.getAnalysisFormDTO());
                    continue block70;
                }
                case "desParamLanguageDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormDTO.getDesParamLanguageDTO());
                    continue block70;
                }
            }
        }
        csvWriter.writeRecord(dataArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportFormRegionCSV(List<String> formKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + "FORM_REGION.CSV");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[FORM_REGION_CSV_HEADER.size()];
            for (int i = 0; i < FORM_REGION_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = FORM_REGION_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String formKey : formKeys) {
                List regionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
                if (CollectionUtils.isEmpty(regionDefines)) continue;
                for (DataRegionDefine regionDefine : regionDefines) {
                    DesignRegionSettingDefine regionSetting = this.designTimeViewController.getRegionSetting(regionDefine.getRegionSettingKey());
                    NrdxFormReginDTO nrdxFormReginDTO = NrdxFormReginDTO.valueOf(regionDefine, (RegionSettingDefine)regionSetting);
                    if (null != regionSetting) {
                        try {
                            List bigDataTables = this.bigDataDao.queryigDataDefines(regionSetting.getKey(), "REGION_TAB");
                            ArrayList<RegionTabSettingDTO> containLangs = new ArrayList<RegionTabSettingDTO>();
                            if (null != bigDataTables) {
                                for (DesignBigDataTable bigDataTable : bigDataTables) {
                                    if (null == bigDataTable.getData()) continue;
                                    List tabSettingDefines = RegionTabSettingData.bytesToRegionTabSettingData((byte[])bigDataTable.getData());
                                    for (DesignRegionTabSettingDefine tabSettingDefine : tabSettingDefines) {
                                        RegionTabSettingDTO regionTabSettingDTO = RegionTabSettingDTO.valueOf((RegionTabSettingDefine)tabSettingDefine);
                                        regionTabSettingDTO.setLanguageType(bigDataTable.getLang());
                                        containLangs.add(regionTabSettingDTO);
                                    }
                                }
                            }
                            if (containLangs.size() != 0) {
                                nrdxFormReginDTO.getRegionSetting().setRegionTabSettings(containLangs);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    this.exportFormRegionCSV(csvWriter, nrdxFormReginDTO);
                }
            }
        }
        catch (Exception exception) {
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
    }

    private void exportFormRegionCSV(CsvWriter csvWriter, NrdxFormReginDTO nrdxFormReginDTO) throws Exception {
        String[] dataArray = new String[FORM_REGION_CSV_HEADER.size()];
        block74: for (int i = 0; i < FORM_REGION_CSV_HEADER.size(); ++i) {
            switch (FORM_REGION_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxFormReginDTO.getKey();
                    continue block74;
                }
                case "code": {
                    dataArray[i] = nrdxFormReginDTO.getCode();
                    continue block74;
                }
                case "title": {
                    dataArray[i] = nrdxFormReginDTO.getTitle();
                    continue block74;
                }
                case "formKey": {
                    dataArray[i] = nrdxFormReginDTO.getFormKey();
                    continue block74;
                }
                case "regionLeft": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.getRegionLeft());
                    continue block74;
                }
                case "regionRight": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.getRegionRight());
                    continue block74;
                }
                case "regionTop": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.getRegionTop());
                    continue block74;
                }
                case "regionBottom": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.getRegionBottom());
                    continue block74;
                }
                case "regionKind": {
                    dataArray[i] = nrdxFormReginDTO.getRegionKind().name();
                    continue block74;
                }
                case "inputOrderFieldKey": {
                    dataArray[i] = nrdxFormReginDTO.getInputOrderFieldKey();
                    continue block74;
                }
                case "sortFieldsList": {
                    dataArray[i] = nrdxFormReginDTO.getSortFieldsList();
                    continue block74;
                }
                case "rowsInFloatRegion": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.getRowsInFloatRegion());
                    continue block74;
                }
                case "gatherFields": {
                    dataArray[i] = nrdxFormReginDTO.getGatherFields();
                    continue block74;
                }
                case "gatherSetting": {
                    dataArray[i] = nrdxFormReginDTO.getGatherSetting();
                    continue block74;
                }
                case "regionSettingKey": {
                    dataArray[i] = nrdxFormReginDTO.getRegionSettingKey();
                    continue block74;
                }
                case "filterCondition": {
                    dataArray[i] = nrdxFormReginDTO.getFilterCondition();
                    continue block74;
                }
                case "order": {
                    dataArray[i] = nrdxFormReginDTO.getOrder();
                    continue block74;
                }
                case "version": {
                    dataArray[i] = nrdxFormReginDTO.getVersion();
                    continue block74;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxFormReginDTO.getOwnerLevelAndId();
                    continue block74;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxFormReginDTO.getUpdateTime());
                    continue block74;
                }
                case "maxRowCount": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.getMaxRowCount());
                    continue block74;
                }
                case "canDeleteRow": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.isCanDeleteRow());
                    continue block74;
                }
                case "canInsertRow": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.isCanInsertRow());
                    continue block74;
                }
                case "pageSize": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.getPageSize());
                    continue block74;
                }
                case "readOnlyCondition": {
                    dataArray[i] = nrdxFormReginDTO.getReadOnlyCondition();
                    continue block74;
                }
                case "showGatherDetailRows": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.isShowGatherDetailRows());
                    continue block74;
                }
                case "showGatherDetailRowByOne": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.isShowGatherDetailRowByOne());
                    continue block74;
                }
                case "showGatherSummaryRow": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.isShowGatherSummaryRow());
                    continue block74;
                }
                case "allowDuplicateKey": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.isAllowDuplicateKey());
                    continue block74;
                }
                case "showAddress": {
                    dataArray[i] = nrdxFormReginDTO.getShowAddress();
                    continue block74;
                }
                case "canFold": {
                    dataArray[i] = String.valueOf(nrdxFormReginDTO.isCanFold());
                    continue block74;
                }
                case "hideZeroGatherFields": {
                    dataArray[i] = nrdxFormReginDTO.getHideZeroGatherFields();
                    continue block74;
                }
                case "regionSetting": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormReginDTO.getRegionSetting());
                    continue block74;
                }
                case "regionEnterNext": {
                    dataArray[i] = nrdxFormReginDTO.getRegionEnterNext().name();
                    continue block74;
                }
                case "levelSetting": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormReginDTO.getLevelSetting());
                    continue block74;
                }
            }
        }
        csvWriter.writeRecord(dataArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportDataLinkCSV(List<String> formKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + "DATA_LINK.CSV");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[DATA_LINK_CSV_HEADER.size()];
            for (int i = 0; i < DATA_LINK_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = DATA_LINK_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String formKey : formKeys) {
                List dataLinks = this.runTimeViewController.getAllLinksInForm(formKey);
                if (CollectionUtils.isEmpty(dataLinks)) continue;
                for (DataLinkDefine dataLink : dataLinks) {
                    FilterTemplateDTO filterTemplateDTO;
                    FilterTemplateDO filterTemplateDO = this.getEntityViewBusiness(dataLink);
                    NrdxDataLinkDTO nrdxDataLinkDTO = NrdxDataLinkDTO.valueOf(dataLink);
                    if (filterTemplateDO != null) {
                        nrdxDataLinkDTO.setFilterExpression(filterTemplateDO.getFilterContent());
                        nrdxDataLinkDTO.setEntityView(EntityViewDTO.valueOf((FilterTemplateDO)filterTemplateDO));
                    }
                    if (nrdxDataLinkDTO.getEntityViewID() != null && (filterTemplateDTO = this.filterTemplateService.getFilterTemplate(nrdxDataLinkDTO.getEntityViewID())) != null) {
                        nrdxDataLinkDTO.setFilterExpression(filterTemplateDTO.getFilterContent());
                    }
                    this.exportDataLinkCSV(csvWriter, nrdxDataLinkDTO);
                }
            }
        }
        catch (Exception exception) {
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
    }

    public void exportDataLinkCSV(CsvWriter csvWriter, NrdxDataLinkDTO nrdxDataLinkDTO) throws Exception {
        String[] dataArray = new String[DATA_LINK_CSV_HEADER.size()];
        block72: for (int i = 0; i < DATA_LINK_CSV_HEADER.size(); ++i) {
            switch (DATA_LINK_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxDataLinkDTO.getKey();
                    continue block72;
                }
                case "title": {
                    dataArray[i] = nrdxDataLinkDTO.getTitle();
                    continue block72;
                }
                case "regionKey": {
                    dataArray[i] = nrdxDataLinkDTO.getRegionKey();
                    continue block72;
                }
                case "linkExpression": {
                    dataArray[i] = nrdxDataLinkDTO.getLinkExpression();
                    continue block72;
                }
                case "posX": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.getPosX());
                    continue block72;
                }
                case "posY": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.getPosY());
                    continue block72;
                }
                case "colNum": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.getColNum());
                    continue block72;
                }
                case "rowNum": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.getRowNum());
                    continue block72;
                }
                case "editMode": {
                    dataArray[i] = nrdxDataLinkDTO.getEditMode().name();
                    continue block72;
                }
                case "displayMode": {
                    dataArray[i] = nrdxDataLinkDTO.getDisplayMode().name();
                    continue block72;
                }
                case "dataValidation": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxDataLinkDTO.getDataValidation());
                    continue block72;
                }
                case "captionFieldsString": {
                    dataArray[i] = nrdxDataLinkDTO.getCaptionFieldsString();
                    continue block72;
                }
                case "dropDownFieldsString": {
                    dataArray[i] = nrdxDataLinkDTO.getDropDownFieldsString();
                    continue block72;
                }
                case "order": {
                    dataArray[i] = nrdxDataLinkDTO.getOrder();
                    continue block72;
                }
                case "version": {
                    dataArray[i] = nrdxDataLinkDTO.getVersion();
                    continue block72;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxDataLinkDTO.getOwnerLevelAndId();
                    continue block72;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxDataLinkDTO.getUpdateTime());
                    continue block72;
                }
                case "allowUndefinedCode": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.getAllowUndefinedCode());
                    continue block72;
                }
                case "allowNullAble": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.getAllowNullAble());
                    continue block72;
                }
                case "allowNotLeafNodeRefer": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.isAllowNotLeafNodeRefer());
                    continue block72;
                }
                case "uniqueCode": {
                    dataArray[i] = nrdxDataLinkDTO.getUniqueCode();
                    continue block72;
                }
                case "enumShowFullPath": {
                    dataArray[i] = nrdxDataLinkDTO.getEnumShowFullPath();
                    continue block72;
                }
                case "type": {
                    dataArray[i] = nrdxDataLinkDTO.getType().name();
                    continue block72;
                }
                case "enumTitleField": {
                    dataArray[i] = nrdxDataLinkDTO.getEnumTitleField();
                    continue block72;
                }
                case "enumLinkage": {
                    dataArray[i] = nrdxDataLinkDTO.getEnumLinkage();
                    continue block72;
                }
                case "enumCount": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.getEnumCount());
                    continue block72;
                }
                case "formatProperties": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxDataLinkDTO.getFormatProperties());
                    continue block72;
                }
                case "enumPos": {
                    dataArray[i] = nrdxDataLinkDTO.getEnumPos();
                    continue block72;
                }
                case "enumLinkageStatus": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.isEnumLinkageStatus());
                    continue block72;
                }
                case "entityViewID": {
                    dataArray[i] = nrdxDataLinkDTO.getEntityViewID();
                    continue block72;
                }
                case "filterExpression": {
                    dataArray[i] = nrdxDataLinkDTO.getFilterExpression();
                    continue block72;
                }
                case "ignorePermissions": {
                    dataArray[i] = String.valueOf(nrdxDataLinkDTO.isIgnorePermissions());
                    continue block72;
                }
                case "measureUnit": {
                    dataArray[i] = nrdxDataLinkDTO.getMeasureUnit();
                    continue block72;
                }
                case "entityViews": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxDataLinkDTO.getEntityView());
                    continue block72;
                }
            }
        }
        csvWriter.writeRecord(dataArray);
    }

    public FilterTemplateDO getEntityViewBusiness(DataLinkDefine dataLinkDefine) {
        FilterTemplateDTO entityViewDefine;
        if (dataLinkDefine.getFilterTemplate() != null && (entityViewDefine = this.filterTemplateService.getFilterTemplate(dataLinkDefine.getFilterTemplate())) != null) {
            return new FilterTemplateDO(entityViewDefine);
        }
        return null;
    }

    public void importModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
    }

    public void preAnalysis(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
    }

    public DepResource depResource(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        return null;
    }

    @Override
    public List<ITransferModel> depModel(String s) {
        return null;
    }

    @Override
    public String code() {
        return NrdxParamNodeType.FORM.getCode();
    }

    @Override
    public String version() {
        return null;
    }
}

