/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermission
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionResource
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.fielddatacrud.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermission;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.RegionPO;
import com.jiuqi.nr.fielddatacrud.TableDimSet;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.impl.IFieldDataStrategy;
import com.jiuqi.nr.fielddatacrud.impl.dto.AccessDTO;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import com.jiuqi.nr.fielddatacrud.spi.TableUpdaterProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DefaultFieldDataQueryServiceImpl
implements IFieldDataService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFieldDataQueryServiceImpl.class);
    @Autowired
    protected FieldRelationFactory fieldRelationFactory;
    @Autowired
    protected FieldDataStrategyFactory strategyFactory;
    @Autowired
    protected TableUpdaterProvider tableUpdaterProvider;
    @Autowired
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    protected DimCollectionBuildUtil dimCollectionBuildUtil;

    @Override
    public void queryTableData(IFieldQueryInfo queryInfo, IDataReader dataReader) throws CrudException {
        FieldRelation fieldRelation = this.fieldRelationFactory.getFieldRelation(queryInfo.selectFieldItr());
        IFieldDataStrategy strategy = this.strategyFactory.getStrategy(queryInfo, fieldRelation);
        strategy.queryTableData(queryInfo, fieldRelation, dataReader);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public TableUpdater getTableUpdater(FieldSaveInfo saveInfo) {
        AccessDTO accessDTO;
        Set taskKeys;
        List<IMetaData> fields = saveInfo.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            throw new IllegalArgumentException("\u53c2\u6570\u9519\u8bef,\u64cd\u4f5c\u6307\u6807\u5fc5\u4f20");
        }
        FieldRelation fieldRelation = this.fieldRelationFactory.getFieldRelation(fields.stream().map(IMetaData::getFieldKey).collect(Collectors.toList()));
        String dataTableKey = saveInfo.getDataTableKey();
        DataTable dataTable = null;
        if (StringUtils.hasLength(dataTableKey)) {
            dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
            fields = fieldRelation.getMetaDataByTableKey(dataTableKey);
        }
        if (dataTable == null) {
            List<TableDimSet> tableDim = fieldRelation.getTableDim(fields);
            if (tableDim.size() != 1) {
                throw new UnsupportedOperationException("\u53ea\u652f\u6301\u540c\u4e00\u4e2a\u6570\u636e\u8868\u6307\u6807\u4fdd\u5b58\u6570\u636e");
            }
            dataTable = tableDim.get(0).getDataTable();
        }
        IParamDataProvider paramDataProvider = this.strategyFactory.getParamDataProvider();
        ParamProvider paramProvider = paramDataProvider.getParamProvider();
        Set<RegionPO> regions = paramProvider.getRegions(dataTable.getCode(), fields.stream().map(IMetaData::getCode).collect(Collectors.toList()));
        if (logger.isTraceEnabled()) {
            logger.trace("\u6307\u6807\u6240\u5728\u533a\u57df\u8303\u56f4{}", (Object)regions);
        }
        HashMap<String, Map> regionMap = new HashMap<String, Map>();
        for (RegionPO regionKey : regions) {
            Map schemeMap = regionMap.computeIfAbsent(regionKey.getTaskKey(), r -> new HashMap());
            schemeMap.computeIfAbsent(regionKey.getFormSchemeKey(), r -> new ArrayList()).add(regionKey);
        }
        if (saveInfo.getAuthMode() == ResouceType.FORM) {
            taskKeys = regionMap.keySet();
            if (CollectionUtils.isEmpty(regionMap)) {
                throw new IllegalArgumentException("\u8868\u5355\u6743\u9650\u5224\u65ad\uff0c\u672a\u627e\u5230\u4efb\u52a1\uff0c\u8bf7\u63d0\u4f9b\u6743\u9650\u5224\u65ad\u6240\u9700\u53c2\u6570");
            }
            if (taskKeys.size() != 1) {
                throw new IllegalArgumentException("\u8868\u5355\u6743\u9650\u5224\u65ad\uff0c\u627e\u5230\u591a\u4e2a\u4efb\u52a1\uff0c\u4e0d\u652f\u6301\u5bfc\u5165\uff0c\u9700\u8981\u9650\u5b9a\u4efb\u52a1");
            }
            Map formSchemeMap = (Map)regionMap.get(taskKeys.stream().findFirst().get());
            if (CollectionUtils.isEmpty(formSchemeMap)) {
                throw new IllegalArgumentException("\u8868\u5355\u6743\u9650\u5224\u65ad\uff0c\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848\uff0c\u8bf7\u63d0\u4f9b\u6743\u9650\u5224\u65ad\u6240\u9700\u53c2\u6570");
            }
            Set formSchemeKey = formSchemeMap.keySet();
            if (formSchemeKey.size() != 1) {
                throw new IllegalArgumentException("\u8868\u5355\u6743\u9650\u5224\u65ad\uff0c\u627e\u5230\u591a\u4e2a\u62a5\u8868\u65b9\u6848\uff0c\u4e0d\u652f\u6301\u5bfc\u5165\uff0c\u9700\u8981\u9650\u5b9a\u62a5\u8868\u65b9\u6848");
            }
            List regionPO = (List)formSchemeMap.get(formSchemeKey.stream().findFirst().get());
            if (CollectionUtils.isEmpty(regionPO)) {
                throw new IllegalArgumentException("\u8868\u5355\u6743\u9650\u5224\u65ad\uff0c\u672a\u627e\u5230\u62a5\u8868\uff0c\u8bf7\u63d0\u4f9b\u6743\u9650\u5224\u65ad\u6240\u9700\u53c2\u6570");
            }
            accessDTO = this.getAccessMasterKeys(saveInfo, regionPO);
        } else {
            if (saveInfo.getAuthMode() != ResouceType.ZB) throw new UnsupportedOperationException("Unsupported auth mode " + saveInfo.getAuthMode());
            taskKeys = regionMap.keySet();
            if (!CollectionUtils.isEmpty(regionMap) && taskKeys.size() > 1) {
                throw new IllegalArgumentException("\u6307\u6807\u627e\u5230\u591a\u4e2a\u4efb\u52a1\uff0c\u4e0d\u652f\u6301\u5bfc\u5165\uff0c\u9700\u8981\u9650\u5b9a\u4efb\u52a1");
            }
            Optional taskOptional = taskKeys.stream().findFirst();
            if (!taskOptional.isPresent()) throw new IllegalArgumentException("\u6307\u6807\u6743\u9650\u5224\u65ad\u672a\u627e\u5230\u4efb\u52a1\uff0c\u65e0\u6cd5\u5224\u65ad\u6743\u9650");
            String taskKey = (String)taskOptional.get();
            Set formSchemeKeys = ((Map)regionMap.get(taskKey)).keySet();
            Optional first = formSchemeKeys.stream().findFirst();
            if (!first.isPresent()) throw new IllegalArgumentException("\u6307\u6807\u6743\u9650\u5224\u65ad\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848\uff0c\u65e0\u6cd5\u5224\u65ad\u6743\u9650");
            List<String> zbKeys = fields.stream().map(a -> a.getDataField().getKey()).collect(Collectors.toList());
            accessDTO = this.getZBAccessMasterKeys(saveInfo, taskKey, (String)first.get(), zbKeys);
        }
        if (!logger.isTraceEnabled()) return this.tableUpdaterProvider.getTableUpdater(saveInfo, this.strategyFactory, accessDTO);
        logger.trace("\u6743\u9650\u5224\u65ad\u7ed3\u679c{}", (Object)accessDTO);
        return this.tableUpdaterProvider.getTableUpdater(saveInfo, this.strategyFactory, accessDTO);
    }

    protected AccessDTO getAccessMasterKeys(FieldSaveInfo saveInfo, List<RegionPO> regions) {
        if (logger.isTraceEnabled()) {
            logger.trace("\u8868\u5355\u6743\u9650\u5224\u65ad\u6a21\u5f0f");
        }
        AccessDTO accessDTO = new AccessDTO();
        DimensionCollection masterKey = saveInfo.getMasterKey();
        List dimensionCombinations = masterKey.getDimensionCombinations();
        HashSet<DimensionValueSet> allDimKeys = new HashSet<DimensionValueSet>();
        HashSet<String> unAccessResourceIds = new HashSet<String>();
        HashSet<DimensionValueSet> unAccessDims = new HashSet<DimensionValueSet>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            allDimKeys.add(dimensionCombination.toDimensionValueSet());
        }
        List formKeys = regions.stream().map(RegionPO::getFormKey).collect(Collectors.toList());
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setTaskId(regions.get(0).getTaskKey());
        String formSchemeKey = regions.get(0).getFormSchemeKey();
        evaluatorParam.setFormSchemeId(formSchemeKey);
        evaluatorParam.setResourceType(saveInfo.getAuthMode().getCode());
        IProviderStore providerStore = this.strategyFactory.getProviderStore();
        DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory = providerStore.getDataPermissionEvaluatorFactory();
        DataPermissionEvaluator evaluator = dataPermissionEvaluatorFactory.createEvaluator(evaluatorParam, masterKey, formKeys);
        DataPermission dataPermission = evaluator.haveAccess(masterKey, formKeys, AuthType.WRITEABLE);
        Collection unAccessResources = dataPermission.getUnAccessResources();
        for (DataPermissionResource unAccessResource : unAccessResources) {
            unAccessResourceIds.add(unAccessResource.getResourceId());
            DimensionCombination dimensionCombination = unAccessResource.getDimensionCombination();
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            unAccessDims.add(dimensionValueSet);
            allDimKeys.remove(dimensionValueSet);
        }
        if (!unAccessResourceIds.isEmpty()) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList(unAccessDims));
            DimensionCollection readCheckMasterKey = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionValueSet, formSchemeKey);
            DataPermissionEvaluator readEvaluator = dataPermissionEvaluatorFactory.createEvaluator(evaluatorParam, readCheckMasterKey, unAccessResourceIds);
            DataPermission readCheckPermission = readEvaluator.haveAccess(readCheckMasterKey, unAccessResourceIds, AuthType.VISIBLE);
            Collection unReadResources = readCheckPermission.getUnAccessResources();
            for (DataPermissionResource unReadResource : unReadResources) {
                DimensionValueSet unRead = unReadResource.getDimensionCombination().toDimensionValueSet();
                unAccessDims.remove(unRead);
            }
        }
        accessDTO.setAccessMasterKeys(allDimKeys);
        accessDTO.setNoAccessMasterKeys(unAccessDims);
        return accessDTO;
    }

    protected AccessDTO getZBAccessMasterKeys(FieldSaveInfo saveInfo, String taskKey, String formSchemeKey, List<String> zbKeys) {
        if (logger.isTraceEnabled()) {
            logger.trace("\u6307\u6807\u6743\u9650\u5224\u65ad\u6a21\u5f0f");
        }
        AccessDTO accessDTO = new AccessDTO();
        DimensionCollection masterKey = saveInfo.getMasterKey();
        List dimensionCombinations = masterKey.getDimensionCombinations();
        HashSet<DimensionValueSet> dimensionValueSets = new HashSet<DimensionValueSet>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            dimensionValueSets.add(dimensionCombination.toDimensionValueSet());
        }
        IProviderStore providerStore = this.strategyFactory.getProviderStore();
        DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory = providerStore.getDataPermissionEvaluatorFactory();
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setTaskId(taskKey);
        evaluatorParam.setResourceType(saveInfo.getAuthMode().getCode());
        DataPermissionEvaluator evaluator = dataPermissionEvaluatorFactory.createEvaluator(evaluatorParam, masterKey, zbKeys);
        DataPermission dataPermission = evaluator.haveAccess(masterKey, zbKeys, AuthType.WRITEABLE);
        Collection unAccessResources = dataPermission.getUnAccessResources();
        HashSet<String> unAccessResourceIds = new HashSet<String>();
        HashSet<DimensionValueSet> unAccessDims = new HashSet<DimensionValueSet>();
        for (DataPermissionResource unAccessResource : unAccessResources) {
            unAccessResourceIds.add(unAccessResource.getResourceId());
            DimensionCombination dimensionCombination = unAccessResource.getDimensionCombination();
            unAccessDims.add(dimensionCombination.toDimensionValueSet());
            dimensionValueSets.remove(dimensionCombination.toDimensionValueSet());
        }
        if (!unAccessResourceIds.isEmpty()) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList(unAccessDims));
            DimensionCollection readCheckMasterKey = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionValueSet, formSchemeKey);
            DataPermissionEvaluator readEvaluator = dataPermissionEvaluatorFactory.createEvaluator(evaluatorParam, readCheckMasterKey, unAccessResourceIds);
            DataPermission readCheckPermission = readEvaluator.haveAccess(readCheckMasterKey, unAccessResourceIds, AuthType.VISIBLE);
            Collection unReadResources = readCheckPermission.getUnAccessResources();
            for (DataPermissionResource unReadResource : unReadResources) {
                unAccessDims.remove(unReadResource.getDimensionCombination().toDimensionValueSet());
            }
        }
        accessDTO.setAccessMasterKeys(dimensionValueSets);
        accessDTO.setNoAccessMasterKeys(unAccessDims);
        return accessDTO;
    }
}

