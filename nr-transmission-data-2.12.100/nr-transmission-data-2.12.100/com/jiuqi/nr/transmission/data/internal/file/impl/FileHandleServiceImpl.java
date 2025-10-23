/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.service.FormulaMappingService
 *  com.jiuqi.nr.mapping2.service.PeriodMappingService
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 *  com.jiuqi.nvwa.mapping.service.IBaseDataMappingService
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.nvwa.mapping.service.IOrgMappingService
 */
package com.jiuqi.nr.transmission.data.internal.file.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import com.jiuqi.nr.transmission.data.common.MappingType;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.TransmissionExportType;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import com.jiuqi.nr.transmission.data.dto.AnalysisDTO;
import com.jiuqi.nr.transmission.data.dto.AnalysisParam;
import com.jiuqi.nr.transmission.data.dto.SrcParam.SrcParamDefinitionObj;
import com.jiuqi.nr.transmission.data.dto.SrcParamDTO;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.dto.VersionDTO;
import com.jiuqi.nr.transmission.data.exception.DataExportException;
import com.jiuqi.nr.transmission.data.internal.file.FileHandleService;
import com.jiuqi.nr.transmission.data.intf.ContextExpendParam;
import com.jiuqi.nr.transmission.data.intf.DataImportMessage;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.DefaultTransmissionContext;
import com.jiuqi.nr.transmission.data.intf.DimInfoParam;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.intf.ExecuteParam;
import com.jiuqi.nr.transmission.data.intf.ExpotrFilterResult;
import com.jiuqi.nr.transmission.data.intf.ImportParam;
import com.jiuqi.nr.transmission.data.intf.MappingImportParam;
import com.jiuqi.nr.transmission.data.intf.MappingParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionZBMapping;
import com.jiuqi.nr.transmission.data.intf.UserInfoParam;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.nr.transmission.data.service.IFileAnalysisService;
import com.jiuqi.nr.transmission.data.service.IImportAfterService;
import com.jiuqi.nr.transmission.data.service.IImportBeforeService;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;
import com.jiuqi.nr.transmission.data.vo.TransmissionDataGatherVO;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import com.jiuqi.nvwa.mapping.service.IBaseDataMappingService;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.nvwa.mapping.service.IOrgMappingService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FileHandleServiceImpl
implements FileHandleService {
    private static final Logger logger = LoggerFactory.getLogger(FileHandleServiceImpl.class);
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private ISyncHistoryService syncHistoryService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private IReportParamService reportParamService;
    @Autowired
    private IFileAnalysisService fileAnalysisService;
    private List<IImportBeforeService> importBeforeServiceList;
    private List<IImportAfterService> importAfterServiceList;
    private List<ITransmissionDataGather> gatherList;
    @Autowired(required=false)
    private MultistageUnitReplace multistageUnitReplaceImpl;
    @Autowired(required=false)
    private IMappingSchemeService mappingSchemeService;
    @Autowired
    private FormulaMappingService formulaMappingService;
    @Autowired
    private ZBMappingService zBMappingService;
    @Autowired
    private PeriodMappingService periodMappingService;
    @Autowired
    private IOrgMappingService orgMappingService;
    @Autowired
    private IBaseDataMappingService baseDataMappingService;
    @Value(value="${com.jiuqi.nr.transmission.TransmissionExportType:FIRST_VERSION}")
    private TransmissionExportType conTransmissionExportType;
    @Value(value="${com.jiuqi.nr.transmission.import.HSHD:false}")
    private boolean importHSHD;
    @Value(value="${com.jiuqi.nr.transmission.import.BWZXJCCCSM:false}")
    private boolean importBWZXJCCCSM;
    @Value(value="${com.jiuqi.nr.transmission.import.PZ:false}")
    private boolean importPZ;

    @Autowired
    private void setImportBeforeServiceList(List<IImportBeforeService> importBeforeServiceList) {
        this.importBeforeServiceList = importBeforeServiceList;
        this.importBeforeServiceList.sort(Comparator.comparing(IImportBeforeService::getOrder));
    }

    @Autowired
    private void setImportAfterList(List<IImportAfterService> importAfterServiceList) {
        this.importAfterServiceList = importAfterServiceList;
        this.importAfterServiceList.sort(Comparator.comparing(IImportAfterService::getOrder));
    }

    @Autowired
    private void setGatherList(List<ITransmissionDataGather> gatherList) {
        this.gatherList = gatherList;
        this.gatherList.sort(Comparator.comparing(ITransmissionDataGather::getOrder));
    }

    private MappingImportParam getImportMapping(AnalysisDTO analysisDTO) throws Exception {
        MappingImportParam mappingParam = new MappingImportParam();
        SyncSchemeParamDTO syncSchemeParamDTO = analysisDTO.getSyncSchemeParamDTO();
        String mappingSchemeKey = syncSchemeParamDTO.getMappingSchemeKey();
        MappingScheme mappingScheme = this.mappingSchemeService.getSchemeByKey(mappingSchemeKey);
        if (this.mappingSchemeService == null) {
            throw new RuntimeException("\u6620\u5c04\u5f02\u5e38");
        }
        if (mappingScheme == null) {
            throw new RuntimeException("\u591a\u7ea7\u90e8\u7f72\u5bfc\u5165\uff0ckey\u4e3a" + mappingSchemeKey + " \u7684\u6620\u5c04\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        String message = "\u591a\u7ea7\u90e8\u7f72\u5bfc\u5165\u6240\u9009\u6620\u5c04\u65b9\u6848\u4e3a" + mappingScheme.getTitle();
        analysisDTO.setMessage(message);
        logger.info(message);
        List allePeriodMappings = this.periodMappingService.findByMS(mappingSchemeKey);
        allePeriodMappings = allePeriodMappings.stream().filter(a -> StringUtils.hasText(a.getMapping())).collect(Collectors.toList());
        HashMap<String, PeriodMapping> periodToMappings = CollectionUtils.isEmpty(allePeriodMappings) ? new HashMap<String, PeriodMapping>() : allePeriodMappings.stream().collect(Collectors.toMap(PeriodMapping::getMapping, a -> a, (k1, k2) -> k1));
        mappingParam.setPeriodToMappings(periodToMappings);
        String periodValue = syncSchemeParamDTO.getPeriodValue();
        PeriodMapping periodMapping = (PeriodMapping)periodToMappings.get(periodValue);
        if (periodMapping != null) {
            syncSchemeParamDTO.setPeriodValue(periodMapping.getPeriod());
        }
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(syncSchemeParamDTO.getPeriodValue(), syncSchemeParamDTO.getTask());
        syncSchemeParamDTO.setFormSchemeKey(schemePeriodLinkDefine.getSchemeKey());
        List allOrgMappings = this.orgMappingService.getOrgMappingByMS(mappingSchemeKey);
        allOrgMappings = allOrgMappings.stream().filter(a -> StringUtils.hasText(a.getMapping())).collect(Collectors.toList());
        HashMap<String, OrgMapping> orgToMappings = CollectionUtils.isEmpty(allOrgMappings) ? new HashMap<String, OrgMapping>() : allOrgMappings.stream().collect(Collectors.toMap(OrgMapping::getMapping, a -> a, (k1, k2) -> k1));
        mappingParam.setOrgToMappings(orgToMappings);
        List allBaseDataMappings = this.baseDataMappingService.getBaseDataMapping(mappingSchemeKey);
        allBaseDataMappings = allBaseDataMappings.stream().filter(a -> StringUtils.hasText(a.getMappingCode())).collect(Collectors.toList());
        HashMap<String, BaseDataMapping> srcBaseToMappings = CollectionUtils.isEmpty(allBaseDataMappings) ? new HashMap<String, BaseDataMapping>() : allBaseDataMappings.stream().collect(Collectors.toMap(BaseDataMapping::getMappingCode, a -> a, (k1, k2) -> k1));
        mappingParam.setBaseToMappings(srcBaseToMappings);
        HashMap desBaseToMappings = CollectionUtils.isEmpty(allBaseDataMappings) ? new HashMap() : allBaseDataMappings.stream().collect(Collectors.toMap(BaseDataMapping::getBaseDataCode, a -> a, (k1, k2) -> k1));
        List allBaseDataItemMappings = this.baseDataMappingService.getAllBaseDataItem(mappingSchemeKey);
        allBaseDataItemMappings = allBaseDataItemMappings.stream().filter(a -> StringUtils.hasText(a.getMappingCode())).collect(Collectors.toList());
        HashMap baseToDataDataToMappings = CollectionUtils.isEmpty(allBaseDataItemMappings) ? new HashMap() : allBaseDataItemMappings.stream().collect(Collectors.groupingBy(BaseDataItemMapping::getBaseDataCode, Collectors.toMap(BaseDataItemMapping::getMappingCode, a -> a, (k1, k2) -> k1)));
        HashMap<String, Map<String, BaseDataItemMapping>> srcBaseToDataDataToMappings = new HashMap<String, Map<String, BaseDataItemMapping>>();
        for (Map.Entry stringMapEntry : baseToDataDataToMappings.entrySet()) {
            BaseDataMapping baseDataMapping = (BaseDataMapping)desBaseToMappings.get(stringMapEntry.getKey());
            String srcMappingCode = StringUtils.hasText(baseDataMapping.getMappingCode()) ? baseDataMapping.getMappingCode() : (String)stringMapEntry.getKey();
            srcBaseToDataDataToMappings.put(srcMappingCode, (Map<String, BaseDataItemMapping>)stringMapEntry.getValue());
        }
        mappingParam.setBaseToDataDataToMappings(srcBaseToDataDataToMappings);
        SrcParamDTO srcParamDTO = analysisDTO.getSrcParamDTO();
        List<SrcParamDefinitionObj> formulaSchemeDefinitions = srcParamDTO.getFormulaSchemeDefinitions();
        Map<String, String> srcFormulaSchemeTitleToKey = formulaSchemeDefinitions.stream().collect(Collectors.toMap(SrcParamDefinitionObj::getTitle, SrcParamDefinitionObj::getKey, (k1, k2) -> k1));
        mappingParam.setFormulaSchemeTitleToKey(srcFormulaSchemeTitleToKey);
        List allFormulaMappings = this.formulaMappingService.findByMS(mappingSchemeKey);
        allFormulaMappings = allFormulaMappings.stream().filter(a -> StringUtils.hasText(a.getmFormulaCode())).collect(Collectors.toList());
        HashMap formulaSchemeToFormToFormulaMappings = CollectionUtils.isEmpty(allFormulaMappings) ? new HashMap() : allFormulaMappings.stream().collect(Collectors.groupingBy(FormulaMapping::getFormulaScheme, Collectors.groupingBy(FormulaMapping::getFormCode)));
        Map<String, String> formulaSchemeKeyToTitle = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(syncSchemeParamDTO.getFormSchemeKey()).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, IBaseMetaItem::getTitle, (k1, k2) -> k1));
        HashMap<String, Map<String, List<FormulaMapping>>> srcFormulaSchemeToFormToFormulaMappings = new HashMap<String, Map<String, List<FormulaMapping>>>();
        for (Map.Entry stringMapEntry : formulaSchemeToFormToFormulaMappings.entrySet()) {
            String formulaSchemeKey = (String)stringMapEntry.getKey();
            String formulaSchemeTitle = formulaSchemeKeyToTitle.get(formulaSchemeKey);
            String srcFormulaSchemeKey = srcFormulaSchemeTitleToKey.get(formulaSchemeTitle);
            if (!StringUtils.hasLength(srcFormulaSchemeKey)) continue;
            srcFormulaSchemeToFormToFormulaMappings.put(srcFormulaSchemeKey, (Map<String, List<FormulaMapping>>)stringMapEntry.getValue());
        }
        mappingParam.setFormulaSchemeToFormToFormulaMappings(srcFormulaSchemeToFormToFormulaMappings);
        List allZBMappings = this.zBMappingService.findByMS(mappingSchemeKey);
        allZBMappings = allZBMappings.stream().filter(a -> StringUtils.hasText(a.getMapping())).collect(Collectors.toList());
        ArrayList<TransmissionZBMapping> allTransmissionZBMappings = new ArrayList<TransmissionZBMapping>(allZBMappings.size());
        for (ZBMapping zBMapping : allZBMappings) {
            TransmissionZBMapping transmissionZBMapping = TransmissionZBMapping.getTransmissionZBMappingImport(zBMapping);
            allTransmissionZBMappings.add(transmissionZBMapping);
        }
        HashMap<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings = CollectionUtils.isEmpty(allTransmissionZBMappings) ? new HashMap<String, Map<String, List<TransmissionZBMapping>>>() : allTransmissionZBMappings.stream().collect(Collectors.groupingBy(TransmissionZBMapping::getForm, Collectors.groupingBy(TransmissionZBMapping::getTable)));
        mappingParam.setFormToDataTableToZBMappings(formToDataTableToZBMappings);
        if (MappingType.IMPORT_MAPPING == analysisDTO.getMappingType()) {
            ArrayList<String> srcEntities = new ArrayList<String>(Arrays.asList(syncSchemeParamDTO.getEntity().split(";")));
            ArrayList<String> desEntities = new ArrayList<String>();
            for (String srcEntity : srcEntities) {
                desEntities.add(mappingParam.getOrgToMappings(srcEntity));
            }
            syncSchemeParamDTO.setEntity(String.join((CharSequence)";", desEntities));
            if (StringUtils.hasText(syncSchemeParamDTO.getDimKeys()) && StringUtils.hasText(syncSchemeParamDTO.getDimValues())) {
                List<String> srcSelectDimCodes = Arrays.asList(syncSchemeParamDTO.getDimKeys().split(";"));
                List<String> srcSelectDimValues = Arrays.asList(syncSchemeParamDTO.getDimValues().split(";"));
                ArrayList<String> desDimCodes = new ArrayList<String>();
                ArrayList<String> desDimValues = new ArrayList<String>();
                for (int i = 0; i < srcSelectDimCodes.size(); ++i) {
                    BaseDataMapping baseDataMapping;
                    String srcDimValueStrings = srcSelectDimValues.get(i);
                    String srcDimCode = srcSelectDimCodes.get(i);
                    ArrayList<String> thisDimValues = new ArrayList<String>();
                    String desDimCode = "";
                    if (StringUtils.hasText(srcDimValueStrings)) {
                        List<String> srcDimValues = Arrays.asList(srcDimValueStrings.split(","));
                        Map stringBaseDataItemMappingMap = (Map)srcBaseToDataDataToMappings.get(srcDimCode);
                        for (String srcDimValue : srcDimValues) {
                            BaseDataItemMapping baseDataItemMapping = (BaseDataItemMapping)stringBaseDataItemMappingMap.get(srcDimValue);
                            thisDimValues.add(baseDataItemMapping != null && StringUtils.hasLength(baseDataItemMapping.getBaseDataItemCode()) ? baseDataItemMapping.getBaseDataItemCode() : srcDimValue);
                        }
                    }
                    desDimCode = (baseDataMapping = (BaseDataMapping)srcBaseToMappings.get(srcDimCode)) != null && StringUtils.hasLength(baseDataMapping.getBaseDataCode()) ? baseDataMapping.getBaseDataCode() : srcDimCode;
                    desDimCodes.add(desDimCode);
                    desDimValues.add(String.join((CharSequence)",", thisDimValues));
                }
                syncSchemeParamDTO.setDimKeys(String.join((CharSequence)";", desDimCodes));
                syncSchemeParamDTO.setDimValues(String.join((CharSequence)";", desDimValues));
            }
        }
        return mappingParam;
    }

    @Override
    public DataImportResult fileImport(InputStream is, AsyncTaskMonitor monitor, ImportParam importParam) throws Exception {
        String exportFilterMessage;
        Map<String, ZipUtils.ZipSubFile> zipFiles;
        try {
            zipFiles = ZipUtils.unZip((InputStream)is).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFileName, f -> f));
        }
        catch (Exception e) {
            logger.error("\u591a\u7ea7\u90e8\u7f72\u88c5\u5165\u5931\u8d25\uff0c\u6587\u4ef6\u670d\u52a1\u5668\u4e0b\u8f7d\u6587\u4ef6\u4e3a\u7a7a", e);
            LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0a\u7ea7\u540c\u6b65\u5b8c\u6210\u4fe1\u606f", (String)"\u5931\u8d25\u539f\u56e0\uff1a\u88c5\u5165\u6570\u636e\u6587\u4ef6\u8bfb\u53d6\u5931\u8d25", (int)0);
            throw e;
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u51c6\u5907\u5f00\u59cb\u6570\u636e\u88c5\u5165\uff01");
        String mappingSchemeKey = importParam.getMappingSchemeKey();
        MappingType mappingType = StringUtils.hasLength(mappingSchemeKey) ? MappingType.IMPORT_MAPPING : MappingType.NO_MAPPING;
        monitor.progressAndMessage(0.0, MultilingualLog.fileImportMessage(1));
        AnalysisParam analysisParam = AnalysisParam.getAnalysisParam(importParam, mappingType);
        AnalysisDTO analysisDTO = this.fileAnalysisService.analysisParam(zipFiles, analysisParam);
        SyncSchemeParamDTO param = analysisDTO.getSyncSchemeParamDTO();
        param.setMappingSchemeKey(mappingSchemeKey);
        boolean isNrd = analysisDTO.getIsNrd();
        zipFiles = analysisDTO.getFormDataZipMap();
        ArrayList<String> srcAllDimCodes = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(param.getSrcAllDimCodes())) {
            srcAllDimCodes.addAll(param.getSrcAllDimCodes());
        } else if (StringUtils.hasText(param.getDimKeys())) {
            srcAllDimCodes.addAll(new ArrayList<String>(Arrays.asList(param.getDimKeys().split(";"))));
        }
        MappingImportParam mappingImportParam = null;
        if (StringUtils.hasLength(mappingSchemeKey)) {
            mappingImportParam = this.getImportMapping(analysisDTO);
        }
        boolean hasWorkFlowData = this.doImportHistoryForOffline(importParam, analysisDTO);
        String string = exportFilterMessage = StringUtils.hasText(param.getExportFilterMessage()) ? param.getExportFilterMessage() : "";
        if (StringUtils.hasText(analysisDTO.getMessage())) {
            exportFilterMessage = exportFilterMessage + analysisDTO.getMessage();
        }
        ExecuteParam executeParam = this.buildParam(param);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        String dw = formScheme.getDw();
        String dimensionName = this.entityMetaService.getDimensionName(dw);
        DefaultTransmissionContext defaultTransmissionContext = this.setTransmissionContext(executeParam, dimensionName, exportFilterMessage, monitor, formScheme.getFlowsSetting().getWordFlowType(), importParam);
        defaultTransmissionContext.getContextExpendParam().setNrd(isNrd);
        defaultTransmissionContext.setMappingImportParam(mappingImportParam);
        defaultTransmissionContext.getContextExpendParam().setDw(dw);
        defaultTransmissionContext.getContextExpendParam().setSrcDimCodes(srcAllDimCodes);
        defaultTransmissionContext.getContextExpendParam().setEntityCode(this.entityMetaService.getEntityCode(dw));
        defaultTransmissionContext.getContextExpendParam().setNotImportFMDMEntity(importParam.getNotImportFMDMEntity());
        defaultTransmissionContext.getContextExpendParam().setMode(importParam.getMode());
        defaultTransmissionContext.getContextExpendParam().setHasWorkFlowData(hasWorkFlowData);
        this.setDimensionValueSetWithAllDim(defaultTransmissionContext, this.runTimeViewController.queryTaskDefine(executeParam.getTaskKey()).getDateTime());
        DataImportResult syncResult = defaultTransmissionContext.getDataImportResult();
        if (StringUtils.hasLength(param.getFmdmForm())) {
            String fmdmForm = param.getFmdmForm();
            FormDefine formDefine = this.runTimeViewController.queryFormById(fmdmForm);
            if (formDefine == null) {
                String noFmdmMessage = MultilingualLog.fileImportMessage(3);
                syncResult.getFailForms().computeIfAbsent(fmdmForm, key -> new ArrayList()).add(new DataImportMessage("\u672a\u77e5\u540d\u79f0", "00000000", fmdmForm, noFmdmMessage));
                syncResult.setSyncErrorNumInc();
                defaultTransmissionContext.getLogHelper().appendLog(noFmdmMessage);
            } else {
                defaultTransmissionContext.setFmdmForm(param.getFmdmForm());
            }
        }
        if (param.isHasError()) {
            syncResult.setSyncErrorNumInc();
        }
        for (IImportBeforeService importBeforeService : this.importBeforeServiceList) {
            logger.info(importBeforeService.getTitle());
            importBeforeService.beforeImport(defaultTransmissionContext);
        }
        int i = 0;
        double start = 0.03;
        double size = 0.8 / (double)this.gatherList.size();
        if (syncResult.isResult()) {
            try {
                for (ITransmissionDataGather dataGather : this.gatherList) {
                    monitor.progressAndMessage(start + (double)i * size, "\u5f00\u59cb\u88c5\u5165" + dataGather.getTitle() + "\u3002");
                    TransmissionMonitor subMonitor = new TransmissionMonitor(dataGather.getCode(), this.cacheObjectResourceRemote, monitor, size);
                    defaultTransmissionContext.setTransmissionMonitor(subMonitor);
                    ZipUtils.ZipSubFile subFileData = zipFiles.get(dataGather.getCode() + ".zip");
                    if (subFileData == null) continue;
                    try (InputStream subFileDataIs = subFileData.getSubFileInputStream();){
                        dataGather.dataImport(subFileDataIs, defaultTransmissionContext);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    ++i;
                }
                this.setSuccessForm(defaultTransmissionContext);
                monitor.progressAndMessage(0.83, MultilingualLog.fileImportMessage(2));
                for (IImportAfterService iImportAfterService : this.importAfterServiceList) {
                    iImportAfterService.afterImport(defaultTransmissionContext);
                }
            }
            catch (Exception e) {
                String log = Utils.getLog(defaultTransmissionContext.getLogHelper());
                monitor.finish(log, (Object)syncResult);
                LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0a\u7ea7\u540c\u6b65\u5b8c\u6210\u4fe1\u606f", (String)(this.reportParamService.doLogHelperMessage(param) + "\uff0c\u540c\u6b65\u6267\u884c\u4eba\uff1a" + defaultTransmissionContext.getContextExpendParam().getUserInfoParam().getSyncUserId() + "\u3002\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()), (int)0);
                monitor.error("\u540c\u6b65\u5931\u8d25", (Throwable)e);
                throw new Exception(exportFilterMessage + log + "\r\n" + e.getMessage(), e);
            }
            String messageResult = syncResult.getSyncErrorNum() == 0 ? "\u540c\u6b65\u7ed3\u679c\uff1a\u540c\u6b65\u6210\u529f\u3002" : "\u540c\u6b65\u7ed3\u679c\uff1a\u90e8\u5206\u6210\u529f\u3002";
            logger.info(messageResult);
            String log = this.doAfterUploadMessage(defaultTransmissionContext, param, syncResult.getSyncErrorNum() == 0 ? 3 : 6);
            LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0a\u7ea7\u540c\u6b65\u5b8c\u6210\u4fe1\u606f", (String)log);
            syncResult.setLog(log);
            monitor.finish(log, (Object)syncResult);
            syncResult.setResult(true);
        } else {
            String log = exportFilterMessage + Utils.getLog(defaultTransmissionContext.getLogHelper());
            syncResult.setResult(false);
            syncResult.setLog(log);
            monitor.finish(log, (Object)syncResult);
            LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0a\u7ea7\u540c\u6b65\u5b8c\u6210\u4fe1\u606f", (String)(this.reportParamService.doLogHelperMessage(param) + "\uff0c\u540c\u6b65\u6267\u884c\u4eba\uff1a" + defaultTransmissionContext.getContextExpendParam().getUserInfoParam().getSyncUserId() + "\u3002\u3002\u5931\u8d25\u539f\u56e0\uff1a\u6240\u9009\u5168\u90e8\u5355\u4f4d\u90fd\u4e0a\u62a5\u4e86\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u5bfc\u5165\uff01"), (int)0);
            logger.info("\u6240\u9009\u5355\u4f4d\u5168\u90fd\u4e0a\u62a5\u4e86\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u5bfc\u5165");
            monitor.error("\u540c\u6b65\u5931\u8d25", (Throwable)new Exception(log));
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u88c5\u5165\u6570\u636e\u5b8c\u6210");
        return syncResult;
    }

    private DefaultTransmissionContext setTransmissionContext(IExecuteParam executeParam, String dimensionName, String exportFiltermessage, AsyncTaskMonitor monitor, WorkFlowType wordFlowType, ImportParam importParam) {
        DefaultTransmissionContext defaultTransmissionContext = new DefaultTransmissionContext(null, executeParam);
        ContextExpendParam contextExpendParam = defaultTransmissionContext.getContextExpendParam();
        contextExpendParam.setDimensionName(dimensionName);
        contextExpendParam.setVariables(importParam.getVariables());
        defaultTransmissionContext.getLogHelper().appendLog(exportFiltermessage);
        DimensionValueSet dimensionValueSetWithOutDim = Utils.getDimensionValueSetWithOutDim(executeParam.getDimensionValueSet(), dimensionName);
        List<EntityInfoParam> entityInfoParams = this.reportParamService.getEntityList(dimensionValueSetWithOutDim, executeParam.getFormSchemeKey(), AuthorityType.Read, false);
        Map<String, EntityInfoParam> allUnits = entityInfoParams.stream().collect(Collectors.toMap(EntityInfoParam::getEntityKeyData, a -> a, (k1, k2) -> k1));
        contextExpendParam.setUnits(allUnits);
        DataImportResult syncResult = new DataImportResult();
        defaultTransmissionContext.setDataImportResult(syncResult);
        defaultTransmissionContext.setTransmissionMonitor(monitor);
        UserInfoParam userInfoParam = importParam.getUserInfoParam();
        contextExpendParam.setUserInfoParam(userInfoParam != null ? userInfoParam : new UserInfoParam());
        contextExpendParam.setWorkFlowType(wordFlowType);
        return defaultTransmissionContext;
    }

    private String doAfterUploadMessage(DefaultTransmissionContext defaultTransmissionContext, SyncSchemeParamDTO param, int statu) {
        String log = Utils.getLog(defaultTransmissionContext.getLogHelper());
        SyncHistoryDTO syncHistoryDTO = this.syncHistoryService.get(param.getKey());
        Date endTime = new Date();
        syncHistoryDTO.setEndTime(endTime);
        syncHistoryDTO.setDetail(log);
        syncHistoryDTO.setStatus(statu);
        syncHistoryDTO.setResult(defaultTransmissionContext.getDataImportResult());
        this.syncHistoryService.update(syncHistoryDTO);
        DataImportResult syncResult = defaultTransmissionContext.getDataImportResult();
        syncResult.setLog(log);
        logger.info(log);
        return log;
    }

    private boolean doImportHistoryForOffline(ImportParam importParam, AnalysisDTO analysisDTO) {
        boolean hasWorkFlowData = false;
        SyncSchemeParamDTO param = analysisDTO.getSyncSchemeParamDTO();
        if (importParam.getImportType() == 2 || importParam.getImportType() == 0) {
            if (StringUtils.hasText(importParam.getFileKey())) {
                param.setIsUpload(importParam.getDoUpload());
                param.setAllowForceUpload(importParam.getAllowForceUpload());
                param.setDescription(importParam.getDescription());
            }
            param.setKey(importParam.getExecuteKey());
            this.addImportHistory(param, importParam);
        }
        if (analysisDTO.getFormDataZipMap() != null && analysisDTO.getFormDataZipMap().get("WORKFLOW_TRANSMISSION_DATA.zip") != null) {
            param.setIsUpload(0);
            hasWorkFlowData = true;
        }
        return hasWorkFlowData;
    }

    private void addImportHistory(SyncSchemeParamDTO syncSchemeParamDTO, ImportParam importParam) {
        SyncHistoryDTO syncHistoryDTO = new SyncHistoryDTO();
        syncHistoryDTO.setKey(importParam.getExecuteKey());
        syncHistoryDTO.setStatus(1);
        syncHistoryDTO.setType(1);
        syncHistoryDTO.setSchemeKey(syncSchemeParamDTO.getSchemeKey());
        SyncSchemeParamDO syncSchemeParamDO = new SyncSchemeParamDO(syncSchemeParamDTO);
        syncSchemeParamDO.setKey(importParam.getExecuteKey());
        syncHistoryDTO.setSyncSchemeParamDO(syncSchemeParamDO);
        syncHistoryDTO.setStartTime(new Date());
        syncHistoryDTO.setFileKey(importParam.getFileKey());
        syncHistoryDTO.setUserId("");
        this.syncHistoryService.insert(syncHistoryDTO);
    }

    private void setSuccessForm(ITransmissionContext defaultTransmissionContext) {
        List<String> formkeys = defaultTransmissionContext.getExecuteParam().getForms();
        DataImportResult syncResult = defaultTransmissionContext.getDataImportResult();
        Set<String> errorForm = syncResult.getFailForms().keySet();
        List<String> needImportForm = defaultTransmissionContext.getContextExpendParam().getNeedImportForm();
        ArrayList<String> successImportFormkeys = new ArrayList<String>(needImportForm.size() > 0 ? needImportForm : formkeys);
        if (StringUtils.hasText(defaultTransmissionContext.getFmdmForm())) {
            successImportFormkeys.add(defaultTransmissionContext.getFmdmForm());
        }
        if (!CollectionUtils.isEmpty(errorForm)) {
            successImportFormkeys.removeAll(errorForm);
        }
        List successImportFormDefines = this.runTimeViewController.queryFormsById(successImportFormkeys);
        for (FormDefine successImportFormDefine : successImportFormDefines) {
            syncResult.addSuccessForm(successImportFormDefine.getTitle(), successImportFormDefine.getFormCode(), successImportFormDefine.getKey(), "\u8be5\u62a5\u8868\u4e0b\u7684\u5355\u4f4d\u5bfc\u5165\u6570\u636e\u6210\u529f");
        }
    }

    private MappingParam getExportMapping(SyncSchemeParamDTO schemeParam, String formSchemeKey) {
        MappingParam mappingParam = new MappingParam();
        String mappingSchemeKey = schemeParam.getMappingSchemeKey();
        if (this.mappingSchemeService == null) {
            throw new RuntimeException("\u6620\u5c04\u5f02\u5e38");
        }
        List allRPTFormulaSchemeDefinesByFormScheme = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
        Map<String, String> formulaSchemeTitleToKey = allRPTFormulaSchemeDefinesByFormScheme.stream().collect(Collectors.toMap(IBaseMetaItem::getTitle, IBaseMetaItem::getKey, (k1, k2) -> k1));
        mappingParam.setFormulaSchemeTitleToKey(formulaSchemeTitleToKey);
        List allFormulaMappings = this.formulaMappingService.findByMS(mappingSchemeKey);
        HashMap<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings = CollectionUtils.isEmpty(allFormulaMappings) ? new HashMap<String, Map<String, List<FormulaMapping>>>() : allFormulaMappings.stream().collect(Collectors.groupingBy(FormulaMapping::getFormulaScheme, Collectors.groupingBy(FormulaMapping::getFormCode)));
        mappingParam.setFormulaSchemeToFormToFormulaMappings(formulaSchemeToFormToFormulaMappings);
        List allZBMappings = this.zBMappingService.findByMS(mappingSchemeKey);
        allZBMappings = allZBMappings.stream().filter(a -> StringUtils.hasText(a.getMapping())).collect(Collectors.toList());
        ArrayList<TransmissionZBMapping> allTransmissionZBMappings = new ArrayList<TransmissionZBMapping>();
        for (ZBMapping zBMapping : allZBMappings) {
            TransmissionZBMapping transmissionZBMapping = TransmissionZBMapping.getTransmissionZBMappingExport(zBMapping);
            allTransmissionZBMappings.add(transmissionZBMapping);
        }
        HashMap<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings = CollectionUtils.isEmpty(allTransmissionZBMappings) ? new HashMap<String, Map<String, List<TransmissionZBMapping>>>() : allTransmissionZBMappings.stream().collect(Collectors.groupingBy(TransmissionZBMapping::getForm, Collectors.groupingBy(TransmissionZBMapping::getTable)));
        mappingParam.setFormToDataTableToZBMappings(formToDataTableToZBMappings);
        List allePeriodMappings = this.periodMappingService.findByMS(mappingSchemeKey);
        HashMap<String, PeriodMapping> periodToMappings = CollectionUtils.isEmpty(allePeriodMappings) ? new HashMap<String, PeriodMapping>() : allePeriodMappings.stream().collect(Collectors.toMap(PeriodMapping::getPeriod, a -> a, (k1, k2) -> k1));
        mappingParam.setPeriodToMappings(periodToMappings);
        List allOrgMappings = this.orgMappingService.getOrgMappingByMS(mappingSchemeKey);
        allOrgMappings.forEach(a -> {
            if (!StringUtils.hasText(a.getMapping())) {
                a.setMapping(a.getCode());
            }
        });
        HashMap<String, OrgMapping> orgToMappings = CollectionUtils.isEmpty(allOrgMappings) ? new HashMap<String, OrgMapping>() : allOrgMappings.stream().collect(Collectors.toMap(OrgMapping::getCode, a -> a, (k1, k2) -> k1));
        mappingParam.setOrgToMappings(orgToMappings);
        List allBaseDataMappings = this.baseDataMappingService.getBaseDataMapping(mappingSchemeKey);
        allBaseDataMappings.forEach(a -> {
            if (!StringUtils.hasText(a.getMappingCode())) {
                a.setMappingCode(a.getBaseDataCode());
            }
        });
        HashMap<String, BaseDataMapping> baseToMappings = CollectionUtils.isEmpty(allBaseDataMappings) ? new HashMap<String, BaseDataMapping>() : allBaseDataMappings.stream().collect(Collectors.toMap(BaseDataMapping::getBaseDataCode, a -> a, (k1, k2) -> k1));
        mappingParam.setBaseToMappings(baseToMappings);
        List allBaseDataItemMappings = this.baseDataMappingService.getAllBaseDataItem(mappingSchemeKey);
        HashMap<String, Map<String, BaseDataItemMapping>> baseToDataDataToMappings = CollectionUtils.isEmpty(allBaseDataItemMappings) ? new HashMap<String, Map<String, BaseDataItemMapping>>() : allBaseDataItemMappings.stream().collect(Collectors.groupingBy(BaseDataItemMapping::getBaseDataCode, Collectors.toMap(BaseDataItemMapping::getBaseDataItemCode, a -> a, (k1, k2) -> k1)));
        mappingParam.setBaseToDataDataToMappings(baseToDataDataToMappings);
        return mappingParam;
    }

    private File buildOldParamFile(SyncSchemeParamDTO schemeParam, AsyncTaskMonitor monitor, String tempDir2) throws Exception {
        monitor.progressAndMessage(0.94, "\u6253\u5305\u53c2\u6570\u8bf4\u660e\u6587\u4ef6");
        String path = tempDir2 + File.separator + "param.json";
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (IOException e) {
                logger.info(e.getMessage(), e);
                throw e;
            }
        }
        String value = null;
        try {
            String lowerUnit;
            ArrayList superUnitList = new ArrayList();
            String superUnit = lowerUnit = schemeParam.getEntity();
            if (this.multistageUnitReplaceImpl != null) {
                List<String> unitList = Arrays.asList(lowerUnit.split(";"));
                unitList.forEach(a -> superUnitList.add(this.multistageUnitReplaceImpl.getSuperiorCode(a)));
                superUnit = String.join((CharSequence)";", superUnitList);
            }
            schemeParam.setEntity(superUnit);
            schemeParam.setSuperEntity(superUnit);
            value = this.mapper.writeValueAsString((Object)schemeParam);
            schemeParam.setEntity(lowerUnit);
        }
        catch (JsonProcessingException e) {
            Utils.deleteAllFilesOfDirByPath(tempDir2);
            logger.info(e.getMessage(), e);
            throw new Exception(MultilingualLog.buildParamFileMessage(1) + e.getMessage(), e);
        }
        try {
            FileUtils.writeStringToFile(file, value, Charset.defaultCharset());
        }
        catch (IOException e) {
            Utils.deleteAllFilesOfDirByPath(tempDir2);
            logger.info(e.getMessage(), e);
            throw new Exception(MultilingualLog.buildParamFileMessage(1) + e.getMessage(), e);
        }
        monitor.progressAndMessage(0.99, MultilingualLog.buildParamFileMessage(2));
        return file;
    }

    private File oldFileExport(List<File> files, SyncSchemeParamDTO schemeParam, AsyncTaskMonitor monitor, String schemeName, String tempDir) throws Exception {
        String tempDir2 = ZipUtils.newTempDir();
        File paramFile = this.buildOldParamFile(schemeParam, monitor, tempDir2);
        SyncSchemeParamDTO newSchemeParam = new SyncSchemeParamDTO();
        BeanUtils.copyProperties(schemeParam, newSchemeParam);
        schemeParam.setSyncSchemeParamDTOAfterMapping(newSchemeParam);
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u6253\u5305\u53c2\u6570\u6587\u4ef6\u5b8c\u6210");
        String tempDir1 = ZipUtils.newTempDir();
        String path = tempDir1 + File.separator + schemeName + ".nrd";
        PathUtils.validatePathManipulation((String)path);
        File zipFile = new File(path);
        if (zipFile.exists()) {
            try {
                FileUtils.forceMkdirParent(zipFile);
            }
            catch (IOException e) {
                logger.info(e.getMessage(), e);
            }
        }
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos);){
            for (File dataFile : files) {
                zos.putNextEntry(new ZipEntry(dataFile.getName()));
                FileUtils.copyFile(dataFile, zos);
                zos.closeEntry();
            }
            zos.putNextEntry(new ZipEntry("param.json"));
            FileUtils.copyFile(paramFile, zos);
            zos.closeEntry();
        }
        catch (IOException e) {
            monitor.error("\u5bfc\u51fa\u5931\u8d25", (Throwable)e);
            logger.info(e.getMessage(), e);
            throw e;
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempDir2);
            Utils.deleteAllFilesOfDirByPath(tempDir);
        }
        String exportFinish = MultilingualLog.fileExportMessage();
        monitor.finish(schemeParam.getExportFilterMessage() + exportFinish, (Object)exportFinish);
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u5168\u90e8\u5185\u5bb9\u6253\u5305\u5b8c\u6210\uff01");
        return zipFile;
    }

    private void addExportData(SyncSchemeParamDTO schemeParam) {
        String dataMessage = schemeParam.getDataMessage();
        Set exportData = this.gatherList.stream().map(ITransmissionDataGather::getCode).collect(Collectors.toSet());
        Set<Object> newExportData = null;
        if (StringUtils.hasText(dataMessage)) {
            List<String> exportDataList = Arrays.asList(dataMessage.split(";"));
            newExportData = exportDataList.stream().filter(exportData::contains).collect(Collectors.toSet());
        }
        if (CollectionUtils.isEmpty(newExportData)) {
            newExportData = new HashSet(TransmissionDataGatherVO.DEFAULT_DATA_SET);
        }
        Set<String> thisExportData = schemeParam.getExportData();
        thisExportData.clear();
        thisExportData.addAll(newExportData);
        if (newExportData.contains("WORKFLOW_TRANSMISSION_DATA")) {
            schemeParam.setIsUpload(0);
        }
    }

    @Override
    public File fileExport(AsyncTaskMonitor monitor, SyncSchemeParamDTO schemeParam) throws Exception {
        ExpotrFilterResult expotrFilterResult;
        String schemeName = Utils.getSchemeName(schemeParam);
        this.addExportData(schemeParam);
        try {
            expotrFilterResult = this.filterFormAndEntity(monitor, schemeParam);
            logger.info(schemeParam.getExportFilterMessage());
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(schemeParam.getFormSchemeKey());
        MappingParam mappingParam = null;
        if (StringUtils.hasLength(schemeParam.getMappingSchemeKey())) {
            mappingParam = this.getExportMapping(schemeParam, formScheme.getKey());
        }
        String entityId = formScheme.getDw();
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        ExecuteParam executeParam = this.buildParam(schemeParam);
        DefaultTransmissionContext defaultTransmissionContext = new DefaultTransmissionContext(null, executeParam);
        defaultTransmissionContext.setFmdmForm(schemeParam.getFmdmForm());
        defaultTransmissionContext.getLogHelper().appendLog(schemeParam.getExportFilterMessage());
        defaultTransmissionContext.getContextExpendParam().setDimensionName(dimensionName);
        defaultTransmissionContext.getContextExpendParam().setDw(entityId);
        defaultTransmissionContext.getContextExpendParam().setEntityCode(this.entityMetaService.getEntityCode(entityId));
        defaultTransmissionContext.getContextExpendParam().setVariables(schemeParam.getVariables());
        defaultTransmissionContext.getContextExpendParam().setExportData(schemeParam.getExportData());
        defaultTransmissionContext.setMappingParam(mappingParam);
        TransmissionExportType paramTransmissionExportType = schemeParam.getTransmissionExportType();
        TransmissionExportType transmissionExportType = paramTransmissionExportType != null ? paramTransmissionExportType : this.conTransmissionExportType;
        defaultTransmissionContext.setTransmissionExportType(transmissionExportType);
        this.setDimensionValueSetWithAllDim(defaultTransmissionContext, this.runTimeViewController.queryTaskDefine(executeParam.getTaskKey()).getDateTime());
        if (transmissionExportType == TransmissionExportType.FIRST_VERSION && schemeParam.getEntityType() == 1 && CollectionUtils.isEmpty(expotrFilterResult.getAllNoAuthUnits())) {
            defaultTransmissionContext.getContextExpendParam().setAllEntity(true);
        }
        String tempDir = ZipUtils.newTempDir();
        List<File> files = this.buildFiles(defaultTransmissionContext, monitor, tempDir);
        if (defaultTransmissionContext.getDataImportResult().getSyncErrorNum() > 0) {
            schemeParam.setHasError(true);
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u6253\u5305\u62a5\u8868\u6570\u636e\u548c\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
        schemeParam.setSrcAllDimCodes(defaultTransmissionContext.getContextExpendParam().getSrcDimCodes());
        String log = "";
        if (transmissionExportType == TransmissionExportType.FIRST_VERSION) {
            log = Utils.getLog(defaultTransmissionContext.getLogHelper());
            schemeParam.setExportFilterMessage(log);
            return this.oldFileExport(files, schemeParam, monitor, schemeName, tempDir);
        }
        defaultTransmissionContext.getLogHelper().appendLog("\u5bfc\u51fa\u65b0\u6587\u4ef6\uff01");
        log = Utils.getLog(defaultTransmissionContext.getLogHelper());
        schemeParam.setExportFilterMessage(log);
        logger.info(log);
        String paramFilePath = ZipUtils.newTempDir();
        File srcParamFile = this.buildSrcParamFile(schemeParam, monitor, paramFilePath);
        File paramFile = this.buildParamFile(schemeParam, monitor, paramFilePath, mappingParam);
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u6253\u5305\u53c2\u6570\u6587\u4ef6\u5b8c\u6210");
        String nrdParentPath = ZipUtils.newTempDir();
        String nrdPath = nrdParentPath + File.separator + schemeName + ".nrd";
        PathUtils.validatePathManipulation((String)nrdPath);
        File nrdFile = new File(nrdPath);
        if (nrdFile.exists()) {
            try {
                FileUtils.forceMkdirParent(nrdFile);
            }
            catch (IOException e) {
                logger.info(e.getMessage(), e);
            }
        }
        try (FileOutputStream fos = new FileOutputStream(nrdFile);
             ZipOutputStream zos = new ZipOutputStream(fos);){
            for (File dataFile : files) {
                zos.putNextEntry(new ZipEntry(dataFile.getName()));
                FileUtils.copyFile(dataFile, zos);
                zos.closeEntry();
            }
            zos.putNextEntry(new ZipEntry("param.json"));
            FileUtils.copyFile(paramFile, zos);
            zos.closeEntry();
            zos.putNextEntry(new ZipEntry("srcParam.json"));
            FileUtils.copyFile(srcParamFile, zos);
            zos.closeEntry();
            File versionFile = this.buildVersionFile(monitor, paramFilePath);
            zos.putNextEntry(new ZipEntry("version.json"));
            FileUtils.copyFile(versionFile, zos);
            zos.closeEntry();
        }
        catch (IOException e) {
            monitor.error("\u5bfc\u51fa\u5931\u8d25", (Throwable)e);
            logger.info(e.getMessage(), e);
            throw e;
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(paramFilePath);
            Utils.deleteAllFilesOfDirByPath(tempDir);
        }
        String exportFinish = MultilingualLog.fileExportMessage();
        monitor.finish(schemeParam.getExportFilterMessage() + exportFinish, (Object)exportFinish);
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u5168\u90e8\u5185\u5bb9\u6253\u5305\u5b8c\u6210\uff01");
        return nrdFile;
    }

    private void setDimensionValueSetWithAllDim(ITransmissionContext defaultTransmissionContext, String taskDateTime) {
        IExecuteParam executeParam = defaultTransmissionContext.getExecuteParam();
        DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
        DimensionValueSet dimensionValueSetWithAllDim = new DimensionValueSet();
        dimensionValueSetWithAllDim.assign(dimensionValueSet);
        Map<String, String> uploadDimMap = executeParam.getUploadDimMap();
        for (Map.Entry<String, String> dimEntry : uploadDimMap.entrySet()) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimEntry.getKey());
            Object value = dimensionValueSet.getValue(entityDefine.getDimensionName());
            if (value != null) continue;
            List<DimInfoParam> dimParams = this.reportParamService.getDimValues(dimEntry.getKey(), taskDateTime);
            List dimValues = dimParams.stream().map(DimInfoParam::getId).collect(Collectors.toList());
            dimensionValueSetWithAllDim.setValue(dimEntry.getKey(), dimValues);
        }
        defaultTransmissionContext.getContextExpendParam().setDimensionValueSetWithAllDim(dimensionValueSetWithAllDim);
    }

    private ExpotrFilterResult filterFormAndEntity(AsyncTaskMonitor monitor, SyncSchemeParamDTO syncSchemeParamDTO) throws Exception {
        List selectFormDefines;
        int formType = syncSchemeParamDTO.getFormType();
        ArrayList<FormDefine> allForms = new ArrayList<FormDefine>();
        String periodValue = 2 == syncSchemeParamDTO.getPeriod() ? syncSchemeParamDTO.getPeriodValue() : this.reportParamService.getPeriodValue(syncSchemeParamDTO.getPeriod(), syncSchemeParamDTO.getTask()).getPeriod();
        syncSchemeParamDTO.setPeriodValue(periodValue);
        String formSchemeKey = this.getFormScheme(syncSchemeParamDTO.getTask(), periodValue);
        syncSchemeParamDTO.setFormSchemeKey(formSchemeKey);
        String task = syncSchemeParamDTO.getTask();
        if (!this.authorityProvider.canReadTask(task)) {
            throw new RuntimeException(MultilingualLog.filterFormAndEntityMessage(1));
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(syncSchemeParamDTO.getTask());
        this.setAdjustPeriod(syncSchemeParamDTO);
        if (syncSchemeParamDTO.getEntityType() == 2 && !StringUtils.hasText(syncSchemeParamDTO.getEntity())) {
            throw new RuntimeException(MultilingualLog.filterFormAndEntityMessage(2));
        }
        if (formType == 2 && !StringUtils.hasText(syncSchemeParamDTO.getForm())) {
            throw new RuntimeException(MultilingualLog.filterFormAndEntityMessage(3));
        }
        if (formType == 1) {
            selectFormDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        } else {
            List<String> forms = Arrays.asList(syncSchemeParamDTO.getForm().split(";"));
            selectFormDefines = this.runTimeViewController.queryFormsById(forms);
        }
        ArrayList<FormDefine> selectFormsWithFmdmType = new ArrayList<FormDefine>();
        for (FormDefine selectFormDefine : selectFormDefines) {
            int value = selectFormDefine.getFormType().getValue();
            if (FormType.FORM_TYPE_FMDM.getValue() == value || FormType.FORM_TYPE_NEWFMDM.getValue() == value) {
                selectFormsWithFmdmType.add(selectFormDefine);
                allForms.add(selectFormDefine);
                continue;
            }
            allForms.add(selectFormDefine);
        }
        if (CollectionUtils.isEmpty(allForms)) {
            throw new RuntimeException(MultilingualLog.filterFormAndEntityMessage(5));
        }
        List allExportFormKeys = allForms.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        syncSchemeParamDTO.setForm(String.join((CharSequence)";", allExportFormKeys));
        if (syncSchemeParamDTO.getEntityType() == 1) {
            syncSchemeParamDTO.setEntity("");
        }
        DimensionValueSet dimensionValueSet = this.getDimensionValueSet(syncSchemeParamDTO, formSchemeKey);
        List<EntityInfoParam> allRows = this.reportParamService.getEntityList(dimensionValueSet, formSchemeKey, null, true);
        List<EntityInfoParam> allRightRows = this.reportParamService.getEntityList(dimensionValueSet, formSchemeKey, AuthorityType.Read, false);
        if (CollectionUtils.isEmpty(allRightRows)) {
            throw new RuntimeException(MultilingualLog.filterFormAndEntityMessage(4));
        }
        Set allExportEntityKeys = allRightRows.stream().map(EntityInfoParam::getEntityKeyData).collect(Collectors.toSet());
        syncSchemeParamDTO.setEntity(String.join((CharSequence)";", allExportEntityKeys));
        ExpotrFilterResult expotrFilterResult = new ExpotrFilterResult();
        expotrFilterResult.setAllRows(allRightRows);
        expotrFilterResult.setSelectFormsWithFmdmType(selectFormsWithFmdmType);
        Map<String, String> allRowsKeyMap = allRows.stream().collect(Collectors.toMap(EntityInfoParam::getEntityKeyData, EntityInfoParam::getTitle, (e1, e2) -> e2));
        Set<String> allRowsSet = allRowsKeyMap.keySet();
        allRowsSet.removeAll(allExportEntityKeys);
        String dmdmMesage = "";
        StringBuilder noAuthUnitMessage = new StringBuilder();
        if (!CollectionUtils.isEmpty(selectFormsWithFmdmType)) {
            syncSchemeParamDTO.setFmdmForm(((FormDefine)selectFormsWithFmdmType.get(0)).getKey());
            dmdmMesage = this.doFormFilterMessage(selectFormsWithFmdmType);
        }
        if (!CollectionUtils.isEmpty(allRowsSet)) {
            expotrFilterResult.setAllNoAuthUnits(allRowsSet);
            int i = 0;
            for (String entityKey : allRowsSet) {
                String title = allRowsKeyMap.get(entityKey);
                noAuthUnitMessage.append(title).append("[").append(entityKey).append("]");
                if (i < allRowsSet.size() - 1) {
                    noAuthUnitMessage.append("\u3001");
                }
                ++i;
            }
            noAuthUnitMessage.append("\u3002");
            noAuthUnitMessage.append("\r\n");
            syncSchemeParamDTO.setHasError(true);
        }
        String logMessage = MultilingualLog.getfilterFormAndEntityMessage(NpContextHolder.getContext().getUserName(), taskDefine.getTitle(), syncSchemeParamDTO.getPeriodValue(), syncSchemeParamDTO.getAdjustPeriod(), dmdmMesage, "", noAuthUnitMessage.toString());
        syncSchemeParamDTO.setExportFilterMessage(logMessage);
        monitor.progressAndMessage(0.03, logMessage);
        return expotrFilterResult;
    }

    private String doFormFilterMessage(List<FormDefine> excludeForm) {
        int i = 0;
        StringBuilder logMessage = new StringBuilder();
        for (FormDefine formDefine : excludeForm) {
            logMessage.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]");
            if (i < excludeForm.size() - 1) {
                logMessage.append("\u3001");
            }
            ++i;
        }
        logMessage.append("\u3002");
        return logMessage.toString();
    }

    private File buildParamFile(SyncSchemeParamDTO schemeParam, AsyncTaskMonitor monitor, String tempDir2, MappingParam mappingParam) throws Exception {
        SyncSchemeParamDTO newSchemeParam = new SyncSchemeParamDTO();
        BeanUtils.copyProperties(schemeParam, newSchemeParam);
        if (mappingParam != null) {
            ArrayList<String> srcEntities = new ArrayList<String>(Arrays.asList(newSchemeParam.getEntity().split(";")));
            ArrayList<String> desEntities = new ArrayList<String>();
            for (String srcEntity : srcEntities) {
                desEntities.add(mappingParam.getOrgMapByCode(srcEntity));
            }
            newSchemeParam.setEntity(String.join((CharSequence)";", desEntities));
            String periodValue = newSchemeParam.getPeriodValue();
            newSchemeParam.setPeriodValue(mappingParam.getPeriodMapByCode(periodValue));
            if (StringUtils.hasText(newSchemeParam.getDimKeys()) && StringUtils.hasText(newSchemeParam.getDimValues())) {
                Map<String, Map<String, BaseDataItemMapping>> baseToDataDataToMappings = mappingParam.getBaseToDataDataToMappings();
                Map<String, BaseDataMapping> baseToMappings = mappingParam.getBaseToMappings();
                ArrayList<String> srcSelectDimCodes = new ArrayList<String>(Arrays.asList(newSchemeParam.getDimKeys().split(";")));
                List<String> srcSelectDimValues = Arrays.asList(newSchemeParam.getDimValues().split(";"));
                ArrayList<String> desDimCodes = new ArrayList<String>();
                ArrayList<String> desDimValues = new ArrayList<String>();
                for (int i = 0; i < srcSelectDimCodes.size(); ++i) {
                    BaseDataMapping baseDataMapping;
                    String srcDimValueStrings = srcSelectDimValues.get(i);
                    String srcDimCode = (String)srcSelectDimCodes.get(i);
                    ArrayList<String> thisDimValues = new ArrayList<String>();
                    String desDimCode = "";
                    if (StringUtils.hasText(srcDimValueStrings)) {
                        List<String> srcDimValues = Arrays.asList(srcDimValueStrings.split(","));
                        Map<String, BaseDataItemMapping> stringBaseDataItemMappingMap = baseToDataDataToMappings.get(srcDimCode);
                        for (String srcDimValue : srcDimValues) {
                            BaseDataItemMapping baseDataItemMapping = stringBaseDataItemMappingMap.get(srcDimValue);
                            thisDimValues.add(baseDataItemMapping != null && StringUtils.hasLength(baseDataItemMapping.getMappingCode()) ? baseDataItemMapping.getMappingCode() : srcDimValue);
                        }
                    }
                    desDimCode = (baseDataMapping = baseToMappings.get(srcDimCode)) != null && StringUtils.hasLength(baseDataMapping.getMappingCode()) ? baseDataMapping.getMappingCode() : srcDimCode;
                    desDimCodes.add(desDimCode);
                    desDimValues.add(String.join((CharSequence)",", thisDimValues));
                }
                newSchemeParam.setDimKeys(String.join((CharSequence)";", desDimCodes));
                newSchemeParam.setDimValues(String.join((CharSequence)";", desDimValues));
            }
        }
        schemeParam.setSyncSchemeParamDTOAfterMapping(newSchemeParam);
        monitor.progressAndMessage(0.94, "\u6253\u5305\u53c2\u6570\u8bf4\u660e\u6587\u4ef6");
        String path = tempDir2 + File.separator + "param.json";
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (IOException e) {
                logger.info(e.getMessage(), e);
                throw e;
            }
        }
        String value = null;
        try {
            String lowerUnit;
            ArrayList superUnitList = new ArrayList();
            String superUnit = lowerUnit = newSchemeParam.getEntity();
            if (this.multistageUnitReplaceImpl != null) {
                List<String> unitList = Arrays.asList(lowerUnit.split(";"));
                unitList.forEach(a -> superUnitList.add(this.multistageUnitReplaceImpl.getSuperiorCode(a)));
                superUnit = String.join((CharSequence)";", superUnitList);
            }
            newSchemeParam.setEntity(superUnit);
            newSchemeParam.setSuperEntity(superUnit);
            value = this.mapper.writeValueAsString((Object)newSchemeParam);
        }
        catch (JsonProcessingException e) {
            Utils.deleteAllFilesOfDirByPath(tempDir2);
            logger.info(e.getMessage(), e);
            throw new Exception(MultilingualLog.buildParamFileMessage(1) + e.getMessage(), e);
        }
        try {
            FileUtils.writeStringToFile(file, value, Charset.defaultCharset());
        }
        catch (IOException e) {
            Utils.deleteAllFilesOfDirByPath(tempDir2);
            logger.info(e.getMessage(), e);
            throw new Exception(MultilingualLog.buildParamFileMessage(1) + e.getMessage(), e);
        }
        monitor.progressAndMessage(0.99, MultilingualLog.buildParamFileMessage(2));
        return file;
    }

    private File buildSrcParamFile(SyncSchemeParamDTO schemeParam, AsyncTaskMonitor monitor, String tempDir2) throws Exception {
        SrcParamDTO srcParamDTO = new SrcParamDTO();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(schemeParam.getTask());
        SrcParamDefinitionObj taskParamDefinition = new SrcParamDefinitionObj(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle(), null);
        srcParamDTO.setTaskDefinition(taskParamDefinition);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(schemeParam.getFormSchemeKey());
        SrcParamDefinitionObj formSchemeParamDefinition = new SrcParamDefinitionObj(formScheme.getKey(), formScheme.getFormSchemeCode(), formScheme.getTitle(), taskDefine.getKey());
        srcParamDTO.setFormSchemeDefinition(formSchemeParamDefinition);
        List allRPTFormulaSchemeDefinesByFormScheme = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(schemeParam.getFormSchemeKey());
        List<SrcParamDefinitionObj> formulaSchemeParamDefinitions = allRPTFormulaSchemeDefinesByFormScheme.stream().map(a -> new SrcParamDefinitionObj(a.getKey(), null, a.getTitle(), formScheme.getKey())).collect(Collectors.toList());
        srcParamDTO.setFormulaSchemeDefinitions(formulaSchemeParamDefinitions);
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(schemeParam.getFormSchemeKey());
        List<SrcParamDefinitionObj> formParamDefinitions = formDefines.stream().map(a -> new SrcParamDefinitionObj(a.getKey(), a.getFormCode(), a.getTitle(), formScheme.getKey())).collect(Collectors.toList());
        srcParamDTO.setFormDefinitions(formParamDefinitions);
        monitor.progressAndMessage(0.94, "\u6253\u5305\u53c2\u6570\u8bf4\u660e\u6587\u4ef6");
        String path = tempDir2 + File.separator + "srcParam.json";
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (IOException e) {
                logger.info(e.getMessage(), e);
                throw e;
            }
        }
        String value = null;
        try {
            value = this.mapper.writeValueAsString((Object)srcParamDTO);
        }
        catch (JsonProcessingException e) {
            Utils.deleteAllFilesOfDirByPath(tempDir2);
            logger.info(e.getMessage(), e);
            throw new Exception(MultilingualLog.buildParamFileMessage(1) + e.getMessage(), e);
        }
        try {
            FileUtils.writeStringToFile(file, value, Charset.defaultCharset());
        }
        catch (IOException e) {
            Utils.deleteAllFilesOfDirByPath(tempDir2);
            logger.info(e.getMessage(), e);
            throw new Exception(MultilingualLog.buildParamFileMessage(1) + e.getMessage(), e);
        }
        monitor.progressAndMessage(0.99, MultilingualLog.buildParamFileMessage(2));
        return file;
    }

    private File buildVersionFile(AsyncTaskMonitor monitor, String tempDir2) throws Exception {
        VersionDTO versionDTO = new VersionDTO();
        versionDTO.setVersion("12.0");
        monitor.progressAndMessage(0.94, "\u6253\u5305\u53c2\u6570\u8bf4\u660e\u6587\u4ef6");
        String path = tempDir2 + File.separator + "version.json";
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (IOException e) {
                logger.info(e.getMessage(), e);
                throw e;
            }
        }
        String value = null;
        try {
            value = this.mapper.writeValueAsString((Object)versionDTO);
        }
        catch (JsonProcessingException e) {
            Utils.deleteAllFilesOfDirByPath(tempDir2);
            logger.info(e.getMessage(), e);
            throw new Exception(MultilingualLog.buildParamFileMessage(1) + e.getMessage(), e);
        }
        try {
            FileUtils.writeStringToFile(file, value, Charset.defaultCharset());
        }
        catch (IOException e) {
            Utils.deleteAllFilesOfDirByPath(tempDir2);
            logger.info(e.getMessage(), e);
            throw new Exception(MultilingualLog.buildParamFileMessage(1) + e.getMessage(), e);
        }
        monitor.progressAndMessage(0.99, MultilingualLog.buildParamFileMessage(2));
        return file;
    }

    private List<File> buildFiles(DefaultTransmissionContext defaultTransmissionContext, AsyncTaskMonitor monitor, String tempDir) throws Exception {
        int i = 0;
        double start = 0.03;
        ArrayList<File> files = new ArrayList<File>(this.gatherList.size());
        defaultTransmissionContext.setTransmissionMonitor(monitor);
        Set<String> exportData = defaultTransmissionContext.getContextExpendParam().getExportData();
        double size = 0.9 / (double)this.gatherList.size();
        try {
            for (ITransmissionDataGather dataGather : this.gatherList) {
                if (!exportData.contains(dataGather.getCode())) continue;
                monitor.progressAndMessage(start + (double)i * size, "\u5bfc\u51fa" + dataGather.getTitle());
                TransmissionMonitor subMonitor = new TransmissionMonitor(dataGather.getCode(), this.cacheObjectResourceRemote, monitor, size);
                defaultTransmissionContext.setTransmissionMonitor(subMonitor);
                File dataFile = this.buildDataFile(dataGather, defaultTransmissionContext, tempDir);
                files.add(dataFile);
                ++i;
            }
        }
        catch (Exception e) {
            Utils.deleteAllFilesOfDirByPath(tempDir);
            throw new DataExportException(Utils.getLog(defaultTransmissionContext.getLogHelper()) + e.getMessage(), e);
        }
        return files;
    }

    private File buildDataFile(ITransmissionDataGather dataGather, ITransmissionContext context, String tempPath) throws Exception {
        String path = tempPath + "/" + dataGather.getCode() + ".zip";
        context.getContextExpendParam().setFilePath(path);
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (!file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (IOException e) {
                logger.info(e.getMessage(), e);
                throw e;
            }
        }
        try (OutputStream os = Files.newOutputStream(file.toPath(), new OpenOption[0]);){
            dataGather.dataExport(os, context);
        }
        catch (IOException e) {
            logger.info(e.getMessage(), e);
            throw e;
        }
        return file;
    }

    private ExecuteParam buildParam(SyncSchemeParamDTO param) {
        Map<String, String> uploadDimMap;
        String formSchemeKey = StringUtils.hasText(param.getFormSchemeKey()) ? param.getFormSchemeKey() : this.getFormScheme(param.getTask(), param.getPeriodValue());
        DimensionValueSet dimensionValueSet = this.getDimensionValueSet(param, formSchemeKey);
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setTaskKey(param.getTask());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(executeParam.getTaskKey());
        executeParam.setDateTime(taskDefine.getDateTime());
        executeParam.setFormSchemeKey(formSchemeKey);
        executeParam.setDimensionValueSet(dimensionValueSet);
        ArrayList<String> forms = new ArrayList<String>();
        if (param.getFormType() == 2 || StringUtils.hasText(param.getForm())) {
            forms = new ArrayList<String>(Arrays.asList(param.getForm().split(";")));
        }
        executeParam.setForms(forms);
        executeParam.setExtendParam(param.getExtendParam());
        executeParam.setDoUpload(param.getIsUpload());
        executeParam.setUploadDesc(param.getDescription());
        executeParam.setAllowForceUpload(param.getAllowForceUpload());
        if (StringUtils.hasText(param.getDimKeys()) && StringUtils.hasText(param.getDimValues())) {
            List<String> uploadDimLists = Arrays.asList(param.getDimKeys().split(";"));
            List<String> uploadDimvalues = Arrays.asList(param.getDimValues().split(";"));
            uploadDimMap = executeParam.getUploadDimMap();
            for (int i = 0; i < uploadDimLists.size(); ++i) {
                if (StringUtils.hasText(uploadDimvalues.get(i))) {
                    uploadDimMap.put(uploadDimLists.get(i), uploadDimvalues.get(i));
                    dimensionValueSet.setValue(uploadDimLists.get(i), Arrays.asList(uploadDimvalues.get(i).split(",")));
                    continue;
                }
                uploadDimMap.put(uploadDimLists.get(i), "");
            }
            executeParam.setUploadDimMap(uploadDimMap);
        }
        List reportDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        List entityDefines = reportDimension.stream().filter(a -> !"ADJUST".equals(a.getDimKey())).map(a -> this.entityMetaService.queryEntity(a.getDimKey())).collect(Collectors.toList());
        uploadDimMap = executeParam.getUploadDimMap();
        for (IEntityDefine entityDefine : entityDefines) {
            String uploadDimValue;
            if (entityDefine == null || StringUtils.hasText(uploadDimValue = uploadDimMap.get(entityDefine.getDimensionName()))) continue;
            uploadDimMap.put(entityDefine.getDimensionName(), "");
        }
        this.setAdjustPeriod(param);
        if (StringUtils.hasText(param.getAdjustPeriod())) {
            executeParam.setAdjustPeriod(Arrays.asList(param.getAdjustPeriod().split(";")));
        }
        return executeParam;
    }

    private void setAdjustPeriod(SyncSchemeParamDTO syncSchemeParamDTO) {
        TaskDefine taskDefine;
        List reportDimension;
        List adjustDataDimension;
        if (!StringUtils.hasText(syncSchemeParamDTO.getAdjustPeriod()) && !CollectionUtils.isEmpty(adjustDataDimension = (reportDimension = this.runtimeDataSchemeService.getReportDimension((taskDefine = this.runTimeViewController.queryTaskDefine(syncSchemeParamDTO.getTask())).getDataScheme())).stream().filter(a -> "ADJUST".equals(a.getDimKey())).collect(Collectors.toList()))) {
            syncSchemeParamDTO.setAdjustPeriod("0");
        }
    }

    private DimensionValueSet getDimensionValueSet(SyncSchemeParamDTO syncParam, String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String entityId = formScheme.getDw();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        ArrayList<String> dimensionValue = null;
        if (syncParam.getEntityType() != 1 || StringUtils.hasText(syncParam.getEntity())) {
            dimensionValue = new ArrayList<String>(Arrays.asList(syncParam.getEntity().split(";")));
        }
        dimensionValueSet.setValue(dimensionName, dimensionValue);
        dimensionValueSet.setValue("DATATIME", (Object)syncParam.getPeriodValue());
        if (StringUtils.hasText(syncParam.getAdjustPeriod())) {
            dimensionValueSet.setValue("ADJUST", Arrays.asList(syncParam.getAdjustPeriod().split(";")));
        }
        return dimensionValueSet;
    }

    private String getFormScheme(String taskKey, String periodValue) {
        String formSchemeKey = null;
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodValue, taskKey);
            formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException(MultilingualLog.getFormSchemeMessage(taskKey, periodValue));
        }
        return formSchemeKey;
    }
}

