/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.SortMode
 *  com.jiuqi.nr.datacrud.impl.DataValue
 *  com.jiuqi.nr.datacrud.impl.RowData
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datacrud.impl.service.DataEngineService
 *  com.jiuqi.nr.datacrud.spi.IExecutorContextFactory
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FilterFunction
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
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
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor
 */
package com.jiuqi.nr.fielddatacrud.impl.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FilterFunction;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
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
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldSort;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.RegionPO;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import com.jiuqi.nr.fielddatacrud.spi.filter.FieldFilter;
import com.jiuqi.nr.fielddatacrud.spi.filter.InValuesFilter;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class BaseFieldDataStrategy<Q extends ICommonQuery> {
    protected IFieldQueryInfo queryInfo;
    protected FieldRelation relation;
    protected Q dataQuery;
    protected ExecutorContext context;
    protected IExecutorContextFactory executorContextFactory;
    protected DataEngineService dataEngineService;
    protected IParamDataProvider paramDataProvider;
    protected IProviderStore providerStore;
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    protected DesensitizedEncryptor desensitizedEncryptor;
    protected IDataReader dataReader;
    protected static Logger logger = LoggerFactory.getLogger(BaseFieldDataStrategy.class);

    public BaseFieldDataStrategy(FieldDataStrategyFactory factory) {
        this.dataEngineService = factory.getDataEngineService();
        this.executorContextFactory = factory.getExecutorContextFactory();
        this.paramDataProvider = factory.getParamDataProvider();
        this.providerStore = factory.getProviderStore();
        this.runtimeDataSchemeService = factory.getRuntimeDataSchemeService();
        this.desensitizedEncryptor = factory.getDesensitizedEncryptor();
    }

    protected void initDataQuery(IFieldQueryInfo queryInfo, ParamRelation paramRelation, DimensionValueSet masterKey) {
        this.dataQuery = this.getDataQuery(queryInfo, paramRelation);
        this.context = this.executorContextFactory.getExecutorContext(paramRelation, masterKey, queryInfo.variableItr());
    }

    protected abstract Q getDataQuery(IFieldQueryInfo var1, ParamRelation var2);

    protected void addQueryCol(List<IMetaData> metaDatas) {
        for (IMetaData metaData : metaDatas) {
            if (metaData.isFieldLink()) {
                int index = this.dataQuery.addColumn((FieldDefine)((DataFieldDTO)metaData.getDataField()));
                metaData.setIndex(index);
                continue;
            }
            if (!metaData.isFormulaLink()) continue;
            this.addExpressionColumn(metaData);
        }
    }

    protected void addExpressionColumn(IMetaData metaData) {
        int index = this.dataQuery.addExpressionColumn(metaData.getCode());
        metaData.setIndex(index);
    }

    protected void addFilter() {
        Iterator<RowFilter> filterItr = this.queryInfo.rowFilterItr();
        if (filterItr != null) {
            ArrayList<String> filters = new ArrayList<String>();
            while (filterItr.hasNext()) {
                RowFilter filter = filterItr.next();
                if (filter instanceof FieldFilter) {
                    ((FieldFilter)filter).setRelation(this.relation);
                    if (filter instanceof InValuesFilter) {
                        InValuesFilter inValuesFilter = (InValuesFilter)filter;
                        String fieldKey = inValuesFilter.getFieldKey();
                        IMetaData meta = this.relation.getMetaDataByField(fieldKey);
                        if (meta == null || inValuesFilter.getValues() == null) continue;
                        ArrayList<String> values = new ArrayList<String>(inValuesFilter.getValues());
                        this.dataQuery.setColumnFilterValueList(meta.getIndex(), values);
                        continue;
                    }
                }
                String formula = filter.toFormula();
                if (filter.supportFormula()) {
                    filters.add("(" + formula + ")");
                    continue;
                }
                filters.add("(FILTER_ROW('" + filter.name() + "','" + formula + "'))");
                FilterFunction.getInstance().registerRowFilter(filter);
            }
            if (!filters.isEmpty()) {
                this.dataQuery.setRowFilter(String.join((CharSequence)" AND ", filters));
            }
        }
    }

    protected void addOrder() {
        Iterator<FieldSort> sortItr = this.queryInfo.fieldSortItr();
        if (sortItr == null) {
            return;
        }
        while (sortItr.hasNext()) {
            FieldSort next = sortItr.next();
            String fieldKey = next.getFieldKey();
            SortMode mode = next.getMode();
            IMetaData meta = this.relation.getMetaDataByField(fieldKey);
            if (meta != null && meta.getDataField() != null) {
                this.dataQuery.addOrderByItem((FieldDefine)((DataFieldDTO)meta.getDataField()), mode == SortMode.DESC);
                continue;
            }
            meta = this.relation.initMetaData(fieldKey);
            if (meta == null || meta.getDataField() == null) continue;
            this.dataQuery.addOrderByItem((FieldDefine)((DataFieldDTO)meta.getDataField()), mode == SortMode.DESC);
        }
    }

    protected void addPage(PageInfo pageInfo) {
        if (pageInfo != null) {
            this.dataQuery.setPagingInfo(pageInfo.getRowsPerPage(), pageInfo.getPageIndex());
        }
    }

    protected AbstractData metaDataTransfer(IMetaData metaData, IDataRow row, RowData rowData) {
        String dataMaskCode;
        AbstractData defaultValue;
        Object valueObject = row.getValueObject(metaData.getIndex());
        AbstractData abstractData = AbstractData.valueOf((Object)valueObject, (int)metaData.getDataType());
        DataField dataField = metaData.getDataField();
        if (abstractData.isNull && dataField != null && StringUtils.hasLength(dataField.getDefaultValue()) && (defaultValue = this.evaluateDefaultValue(row.getRowKeys(), metaData)) != null) {
            return defaultValue;
        }
        if (this.queryInfo.isDesensitized() && this.desensitizedEncryptor != null && dataField != null && dataField.getDataFieldType() == DataFieldType.STRING && StringUtils.hasLength(dataMaskCode = dataField.getDataMaskCode()) && !abstractData.getAsNull()) {
            String desensitizeValue;
            block4: {
                String valueStr = abstractData.getAsString();
                desensitizeValue = null;
                try {
                    desensitizeValue = this.desensitizedEncryptor.desensitize(dataMaskCode, valueStr);
                }
                catch (Exception e) {
                    logger.warn(" \u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{} | \u9519\u8bef:{}", dataMaskCode, valueStr, e.getMessage());
                    if (!logger.isDebugEnabled()) break block4;
                    logger.error(" \u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{}", dataMaskCode, valueStr, e);
                }
            }
            return AbstractData.valueOf((Object)desensitizeValue, (int)metaData.getDataType());
        }
        return abstractData;
    }

    protected AbstractData evaluateDefaultValue(DimensionValueSet rowKeys, IMetaData metaData) {
        DataField dataField = metaData.getDataField();
        if (!StringUtils.hasLength(dataField.getDefaultValue())) {
            return null;
        }
        AbstractData value = null;
        if (metaData.getDataType() != 6) {
            AbstractData dev = this.dataEngineService.expressionEvaluate(dataField.getDefaultValue(), this.context, rowKeys);
            if (dev != null) {
                value = AbstractData.valueOf((Object)dev.getAsObject(), (int)metaData.getDataType());
            }
        } else {
            String defaultValue = dataField.getDefaultValue();
            if (defaultValue.matches("-?\\d+(\\.\\d+)?") || defaultValue.matches("-?\\d{1,3}(,\\d{3})*(\\.\\d+)?")) {
                value = AbstractData.valueOf((String)dataField.getDefaultValue());
            } else {
                AbstractData dev = this.dataEngineService.expressionEvaluate(defaultValue, this.context, rowKeys);
                if (dev != null) {
                    value = AbstractData.valueOf((Object)dev.getAsObject(), (int)metaData.getDataType());
                }
            }
        }
        return value;
    }

    protected Collection<DataPermissionResource> buildDataPermission(IFieldQueryInfo queryInfo) {
        List<IMetaData> metaData = this.relation.getMetaData();
        DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory = this.providerStore.getDataPermissionEvaluatorFactory();
        DimensionCollection dimensionCollection = queryInfo.getDimensionCollection();
        ParamProvider paramProvider = this.paramDataProvider.getParamProvider();
        HashMap<String, List> parMap = new HashMap<String, List>();
        HashMap<String, DataTable> dataTableMap = new HashMap<String, DataTable>();
        for (IMetaData iMetaData : metaData) {
            DataField dataField = iMetaData.getDataField();
            String string = dataField.getDataTableKey();
            DataTable dataTable = (DataTable)dataTableMap.get(string);
            if (dataTable == null) {
                dataTable = this.runtimeDataSchemeService.getDataTable(string);
                dataTableMap.put(string, dataTable);
            }
            List codes = parMap.computeIfAbsent(dataTable.getCode(), k -> new ArrayList());
            codes.add(dataField.getCode());
        }
        HashMap<String, Map> regionMap = new HashMap<String, Map>();
        for (Map.Entry entry : parMap.entrySet()) {
            String string = (String)entry.getKey();
            List value = (List)entry.getValue();
            Set<RegionPO> regions = paramProvider.getRegions(string, value);
            for (RegionPO regionPO : regions) {
                Map schemeMap = regionMap.computeIfAbsent(regionPO.getTaskKey(), r -> new HashMap());
                schemeMap.computeIfAbsent(regionPO.getFormSchemeKey(), r -> new ArrayList()).add(regionPO);
            }
        }
        if (queryInfo.getAuthMode() == ResouceType.FORM) {
            ArrayList<DataPermissionResource> arrayList = new ArrayList<DataPermissionResource>();
            for (Map.Entry entry : regionMap.entrySet()) {
                String taskKey = (String)entry.getKey();
                Map value = (Map)entry.getValue();
                for (Map.Entry entry2 : value.entrySet()) {
                    String formSchemeKey = (String)entry2.getKey();
                    List forms = (List)entry2.getValue();
                    List formKeys = forms.stream().map(RegionPO::getFormKey).collect(Collectors.toList());
                    EvaluatorParam evaluatorParam = new EvaluatorParam();
                    evaluatorParam.setTaskId(taskKey);
                    evaluatorParam.setFormSchemeId(formSchemeKey);
                    evaluatorParam.setResourceType(queryInfo.getAuthMode().getCode());
                    DataPermissionEvaluator evaluator = dataPermissionEvaluatorFactory.createEvaluator(evaluatorParam, dimensionCollection, formKeys);
                    DataPermission dataPermission = evaluator.haveAccess(queryInfo.getDimensionCollection(), formKeys, AuthType.READABLE);
                    Collection accessResources = dataPermission.getAccessResources();
                    arrayList.addAll(accessResources);
                }
            }
            return arrayList;
        }
        Set set = regionMap.keySet();
        if (!CollectionUtils.isEmpty(regionMap) && set.size() > 1) {
            throw new UnsupportedOperationException("\u6307\u6807\u627e\u5230\u591a\u4e2a\u4efb\u52a1\uff0c\u6682\u4e0d\u652f\u6301\u67e5\u8be2");
        }
        Optional optional = set.stream().findFirst();
        if (optional.isPresent()) {
            String string = (String)optional.get();
            List zbKeys = metaData.stream().map(a -> a.getDataField().getKey()).collect(Collectors.toList());
            EvaluatorParam evaluatorParam = new EvaluatorParam();
            evaluatorParam.setTaskId(string);
            evaluatorParam.setResourceType(queryInfo.getAuthMode().getCode());
            DataPermissionEvaluator evaluator = dataPermissionEvaluatorFactory.createEvaluator(evaluatorParam, dimensionCollection, zbKeys);
            DataPermission dataPermission = evaluator.haveAccess(queryInfo.getDimensionCollection(), zbKeys, AuthType.READABLE);
            return dataPermission.getAccessResources();
        }
        throw new UnsupportedOperationException("\u6307\u6807\u6743\u9650\u5224\u65ad\u672a\u627e\u5230\u4efb\u52a1\uff0c\u65e0\u6cd5\u5224\u65ad\u6743\u9650");
    }

    protected DimensionValueSet getAccessMasterKey(IFieldQueryInfo queryInfo) {
        Set<DimensionValueSet> dimensionValueSets = this.getAccessMasterKeys(queryInfo);
        if (CollectionUtils.isEmpty(dimensionValueSets)) {
            return null;
        }
        return DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList<DimensionValueSet>(dimensionValueSets));
    }

    protected Set<DimensionValueSet> getAccessMasterKeys(IFieldQueryInfo queryInfo) {
        ResouceType authMode = queryInfo.getAuthMode();
        HashSet<DimensionValueSet> dimensionValueSets = new HashSet<DimensionValueSet>();
        if (authMode != ResouceType.NONE) {
            Collection<DataPermissionResource> permissionResources = this.buildDataPermission(queryInfo);
            for (DataPermissionResource accessResource : permissionResources) {
                DimensionValueSet dimensionValueSet = accessResource.getDimensionCombination().toDimensionValueSet();
                dimensionValueSets.add(dimensionValueSet);
            }
        } else {
            List dimensionCombinations = queryInfo.getDimensionCollection().getDimensionCombinations();
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                dimensionValueSets.add(dimensionCombination.toDimensionValueSet());
            }
        }
        return dimensionValueSets;
    }

    public void queryTableData(IFieldQueryInfo queryInfo, FieldRelation fieldRelation, IDataReader dataReader) throws CrudException {
        this.relation = fieldRelation;
        this.queryInfo = queryInfo;
        Iterator<String> fieldStr = queryInfo.selectFieldItr();
        ArrayList<String> keys = new ArrayList<String>();
        while (fieldStr.hasNext()) {
            keys.add(fieldStr.next());
        }
        List<IMetaData> metaData = fieldRelation.getMetaData(keys);
        DimensionValueSet dimensionValueSet = this.getAccessMasterKey(queryInfo);
        this.dataReader = dataReader;
        if (dimensionValueSet == null) {
            this.dataReader.start(metaData, 0L);
            this.dataReader.finish();
            return;
        }
        this.initDataQuery(queryInfo, fieldRelation, dimensionValueSet);
        this.dataQuery.setMasterKeys(dimensionValueSet);
        this.addQueryCol(metaData);
        this.addFilter();
        this.addOrder();
        this.addPage(this.queryInfo.getPageInfo());
        this.execQuery();
    }

    public void readRowData(IDataRow row) {
        List<IMetaData> metaData = this.relation.getMetaData();
        RowData rowData = new RowData();
        ArrayList<DataValue> values = new ArrayList<DataValue>(metaData.size() + 1);
        rowData.setDataValues(values);
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(row.getRowKeys());
        rowData.setDimension(builder.getCombination());
        rowData.setMasterDimension(rowData.getDimension());
        for (IMetaData meta : metaData) {
            AbstractData abstractData = this.metaDataTransfer(meta, row, rowData);
            DataValue value = new DataValue(meta, abstractData);
            value.setRowData(rowData);
            values.add(value);
        }
        this.dataReader.readRow((IRowData)rowData);
    }

    protected abstract void execQuery();
}

