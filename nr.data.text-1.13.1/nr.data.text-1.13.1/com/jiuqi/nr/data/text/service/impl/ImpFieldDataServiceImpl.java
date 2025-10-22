/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.attachment.param.ImpFileParams
 *  com.jiuqi.nr.data.attachment.service.ImpFieldDataFileService
 *  com.jiuqi.nr.data.common.DataFile
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.CompletionDimFinder
 *  com.jiuqi.nr.data.common.service.dto.DataFieldMp
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DateParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DateTimeParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.StringParseStrategy
 *  com.jiuqi.nr.datacrud.spi.TypeParseStrategy
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.fielddatacrud.FieldRelation
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 *  com.jiuqi.nr.fielddatacrud.FieldSaveInfo
 *  com.jiuqi.nr.fielddatacrud.RegionPO
 *  com.jiuqi.nr.fielddatacrud.SaveRes
 *  com.jiuqi.nr.fielddatacrud.TableDimSet
 *  com.jiuqi.nr.fielddatacrud.TableUpdater
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataService
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory
 *  com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider
 *  com.jiuqi.nr.fielddatacrud.spi.ParamProvider
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.tds.TdColumn
 *  com.jiuqi.nr.tds.TdRowData
 *  com.jiuqi.nr.tds.api.DataTableReader
 *  com.jiuqi.nr.tds.api.TdStoreFactory
 */
package com.jiuqi.nr.data.text.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.attachment.param.ImpFileParams;
import com.jiuqi.nr.data.attachment.service.ImpFieldDataFileService;
import com.jiuqi.nr.data.common.DataFile;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.CompletionDimFinder;
import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.data.text.DataFileSaveRes;
import com.jiuqi.nr.data.text.FieldDataSaveParam;
import com.jiuqi.nr.data.text.exception.ImportTaskDataException;
import com.jiuqi.nr.data.text.param.ChangMappingParam;
import com.jiuqi.nr.data.text.param.DataFileSaveResImpl;
import com.jiuqi.nr.data.text.param.DefaultFieldDataMonitor;
import com.jiuqi.nr.data.text.param.MetaDataInfo;
import com.jiuqi.nr.data.text.service.ImpFieldDataService;
import com.jiuqi.nr.data.text.spi.IFieldDataMonitor;
import com.jiuqi.nr.data.text.spi.IParamDataFileProvider;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DateParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DateTimeParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.StringParseStrategy;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.RegionPO;
import com.jiuqi.nr.fielddatacrud.SaveRes;
import com.jiuqi.nr.fielddatacrud.TableDimSet;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdRowData;
import com.jiuqi.nr.tds.api.DataTableReader;
import com.jiuqi.nr.tds.api.TdStoreFactory;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class ImpFieldDataServiceImpl
implements ImpFieldDataService {
    private static final Logger logger = LoggerFactory.getLogger(ImpFieldDataServiceImpl.class);
    public static final String MODULE_FILED_DATA_UPLOAD = "\u6570\u636e\u670d\u52a1-\u6307\u6807\u6570\u636e\u5bfc\u5165";
    private IFieldDataServiceFactory fieldDataServiceFactory;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private FieldRelationFactory fieldRelationFactory;
    private ImpFieldDataFileService attachmentUploadService;
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    private IRunTimeViewController runtimeViewController;
    private IProviderStore providerStore;
    private IParamDataFileProvider paramDataFileProvider;
    private TdStoreFactory tdStoreFactory;
    private static final int ROW_LIMIT = 5000;
    private static final String UNZIP_PATH = "output";

    public ImpFieldDataServiceImpl(IFieldDataServiceFactory fieldDataServiceFactory, IRuntimeDataSchemeService runtimeDataSchemeService, FieldRelationFactory fieldRelationFactory, ImpFieldDataFileService attachmentUploadService, DataServiceLoggerFactory dataServiceLoggerFactory, IRunTimeViewController runtimeViewController) {
        this.fieldDataServiceFactory = fieldDataServiceFactory;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.fieldRelationFactory = fieldRelationFactory;
        this.attachmentUploadService = attachmentUploadService;
        this.dataServiceLoggerFactory = dataServiceLoggerFactory;
        this.runtimeViewController = runtimeViewController;
        this.tdStoreFactory = new TdStoreFactory();
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public void setParamDataFileProvider(IParamDataFileProvider paramDataFileProvider) {
        this.paramDataFileProvider = paramDataFileProvider;
    }

    @Override
    public DataFileSaveRes importData(FieldDataSaveParam param, DataFile dataFile) {
        DefaultFieldDataMonitor dataMonitor = new DefaultFieldDataMonitor(null);
        return this.importData(param, dataFile, (IFieldDataMonitor)dataMonitor);
    }

    @Override
    public DataFileSaveRes importData(FieldDataSaveParam param, DataFile dataFile, IFieldDataMonitor zbDataMonitor) {
        return this.importData(param, dataFile, null, zbDataMonitor);
    }

    @Override
    public DataFileSaveRes importData(FieldDataSaveParam param, FileFinder finder) {
        DefaultFieldDataMonitor dataMonitor = new DefaultFieldDataMonitor(null);
        return this.importData(param, finder, (IFieldDataMonitor)dataMonitor);
    }

    @Override
    public DataFileSaveRes importData(FieldDataSaveParam param, FileFinder finder, IFieldDataMonitor zbDataMonitor) {
        return this.importData(param, null, finder, zbDataMonitor);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private DataFileSaveRes importData(FieldDataSaveParam param, DataFile dataFile, FileFinder finder, IFieldDataMonitor zbDataMonitor) {
        DataTableReader dataTableReader;
        DataServiceLogHelper dataServiceLogger = this.dataServiceLoggerFactory.getLogger(MODULE_FILED_DATA_UPLOAD, OperLevel.USER_OPER);
        DimensionCollection masterKey = param.getMasterKey();
        DataFileSaveResImpl result = new DataFileSaveResImpl();
        ParamsMapping paramsMapping = null;
        ParamProvider paramProvider = null;
        if (this.paramDataFileProvider != null) {
            paramsMapping = this.paramDataFileProvider.getParamMapping();
            paramProvider = this.paramDataFileProvider.getParamProvider();
        }
        if (paramProvider == null) {
            return result;
        }
        logger.info("---\u5f00\u59cb\u6307\u6807\u6570\u636e\u5bfc\u5165---");
        zbDataMonitor.progressAndMessage(0.01, "\u5f00\u59cb\u5bfc\u5165\u6570\u636e");
        logger.info("\u5f00\u59cb\u89e3\u6790\u6587\u4ef6");
        ArrayList<String> dimFieldCode = new ArrayList<String>();
        HashMap<String, List> tableCode2FieldCode = new HashMap<String, List>();
        String bieKeyOrder = null;
        String tableCode2BizKeyOrder = null;
        String attachmentDirPath = "";
        try {
            if (finder == null) {
                if (dataFile == null) {
                    return result;
                }
                dataTableReader = this.tdStoreFactory.getDataTableReader();
                File csvFile = null;
                if (dataFile.isZip()) {
                    String unzipPath = dataFile.getFile().getParent() + File.separator + UNZIP_PATH;
                    FileUtil.unZip((File)dataFile.getFile(), (String)unzipPath);
                    File file = new File(unzipPath);
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File file1 : files) {
                            if (file1.isDirectory()) {
                                attachmentDirPath = file1.getAbsolutePath();
                                continue;
                            }
                            if (!file1.isFile()) continue;
                            csvFile = file1;
                        }
                    }
                } else {
                    csvFile = dataFile.getFile();
                }
                if (csvFile == null) return result;
                dataTableReader.open(csvFile, csvFile.getName());
            } else {
                dataTableReader = this.tdStoreFactory.getDataTableReader();
                File file = finder.getFile("");
                dataTableReader.open(file, file.getName());
            }
            for (TdColumn tdColumn : dataTableReader.columns()) {
                String headerName = tdColumn.getName();
                if (headerName.contains(".")) {
                    String[] split = headerName.split("\\.");
                    if (split[1].equals("BIZKEYORDER")) {
                        tableCode2BizKeyOrder = split[0];
                        bieKeyOrder = split[1];
                        continue;
                    }
                    tableCode2FieldCode.computeIfAbsent(split[0], k -> new ArrayList()).add(split[1]);
                    continue;
                }
                dimFieldCode.add(headerName);
            }
        }
        catch (IOException e) {
            zbDataMonitor.error("\u89e3\u6790\u6587\u4ef6\u5931\u8d25\uff01", e);
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("\u89e3\u6790\u6587\u4ef6\u5b8c\u6210");
        zbDataMonitor.progressAndMessage(0.05, "\u89e3\u6790\u8868\u5934\u5b57\u6bb5\u6620\u5c04");
        logger.info("\u5f00\u59cb\u83b7\u53d6\u6620\u5c04\u5173\u7cfb");
        FilterDim filterDim = param.getFilterDim();
        HashMap<String, String> filterDimMap = new HashMap<String, String>();
        List<String> dimFieldCodes = this.filterDims(filterDim, filterDimMap, dimFieldCode);
        CompletionDim completionDim = param.getCompletionDim();
        CompletionDimFinder finder1 = completionDim.getFinder();
        HashMap<String, String> addDimMap = new HashMap<String, String>();
        ArrayList<String> dynamicsCompletionDims = new ArrayList<String>();
        this.addDims(dimFieldCodes, completionDim, addDimMap, dynamicsCompletionDims);
        HashMap<String, Map> aimTable2aimField2FileCode = new HashMap<String, Map>();
        HashMap<String, List<String>> aimTable2AimFieldCode = new HashMap<String, List<String>>();
        HashMap<String, Boolean> tableHasMapping = new HashMap<String, Boolean>();
        for (Map.Entry entry : tableCode2FieldCode.entrySet()) {
            String dataTableCodeInFile = (String)entry.getKey();
            if (paramsMapping != null && paramsMapping.tryDataFieldMap(dataTableCodeInFile)) {
                Map fieldCodeMap = paramsMapping.getOriginDataFieldCode(dataTableCodeInFile, (List)entry.getValue());
                for (Map.Entry entry2 : fieldCodeMap.entrySet()) {
                    DataFieldMp dataFieldMp = (DataFieldMp)entry2.getValue();
                    String aimFieldCode = dataFieldMp.getCode();
                    tableHasMapping.putIfAbsent(dataFieldMp.getTableCode(), true);
                    aimTable2aimField2FileCode.computeIfAbsent(dataFieldMp.getTableCode(), K -> new HashMap()).put(aimFieldCode, dataTableCodeInFile + "." + (String)entry2.getKey());
                    aimTable2AimFieldCode.computeIfAbsent(dataFieldMp.getTableCode(), k -> new ArrayList()).add(aimFieldCode);
                }
                continue;
            }
            tableHasMapping.putIfAbsent(dataTableCodeInFile, false);
            for (String fileCode : (List)entry.getValue()) {
                aimTable2aimField2FileCode.computeIfAbsent(dataTableCodeInFile, k -> new HashMap()).put(fileCode, dataTableCodeInFile + "." + fileCode);
            }
        }
        if (CollectionUtils.isEmpty(aimTable2AimFieldCode)) {
            aimTable2AimFieldCode = tableCode2FieldCode;
        }
        this.isMoreDetailTable(aimTable2AimFieldCode);
        if (bieKeyOrder != null && tableCode2BizKeyOrder != null) {
            Optional first = aimTable2aimField2FileCode.keySet().stream().findFirst();
            ((List)aimTable2AimFieldCode.get(first.get())).add(bieKeyOrder);
            ((Map)aimTable2aimField2FileCode.get(first.get())).put(bieKeyOrder, tableCode2BizKeyOrder + "." + bieKeyOrder);
        }
        HashMap<String, List> form2MetaData = new HashMap<String, List>();
        HashMap<String, FieldRelation> form2FieldRelation = new HashMap<String, FieldRelation>();
        List<DataField> dataFieldsInAimRegion = param.getDataFieldsInAimRegion();
        Map<Object, Object> aimFieldCode2Key = new HashMap();
        if (dataFieldsInAimRegion != null) {
            aimFieldCode2Key = dataFieldsInAimRegion.stream().collect(Collectors.toMap(Basic::getCode, Basic::getKey));
        }
        boolean hasAimRegion = !CollectionUtils.isEmpty(aimFieldCode2Key);
        for (Map.Entry entry : aimTable2AimFieldCode.entrySet()) {
            String dataTableCode = (String)entry.getKey();
            FieldRelation fieldRelation = this.fieldRelationFactory.getFieldRelation();
            form2FieldRelation.put(dataTableCode, fieldRelation);
            List fieldCodes = (List)entry.getValue();
            List dataFieldByTableCode = this.runtimeDataSchemeService.getDataFieldByTableCode(dataTableCode);
            Map<String, String> fieldCode2key = dataFieldByTableCode.stream().collect(Collectors.toMap(Basic::getCode, Basic::getKey));
            ArrayList<Object> dataFieldKeys = new ArrayList<Object>();
            for (String fieldCode : fieldCodes) {
                if (hasAimRegion && !((Boolean)tableHasMapping.get(dataTableCode)).booleanValue()) {
                    if (!aimFieldCode2Key.containsKey(fieldCode)) continue;
                    dataFieldKeys.add(aimFieldCode2Key.get(fieldCode));
                    continue;
                }
                dataFieldKeys.add(fieldCode2key.get(fieldCode));
            }
            List iMetaData = form2MetaData.computeIfAbsent(dataTableCode, k -> new ArrayList());
            iMetaData.addAll(fieldRelation.getMetaData(dataFieldKeys));
            List iMetaData1 = fieldRelation.matchDimMetaData(dimFieldCodes);
            if (iMetaData1 == null) continue;
            iMetaData.addAll(iMetaData1);
        }
        logger.info("\u83b7\u53d6\u6620\u5c04\u5173\u7cfb\u5b8c\u6210");
        zbDataMonitor.progressAndMessage(0.1, "\u5f00\u59cb\u6309\u8868\u5355\u5bfc\u5165\u6570\u636e");
        double d = 0.8 / (double)form2MetaData.entrySet().size();
        int split = 0;
        LogDimensionCollection logDimensionCollection = null;
        String dataSchemeKey = null;
        logger.info("\u5f00\u59cb\u6309\u5b58\u50a8\u8868\u5bfc\u5165\u6570\u636e");
        for (Map.Entry entry : form2MetaData.entrySet()) {
            List allFieldMetas = (List)entry.getValue();
            if (CollectionUtils.isEmpty(allFieldMetas)) continue;
            FieldRelation fieldRelation = (FieldRelation)form2FieldRelation.get(entry.getKey());
            Set regions = paramProvider.getRegions((String)entry.getKey(), (List)aimTable2AimFieldCode.get(entry.getKey()));
            if (CollectionUtils.isEmpty(regions)) {
                logger.info("\u672a\u627e\u5230{}\u5b58\u50a8\u8868", entry.getKey());
                zbDataMonitor.progressAndMessage(0.1 + d * (double)split, "");
                continue;
            }
            RegionPO regionPO = (RegionPO)regions.stream().findFirst().get();
            String taskKey = regionPO.getTaskKey();
            String formSchemeKey = regionPO.getFormSchemeKey();
            if (logDimensionCollection == null) {
                List tableDim = fieldRelation.getTableDim(allFieldMetas);
                dataSchemeKey = ((TableDimSet)tableDim.get(0)).getDataTable().getDataSchemeKey();
                List dimensionCombines = masterKey.getDimensionCombinations();
                ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
                for (DimensionCombination dimensionCombine : dimensionCombines) {
                    dimensionValueSets.add(dimensionCombine.toDimensionValueSet());
                }
                DimensionValueSet dimensionSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSets);
                String targetKey = String.valueOf(dimensionSet.getValue(fieldRelation.getDwDimName()));
                String periodCode = String.valueOf(dimensionSet.getValue("DATATIME"));
                FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
                logDimensionCollection = new LogDimensionCollection();
                logDimensionCollection.setDw(formScheme.getDw(), new String[]{targetKey});
                logDimensionCollection.setPeriod(formScheme.getDateTime(), periodCode);
            }
            zbDataMonitor.progressAndMessage(0.1 + d * (double)split, "\u5f00\u59cb\u5bfc\u5165\u5b58\u50a8\u8868" + (String)entry.getKey() + "\u6570\u636e");
            logger.info("\u5f00\u59cb\u5bfc\u5165\u5b58\u50a8\u8868{}\u6570\u636e", entry.getKey());
            dataServiceLogger.info(taskKey, logDimensionCollection, MODULE_FILED_DATA_UPLOAD, "\u5f00\u59cb\u5bfc\u5165\u5b58\u50a8\u8868" + (String)entry.getKey() + "\u6570\u636e");
            FieldSaveInfo updateParam = new FieldSaveInfo();
            updateParam.setMode(param.getMode());
            updateParam.setAuthMode(param.getAuthMode());
            updateParam.setMasterKey(masterKey);
            updateParam.setVariables(param.getVariables());
            updateParam.setFields(allFieldMetas);
            FormulaFilter formulaFilter = new FormulaFilter(param.getFilterCondition());
            ArrayList<FormulaFilter> filters = new ArrayList<FormulaFilter>();
            filters.add(formulaFilter);
            updateParam.setRowFilters(filters);
            TableUpdater tableUpdater = this.getTableUpdater(updateParam);
            this.initTableUpdater(tableUpdater, param.isAllIllegalDataImport());
            int count = 0;
            MetaDataInfo metaDataInfo = new MetaDataInfo(allFieldMetas);
            ChangMappingParam mappingParam = new ChangMappingParam();
            HashMap<String, String> groupKeyMap = new HashMap<String, String>();
            Map codeMap = (Map)aimTable2aimField2FileCode.get(entry.getKey());
            for (String fieldCode : dimFieldCodes) {
                codeMap.put(fieldCode, fieldCode);
            }
            try {
                while (dataTableReader.hasNext()) {
                    TdRowData next = dataTableReader.next();
                    boolean isContinue = false;
                    for (String field : filterDimMap.keySet()) {
                        String value = (String)next.getValue(field);
                        if (value.equals(filterDimMap.get(field))) continue;
                        isContinue = true;
                    }
                    if (isContinue) continue;
                    Object[] value = new Object[allFieldMetas.size()];
                    for (int i = 0; i < allFieldMetas.size(); ++i) {
                        IMetaData iMetaData = (IMetaData)allFieldMetas.get(i);
                        String fieldCode = (String)codeMap.get(iMetaData.getCode());
                        if (addDimMap.containsKey(fieldCode)) {
                            value[i] = addDimMap.get(fieldCode);
                        } else if (dynamicsCompletionDims.contains(fieldCode)) {
                            value[i] = finder1.findByDw((String)next.getValue((String)codeMap.get("MDCODE")), fieldCode);
                        } else {
                            Object objectValue = next.getValue(fieldCode);
                            if (objectValue == null) continue;
                            value[i] = objectValue;
                        }
                        if (i == metaDataInfo.getDwIndex()) {
                            mappingParam.getDwCodes().add(value[i].toString());
                            continue;
                        }
                        if (i == metaDataInfo.getPeriodIndex()) {
                            mappingParam.getPeriodCodes().add(value[i].toString());
                            continue;
                        }
                        if (metaDataInfo.getIndex2RefEntityId().containsKey(i)) {
                            mappingParam.getFieldIndex2Data().computeIfAbsent(i, k -> new ArrayList()).add(value[i].toString());
                            continue;
                        }
                        if (!metaDataInfo.getFileIndex().contains(i)) continue;
                        String newGroupKey = UUIDUtils.getKey();
                        groupKeyMap.put(value[i].toString(), newGroupKey);
                        mappingParam.getFileIndex2GroupKey().computeIfAbsent(i, k -> new HashMap()).put(value[i].toString(), newGroupKey);
                    }
                    mappingParam.getAllDataValues().add(value);
                    if (++count % 5000 != 0) continue;
                    this.addRowData(paramsMapping, mappingParam, metaDataInfo, tableUpdater, result);
                }
                this.addRowData(paramsMapping, mappingParam, metaDataInfo, tableUpdater, result);
                tableUpdater.commit();
                if (!CollectionUtils.isEmpty(metaDataInfo.getFileIndex()) && !CollectionUtils.isEmpty(groupKeyMap)) {
                    logger.info("\u6307\u6807\u6570\u636e\u5bfc\u5165-\u5f00\u59cb\u5bfc\u5165\u5b58\u50a8\u8868{}\u6307\u6807\u5bf9\u5e94\u9644\u4ef6", entry.getKey());
                    dataServiceLogger.info(taskKey, logDimensionCollection, MODULE_FILED_DATA_UPLOAD, "\u6307\u6807\u6570\u636e\u5bfc\u5165-\u5f00\u59cb\u5bfc\u5165\u5b58\u50a8\u8868" + (String)entry.getKey() + "\u6307\u6807\u5bf9\u5e94\u9644\u4ef6");
                    ImpFileParams impFileParams = finder == null ? new ImpFileParams(dataSchemeKey, taskKey, formSchemeKey, attachmentDirPath, groupKeyMap, false, paramsMapping) : new ImpFileParams(dataSchemeKey, taskKey, formSchemeKey, finder, groupKeyMap, false, paramsMapping, filterDim, completionDim);
                    this.attachmentUploadService.uploadFileds(impFileParams);
                    dataServiceLogger.info(taskKey, logDimensionCollection, MODULE_FILED_DATA_UPLOAD, "\u6307\u6807\u6570\u636e\u5bfc\u5165-\u5bfc\u5165\u5b58\u50a8\u8868" + (String)entry.getKey() + "\u6307\u6807\u5bf9\u5e94\u9644\u4ef6\u5b8c\u6210");
                    logger.info("\u6307\u6807\u6570\u636e\u5bfc\u5165-\u5bfc\u5165\u5b58\u50a8\u8868{}\u6307\u6807\u5bf9\u5e94\u9644\u4ef6\u5b8c\u6210", entry.getKey());
                }
                this.dealResult(result, tableUpdater, (String)entry.getKey(), false);
                zbDataMonitor.progressAndMessage(0.1 + d * (double)(++split), "\u8868\u5355" + (String)entry.getKey() + "\u5bfc\u5165\u5b8c\u6210");
                dataServiceLogger.info(taskKey, logDimensionCollection, MODULE_FILED_DATA_UPLOAD, "\u5bfc\u5165\u5b58\u50a8\u8868" + (String)entry.getKey() + "\u6570\u636e\u5b8c\u6210");
                logger.info("\u5bfc\u5165\u5b58\u50a8\u8868{}\u6570\u636e\u5b8c\u6210", entry.getKey());
            }
            catch (Exception e) {
                zbDataMonitor.error("\u5bfc\u5165\u5931\u8d25", e);
                dataServiceLogger.error(taskKey, logDimensionCollection, MODULE_FILED_DATA_UPLOAD, "\u6307\u6807\u6570\u636e\u5bfc\u5165\u5931\u8d25" + e.getMessage());
                logger.error(e.getMessage(), e);
                throw new ImportTaskDataException("\u6307\u6807\u6570\u636e\u5bfc\u5165\u5931\u8d25", e);
            }
        }
        zbDataMonitor.finish("\u6307\u6807\u6570\u636e\u5bfc\u5165\u6210\u529f", null);
        logger.info("---\u6307\u6807\u6570\u636e\u5bfc\u5165\u6210\u529f---");
        result.dealResult();
        return result;
    }

    private void addDims(List<String> dimFieldCodes, CompletionDim completionDim, Map<String, String> completionDimMap, List<String> dynamicsCompletionDims) {
        if (!completionDim.isCompletionDim()) {
            return;
        }
        DimensionValueSet fixedCompletionDims = completionDim.getFixedCompletionDims();
        if (fixedCompletionDims != null) {
            int count = fixedCompletionDims.size();
            for (int i = 0; i < count; ++i) {
                completionDimMap.put(fixedCompletionDims.getName(i), fixedCompletionDims.getValue(i).toString());
            }
        }
        HashSet<String> addDims = new HashSet<String>(completionDimMap.keySet());
        if (!CollectionUtils.isEmpty(completionDim.getDynamicsCompletionDims())) {
            dynamicsCompletionDims.addAll(completionDim.getDynamicsCompletionDims());
            addDims.addAll(completionDim.getDynamicsCompletionDims());
        }
        dimFieldCodes.addAll(addDims);
    }

    private List<String> filterDims(FilterDim filterDim, Map<String, String> filterDimMap, List<String> dimFieldCode) {
        if (!filterDim.isFilterDim()) {
            return dimFieldCode;
        }
        DimensionValueSet fixedFilterDims = filterDim.getFixedFilterDims();
        if (fixedFilterDims != null) {
            for (int i = 0; i < fixedFilterDims.size(); ++i) {
                filterDimMap.put(fixedFilterDims.getName(i), fixedFilterDims.getValue(i).toString());
            }
        }
        HashSet<String> filterDims = new HashSet<String>(filterDimMap.keySet());
        if (!CollectionUtils.isEmpty(filterDim.getDynamicsFilterDims())) {
            filterDims.addAll(filterDim.getDynamicsFilterDims());
        }
        return dimFieldCode.stream().filter(a -> !filterDims.contains(a)).collect(Collectors.toList());
    }

    private void initTableUpdater(TableUpdater tableUpdater, boolean allIllegalDataImport) {
        tableUpdater.installParseStrategy();
        if (allIllegalDataImport) {
            DecimalParseStrategy decimalParseStrategy = new DecimalParseStrategy();
            decimalParseStrategy.setRoundingMode(RoundingMode.HALF_UP);
            tableUpdater.registerParseStrategy(DataFieldType.BIGDECIMAL.getValue(), (TypeParseStrategy)decimalParseStrategy);
            StringParseStrategy stringParseStrategy = new StringParseStrategy();
            stringParseStrategy.setOverLengthTruncated(true);
            tableUpdater.registerParseStrategy(DataFieldType.STRING.getValue(), (TypeParseStrategy)stringParseStrategy);
            DateParseStrategy dateParseStrategy = new DateParseStrategy();
            dateParseStrategy.setCheckDateRange(false);
            tableUpdater.registerParseStrategy(DataFieldType.DATE.getValue(), (TypeParseStrategy)dateParseStrategy);
            DateTimeParseStrategy dateTimeParseStrategy = new DateTimeParseStrategy();
            dateTimeParseStrategy.setCheckDateRange(false);
            tableUpdater.registerParseStrategy(DataFieldType.DATE_TIME.getValue(), (TypeParseStrategy)dateTimeParseStrategy);
        }
    }

    private void dealResult(DataFileSaveResImpl result, TableUpdater tableUpdater, String tableCode, boolean isReturn) {
        SaveRes saveRes = tableUpdater.getSaveRes();
        result.getSaveRes().put(tableCode, saveRes);
        if (isReturn) {
            result.dealResult();
        }
    }

    private void isMoreDetailTable(Map<String, List<String>> param) {
        Set<String> tableCode;
        DataTable dataTableByCode;
        if (param.size() > 1 && (dataTableByCode = this.runtimeDataSchemeService.getDataTableByCode(new ArrayList<String>(tableCode = param.keySet()).get(0))).getDataTableType() == DataTableType.DETAIL) {
            throw new RuntimeException("\u660e\u7ec6\u8868\u6620\u5c04\u4ec5\u652f\u6301\u4e00\u5bf9\u4e00");
        }
    }

    private void addRowData(ParamsMapping paramsMapping, ChangMappingParam changMappingParam, MetaDataInfo metaDataInfo, TableUpdater tableUpdater, DataFileSaveResImpl result) throws CrudOperateException {
        this.getAllDataValue(paramsMapping, changMappingParam, metaDataInfo);
        for (Object[] rowData : changMappingParam.getAllDataValues()) {
            ReturnRes res = tableUpdater.addRow(rowData);
            if (res.getCode() == 0 || res.getCode() == 1101 || res.getCode() == 1103) continue;
            result.setAddFail(true);
        }
        changMappingParam.clearMappingParams();
    }

    private void getAllDataValue(ParamsMapping paramsMapping, ChangMappingParam changMappingParam, MetaDataInfo metaDataInfo) {
        Map<Integer, Map<String, String>> fileIndex2GroupKey = changMappingParam.getFileIndex2GroupKey();
        if (paramsMapping != null) {
            Map originOrgCode = paramsMapping.getOriginOrgCode(changMappingParam.getDwCodes());
            Map originPeriod = paramsMapping.getOriginPeriod(changMappingParam.getPeriodCodes());
            HashMap fieldIndex2OriginalData = new HashMap();
            Map<Integer, String> index2RefEntityId = metaDataInfo.getIndex2RefEntityId();
            int dwIndex = metaDataInfo.getDwIndex();
            int periodIndex = metaDataInfo.getPeriodIndex();
            for (Map.Entry<Integer, String> entry : index2RefEntityId.entrySet()) {
                Integer index = entry.getKey();
                String string = index2RefEntityId.get(index);
                List<String> fieldData = changMappingParam.getFieldIndex2Data().get(index);
                Map originBaseData = CollectionUtils.isEmpty(fieldData) ? new HashMap() : paramsMapping.getOriginBaseData(string, fieldData);
                fieldIndex2OriginalData.put(index, originBaseData);
            }
            for (Object[] data : changMappingParam.getAllDataValues()) {
                Map<String, String> stringStringMap;
                Integer index;
                data[dwIndex] = originOrgCode.get(data[dwIndex]);
                data[periodIndex] = originPeriod.get(data[periodIndex]);
                for (Map.Entry entry : fieldIndex2OriginalData.entrySet()) {
                    index = (Integer)entry.getKey();
                    stringStringMap = (Map<String, String>)fieldIndex2OriginalData.get(index);
                    data[index.intValue()] = stringStringMap.get(data[index]);
                }
                for (Map.Entry<Object, Object> entry : fileIndex2GroupKey.entrySet()) {
                    index = (Integer)entry.getKey();
                    stringStringMap = fileIndex2GroupKey.get(index);
                    data[index.intValue()] = stringStringMap.get(data[index]);
                }
            }
        } else {
            for (Object[] data : changMappingParam.getAllDataValues()) {
                for (Map.Entry<Integer, Map<String, String>> entry : fileIndex2GroupKey.entrySet()) {
                    Integer index = entry.getKey();
                    Map<String, String> stringStringMap = fileIndex2GroupKey.get(index);
                    data[index.intValue()] = stringStringMap.get(data[index]);
                }
            }
        }
    }

    private TableUpdater getTableUpdater(FieldSaveInfo param) {
        IFieldDataService fieldDataService = null == this.providerStore && null == this.paramDataFileProvider ? this.fieldDataServiceFactory.getFieldDataFileService() : (null != this.providerStore && null == this.paramDataFileProvider ? this.fieldDataServiceFactory.getFieldDataFileService(this.providerStore) : (null == this.providerStore ? this.fieldDataServiceFactory.getFieldDataFileService((IParamDataProvider)this.paramDataFileProvider) : this.fieldDataServiceFactory.getFieldDataFileService(this.providerStore, (IParamDataProvider)this.paramDataFileProvider)));
        return fieldDataService.getTableUpdater(param);
    }
}

