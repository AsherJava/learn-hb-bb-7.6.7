/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fielddatacrud.FieldQueryInfoBuilder
 *  com.jiuqi.nr.fielddatacrud.FieldRelation
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 *  com.jiuqi.nr.fielddatacrud.FieldSaveInfo
 *  com.jiuqi.nr.fielddatacrud.FieldSort
 *  com.jiuqi.nr.fielddatacrud.IFieldQueryInfo
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 *  com.jiuqi.nr.fielddatacrud.TableUpdater
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataService
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory
 *  com.jiuqi.nr.fielddatacrud.spi.IDataReader
 *  com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 */
package com.jiuqi.nr.sbdata.carry.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fielddatacrud.FieldQueryInfoBuilder;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.FieldSort;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.sbdata.carry.bean.DataFileCheckInfo;
import com.jiuqi.nr.sbdata.carry.bean.DataTableCarryResult;
import com.jiuqi.nr.sbdata.carry.bean.TzCarryDownDTO;
import com.jiuqi.nr.sbdata.carry.bean.TzClearDataParam;
import com.jiuqi.nr.sbdata.carry.service.TzCarryDownService;
import com.jiuqi.nr.sbdata.carry.service.impl.TzDataReaderImpl;
import com.jiuqi.nr.sbdata.carry.util.TzCarryUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class TzCarryDownServiceImpl
implements TzCarryDownService {
    private static final Logger logger = LoggerFactory.getLogger(TzCarryDownServiceImpl.class);
    public static final String MODULE_TZ_DATA_CLEAR = "\u53f0\u8d26\u6570\u636e\u6e05\u9664";
    public static final String MODULE_TZ_CARRY_DOWN = "\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c";
    private static final Pattern compile = Pattern.compile("(.+)\\[(.+)\\]");
    @Autowired
    private IFieldDataServiceFactory fieldDataServiceFactory;
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private FieldRelationFactory fieldRelationFactory;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ITaskOptionController iTaskOptionController;

    @Override
    public DataTableCarryResult clearData(TzClearDataParam param) {
        return this.clearData(param, null);
    }

    @Override
    public DataTableCarryResult clearData(TzClearDataParam param, AsyncTaskMonitor monitor) {
        DataServiceLogHelper dataServiceLogger = this.dataServiceLoggerFactory.getLogger(MODULE_TZ_DATA_CLEAR, OperLevel.USER_OPER);
        DimensionCollection masterKey = param.getMasterKey();
        LogDimensionCollection logDimensionCollection = this.getLogDimensionCollection(masterKey, param.getFormSchemeKey());
        String formKey = param.getFormKey();
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(param.getDataTableKey());
        String title = dataTable.getTitle();
        DataTableCarryResult result = new DataTableCarryResult();
        if (CollectionUtils.isEmpty(param.getFields())) {
            result.setSuccess(false);
            result.setErrorMessage("\u53f0\u8d26\u8868" + dataTable + "\u6ca1\u6709\u6307\u6807\u6620\u5c04\uff01");
            if (monitor != null) {
                String detail = JsonUtil.objectToJson((Object)result);
                monitor.error("\u53f0\u8d26\u8868" + title + "\u6570\u636e\u6e05\u9664\u5931\u8d25\uff01", null, detail);
            }
            return result;
        }
        dataServiceLogger.info(param.getTaskKey(), logDimensionCollection, MODULE_TZ_DATA_CLEAR, "\u53f0\u8d26\u8868" + title + "\u6570\u636e\u6e05\u9664\u5f00\u59cb\uff01");
        if (monitor != null) {
            monitor.progressAndMessage(0.01, "\u53f0\u8d26\u8868" + title + "\u6570\u636e\u6e05\u9664\u5f00\u59cb");
        }
        logger.info("\u53f0\u8d26\u8868{}\u6570\u636e\u6e05\u9664\u5f00\u59cb\uff01", (Object)title);
        IParamDataProvider paramDataProvider = TzCarryUtils.getParamDataProvider(param.getTaskKey(), param.getFormSchemeKey(), formKey);
        IFieldDataService fieldDataFileService = this.fieldDataServiceFactory.getFieldDataFileService(paramDataProvider);
        List fieldKeys = param.getFields().stream().map(Basic::getKey).collect(Collectors.toList());
        List publicDims = this.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(param.getDataTableKey(), new DataFieldKind[]{DataFieldKind.PUBLIC_FIELD_DIM});
        fieldKeys.addAll(publicDims.stream().map(Basic::getKey).collect(Collectors.toList()));
        FieldRelation fieldRelation = this.fieldRelationFactory.getFieldRelation();
        List metaData = fieldRelation.getMetaData(fieldKeys);
        FieldSaveInfo fieldSaveInfo = new FieldSaveInfo();
        fieldSaveInfo.setMode(ImpMode.FULL);
        fieldSaveInfo.setAuthMode(ResouceType.FORM);
        fieldSaveInfo.setFields(metaData);
        fieldSaveInfo.setMasterKey(masterKey);
        TableUpdater tableUpdater = null;
        try {
            tableUpdater = fieldDataFileService.getTableUpdater(fieldSaveInfo);
            tableUpdater.setRowByDw(true);
            tableUpdater.installParseStrategy();
            tableUpdater.commit();
        }
        catch (CrudException e) {
            if (e.getCode() == 4000) {
                List combinations = param.getMasterKey().getDimensionCombinations();
                for (DimensionCombination combination : combinations) {
                    String dwCode = (String)combination.getValue(fieldRelation.getDwDimName());
                    result.getNoAuthDw().add(dwCode);
                }
                if (monitor != null) {
                    String detail = JsonUtil.objectToJson((Object)result);
                    monitor.finish("\u6240\u9009\u5355\u4f4d\u5728\u76ee\u6807\u4efb\u52a1\u53f0\u8d26\u8868" + title + "\u65e0\u6743\u9650\uff01", (Object)detail);
                }
                return result;
            }
        }
        catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("\u53f0\u8d26\u8868" + title + "\u6570\u636e\u6e05\u9664\u5931\u8d25\uff01" + e.getMessage());
            if (tableUpdater != null) {
                TzCarryUtils.convertResult(result, tableUpdater.getSaveRes(), null);
            }
            dataServiceLogger.error(param.getTaskKey(), logDimensionCollection, MODULE_TZ_DATA_CLEAR, "\u53f0\u8d26\u8868" + title + "\u6570\u636e\u6e05\u9664\u5931\u8d25\uff01");
            if (monitor != null) {
                String detail = JsonUtil.objectToJson((Object)result);
                monitor.error("\u53f0\u8d26\u8868" + title + "\u6570\u636e\u6e05\u9664\u5931\u8d25\uff01", (Throwable)e, detail);
            }
            logger.error("\u53f0\u8d26\u8868{},\u6e05\u9664\u6570\u636e\u5931\u8d25", (Object)title, (Object)e);
            return result;
        }
        if (tableUpdater != null) {
            TzCarryUtils.convertResult(result, tableUpdater.getSaveRes(), null);
        }
        String detail = JsonUtil.objectToJson((Object)result);
        if (monitor != null) {
            monitor.finish("\u53f0\u8d26\u8868" + title + "\u6570\u636e\u6e05\u9664\u5b8c\u6210\uff01", (Object)detail);
        }
        logger.info("\u53f0\u8d26\u8868{}\u6570\u636e\u6e05\u9664\u5b8c\u6210\uff01", (Object)title);
        dataServiceLogger.info(param.getTaskKey(), logDimensionCollection, MODULE_TZ_DATA_CLEAR, "\u53f0\u8d26\u8868" + title + "\u6570\u636e\u6e05\u9664\u5b8c\u6210\uff01");
        return result;
    }

    @Override
    public DataTableCarryResult carryDown2NextYear(TzCarryDownDTO param) {
        return this.carryDown2NextYear(param, null);
    }

    @Override
    public DataTableCarryResult carryDown2NextYear(TzCarryDownDTO param, AsyncTaskMonitor monitor) {
        FieldSort fieldSort;
        DataFileCheckInfo dataFileCheckInfo;
        boolean allowImportIllegalDataOption;
        DataServiceLogHelper dataServiceLogger = this.dataServiceLoggerFactory.getLogger(MODULE_TZ_CARRY_DOWN, OperLevel.USER_OPER);
        LogDimensionCollection logDimensionCollection = this.getLogDimensionCollection(param.getSourceMasterKey(), param.getSourceFormSchemeKey());
        List<ZBMapping> fieldMapping = param.getMappings();
        DataTableCarryResult result = new DataTableCarryResult();
        DataTable sourceDataTable = param.getDataTable();
        String title = sourceDataTable.getTitle();
        if (CollectionUtils.isEmpty(fieldMapping)) {
            result.setSuccess(false);
            result.setErrorMessage("\u53f0\u8d26\u8868" + title + "\u6ca1\u6709\u6307\u6807\u6620\u5c04\uff01");
            if (monitor != null) {
                String detail = JsonUtil.objectToJson((Object)result);
                monitor.error("\u53f0\u8d26\u8868" + title + "\u6570\u636e\u7ed3\u8f6c\u5931\u8d25\uff01", null, detail);
            }
            return result;
        }
        dataServiceLogger.info(param.getSourceTaskKey(), logDimensionCollection, MODULE_TZ_CARRY_DOWN, "\u53f0\u8d26\u8868" + title + "\u6570\u636e\u7ed3\u8f6c\u5f00\u59cb\uff01");
        logger.info("\u53f0\u8d26\u8868{}\u5f00\u59cb\u7ed3\u8f6c\uff01", (Object)title);
        if (monitor != null) {
            monitor.progressAndMessage(0.01, "\u53f0\u8d26\u8868" + title + "\u6570\u636e\u7ed3\u8f6c\u5f00\u59cb\uff01");
        }
        Map<String, DataField> sourceCode2Fields = this.runtimeDataSchemeService.getDataFieldByTable(sourceDataTable.getKey()).stream().collect(Collectors.toMap(Basic::getCode, a -> a));
        DataTable destDataTable = this.runtimeDataSchemeService.getDataTableByCode(this.getDestDataTableInfo(fieldMapping.get(0)));
        Map<String, DataField> destCode2Fields = this.runtimeDataSchemeService.getDataFieldByTable(destDataTable.getKey()).stream().collect(Collectors.toMap(Basic::getCode, a -> a));
        Map<String, String> source2Dest = this.getDestDataFieldInfo(fieldMapping);
        ArrayList<String> zbCodes = new ArrayList<String>(source2Dest.keySet());
        ArrayList<DataField> sourceFields = new ArrayList<DataField>();
        zbCodes.forEach(a -> sourceFields.add((DataField)sourceCode2Fields.get(a)));
        ArrayList<DataField> destFields = new ArrayList<DataField>();
        zbCodes.forEach(a -> destFields.add((DataField)destCode2Fields.get(source2Dest.get(a))));
        String value = this.iTaskOptionController.getValue(param.getDestTaskKey(), "IllegalDataImport_2132");
        boolean bl = allowImportIllegalDataOption = !value.equals("0");
        if (!allowImportIllegalDataOption && !(dataFileCheckInfo = TzCarryUtils.checkDataField(sourceFields, destFields)).isCheckSuccess()) {
            result.setSuccess(false);
            result.setDataFileCheckInfo(dataFileCheckInfo);
            dataServiceLogger.error(param.getSourceTaskKey(), logDimensionCollection, MODULE_TZ_CARRY_DOWN, "\u53f0\u8d26\u8868" + title + "\u53c2\u6570\u68c0\u67e5\u4e0d\u901a\u8fc7\uff01");
            logger.error("\u53f0\u8d26\u8868{},\u53c2\u6570\u68c0\u67e5\u4e0d\u901a\u8fc7\uff01", (Object)title);
            if (monitor != null) {
                String detail = JsonUtil.objectToJson((Object)result);
                monitor.error("\u53f0\u8d26\u8868" + title + "\u53c2\u6570\u68c0\u67e5\u4e0d\u901a\u8fc7\uff01", null, detail);
            }
            return result;
        }
        List publicDims = this.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(sourceDataTable.getKey(), new DataFieldKind[]{DataFieldKind.PUBLIC_FIELD_DIM});
        List tableDims = this.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(sourceDataTable.getKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
        List fieldKeys = sourceFields.stream().map(Basic::getKey).collect(Collectors.toList());
        fieldKeys.addAll(publicDims.stream().map(Basic::getKey).collect(Collectors.toList()));
        FieldQueryInfoBuilder fieldQueryInfoBuilder = FieldQueryInfoBuilder.create((DimensionCollection)param.getSourceMasterKey());
        fieldQueryInfoBuilder.setAuthType(ResouceType.NONE);
        fieldQueryInfoBuilder.select(fieldKeys);
        for (DataField publicDim : publicDims) {
            fieldSort = new FieldSort();
            fieldSort.setFieldKey(publicDim.getKey());
            fieldQueryInfoBuilder.orderBy(fieldSort);
        }
        for (DataField tableDim : tableDims) {
            fieldSort = new FieldSort();
            fieldSort.setFieldKey(tableDim.getKey());
            fieldQueryInfoBuilder.orderBy(fieldSort);
        }
        DataField floatOrder = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(sourceDataTable.getKey(), "FLOATORDER");
        if (floatOrder != null) {
            FieldSort fieldSort2 = new FieldSort();
            fieldSort2.setFieldKey(floatOrder.getKey());
            fieldQueryInfoBuilder.orderBy(fieldSort2);
        }
        IFieldQueryInfo build = fieldQueryInfoBuilder.build();
        IParamDataProvider paramDataProvider = TzCarryUtils.getParamDataProvider(param.getSourceTaskKey(), param.getSourceFormSchemeKey());
        IFieldDataService sourceService = this.fieldDataServiceFactory.getFieldDataFileService(paramDataProvider);
        TzDataReaderImpl dataReader = new TzDataReaderImpl(param, destFields, this.runtimeDataSchemeService, this.fieldRelationFactory, this.fieldDataServiceFactory);
        try {
            dataReader.initDestTableUpdater(allowImportIllegalDataOption);
            sourceService.queryTableData(build, (IDataReader)dataReader);
        }
        catch (CrudException e) {
            if (e.getCode() == 4000) {
                result = dataReader.getSaveRes();
                if (monitor != null) {
                    String detail = JsonUtil.objectToJson((Object)result);
                    monitor.finish("\u6240\u9009\u5355\u4f4d\u5728\u76ee\u6807\u4efb\u52a1\u53f0\u8d26\u8868" + title + "\u65e0\u6743\u9650\uff01", (Object)detail);
                }
                return result;
            }
        }
        catch (Exception e) {
            result = dataReader.getSaveRes();
            result.setSuccess(false);
            if (!StringUtils.hasLength(result.getErrorMessage())) {
                result.setErrorMessage("\u53f0\u8d26\u8868" + title + "\u6570\u636e\u6e05\u9664\u5931\u8d25\uff01" + e.getMessage());
            }
            dataServiceLogger.error(param.getSourceTaskKey(), logDimensionCollection, MODULE_TZ_CARRY_DOWN, "\u53f0\u8d26\u8868" + title + "\u6570\u636e\u7ed3\u8f6c\u5931\u8d25\uff01");
            logger.error("\u53f0\u8d26\u8868{},\u6570\u636e\u7ed3\u8f6c\u5931\u8d25", (Object)title, (Object)e);
            if (monitor != null) {
                String detail = JsonUtil.objectToJson((Object)result);
                monitor.error("\u53f0\u8d26\u8868" + title + "\u6570\u636e\u7ed3\u8f6c\u5931\u8d25\uff01", (Throwable)e, detail);
            }
            return result;
        }
        result = dataReader.getSaveRes();
        if (monitor != null) {
            String detail = JsonUtil.objectToJson((Object)result);
            monitor.finish("\u53f0\u8d26\u8868" + title + "\u6570\u636e\u7ed3\u8f6c\u5b8c\u6210", (Object)detail);
        }
        dataServiceLogger.info(param.getSourceTaskKey(), logDimensionCollection, MODULE_TZ_CARRY_DOWN, "\u53f0\u8d26\u8868" + title + "\u6570\u636e\u7ed3\u8f6c\u5b8c\u6210\uff01");
        logger.info("\u53f0\u8d26\u8868{}\u7ed3\u8f6c\u5b8c\u6210\uff01", (Object)title);
        return result;
    }

    private LogDimensionCollection getLogDimensionCollection(DimensionCollection masterKey, String formSchemeKey) {
        LogDimensionCollection result = new LogDimensionCollection();
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
        String entityDimension = this.entityMetaService.getDimensionName(formScheme.getDw());
        List dimensionCombines = masterKey.getDimensionCombinations();
        String[] targetKeys = new String[dimensionCombines.size()];
        for (int i = 0; i < dimensionCombines.size(); ++i) {
            DimensionCombination dimensionCombine = (DimensionCombination)dimensionCombines.get(i);
            Object value = dimensionCombine.getValue(entityDimension);
            targetKeys[i] = (String)value;
        }
        result.setDw(formScheme.getDw(), targetKeys);
        String periodCode = String.valueOf(((DimensionCombination)dimensionCombines.get(0)).getValue("DATATIME"));
        result.setPeriod(formScheme.getDateTime(), periodCode);
        return result;
    }

    private Map<String, String> getDestDataFieldInfo(List<ZBMapping> zbMappingsByTable) {
        HashMap<String, String> result = new HashMap<String, String>();
        for (ZBMapping zbMapping : zbMappingsByTable) {
            Matcher matcher = compile.matcher(zbMapping.getMapping());
            if (!matcher.find()) continue;
            result.put(zbMapping.getZbCode(), matcher.group(2));
        }
        return result;
    }

    private String getDestDataTableInfo(ZBMapping zbMapping) {
        Matcher matcher = compile.matcher(zbMapping.getMapping());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}

