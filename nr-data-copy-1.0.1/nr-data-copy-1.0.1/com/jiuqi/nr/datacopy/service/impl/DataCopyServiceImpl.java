/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.loader.ILoadListener
 *  com.jiuqi.bi.database.sql.loader.ITableLoader
 *  com.jiuqi.bi.database.sql.loader.LoadFieldMap
 *  com.jiuqi.bi.database.sql.loader.TableLoaderException
 *  com.jiuqi.bi.database.sql.loader.defaultdb.DefaultInsertAutoRownumLoader
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.tables.InnerTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.impl.LoggingTableLoadListener
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.attachment.factory.IFileCopyServiceFactory
 *  com.jiuqi.nr.attachment.input.FileCopyFixedParam
 *  com.jiuqi.nr.attachment.input.FileCopyFloatParam
 *  com.jiuqi.nr.attachment.message.FixedFieldAndGroupInfo
 *  com.jiuqi.nr.attachment.message.FloatFieldAndGroupInfo
 *  com.jiuqi.nr.attachment.provider.IFileCopyParamProvider
 *  com.jiuqi.nr.attachment.service.IFileCopyService
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.gather.util.GatherTempTableUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermission
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionResource
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.common.DataFieldMap
 *  com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter
 *  com.jiuqi.nr.dataservice.core.common.DimensionMapInfo
 *  com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.datacopy.service.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.ILoadListener;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.loader.defaultdb.DefaultInsertAutoRownumLoader;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.tables.InnerTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.impl.LoggingTableLoadListener;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.attachment.factory.IFileCopyServiceFactory;
import com.jiuqi.nr.attachment.input.FileCopyFixedParam;
import com.jiuqi.nr.attachment.input.FileCopyFloatParam;
import com.jiuqi.nr.attachment.message.FixedFieldAndGroupInfo;
import com.jiuqi.nr.attachment.message.FloatFieldAndGroupInfo;
import com.jiuqi.nr.attachment.provider.IFileCopyParamProvider;
import com.jiuqi.nr.attachment.service.IFileCopyService;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.gather.util.GatherTempTableUtils;
import com.jiuqi.nr.datacopy.param.CopyDataParam;
import com.jiuqi.nr.datacopy.param.CopyDataTable;
import com.jiuqi.nr.datacopy.param.CopyDataTableDefine;
import com.jiuqi.nr.datacopy.param.CopyDataTempTable;
import com.jiuqi.nr.datacopy.param.DataCopyParamProvider;
import com.jiuqi.nr.datacopy.param.DataCopyReturnInfo;
import com.jiuqi.nr.datacopy.param.DataFieldMappingConverterImpl;
import com.jiuqi.nr.datacopy.param.DimMappingConvertImpl;
import com.jiuqi.nr.datacopy.param.FileCopyParamProviderImpl;
import com.jiuqi.nr.datacopy.param.TempTableReturnInfo;
import com.jiuqi.nr.datacopy.param.monitor.IDataCopyMonitor;
import com.jiuqi.nr.datacopy.param.monitor.impl.DefaultDataCopyMonitor;
import com.jiuqi.nr.datacopy.service.IDataCopyService;
import com.jiuqi.nr.datacopy.util.CopyDataUtils;
import com.jiuqi.nr.datacopy.util.TempTableUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermission;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.common.DataFieldMap;
import com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter;
import com.jiuqi.nr.dataservice.core.common.DimensionMapInfo;
import com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

public class DataCopyServiceImpl
implements IDataCopyService {
    private DataCopyParamProvider dataCopyParamProvider;
    private IProviderStore providerStore;
    private TempTableUtils tempTableUtils;
    private QueryParam queryParam;
    private DimensionBuildUtil dimensionBuildUtil;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private DataModelService dataModelService;
    private IFileCopyServiceFactory fileCopyServiceFactory;
    private ITempTable bizKeyOrderTempTable = null;
    private static final Logger logger = LoggerFactory.getLogger(DataCopyServiceImpl.class);

    public DataCopyServiceImpl(DataCopyParamProvider dataCopyParamProvider, IProviderStore providerStore, TempTableUtils tempTableUtils, QueryParam queryParam, DimensionBuildUtil dimensionBuildUtil, IRuntimeDataSchemeService runtimeDataSchemeService, DataModelService dataModelService, IFileCopyServiceFactory fileCopyServiceFactory) {
        this.dataCopyParamProvider = dataCopyParamProvider;
        this.providerStore = providerStore;
        this.tempTableUtils = tempTableUtils;
        this.queryParam = queryParam;
        this.dimensionBuildUtil = dimensionBuildUtil;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.dataModelService = dataModelService;
        this.fileCopyServiceFactory = fileCopyServiceFactory;
    }

    @Override
    public DataCopyReturnInfo pushData(CopyDataParam copyDataParam) {
        return this.pushData(copyDataParam, new DefaultDataCopyMonitor(null));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public DataCopyReturnInfo pushData(CopyDataParam copyDataParam, IDataCopyMonitor monitor) {
        DataCopyReturnInfo dataCopyReturnInfo = new DataCopyReturnInfo();
        Assert.notNull((Object)copyDataParam.getMasterKey(), "\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)copyDataParam.getSourceTaskKey(), "\u6765\u6e90\u4efb\u52a1key\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)copyDataParam.getSourceFormSchemeKey(), "\u6765\u6e90\u62a5\u8868\u65b9\u6848key\u4e0d\u80fd\u4e3a\u7a7a");
        if (CollectionUtils.isEmpty(copyDataParam.getFormKeys())) {
            return dataCopyReturnInfo;
        }
        monitor.progressAndMessage(0.01, null);
        DsContext dsContext = DsContextHolder.getDsContext();
        DsContextImpl dsContextImpl = (DsContextImpl)dsContext;
        String tempEntityId = dsContextImpl.getContextEntityId();
        if (!copyDataParam.getSourceEntityId().equals(tempEntityId)) {
            dsContextImpl.setEntityId(copyDataParam.getSourceEntityId());
        }
        EvaluatorParam sourceEvaluatorParam = new EvaluatorParam();
        sourceEvaluatorParam.setTaskId(copyDataParam.getSourceTaskKey());
        sourceEvaluatorParam.setFormSchemeId(copyDataParam.getSourceFormSchemeKey());
        DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory = this.providerStore.getDataPermissionEvaluatorFactory();
        DataPermissionEvaluator evaluator = dataPermissionEvaluatorFactory.createEvaluator(sourceEvaluatorParam, copyDataParam.getMasterKey(), copyDataParam.getFormKeys());
        DataPermission dataPermission = evaluator.haveAccess(copyDataParam.getMasterKey(), copyDataParam.getFormKeys(), AuthType.READABLE);
        dsContextImpl.setEntityId(tempEntityId);
        Collection accessResources = dataPermission.getAccessResources();
        dataCopyReturnInfo.setUnAccessResources(dataPermission.getUnAccessResources());
        if (CollectionUtils.isEmpty(accessResources)) {
            monitor.error("\u6240\u9009\u7ef4\u5ea6\u5bf9\u6240\u9009\u8868\u5355\u65e0\u6743\u9650\uff01", null);
            logger.error("\u6240\u9009\u7ef4\u5ea6\u5bf9\u6240\u9009\u8868\u5355\u65e0\u6743\u9650\uff01");
            return dataCopyReturnInfo;
        }
        dataCopyReturnInfo.setAccessResources(accessResources);
        HashMap<String, List> formKey2Dims = new HashMap<String, List>();
        for (DataPermissionResource accessResource : accessResources) {
            formKey2Dims.computeIfAbsent(accessResource.getResourceId(), k -> new ArrayList()).add(accessResource.getDimensionCombination());
        }
        monitor.progressAndMessage(0.02, null);
        DimensionMappingConverter dimensionMappingConverter = this.dataCopyParamProvider.getDimensionMappingConverter();
        DataFieldMappingConverter dataFieldMappingConverter = this.dataCopyParamProvider.getDataFieldMappingConverter();
        EvaluatorParam targetEvaluatorParam = new EvaluatorParam();
        targetEvaluatorParam.setTaskId(copyDataParam.getTargetTaskKey());
        targetEvaluatorParam.setFormSchemeId(copyDataParam.getTargetFormSchemeKey());
        targetEvaluatorParam.setResourceType(ResouceType.ZB.getCode());
        HashSet<String> failFormKeys = new HashSet<String>();
        dataCopyReturnInfo.setFailFormKeys(failFormKeys);
        HashMap<String, String> bizKeyOrderMap = new HashMap<String, String>();
        dataCopyReturnInfo.setBizKeyOrderMap(bizKeyOrderMap);
        TempTableReturnInfo tempTableInfo = null;
        monitor.progressAndMessage(0.03, null);
        double item = 0.85 / (double)formKey2Dims.keySet().size();
        double cur = 0.03;
        for (Map.Entry entry : formKey2Dims.entrySet()) {
            monitor.progressAndMessage(cur += item, null);
            String formKey = (String)entry.getKey();
            List dimensionCombinations = (List)entry.getValue();
            List sourceDV = dimensionCombinations.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
            List source2TargetMap = dimensionMappingConverter.getMappingMasterKeys(sourceDV);
            if (CollectionUtils.isEmpty(source2TargetMap)) {
                failFormKeys.add(formKey);
                logger.error("\u672a\u627e\u5230\u7ef4\u5ea6\u6620\u5c04\uff01");
                continue;
            }
            HashMap<DimensionValueSet, DimensionValueSet> source2TargetDVMap = new HashMap<DimensionValueSet, DimensionValueSet>();
            for (DimensionMapInfo dimensionMapInfo : source2TargetMap) {
                source2TargetDVMap.put(dimensionMapInfo.getSource(), dimensionMapInfo.getTarget());
            }
            dsContextImpl.setEntityId(copyDataParam.getTargetEntityId());
            HashSet targetDV = new HashSet(source2TargetDVMap.values());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList(targetDV));
            DimensionCollection targetDimCollection = this.dimensionBuildUtil.getDimensionCollection(dimensionValueSet, copyDataParam.getTargetFormSchemeKey());
            List targetCombination = targetDimCollection.getDimensionCombinations();
            List<CopyDataTableDefine> copyDataTableDefineList = CopyDataUtils.getCopyDataTableDefineList(formKey);
            for (CopyDataTableDefine sourceTableDefine : copyDataTableDefineList) {
                ArrayList<CopyDataTableDefine> targetTableDefines = new ArrayList<CopyDataTableDefine>();
                HashMap<String, Map<String, String>> tagetTable2TargetFiled2SourceField = new HashMap<String, Map<String, String>>();
                this.getCopyDataTableDefines(sourceTableDefine, dataFieldMappingConverter, targetTableDefines, tagetTable2TargetFiled2SourceField);
                boolean hasFieldMapping = !CollectionUtils.isEmpty(targetTableDefines);
                List<DataField> targetFields = this.getFields(sourceTableDefine, targetTableDefines);
                List targetFieldKeys = targetFields.stream().map(Basic::getKey).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(targetFieldKeys)) {
                    failFormKeys.add(formKey);
                    logger.error("\u533a\u57df\uff1a{}\u672a\u627e\u5230\u53ef\u590d\u5236\u6570\u636e\u7684\u6307\u6807\uff01", (Object)sourceTableDefine.getRegionKey());
                    continue;
                }
                DataPermissionEvaluator targetEvaluator = dataPermissionEvaluatorFactory.createEvaluator(targetEvaluatorParam, targetDimCollection, targetFieldKeys);
                HashSet<DimensionValueSet> targetAccessDV = new HashSet<DimensionValueSet>();
                for (DimensionCombination dimensionCombination : targetCombination) {
                    boolean allFieldsHaveAuth = true;
                    for (String targetFieldKey : targetFieldKeys) {
                        if (targetEvaluator.haveAccess(dimensionCombination, targetFieldKey, AuthType.WRITEABLE)) continue;
                        allFieldsHaveAuth = false;
                        break;
                    }
                    if (!allFieldsHaveAuth) continue;
                    targetAccessDV.add(dimensionCombination.toDimensionValueSet());
                }
                dsContextImpl.setEntityId(tempEntityId);
                targetDV.removeAll(targetAccessDV);
                source2TargetDVMap.entrySet().removeIf(entry1 -> targetDV.contains(entry1.getValue()));
                if (CollectionUtils.isEmpty(source2TargetDVMap)) {
                    failFormKeys.add(formKey);
                    logger.error("\u5b58\u50a8\u8868\uff1a{}\u672a\u627e\u5230\u53ef\u590d\u5236\u6570\u636e\u7684\u7ef4\u5ea6\uff01", (Object)sourceTableDefine.getTableDefine().getTitle());
                    continue;
                }
                DimMappingConvertImpl fileDimensionMapping = new DimMappingConvertImpl(source2TargetDVMap);
                FileCopyParamProviderImpl fileCopyParamProvider = new FileCopyParamProviderImpl(dataFieldMappingConverter, fileDimensionMapping);
                if (tempTableInfo == null) {
                    tempTableInfo = this.tempTableUtils.createTempTable(sourceTableDefine, targetTableDefines);
                }
                this.tempTableUtils.insertData(tempTableInfo, source2TargetDVMap);
                List<CopyDataTable> sourceDataTables = this.splitTable(sourceTableDefine);
                List<CopyDataTable> targetDataTables = this.splitTable(targetTableDefines);
                if (!sourceTableDefine.isFixed()) {
                    CopyDataTable sourceDataTable = sourceDataTables.get(0);
                    CopyDataTable targetDataTable = !hasFieldMapping ? sourceDataTable : targetDataTables.get(0);
                    Map map = (Map)tagetTable2TargetFiled2SourceField.get(targetDataTable.getCopyDataTableDefine().getTableDefine().getCode());
                    this.executeFloatTableCopyData(copyDataParam, sourceDataTable, targetDataTable, tempTableInfo, map, fileCopyParamProvider, bizKeyOrderMap);
                } else if (!hasFieldMapping) {
                    for (CopyDataTable copyDataTable : sourceDataTables) {
                        ArrayList<CopyDataTable> dataTables = new ArrayList<CopyDataTable>();
                        dataTables.add(copyDataTable);
                        this.executeFixedTableCopyData(copyDataParam, dataTables, copyDataTable, tempTableInfo, null, fileCopyParamProvider);
                    }
                } else {
                    Map<CopyDataTable, List<CopyDataTable>> target2SourceTableMap = this.buildTarget2SourceMap(sourceDataTables, targetDataTables, true, tagetTable2TargetFiled2SourceField);
                    for (Map.Entry<CopyDataTable, List<CopyDataTable>> entry12 : target2SourceTableMap.entrySet()) {
                        CopyDataTable targetDataTable = entry12.getKey();
                        List<CopyDataTable> sourceDataTables1 = entry12.getValue();
                        Map map = (Map)tagetTable2TargetFiled2SourceField.get(targetDataTable.getCopyDataTableDefine().getTableDefine().getCode());
                        this.executeFixedTableCopyData(copyDataParam, sourceDataTables1, targetDataTable, tempTableInfo, map, fileCopyParamProvider);
                    }
                }
                try {
                    this.tempTableUtils.clearData(tempTableInfo);
                }
                catch (SQLException e) {
                    logger.error("\u4e34\u65f6\u8868\u590d\u7528-\u5220\u9664\u6570\u636e\u5931\u8d25\uff01", e);
                }
                continue;
                catch (Exception e) {
                    try {
                        failFormKeys.add(formKey);
                        logger.error("\u5b58\u50a8\u8868:{}\u590d\u5236\u6570\u636e\u5931\u8d25\uff01", (Object)sourceTableDefine.getTableDefine().getTitle(), (Object)e);
                    }
                    catch (Throwable throwable) {
                        try {
                            this.tempTableUtils.clearData(tempTableInfo);
                        }
                        catch (SQLException e2) {
                            logger.error("\u4e34\u65f6\u8868\u590d\u7528-\u5220\u9664\u6570\u636e\u5931\u8d25\uff01", e2);
                        }
                        throw throwable;
                    }
                    try {
                        this.tempTableUtils.clearData(tempTableInfo);
                    }
                    catch (SQLException e3) {
                        logger.error("\u4e34\u65f6\u8868\u590d\u7528-\u5220\u9664\u6570\u636e\u5931\u8d25\uff01", e3);
                    }
                }
            }
        }
        return dataCopyReturnInfo;
    }

    @Override
    public DataCopyReturnInfo pullData(CopyDataParam copyDataParam) {
        return this.pullData(copyDataParam, new DefaultDataCopyMonitor(null));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public DataCopyReturnInfo pullData(CopyDataParam copyDataParam, IDataCopyMonitor monitor) {
        DataCopyReturnInfo dataCopyReturnInfo = new DataCopyReturnInfo();
        Assert.notNull((Object)copyDataParam.getMasterKey(), "\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)copyDataParam.getTargetTaskKey(), "\u76ee\u6807\u4efb\u52a1key\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)copyDataParam.getTargetFormSchemeKey(), "\u76ee\u6807\u62a5\u8868\u65b9\u6848key\u4e0d\u80fd\u4e3a\u7a7a");
        if (CollectionUtils.isEmpty(copyDataParam.getFormKeys())) {
            return dataCopyReturnInfo;
        }
        monitor.progressAndMessage(0.01, null);
        DsContext dsContext = DsContextHolder.getDsContext();
        DsContextImpl dsContextImpl = (DsContextImpl)dsContext;
        String tempEntityId = dsContextImpl.getContextEntityId();
        if (!copyDataParam.getTargetEntityId().equals(tempEntityId)) {
            dsContextImpl.setEntityId(copyDataParam.getTargetEntityId());
        }
        EvaluatorParam targetEvaluatorParam = new EvaluatorParam();
        targetEvaluatorParam.setTaskId(copyDataParam.getTargetTaskKey());
        targetEvaluatorParam.setFormSchemeId(copyDataParam.getTargetFormSchemeKey());
        DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory = this.providerStore.getDataPermissionEvaluatorFactory();
        DataPermissionEvaluator evaluator = dataPermissionEvaluatorFactory.createEvaluator(targetEvaluatorParam, copyDataParam.getMasterKey(), copyDataParam.getFormKeys());
        DataPermission dataPermission = evaluator.haveAccess(copyDataParam.getMasterKey(), copyDataParam.getFormKeys(), AuthType.WRITEABLE);
        dsContextImpl.setEntityId(tempEntityId);
        Collection accessResources = dataPermission.getAccessResources();
        dataCopyReturnInfo.setUnAccessResources(dataPermission.getUnAccessResources());
        if (CollectionUtils.isEmpty(accessResources)) {
            monitor.error("\u6240\u9009\u7ef4\u5ea6\u5bf9\u6240\u9009\u8868\u5355\u65e0\u6743\u9650\uff01", null);
            logger.error("\u6240\u9009\u7ef4\u5ea6\u5bf9\u6240\u9009\u8868\u5355\u65e0\u6743\u9650\uff01");
            return dataCopyReturnInfo;
        }
        dataCopyReturnInfo.setAccessResources(accessResources);
        HashMap<String, List> formKey2Dims = new HashMap<String, List>();
        for (DataPermissionResource accessResource : accessResources) {
            formKey2Dims.computeIfAbsent(accessResource.getResourceId(), k -> new ArrayList()).add(accessResource.getDimensionCombination());
        }
        monitor.progressAndMessage(0.02, null);
        DimensionMappingConverter dimensionMappingConverter = this.dataCopyParamProvider.getDimensionMappingConverter();
        DataFieldMappingConverter dataFieldMappingConverter = this.dataCopyParamProvider.getDataFieldMappingConverter();
        EvaluatorParam sourceEvaluatorParam = new EvaluatorParam();
        sourceEvaluatorParam.setTaskId(copyDataParam.getSourceTaskKey());
        sourceEvaluatorParam.setFormSchemeId(copyDataParam.getSourceFormSchemeKey());
        sourceEvaluatorParam.setResourceType(ResouceType.ZB.getCode());
        HashSet<String> failFormKeys = new HashSet<String>();
        dataCopyReturnInfo.setFailFormKeys(failFormKeys);
        HashMap<String, String> bizKeyOrderMap = new HashMap<String, String>();
        dataCopyReturnInfo.setBizKeyOrderMap(bizKeyOrderMap);
        TempTableReturnInfo tempTableInfo = null;
        monitor.progressAndMessage(0.03, null);
        double item = 0.85 / (double)formKey2Dims.keySet().size();
        double cur = 0.03;
        block11: for (Map.Entry entry : formKey2Dims.entrySet()) {
            monitor.progressAndMessage(cur += item, null);
            String formKey = (String)entry.getKey();
            List dimensionCombinations = (List)entry.getValue();
            List targetDV = dimensionCombinations.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
            List dimMappings = dimensionMappingConverter.getMappingMasterKeys(targetDV);
            if (CollectionUtils.isEmpty(dimMappings)) {
                failFormKeys.add(formKey);
                logger.error("\u672a\u627e\u5230\u7ef4\u5ea6\u6620\u5c04\uff01");
                continue;
            }
            HashMap<DimensionValueSet, DimensionValueSet> source2TargetDVMap = new HashMap<DimensionValueSet, DimensionValueSet>();
            for (DimensionMapInfo dimensionMapInfo : dimMappings) {
                source2TargetDVMap.put(dimensionMapInfo.getSource(), dimensionMapInfo.getTarget());
            }
            dsContextImpl.setEntityId(copyDataParam.getSourceEntityId());
            HashSet sourceDV = new HashSet(source2TargetDVMap.keySet());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList(sourceDV));
            DimensionCollection sourceDimCollection = this.dimensionBuildUtil.getDimensionCollection(dimensionValueSet, copyDataParam.getSourceFormSchemeKey());
            List sourceCombination = sourceDimCollection.getDimensionCombinations();
            List<CopyDataTableDefine> copyDataTableDefineList = CopyDataUtils.getCopyDataTableDefineList(formKey);
            for (CopyDataTableDefine targetTableDefine : copyDataTableDefineList) {
                HashMap<String, Map<String, String>> sourceTable2SourceFiled2TargetField = new HashMap<String, Map<String, String>>();
                ArrayList<CopyDataTableDefine> sourceTableDefines = new ArrayList<CopyDataTableDefine>();
                this.getCopyDataTableDefines(targetTableDefine, dataFieldMappingConverter, sourceTableDefines, sourceTable2SourceFiled2TargetField);
                boolean hasFieldMapping = !CollectionUtils.isEmpty(sourceTableDefines);
                List<DataField> sourceFields = this.getFields(targetTableDefine, sourceTableDefines);
                List sourceFieldKeys = sourceFields.stream().map(Basic::getKey).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(sourceFieldKeys)) {
                    failFormKeys.add(formKey);
                    logger.error("\u533a\u57df\uff1a{}\u672a\u627e\u5230\u53ef\u590d\u5236\u6570\u636e\u7684\u6307\u6807\uff01", (Object)targetTableDefine.getRegionKey());
                    continue;
                }
                DataPermissionEvaluator targetEvaluator = dataPermissionEvaluatorFactory.createEvaluator(sourceEvaluatorParam, sourceDimCollection, sourceFieldKeys);
                HashSet<DimensionValueSet> sourceAccessDV = new HashSet<DimensionValueSet>();
                for (DimensionCombination dimensionCombination : sourceCombination) {
                    boolean allFieldsHaveAuth = true;
                    for (String sourceFieldKey : sourceFieldKeys) {
                        if (targetEvaluator.haveAccess(dimensionCombination, sourceFieldKey, AuthType.READABLE)) continue;
                        allFieldsHaveAuth = false;
                        break;
                    }
                    if (!allFieldsHaveAuth) continue;
                    sourceAccessDV.add(dimensionCombination.toDimensionValueSet());
                }
                dsContextImpl.setEntityId(tempEntityId);
                sourceDV.removeAll(sourceAccessDV);
                source2TargetDVMap.entrySet().removeIf(entry1 -> sourceDV.contains(entry1.getKey()));
                if (CollectionUtils.isEmpty(source2TargetDVMap)) {
                    failFormKeys.add(formKey);
                    logger.error("\u5b58\u50a8\u8868\uff1a{}\u672a\u627e\u5230\u53ef\u590d\u5236\u6570\u636e\u7684\u7ef4\u5ea6\uff01", (Object)targetTableDefine.getTableDefine().getTitle());
                    continue;
                }
                DimMappingConvertImpl fileDimensionMapping = new DimMappingConvertImpl(source2TargetDVMap);
                DataFieldMappingConverter fieldMappingConverter = hasFieldMapping ? this.buildFieldMappingConverter(targetTableDefine, sourceTable2SourceFiled2TargetField) : null;
                FileCopyParamProviderImpl fileCopyParamProvider = new FileCopyParamProviderImpl(fieldMappingConverter, fileDimensionMapping);
                CopyDataTableDefine sourceTableDefineModel = !hasFieldMapping ? targetTableDefine : (CopyDataTableDefine)sourceTableDefines.get(0);
                ArrayList<CopyDataTableDefine> targetTableDefines = new ArrayList<CopyDataTableDefine>();
                targetTableDefines.add(targetTableDefine);
                if (tempTableInfo == null) {
                    tempTableInfo = this.tempTableUtils.createTempTable(sourceTableDefineModel, targetTableDefines);
                }
                this.tempTableUtils.insertData(tempTableInfo, source2TargetDVMap);
                List<CopyDataTable> sourceDataTables = this.splitTable(sourceTableDefines);
                List<CopyDataTable> targetDataTables = this.splitTable(targetTableDefines);
                if (!targetTableDefine.isFixed()) {
                    CopyDataTable targetDataTable = targetDataTables.get(0);
                    CopyDataTable sourceDataTable = !hasFieldMapping ? targetDataTable : sourceDataTables.get(0);
                    Map<String, String> target2Source = this.getTarget2SourceFiledMap(sourceTable2SourceFiled2TargetField, sourceDataTable);
                    this.executeFloatTableCopyData(copyDataParam, sourceDataTable, targetDataTable, tempTableInfo, target2Source, fileCopyParamProvider, bizKeyOrderMap);
                } else if (!hasFieldMapping) {
                    for (CopyDataTable copyDataTable : targetDataTables) {
                        ArrayList<CopyDataTable> dataTables = new ArrayList<CopyDataTable>();
                        dataTables.add(copyDataTable);
                        this.executeFixedTableCopyData(copyDataParam, dataTables, copyDataTable, tempTableInfo, null, fileCopyParamProvider);
                    }
                } else {
                    Map<CopyDataTable, List<CopyDataTable>> target2SourceTableMap = this.buildTarget2SourceMap(sourceDataTables, targetDataTables, false, sourceTable2SourceFiled2TargetField);
                    for (Map.Entry<CopyDataTable, List<CopyDataTable>> entry12 : target2SourceTableMap.entrySet()) {
                        CopyDataTable targetDataTable = entry12.getKey();
                        List<CopyDataTable> sourceDataTables1 = entry12.getValue();
                        Map<String, String> target2Source = this.getTarget2SourceFiledMap(sourceTable2SourceFiled2TargetField, sourceDataTables1.get(0));
                        this.executeFixedTableCopyData(copyDataParam, sourceDataTables1, targetDataTable, tempTableInfo, target2Source, fileCopyParamProvider);
                    }
                }
                try {
                    this.tempTableUtils.clearData(tempTableInfo);
                }
                catch (SQLException e) {
                    logger.error("\u4e34\u65f6\u8868\u590d\u7528-\u5220\u9664\u6570\u636e\u5931\u8d25\uff01", e);
                }
                continue;
                catch (Exception e) {
                    try {
                        failFormKeys.add(formKey);
                        logger.error("\u5b58\u50a8\u8868\uff1a{}\u590d\u5236\u6570\u636e\u5931\u8d25\uff01", (Object)targetTableDefine.getTableDefine().getTitle(), (Object)e);
                    }
                    catch (Throwable throwable) {
                        try {
                            this.tempTableUtils.clearData(tempTableInfo);
                        }
                        catch (SQLException e2) {
                            logger.error("\u4e34\u65f6\u8868\u590d\u7528-\u5220\u9664\u6570\u636e\u5931\u8d25\uff01", e2);
                        }
                        throw throwable;
                    }
                    try {
                        this.tempTableUtils.clearData(tempTableInfo);
                    }
                    catch (SQLException e3) {
                        logger.error("\u4e34\u65f6\u8868\u590d\u7528-\u5220\u9664\u6570\u636e\u5931\u8d25\uff01", e3);
                    }
                    continue block11;
                }
            }
        }
        monitor.finish("\u6570\u636e\u590d\u5236\u5b8c\u6210\uff01", null);
        return dataCopyReturnInfo;
    }

    private List<DataField> getFields(CopyDataTableDefine targetTableDefine, List<CopyDataTableDefine> sourceTableDefines) {
        ArrayList<DataField> sourceFields = new ArrayList<DataField>();
        if (!CollectionUtils.isEmpty(sourceTableDefines)) {
            for (CopyDataTableDefine sourceTableDefine : sourceTableDefines) {
                sourceFields.addAll(sourceTableDefine.getCopyDataFields());
            }
        } else {
            sourceFields.addAll(targetTableDefine.getCopyDataFields());
        }
        return sourceFields;
    }

    private void getCopyDataTableDefines(CopyDataTableDefine curTableDefine, DataFieldMappingConverter dataFieldMappingConverter, List<CopyDataTableDefine> mappingTableDefines, Map<String, Map<String, String>> sourceTable2SourceFiled2TargetField) {
        List filedCodes = curTableDefine.getCopyDataFields().stream().map(Basic::getCode).collect(Collectors.toList());
        if (dataFieldMappingConverter != null) {
            Map dataFieldMapByTable = dataFieldMappingConverter.getDataFieldMapByTable(curTableDefine.getTableDefine().getCode(), filedCodes);
            mappingTableDefines.addAll(this.getMappingTableDefineList(curTableDefine, dataFieldMapByTable, sourceTable2SourceFiled2TargetField));
        } else {
            CopyDataUtils.setCopyDataTableDefine(curTableDefine);
        }
    }

    private DataFieldMappingConverter buildFieldMappingConverter(CopyDataTableDefine targetTableDefine, Map<String, Map<String, String>> sourceTable2SourceFiled2TargetField) {
        Set<String> sourceTableCodes = sourceTable2SourceFiled2TargetField.keySet();
        HashMap<String, String> sourceTable2TargetTable = new HashMap<String, String>();
        for (String sourceTableCode : sourceTableCodes) {
            sourceTable2TargetTable.put(sourceTableCode, targetTableDefine.getTableDefine().getCode());
        }
        return new DataFieldMappingConverterImpl(sourceTable2TargetTable, sourceTable2SourceFiled2TargetField);
    }

    private Map<String, String> getTarget2SourceFiledMap(Map<String, Map<String, String>> sourceTable2SourceFiled2TargetField, CopyDataTable sourceDataTable) {
        Map<String, String> map = sourceTable2SourceFiled2TargetField.get(sourceDataTable.getCopyDataTableDefine().getTableDefine().getCode());
        HashMap<String, String> target2Source = new HashMap<String, String>();
        if (!CollectionUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry2 : map.entrySet()) {
                target2Source.put(entry2.getValue(), entry2.getKey());
            }
        }
        return target2Source;
    }

    private Map<CopyDataTable, List<CopyDataTable>> buildTarget2SourceMap(List<CopyDataTable> sourceDataTables, List<CopyDataTable> targetDataTables, boolean isPush, Map<String, Map<String, String>> table2Filed2CurField) {
        HashMap<CopyDataTable, List<CopyDataTable>> target2SourceTableMap = new HashMap<CopyDataTable, List<CopyDataTable>>();
        HashMap<String, Integer> field2TableIndex = new HashMap<String, Integer>();
        if (isPush) {
            for (CopyDataTable sourceDataTable : sourceDataTables) {
                List<ColumnModelDefine> copyDataColModel = sourceDataTable.getCopyDataColModel();
                for (ColumnModelDefine columnModelDefine : copyDataColModel) {
                    field2TableIndex.put(columnModelDefine.getCode(), sourceDataTables.indexOf(sourceDataTable));
                }
            }
            for (CopyDataTable targetDataTable : targetDataTables) {
                CopyDataTableDefine copyDataTableDefine = targetDataTable.getCopyDataTableDefine();
                DataTable tableDefine = copyDataTableDefine.getTableDefine();
                Map<String, String> target2Source = table2Filed2CurField.get(tableDefine.getCode());
                List<ColumnModelDefine> copyDataColModel = targetDataTable.getCopyDataColModel();
                HashSet<CopyDataTable> sourceSet = new HashSet<CopyDataTable>();
                for (ColumnModelDefine columnModelDefine : copyDataColModel) {
                    String sourceFieldCode = target2Source.get(columnModelDefine.getCode());
                    Integer sourceTableIndex = (Integer)field2TableIndex.get(sourceFieldCode);
                    sourceSet.add(sourceDataTables.get(sourceTableIndex));
                }
                target2SourceTableMap.put(targetDataTable, new ArrayList(sourceSet));
            }
        } else {
            List<ColumnModelDefine> copyDataColModel;
            for (CopyDataTable targetDataTable : targetDataTables) {
                copyDataColModel = targetDataTable.getCopyDataColModel();
                for (ColumnModelDefine columnModelDefine : copyDataColModel) {
                    field2TableIndex.put(columnModelDefine.getCode(), targetDataTables.indexOf(targetDataTable));
                }
            }
            for (CopyDataTable sourceDataTable : sourceDataTables) {
                copyDataColModel = sourceDataTable.getCopyDataColModel();
                CopyDataTableDefine copyDataTableDefine = sourceDataTable.getCopyDataTableDefine();
                DataTable tableDefine = copyDataTableDefine.getTableDefine();
                Map<String, String> map = table2Filed2CurField.get(tableDefine.getCode());
                for (ColumnModelDefine columnModelDefine : copyDataColModel) {
                    List<CopyDataTable> copyDataTables;
                    String curFieldCode = map.get(columnModelDefine.getCode());
                    Integer index = (Integer)field2TableIndex.get(curFieldCode);
                    CopyDataTable copyDataTable = targetDataTables.get(index);
                    if (target2SourceTableMap.containsKey(copyDataTable)) {
                        copyDataTables = (List)target2SourceTableMap.get(copyDataTable);
                        if (copyDataTables.contains(sourceDataTable)) continue;
                        copyDataTables.add(sourceDataTable);
                        continue;
                    }
                    copyDataTables = new ArrayList();
                    copyDataTables.add(sourceDataTable);
                    target2SourceTableMap.put(copyDataTable, copyDataTables);
                }
            }
        }
        return target2SourceTableMap;
    }

    private void executeFixedTableCopyData(CopyDataParam copyDataParam, List<CopyDataTable> sourceTables, CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, Map<String, String> targetField2SourceField, IFileCopyParamProvider fileCopyParamProvider) throws Exception {
        List<DataField> attachmentFields = this.getTargetAttachmentFields(targetTable);
        ITempTable fileCopyTempTable = null;
        if (!CollectionUtils.isEmpty(attachmentFields)) {
            IFileCopyService fileCopyService = this.fileCopyServiceFactory.getFileCopyService(fileCopyParamProvider);
            FileCopyFixedParam fileCopyParam = this.buildFixedFileCopyParam(copyDataParam, targetTable, tempTableInfo, attachmentFields);
            List<DataField> sourceAttachmentFields = this.getSourceAttachmentFields(attachmentFields, targetField2SourceField, sourceTables);
            String sql = this.buildGroupKeySql2CopyFixed(sourceTables, tempTableInfo, sourceAttachmentFields);
            ArrayList<FixedFieldAndGroupInfo> fixedFieldAndGroupInfos = new ArrayList<FixedFieldAndGroupInfo>();
            CopyDataTableDefine copyDataTableDefine = sourceTables.get(0).getCopyDataTableDefine();
            List<DataField> dimFields = this.getDimFields(copyDataTableDefine);
            try (PreparedStatement prep = this.queryParam.getConnection().prepareStatement(sql);
                 ResultSet resultSet = prep.executeQuery();){
                while (resultSet.next()) {
                    FixedFieldAndGroupInfo info = new FixedFieldAndGroupInfo();
                    Object[] dimValue = new Object[dimFields.size()];
                    for (int i = 0; i < dimFields.size(); ++i) {
                        dimValue[i] = resultSet.getObject(this.queryFieldName(dimFields.get(i)));
                    }
                    info.setDims(dimValue);
                    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                    for (DataField dataField : sourceAttachmentFields) {
                        map.put(dataField.getKey(), resultSet.getString(this.queryFieldName(dataField)));
                    }
                    info.setFieldGroupMap(map);
                    fixedFieldAndGroupInfos.add(info);
                }
            }
            fileCopyParam.setFixedFieldAndGroupInfos(fixedFieldAndGroupInfos);
            List fixedFieldAndGroupInfos1 = fileCopyService.batchCopyFile(fileCopyParam);
            fileCopyTempTable = this.getFileCopyTempTable(copyDataTableDefine, sourceAttachmentFields);
            List<Object[]> batchValues = this.tempTableUtils.buildFixedTempValues(fixedFieldAndGroupInfos1, fileCopyTempTable, sourceAttachmentFields);
            fileCopyTempTable.insertRecords(batchValues);
        }
        this.clearTargetFixedTableData(targetTable, tempTableInfo);
        this.executeInsertFixedTableData(sourceTables, targetTable, tempTableInfo, targetField2SourceField, fileCopyTempTable);
    }

    private List<DataField> getDimFields(CopyDataTableDefine tableDefine) {
        ArrayList<DataField> dimFields = new ArrayList<DataField>();
        dimFields.add(tableDefine.getUnitField());
        dimFields.add(tableDefine.getPeriodField());
        if (!CollectionUtils.isEmpty(tableDefine.getPublicDimFields())) {
            dimFields.addAll(tableDefine.getPublicDimFields());
        }
        return dimFields;
    }

    private void clearTargetFixedTableData(CopyDataTable targetTable, TempTableReturnInfo tempTableInfo) throws SQLException {
        List<String> strings = this.buildClearFixedTableSql(targetTable, tempTableInfo);
        for (String sql : strings) {
            this.executeSql(this.getRealTableName(targetTable), sql);
        }
    }

    private List<String> buildClearFixedTableSql(CopyDataTable targetTable, TempTableReturnInfo tempTableInfo) {
        String realColName;
        ArrayList<String> clearSql = new ArrayList<String>();
        List<String> targetColName = tempTableInfo.getTargetColName();
        StringBuilder updateSql = new StringBuilder();
        ITempTable tempTable = tempTableInfo.getTempTable();
        updateSql.append("update ").append(targetTable.getTableModelDefine().getName()).append(" t1");
        updateSql.append(" set ");
        for (ColumnModelDefine columnModelDefine : targetTable.getCopyDataColModel()) {
            updateSql.append(columnModelDefine.getCode()).append("=null,");
        }
        updateSql.setLength(updateSql.length() - 1);
        updateSql.append(" where exists (select 1 from ").append(tempTable.getTableName()).append(" t2 where");
        for (String targetCol : targetColName) {
            String realColName2 = tempTable.getRealColName("TARGET_" + targetCol.toUpperCase());
            updateSql.append(" t1.").append(targetCol).append("=t2.").append(realColName2).append(" and");
        }
        updateSql.setLength(updateSql.length() - 4);
        updateSql.append(")");
        clearSql.add(updateSql.toString());
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ").append(targetTable.getTableModelDefine().getName()).append("(");
        for (String targetCol : targetColName) {
            insertSql.append(targetCol).append(",");
        }
        insertSql.setLength(insertSql.length() - 1);
        insertSql.append(") select distinct ");
        for (String targetCol : targetColName) {
            realColName = tempTable.getRealColName("TARGET_" + targetCol.toUpperCase());
            insertSql.append(realColName).append(" as ").append(targetCol.toUpperCase()).append(",");
        }
        insertSql.setLength(insertSql.length() - 1);
        insertSql.append(" from ").append(tempTable.getTableName());
        insertSql.append(" where not exists (select 1 from ").append(targetTable.getTableModelDefine().getName()).append(" t1 where");
        for (String targetCol : targetColName) {
            realColName = tempTable.getRealColName("TARGET_" + targetCol.toUpperCase());
            insertSql.append(" t1.").append(targetCol).append(" = ").append(realColName).append(" and");
        }
        insertSql.setLength(insertSql.length() - 4);
        insertSql.append(")");
        clearSql.add(insertSql.toString());
        return clearSql;
    }

    private void executeInsertFixedTableData(List<CopyDataTable> sourceTable, CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, Map<String, String> targetFiled2SourceField, ITempTable fileCopyTempTable) throws Exception {
        String selectSql = this.createSelectSql(sourceTable, targetTable, tempTableInfo, targetFiled2SourceField, fileCopyTempTable);
        this.doUpdateFixedTable(targetTable, selectSql, tempTableInfo);
    }

    private String createSelectSql(List<CopyDataTable> sourceTables, CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, Map<String, String> targetFiled2SourceField, ITempTable fileCopyTempTable) {
        HashMap<String, Integer> sourceField2TableIndex = new HashMap<String, Integer>();
        HashMap<Integer, Iterator<ColumnModelDefine>> tableIndex2FiledMap = new HashMap<Integer, Iterator<ColumnModelDefine>>();
        for (CopyDataTable sourceTable : sourceTables) {
            List<ColumnModelDefine> copyDataColModel = sourceTable.getCopyDataColModel();
            Iterator<ColumnModelDefine> collect = new HashMap();
            int index = sourceTables.indexOf(sourceTable);
            for (ColumnModelDefine columnModelDefine : copyDataColModel) {
                sourceField2TableIndex.put(columnModelDefine.getCode(), index);
                collect.put(columnModelDefine.getCode(), columnModelDefine);
            }
            tableIndex2FiledMap.put(index, collect);
        }
        if (CollectionUtils.isEmpty(targetFiled2SourceField)) {
            targetFiled2SourceField = new HashMap<String, String>();
            for (ColumnModelDefine columnModelDefine : targetTable.getCopyDataColModel()) {
                targetFiled2SourceField.put(columnModelDefine.getCode(), columnModelDefine.getCode());
            }
        }
        StringBuilder selectSql = new StringBuilder();
        ITempTable tempTable = tempTableInfo.getTempTable();
        selectSql.append(" select ");
        String alias = "s";
        for (String colName : tempTableInfo.getTargetColName()) {
            String realColName = tempTable.getRealColName("TARGET_" + colName.toUpperCase());
            selectSql.append("t1.").append(realColName).append(" as ").append(colName.toUpperCase()).append(",");
        }
        for (ColumnModelDefine target : targetTable.getCopyDataColModel()) {
            String sourceFieldCode = targetFiled2SourceField.get(target.getCode());
            Integer index = (Integer)sourceField2TableIndex.get(sourceFieldCode);
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)((Map)tableIndex2FiledMap.get(index)).get(sourceFieldCode);
            String aliasName = alias + (index + 1);
            if (target.getColumnType() == ColumnModelType.ATTACHMENT) {
                if (fileCopyTempTable == null) continue;
                selectSql.append("t2.").append(columnModelDefine.getCode()).append(" as ").append(this.queryFieldName(target)).append(",");
                continue;
            }
            selectSql.append(aliasName).append(".").append(this.queryFieldName(columnModelDefine)).append(" as ").append(this.queryFieldName(target)).append(",");
        }
        selectSql.setLength(selectSql.length() - 1);
        selectSql.append(" from ").append(tempTable.getTableName()).append(" t1");
        for (int i = 0; i < sourceTables.size(); ++i) {
            CopyDataTable copyDataTable = sourceTables.get(i);
            String aliasName = " " + alias + (i + 1);
            selectSql.append(" inner join ").append(this.getRealTableName(copyDataTable)).append(aliasName).append(" on ");
            for (String source : tempTableInfo.getSourceColName()) {
                selectSql.append(aliasName).append(".").append(source).append("=t1.").append(tempTable.getRealColName(source)).append(" and ");
            }
            selectSql.setLength(selectSql.length() - 5);
        }
        if (fileCopyTempTable != null) {
            selectSql.append(" left join ").append(fileCopyTempTable.getTableName()).append(" t2 on");
            CopyDataTableDefine sourceTableDefine = sourceTables.get(0).getCopyDataTableDefine();
            List<DataField> dimFields = this.getDimFields(sourceTableDefine);
            for (DataField dataField : dimFields) {
                String code = dataField.getCode();
                selectSql.append(" t1.").append(tempTable.getRealColName(code)).append("=t2.").append(fileCopyTempTable.getRealColName(code)).append(" and");
            }
            selectSql.setLength(selectSql.length() - 4);
        }
        return selectSql.toString();
    }

    private void executeFloatTableCopyData(CopyDataParam copyDataParam, CopyDataTable sourceTable, CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, Map<String, String> targetField2SourceField, IFileCopyParamProvider fileCopyParamProvider, Map<String, String> bizKeyOrderMap) throws Exception {
        List<DataField> attachmentFields = this.getTargetAttachmentFields(targetTable);
        ITempTable fileCopyTempTable = null;
        if (!CollectionUtils.isEmpty(attachmentFields)) {
            CopyDataTableDefine sourceDefine = sourceTable.getCopyDataTableDefine();
            IFileCopyService fileCopyService = this.fileCopyServiceFactory.getFileCopyService(fileCopyParamProvider);
            FileCopyFloatParam fileCopyParam = this.buildFloatFileCopyParam(copyDataParam, targetTable, tempTableInfo, attachmentFields);
            List<DataField> sourceAttachmentFields = this.getSourceAttachmentFields(attachmentFields, targetField2SourceField, sourceTable);
            String getGroupKey = this.buildGroupKeySql2CopyFloat(sourceTable, tempTableInfo, sourceAttachmentFields);
            ArrayList<FloatFieldAndGroupInfo> floatFieldAndGroupInfos = new ArrayList<FloatFieldAndGroupInfo>();
            try (PreparedStatement prep = this.queryParam.getConnection().prepareStatement(getGroupKey);
                 ResultSet resultSet = prep.executeQuery();){
                while (resultSet.next()) {
                    FloatFieldAndGroupInfo info = new FloatFieldAndGroupInfo();
                    info.setBizKey(resultSet.getString(this.queryFieldName(sourceDefine.getBizOrderField())));
                    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                    for (DataField dataField : sourceAttachmentFields) {
                        map.put(dataField.getKey(), resultSet.getString(this.queryFieldName(dataField)));
                    }
                    info.setFieldGroupMap(map);
                    floatFieldAndGroupInfos.add(info);
                }
            }
            fileCopyParam.setFloatFieldAndGroupInfos(floatFieldAndGroupInfos);
            List floatFieldAndGroupInfos1 = fileCopyService.batchCopyFile(fileCopyParam);
            fileCopyTempTable = this.getFileCopyTempTable(sourceDefine, sourceAttachmentFields);
            List<Object[]> objects = this.tempTableUtils.buildFloatTempValues(floatFieldAndGroupInfos1, fileCopyTempTable, sourceAttachmentFields);
            fileCopyTempTable.insertRecords(objects);
        }
        this.clearTargetFloatTableData(targetTable, tempTableInfo);
        this.executeInsertFloatTableData(sourceTable, targetTable, tempTableInfo, targetField2SourceField, fileCopyTempTable, bizKeyOrderMap);
    }

    private FileCopyFloatParam buildFloatFileCopyParam(CopyDataParam copyDataParam, CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, List<DataField> attachmentFields) throws SQLException {
        FileCopyFloatParam fileCopyParam = new FileCopyFloatParam();
        fileCopyParam.setFromTaskKey(copyDataParam.getSourceTaskKey());
        fileCopyParam.setToTaskKey(copyDataParam.getTargetTaskKey());
        fileCopyParam.setToFormSchemeKey(copyDataParam.getTargetFormSchemeKey());
        ArrayList<String> attachmentGroupKeys = new ArrayList<String>();
        ArrayList<String> picGroupKeys = new ArrayList<String>();
        this.getDeleteGroupKey(targetTable, tempTableInfo, attachmentFields, attachmentGroupKeys, picGroupKeys);
        fileCopyParam.setOldFileGroupKeys(attachmentGroupKeys);
        fileCopyParam.setOldPicGroupKeys(picGroupKeys);
        return fileCopyParam;
    }

    private FileCopyFixedParam buildFixedFileCopyParam(CopyDataParam copyDataParam, CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, List<DataField> attachmentFields) throws SQLException {
        FileCopyFixedParam fileCopyParam = new FileCopyFixedParam();
        fileCopyParam.setFromTaskKey(copyDataParam.getSourceTaskKey());
        fileCopyParam.setToTaskKey(copyDataParam.getTargetTaskKey());
        fileCopyParam.setToFormSchemeKey(copyDataParam.getTargetFormSchemeKey());
        ArrayList<String> attachmentGroupKeys = new ArrayList<String>();
        ArrayList<String> picGroupKeys = new ArrayList<String>();
        this.getDeleteGroupKey(targetTable, tempTableInfo, attachmentFields, attachmentGroupKeys, picGroupKeys);
        fileCopyParam.setOldFileGroupKeys(attachmentGroupKeys);
        fileCopyParam.setOldPicGroupKeys(picGroupKeys);
        return fileCopyParam;
    }

    private List<DataField> getTargetAttachmentFields(CopyDataTable targetTable) {
        CopyDataTableDefine targetDefine = targetTable.getCopyDataTableDefine();
        return targetDefine.getCopyDataFields().stream().filter(e -> e.getDataFieldType() == DataFieldType.PICTURE || e.getDataFieldType() == DataFieldType.FILE).collect(Collectors.toList());
    }

    private void getDeleteGroupKey(CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, List<DataField> attachmentFields, List<String> attachmentGroupKeys, List<String> picGroupKeys) throws SQLException {
        String sql = this.buildGroupKeySql2Clear(targetTable, tempTableInfo, attachmentFields);
        boolean[] index = this.getFieldType(attachmentFields);
        try (PreparedStatement prep = this.queryParam.getConnection().prepareStatement(sql);
             ResultSet resultSet = prep.executeQuery();){
            while (resultSet.next()) {
                for (int i = 0; i < attachmentFields.size(); ++i) {
                    String string = resultSet.getString(i + 1);
                    if (!StringUtils.hasLength(string)) continue;
                    if (index[i]) {
                        attachmentGroupKeys.add(string);
                        continue;
                    }
                    picGroupKeys.add(string);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u76ee\u6807\u8868GroupKey\u6570\u636e\u5931\u8d25", e);
            throw e;
        }
    }

    private ITempTable getFileCopyTempTable(CopyDataTableDefine sourceDefine, List<DataField> sourceAttachmentFields) throws SQLException {
        ArrayList<DataField> dimFields = new ArrayList<DataField>();
        if (sourceDefine.isFixed()) {
            dimFields.addAll(this.getDimFields(sourceDefine));
        } else {
            dimFields.add(sourceDefine.getBizOrderField());
        }
        CopyDataTempTable tempTable = new CopyDataTempTable();
        tempTable.setLogicFields(GatherTempTableUtils.ConvertNotNullFields(dimFields));
        tempTable.getLogicFields().addAll(GatherTempTableUtils.ConvertFields(sourceAttachmentFields));
        tempTable.setPrimaryKeyFields(dimFields.stream().map(Basic::getCode).collect(Collectors.toList()));
        return this.tempTableUtils.createTempTable(tempTable);
    }

    private List<DataField> getSourceAttachmentFields(List<DataField> attachmentFields, Map<String, String> targetField2SourceField, CopyDataTable sourceTable) {
        ArrayList<DataField> sourceAttachmentFields = new ArrayList<DataField>();
        List<DataField> copyDataFields = sourceTable.getCopyDataTableDefine().getCopyDataFields();
        Map<String, DataField> collect = copyDataFields.stream().collect(Collectors.toMap(Basic::getCode, a -> a));
        for (DataField attachmentField : attachmentFields) {
            String sourceFieldCode = attachmentField.getCode();
            if (!CollectionUtils.isEmpty(targetField2SourceField)) {
                sourceFieldCode = targetField2SourceField.get(attachmentField.getCode());
            }
            sourceAttachmentFields.add(collect.get(sourceFieldCode));
        }
        return sourceAttachmentFields;
    }

    private List<DataField> getSourceAttachmentFields(List<DataField> attachmentFields, Map<String, String> targetField2SourceField, List<CopyDataTable> sourceTable) {
        ArrayList<DataField> sourceAttachmentFields = new ArrayList<DataField>();
        HashSet<DataField> copyDataFields = new HashSet<DataField>();
        for (CopyDataTable copyDataTable : sourceTable) {
            copyDataFields.addAll(copyDataTable.getCopyDataTableDefine().getCopyDataFields());
        }
        Map<String, DataField> collect = copyDataFields.stream().collect(Collectors.toMap(Basic::getCode, a -> a));
        for (DataField attachmentField : attachmentFields) {
            String sourceFieldCode = attachmentField.getCode();
            if (!CollectionUtils.isEmpty(targetField2SourceField)) {
                sourceFieldCode = targetField2SourceField.get(attachmentField.getCode());
            }
            sourceAttachmentFields.add(collect.get(sourceFieldCode));
        }
        return sourceAttachmentFields;
    }

    private boolean[] getFieldType(List<DataField> attachmentFields) {
        boolean[] index = new boolean[attachmentFields.size()];
        for (int i = 0; i < attachmentFields.size(); ++i) {
            index[i] = attachmentFields.get(i).getDataFieldType() == DataFieldType.FILE;
        }
        return index;
    }

    private String buildGroupKeySql2CopyFloat(CopyDataTable sourceTable, TempTableReturnInfo tempTableInfo, List<DataField> attachmentFields) {
        CopyDataTableDefine copyDataTableDefine = sourceTable.getCopyDataTableDefine();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" select ");
        sqlBuilder.append(String.format("s1.%s,", this.queryFieldName(copyDataTableDefine.getBizOrderField())));
        for (DataField dataField : attachmentFields) {
            sqlBuilder.append("s1.").append(this.queryFieldName(dataField)).append(",");
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        sqlBuilder.append(" from ").append(this.getRealTableName(sourceTable)).append(" s1");
        ITempTable tempTable = tempTableInfo.getTempTable();
        sqlBuilder.append(" where exists (select 1 from ").append(tempTable.getTableName()).append(" t1 where");
        for (String sourceCol : tempTableInfo.getSourceColName()) {
            sqlBuilder.append(" s1.").append(sourceCol).append("=t1.").append(tempTable.getRealColName(sourceCol)).append(" and");
        }
        sqlBuilder.delete(sqlBuilder.length() - 4, sqlBuilder.length());
        sqlBuilder.append(")");
        return sqlBuilder.toString();
    }

    private String buildGroupKeySql2CopyFixed(List<CopyDataTable> sourceTables, TempTableReturnInfo tempTableInfo, List<DataField> attachmentFields) {
        CopyDataTableDefine copyDataTableDefine = sourceTables.get(0).getCopyDataTableDefine();
        List deployInfoByDataFieldKeys = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys((String[])attachmentFields.stream().map(Basic::getKey).toArray(String[]::new));
        Map<String, String> DataFieldKey2ColModelKey = deployInfoByDataFieldKeys.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, DataFieldDeployInfo::getColumnModelKey));
        Map<String, Integer> colModelKey2DataTableIndex = this.buildColModelKey2DataTable(sourceTables);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" select ");
        sqlBuilder.append(String.format("s1.%s,", this.queryFieldName(copyDataTableDefine.getUnitField())));
        sqlBuilder.append(String.format("s1.%s,", this.queryFieldName(copyDataTableDefine.getPeriodField())));
        for (DataField dataField : copyDataTableDefine.getPublicDimFields()) {
            sqlBuilder.append(String.format("s1.%s,", this.queryFieldName(dataField)));
        }
        for (DataField dataField : attachmentFields) {
            Integer index = colModelKey2DataTableIndex.get(DataFieldKey2ColModelKey.get(dataField.getKey()));
            sqlBuilder.append("s").append(index + 1).append(".").append(this.queryFieldName(dataField)).append(",");
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        ITempTable tempTable = tempTableInfo.getTempTable();
        sqlBuilder.append(" from ").append(tempTable.getTableName()).append(" t1");
        for (int i = 0; i < sourceTables.size(); ++i) {
            CopyDataTable sourceTable = sourceTables.get(i);
            Map<String, ColumnModelDefine> collect = sourceTable.getCopyDataColModel().stream().collect(Collectors.toMap(IModelDefineItem::getCode, a -> a));
            sqlBuilder.append(" inner join ").append(this.getRealTableName(sourceTable)).append(" s").append(i + 1).append(" on ");
            for (String sourceCol : tempTableInfo.getSourceColName()) {
                sqlBuilder.append("s").append(i + 1).append(".").append(sourceCol).append("=t1.").append(tempTable.getRealColName(sourceCol)).append(" and ");
            }
            sqlBuilder.delete(sqlBuilder.length() - 5, sqlBuilder.length());
        }
        return sqlBuilder.toString();
    }

    private Map<String, Integer> buildColModelKey2DataTable(List<CopyDataTable> sourceTables) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < sourceTables.size(); ++i) {
            CopyDataTable sourceTable = sourceTables.get(i);
            for (ColumnModelDefine columnModelDefine : sourceTable.getCopyDataColModel()) {
                map.put(columnModelDefine.getID(), i);
            }
        }
        return map;
    }

    private String buildGroupKeySql2Clear(CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, List<DataField> attachmentFields) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" select ");
        for (DataField dataField : attachmentFields) {
            sqlBuilder.append("t1.").append(this.queryFieldName(dataField)).append(",");
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        sqlBuilder.append(" from ").append(this.getRealTableName(targetTable)).append(" t1");
        this.appendTargetWhere(tempTableInfo, sqlBuilder, null);
        return sqlBuilder.toString();
    }

    private void appendTargetWhere(TempTableReturnInfo tempTableInfo, StringBuilder sqlBuilder, String targetTableName) {
        sqlBuilder.append(" where exists (select 1 from ").append(tempTableInfo.getTempTable().getTableName()).append(" t2 where");
        for (String targetCol : tempTableInfo.getTargetColName()) {
            String realColName = tempTableInfo.getTempTable().getRealColName("TARGET_" + targetCol.toUpperCase());
            if (StringUtils.hasLength(targetTableName)) {
                sqlBuilder.append(" ").append(targetTableName).append(".");
            } else {
                sqlBuilder.append(" t1.");
            }
            sqlBuilder.append(targetCol).append("=t2.").append(realColName).append(" and");
        }
        sqlBuilder.delete(sqlBuilder.length() - 4, sqlBuilder.length());
        sqlBuilder.append(")");
    }

    private void clearTargetFloatTableData(CopyDataTable targetTable, TempTableReturnInfo tempTableInfo) throws SQLException {
        String floatTableClearSql = this.buildClearFloatTableSql(targetTable, tempTableInfo);
        this.executeSql(this.getRealTableName(targetTable), floatTableClearSql);
    }

    private String buildClearFloatTableSql(CopyDataTable targetTable, TempTableReturnInfo tempTableInfo) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from ").append(this.getRealTableName(targetTable));
        this.appendTargetWhere(tempTableInfo, deleteSql, this.getRealTableName(targetTable));
        return deleteSql.toString();
    }

    private void executeSql(String tableName, String Sql) throws SQLException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        this.printLoggerSQL(Sql, "\u6279\u91cf\u590d\u5236\uff1a\u6267\u884csql", tableName);
        DataEngineUtil.executeUpdate((Connection)this.queryParam.getConnection(), (String)Sql, null);
        stopWatch.stop();
        logger.debug("\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeInsertFloatTableData(CopyDataTable sourceTable, CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, Map<String, String> targetFiled2SourceField, ITempTable fileCopyTempTable, Map<String, String> bizKeyOrderMap) throws Exception {
        CopyDataTableDefine sourceTableDefine = sourceTable.getCopyDataTableDefine();
        DataTable tableDefine = sourceTableDefine.getTableDefine();
        try {
            if (tableDefine.getDataTableType() == DataTableType.DETAIL && tableDefine.getRepeatCode().booleanValue()) {
                if (this.bizKeyOrderTempTable == null) {
                    this.bizKeyOrderTempTable = this.tempTableUtils.getKeyValueTempTable();
                }
                String sql = this.buildGetBizKeyOrderSql(sourceTable, tempTableInfo);
                HashMap<String, String> bizKeyMap = new HashMap<String, String>();
                try {
                    PreparedStatement prep = this.queryParam.getConnection().prepareStatement(sql);
                    Object object = null;
                    try (ResultSet resultSet = prep.executeQuery();){
                        while (resultSet.next()) {
                            String bizKey = resultSet.getString(this.queryFieldName(sourceTableDefine.getBizOrderField()));
                            if (StringUtils.isEmpty(bizKey)) continue;
                            bizKeyMap.put(bizKey, UUIDUtils.getKey());
                        }
                    }
                    catch (Throwable throwable) {
                        object = throwable;
                        throw throwable;
                    }
                    finally {
                        if (prep != null) {
                            if (object != null) {
                                try {
                                    prep.close();
                                }
                                catch (Throwable throwable) {
                                    ((Throwable)object).addSuppressed(throwable);
                                }
                            } else {
                                prep.close();
                            }
                        }
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw e;
                }
                if (!CollectionUtils.isEmpty(bizKeyMap)) {
                    bizKeyOrderMap.putAll(bizKeyMap);
                    ArrayList<Object[]> objects = new ArrayList<Object[]>();
                    for (Map.Entry entry : bizKeyMap.entrySet()) {
                        Object[] object = new Object[]{entry.getKey(), entry.getValue()};
                        objects.add(object);
                    }
                    this.bizKeyOrderTempTable.insertRecords(objects);
                }
            }
            String selectSql = this.buildFloatTableSelectSQL(sourceTable, targetTable, tempTableInfo, targetFiled2SourceField, fileCopyTempTable, this.bizKeyOrderTempTable);
            this.doInsertData(targetTable, selectSql, tempTableInfo);
        }
        finally {
            if (this.bizKeyOrderTempTable != null) {
                this.bizKeyOrderTempTable.deleteAll();
            }
        }
    }

    private String buildGetBizKeyOrderSql(CopyDataTable sourceTable, TempTableReturnInfo tempTableInfo) {
        CopyDataTableDefine sourceTableDefine = sourceTable.getCopyDataTableDefine();
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select ");
        DataField bizOrderField = sourceTableDefine.getBizOrderField();
        selectSql.append("s1.").append(bizOrderField.getCode()).append(" as ").append(bizOrderField.getCode());
        selectSql.append(" from ").append(this.getRealTableName(sourceTable)).append(" s1");
        selectSql.append(" inner join ").append(tempTableInfo.getTempTable().getTableName()).append(" t1 on");
        for (String source : tempTableInfo.getSourceColName()) {
            selectSql.append(" s1.").append(source).append("=t1.").append(source).append(" and");
        }
        selectSql.setLength(selectSql.length() - 4);
        return selectSql.toString();
    }

    private String buildFloatTableSelectSQL(CopyDataTable sourceTable, CopyDataTable targetTable, TempTableReturnInfo tempTableInfo, Map<String, String> targetField2SourceField, ITempTable fileCopyTempTable, ITempTable bizKeyOrderTempTable) {
        CopyDataTableDefine sourceTableDefine = sourceTable.getCopyDataTableDefine();
        CopyDataTableDefine targetTableDefine = targetTable.getCopyDataTableDefine();
        List<DataField> copyDataFields = sourceTableDefine.getCopyDataFields();
        Map<String, DataField> collect = copyDataFields.stream().collect(Collectors.toMap(Basic::getCode, a -> a));
        if (CollectionUtils.isEmpty(targetField2SourceField)) {
            targetField2SourceField = new HashMap<String, String>();
            for (DataField dataField : copyDataFields) {
                targetField2SourceField.put(dataField.getCode(), dataField.getCode());
            }
        }
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select ");
        List<String> targetColName = tempTableInfo.getTargetColName();
        DataField bizOrderField = targetTableDefine.getBizOrderField();
        for (DataField target : targetTableDefine.getCopyDataFields()) {
            String fieldCode = target.getCode();
            if (targetColName.contains(fieldCode)) {
                String realColName = tempTableInfo.getTempTable().getRealColName("TARGET_" + fieldCode.toUpperCase());
                selectSql.append("t1.").append(realColName).append(" as ").append(fieldCode.toUpperCase()).append(",");
                continue;
            }
            if (fieldCode.equals(bizOrderField.getCode())) {
                if (bizKeyOrderTempTable != null) {
                    selectSql.append("t3.").append("TEMP_VALUE");
                } else {
                    selectSql.append("s1.").append(bizOrderField.getCode());
                }
                selectSql.append(" as ").append(bizOrderField.getCode()).append(",");
                continue;
            }
            if (fieldCode.equals(targetTableDefine.getOrderField().getCode())) {
                selectSql.append("s1.").append(fieldCode).append(" as ").append(fieldCode).append(",");
                continue;
            }
            String sourceFieldCode = targetField2SourceField.get(fieldCode);
            DataField sourceField = collect.get(sourceFieldCode);
            String sourceFieldName = this.queryFieldName(sourceField);
            String targetFieldName = this.queryFieldName(target);
            if (target.getDataFieldType() == DataFieldType.PICTURE || target.getDataFieldType() == DataFieldType.FILE) {
                if (fileCopyTempTable == null) continue;
                selectSql.append(String.format("t2.%s as %s,", fileCopyTempTable.getRealColName(sourceFieldName), targetFieldName));
                continue;
            }
            selectSql.append("s1.").append(sourceFieldName).append(" as ").append(targetFieldName).append(",");
        }
        selectSql.setLength(selectSql.length() - 1);
        selectSql.append(" from ").append(this.getRealTableName(sourceTable)).append(" s1");
        selectSql.append(" inner join ").append(tempTableInfo.getTempTable().getTableName()).append(" t1 on");
        for (String source : tempTableInfo.getSourceColName()) {
            selectSql.append(" s1.").append(source).append("=t1.").append(source).append(" and");
        }
        selectSql.setLength(selectSql.length() - 4);
        if (fileCopyTempTable != null) {
            selectSql.append(" left join ").append(fileCopyTempTable.getTableName()).append(" t2 on");
            String fieldName = this.queryFieldName(sourceTableDefine.getBizOrderField());
            selectSql.append(" s1.").append(fieldName).append("=t2.").append(fileCopyTempTable.getRealColName(fieldName));
        }
        if (bizKeyOrderTempTable != null) {
            selectSql.append(" left join ").append(bizKeyOrderTempTable.getTableName()).append(" t3 on");
            selectSql.append(" s1.").append(this.queryFieldName(bizOrderField)).append("=t3.").append("TEMP_KEY");
        }
        return selectSql.toString();
    }

    private void doUpdateFixedTable(CopyDataTable targetTable, String selectSql, TempTableReturnInfo tempTableInfo) throws TableLoaderException {
        ITableLoader mergeLoader = this.createMergeLoader(this.queryParam.getDatabase(), this.queryParam.getConnection());
        SimpleTable desTable = new SimpleTable(this.getRealTableName(targetTable), "des");
        InnerTable srcTable = new InnerTable(selectSql, "src");
        logger.debug("\u56fa\u5b9a\u8868\u66f4\u65b0sql\uff1a{}", (Object)selectSql);
        List fieldMaps = mergeLoader.getFieldMaps();
        for (String colName : tempTableInfo.getTargetColName()) {
            ISQLField des = desTable.addField(colName);
            ISQLField src = srcTable.addField(colName);
            fieldMaps.add(new LoadFieldMap(src, des, true));
        }
        for (ColumnModelDefine dataField : targetTable.getCopyDataColModel()) {
            String fieldName = this.queryFieldName(dataField);
            ISQLField des = desTable.addField(fieldName);
            ISQLField src = srcTable.addField(fieldName);
            fieldMaps.add(new LoadFieldMap(src, des, false));
        }
        mergeLoader.setDestTable(desTable);
        mergeLoader.setSourceTable((ISQLTable)srcTable);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        mergeLoader.execute();
        stopWatch.stop();
        logger.debug("\u56fa\u5b9a\u8868\u66f4\u65b0\u6570\u636e\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
    }

    private void doInsertData(CopyDataTable targetTable, String selectSql, TempTableReturnInfo tempTableInfo) throws TableLoaderException {
        CopyDataTableDefine copyDataTableDefine = targetTable.getCopyDataTableDefine();
        IDatabase database = this.queryParam.getDatabase();
        ITableLoader tableLoader = this.createInsertAutoRowNumLoader(database, this.queryParam.getConnection(), null);
        tableLoader.setListener((ILoadListener)new LoggingTableLoadListener());
        SimpleTable desTable = new SimpleTable(this.getRealTableName(targetTable), "C2");
        InnerTable srcTable = new InnerTable(selectSql, "C1");
        List fieldMaps = tableLoader.getFieldMaps();
        for (DataField dataField : copyDataTableDefine.getCopyDataFields()) {
            ISQLField des = desTable.addField(this.queryFieldName(dataField));
            ISQLField src = srcTable.addField(this.queryFieldName(dataField));
            fieldMaps.add(new LoadFieldMap(src, des));
        }
        tableLoader.setDestTable(desTable);
        tableLoader.setSourceTable((ISQLTable)srcTable);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        tableLoader.execute();
        stopWatch.stop();
        logger.debug("\u6d6e\u52a8\u8868\u63d2\u5165\u6570\u636e\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
    }

    public ITableLoader createInsertAutoRowNumLoader(IDatabase database, Connection conn, String rowNumFieldName) throws TableLoaderException {
        if (rowNumFieldName == null) {
            return database.createInsertLoader(conn);
        }
        return new DefaultInsertAutoRownumLoader(conn, database, rowNumFieldName);
    }

    private ITableLoader createMergeLoader(IDatabase database, Connection conn) throws TableLoaderException {
        return database.createMergeLoader(conn, true);
    }

    protected String getRealTableName(CopyDataTable copyDataTable) {
        return copyDataTable.getTableModelDefine().getName();
    }

    protected String queryFieldName(DataField dataField) {
        if (Objects.isNull(dataField)) {
            return null;
        }
        List deployInfo = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()});
        if (!CollectionUtils.isEmpty(deployInfo)) {
            return ((DataFieldDeployInfo)deployInfo.get(0)).getFieldName();
        }
        return null;
    }

    protected String queryFieldName(ColumnModelDefine columnModelDefine) {
        if (Objects.isNull(columnModelDefine)) {
            return null;
        }
        return columnModelDefine.getName();
    }

    private List<CopyDataTableDefine> getMappingTableDefineList(CopyDataTableDefine curTableDefine, Map<String, DataFieldMap> dataFieldMapByTable, Map<String, Map<String, String>> table2FiledMap) {
        Object dataFieldMap;
        ArrayList<CopyDataTableDefine> mappingTableDefines = new ArrayList<CopyDataTableDefine>();
        if (CollectionUtils.isEmpty(curTableDefine.getCopyDataFields())) {
            return mappingTableDefines;
        }
        for (Map.Entry<String, DataFieldMap> entry : dataFieldMapByTable.entrySet()) {
            String curFieldCode = entry.getKey();
            dataFieldMap = entry.getValue();
            table2FiledMap.computeIfAbsent(dataFieldMap.getDataTableCode(), k -> new HashMap()).put(dataFieldMap.getFieldCode(), curFieldCode);
        }
        if (!curTableDefine.isFixed() && table2FiledMap.keySet().size() > 1) {
            logger.error("\u6d6e\u52a8\u8868\u4ec5\u652f\u6301\u4e00\u5bf9\u4e00\u6620\u5c04\uff0c\u4e0d\u652f\u6301\u591a\u5bf9\u4e00\u6216\u4e00\u5bf9\u591a\uff01\u95ee\u9898\u6d6e\u52a8\u8868\uff1a{}", (Object)curTableDefine.getTableDefine().getTitle());
            return mappingTableDefines;
        }
        for (Map.Entry<String, Object> entry : table2FiledMap.entrySet()) {
            String mappingTableCode = entry.getKey();
            dataFieldMap = (Map)entry.getValue();
            Set<String> mappingFields = dataFieldMap.keySet();
            CopyDataTableDefine mappingTableDefine = CopyDataUtils.getCopyDataTableDefine(mappingTableCode, curTableDefine.isFixed(), mappingFields);
            if (mappingTableDefine == null) continue;
            mappingTableDefines.add(mappingTableDefine);
        }
        return mappingTableDefines;
    }

    private List<CopyDataTable> splitTable(CopyDataTableDefine sourceTableDefine) {
        ArrayList<CopyDataTableDefine> param = new ArrayList<CopyDataTableDefine>();
        param.add(sourceTableDefine);
        return new ArrayList<CopyDataTable>(this.splitTable(param));
    }

    private List<CopyDataTable> splitTable(List<CopyDataTableDefine> targetTableDefines) {
        ArrayList<CopyDataTable> result = new ArrayList<CopyDataTable>();
        for (CopyDataTableDefine copyDataTableDefine : targetTableDefines) {
            ArrayList<DataField> copyDataFields = new ArrayList<DataField>(copyDataTableDefine.getCopyDataFields());
            List deployInfoList = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys((String[])copyDataFields.stream().map(Basic::getKey).toArray(String[]::new));
            Map<String, List<DataFieldDeployInfo>> table2DeployInfo = deployInfoList.stream().collect(Collectors.groupingBy(DataFieldDeployInfo::getTableName));
            for (Map.Entry<String, List<DataFieldDeployInfo>> entry : table2DeployInfo.entrySet()) {
                CopyDataTable copyDataTable = new CopyDataTable();
                copyDataTable.setCopyDataTableDefine(copyDataTableDefine);
                TableModelDefine table = this.dataModelService.getTableModelDefineByName(entry.getKey());
                copyDataTable.setTableModelDefine(table);
                List allColumnKeys = entry.getValue().stream().map(DataFieldDeployInfo::getColumnModelKey).collect(Collectors.toList());
                List allColumn = this.dataModelService.getColumnModelDefinesByIDs(allColumnKeys);
                copyDataTable.setCopyDataColModel(allColumn);
                result.add(copyDataTable);
            }
        }
        return result;
    }

    public void printLoggerSQL(String executeSql, String title, String tableName) {
        String str = tableName + "\u8868\uff0c" + title + "SQL\uff1a" + executeSql;
        logger.debug(str);
    }
}

