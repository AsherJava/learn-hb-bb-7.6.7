/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.parameter.extend.ds.field;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.parameter.DataSetManagerUtils;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.extend.ds.field.DSFieldDataSourceModel;
import com.jiuqi.bi.parameter.extend.ds.hier.DSHierarchyDataSourceModel;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DsFiledQueryBuilder {
    private Map<String, FilterItem> cascadeFilterItemMap = new HashMap<String, FilterItem>();

    public List<FilterItem> buildFilterItems(DSFieldDataSourceModel dataSourceModel, List<DataSourceValueModel> values, Map<ParameterModel, Object> parameterValueMap) throws DataSourceException {
        try {
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            if (values == null || values.size() == 0) {
                return filterItems;
            }
            this.buildQueryFilter(values, dataSourceModel, filterItems);
            this.buildCascadeQueryFilters(parameterValueMap, dataSourceModel, filterItems);
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    private void buildCascadeQueryFilters(Map<ParameterModel, Object> parameterValueMap, DSFieldDataSourceModel dataSourceModel, List<FilterItem> filterItems) throws DataSourceException {
        if (parameterValueMap == null || parameterValueMap.size() == 0) {
            return;
        }
        this.cascadeFilterItemMap.clear();
        for (ParameterModel parameterModel : parameterValueMap.keySet()) {
            DSHierarchyDataSourceModel dsHierDataSourceModel;
            Object[] vs;
            MemoryDataSet values;
            Object cascadeValue;
            DataSourceModel cascadeDataSourceModel = parameterModel.getDataSourceModel();
            if (cascadeDataSourceModel.getType().equals("com.jiuqi.bi.datasource.dsfield")) {
                DSFieldDataSourceModel dsFieldDataSourceModel = (DSFieldDataSourceModel)cascadeDataSourceModel;
                if (!dsFieldDataSourceModel.getDsName().equals(dataSourceModel.getDsName())) continue;
                cascadeValue = parameterValueMap.get(parameterModel);
                if (cascadeValue instanceof MemoryDataSet) {
                    values = (MemoryDataSet)cascadeValue;
                    if (values.size() == 0) continue;
                    this.buildCascadeQueryFilter((MemoryDataSet<ParameterColumnInfo>)values, (DSFieldDataSourceModel)cascadeDataSourceModel, filterItems);
                    continue;
                }
                if (!(cascadeValue instanceof Object[])) continue;
                vs = (Object[])cascadeValue;
                this.buildCascadeQueryFilter(Arrays.asList(vs), (DSFieldDataSourceModel)cascadeDataSourceModel, filterItems);
                continue;
            }
            if (!cascadeDataSourceModel.getType().equals("com.jiuqi.bi.datasource.dshier") || !(dsHierDataSourceModel = (DSHierarchyDataSourceModel)cascadeDataSourceModel).getDsName().equals(dataSourceModel.getDsName()) || !dsHierDataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.PARENT_SON) && !dsHierDataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.STRUCTURECODE)) continue;
            cascadeValue = parameterValueMap.get(parameterModel);
            if (cascadeValue instanceof MemoryDataSet) {
                values = (MemoryDataSet)cascadeValue;
                if (values.size() == 0) continue;
                this.buildCascadeQueryFilterForHier((MemoryDataSet<ParameterColumnInfo>)values, dsHierDataSourceModel, filterItems);
                continue;
            }
            if (!(cascadeValue instanceof Object[])) continue;
            vs = (Object[])cascadeValue;
            this.buildCascadeQueryFilterForHier(Arrays.asList(vs), dsHierDataSourceModel, filterItems);
        }
    }

    private void buildCascadeQueryFilterForHier(MemoryDataSet<ParameterColumnInfo> values, DSHierarchyDataSourceModel dsHierDataSourceModel, List<FilterItem> filterItems) throws DataSourceException {
        try {
            FilterItem filterItem;
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            DSHierarchy dsHier = dataSetManager.findHierarchy(dsHierDataSourceModel.getDsName(), dsHierDataSourceModel.getDsType(), dsHierDataSourceModel.getDsHierarchyName());
            String dsName = dsHierDataSourceModel.getDsName();
            String dsFieldName = dsHier.getLevels().get(0);
            DSField dsField = dataSetManager.findField(dsName, dsHierDataSourceModel.getDsType(), dsFieldName);
            if (dsField == null) {
                return;
            }
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyField = keyAndNameCol[0];
            ArrayList<Object> keyList = new ArrayList<Object>();
            for (DataRow row : values) {
                Object value = row.getValue(values.getMetadata().indexOf(keyField));
                value = DataSourceUtils.formatDSParentValue(value.toString(), dsFieldName, dsHierDataSourceModel);
                keyList.add(value);
            }
            if (this.cascadeFilterItemMap.containsKey(keyField)) {
                filterItem = this.cascadeFilterItemMap.get(keyField);
                filterItem.getKeyList().addAll(keyList);
            } else {
                filterItem = new FilterItem();
                filterItem.setFieldName(keyField);
                filterItem.setKeyList(keyList);
                filterItems.add(filterItem);
                this.cascadeFilterItemMap.put(keyField, filterItem);
            }
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u5efa\u6570\u636e\u96c6\u7ea7\u8054\u67e5\u8be2\u5bf9\u8c61\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private void buildCascadeQueryFilterForHier(List<Object> keyList, DSHierarchyDataSourceModel dsHierDataSourceModel, List<FilterItem> filterItems) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            DSHierarchy dsHier = dataSetManager.findHierarchy(dsHierDataSourceModel.getDsName(), dsHierDataSourceModel.getDsType(), dsHierDataSourceModel.getDsHierarchyName());
            String dsName = dsHierDataSourceModel.getDsName();
            String dsFieldName = dsHier.getLevels().get(0);
            DSField dsField = dataSetManager.findField(dsName, dsHierDataSourceModel.getDsType(), dsFieldName);
            if (dsField == null) {
                return;
            }
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyField = keyAndNameCol[0];
            if (this.cascadeFilterItemMap.containsKey(keyField)) {
                FilterItem filterItem = this.cascadeFilterItemMap.get(keyField);
                filterItem.getKeyList().addAll(keyList);
            } else {
                FilterItem filterItem = new FilterItem();
                filterItem.setFieldName(keyField);
                filterItem.setKeyList(keyList);
                filterItems.add(filterItem);
                this.cascadeFilterItemMap.put(keyField, filterItem);
            }
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u5efa\u6570\u636e\u96c6\u7ea7\u8054\u67e5\u8be2\u5bf9\u8c61\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private void buildCascadeQueryFilter(MemoryDataSet<ParameterColumnInfo> values, DSFieldDataSourceModel dataSourceModel, List<FilterItem> filterItems) throws DataSourceException {
        try {
            FilterItem filterItem;
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSField dsField = dataSetManager.findField(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsFieldName());
            if (dsField == null) {
                return;
            }
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyField = keyAndNameCol[0];
            ArrayList<Object> keyList = new ArrayList<Object>();
            for (DataRow row : values) {
                Object value = row.getValue(values.getMetadata().indexOf(keyField));
                value = DataSourceUtils.formatDSParentValue(value.toString(), dataSourceModel.getDsFieldName(), dataSourceModel);
                keyList.add(value);
            }
            if (this.cascadeFilterItemMap.containsKey(keyField)) {
                filterItem = this.cascadeFilterItemMap.get(keyField);
                filterItem.getKeyList().addAll(keyList);
            } else {
                filterItem = new FilterItem();
                filterItem.setFieldName(keyField);
                filterItem.setKeyList(keyList);
                filterItems.add(filterItem);
                this.cascadeFilterItemMap.put(keyField, filterItem);
            }
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u5efa\u6570\u636e\u96c6\u7ea7\u8054\u67e5\u8be2\u5bf9\u8c61\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private void buildCascadeQueryFilter(List<Object> keyList, DSFieldDataSourceModel dataSourceModel, List<FilterItem> filterItems) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSField dsField = dataSetManager.findField(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsFieldName());
            if (dsField == null) {
                return;
            }
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyField = keyAndNameCol[0];
            if (this.cascadeFilterItemMap.containsKey(keyField)) {
                FilterItem filterItem = this.cascadeFilterItemMap.get(keyField);
                filterItem.getKeyList().addAll(keyList);
            } else {
                FilterItem filterItem = new FilterItem();
                filterItem.setFieldName(keyField);
                filterItem.setKeyList(keyList);
                filterItems.add(filterItem);
                this.cascadeFilterItemMap.put(keyField, filterItem);
            }
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u5efa\u6570\u636e\u96c6\u7ea7\u8054\u67e5\u8be2\u5bf9\u8c61\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private void buildQueryFilter(List<DataSourceValueModel> values, DSFieldDataSourceModel dataSourceModel, List<FilterItem> filterItems) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSField dsField = dataSetManager.findField(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsFieldName());
            if (dsField == null) {
                return;
            }
            ArrayList<Object> keyValues = new ArrayList<Object>();
            for (DataSourceValueModel dataSourceValueModel : values) {
                keyValues.add(DataSourceUtils.format(dataSourceValueModel.getCode(), dataSourceModel.getDataType()));
            }
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyField = keyAndNameCol[0];
            FilterItem filterItem = new FilterItem(keyField, keyValues);
            filterItems.add(filterItem);
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    public List<FilterItem> buildFiterItems(DSFieldDataSourceModel dataSourceModel, String expression, Map<ParameterModel, Object> parameterValueMap) throws DataSourceException {
        try {
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            if (StringUtils.isEmpty((String)expression)) {
                return filterItems;
            }
            this.buildQueryFilter(expression, dataSourceModel, filterItems);
            this.buildCascadeQueryFilters(parameterValueMap, dataSourceModel, filterItems);
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    public List<FilterItem> buildCascadeFiterItems(DSFieldDataSourceModel dataSourceModel, Map<ParameterModel, Object> parameterValueMap) throws DataSourceException {
        try {
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            this.buildCascadeQueryFilters(parameterValueMap, dataSourceModel, filterItems);
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    private void buildQueryFilter(String expression, DSFieldDataSourceModel dataSourceModel, List<FilterItem> filterItems) throws BIDataSetNotFoundException, BIDataSetException {
        String dsName = dataSourceModel.getDsName();
        String dsFieldName = dataSourceModel.getDsFieldName();
        IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
        DSField dsField = dataSetManager.findField(dsName, dataSourceModel.getDsType(), dsFieldName);
        String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
        String keyField = keyAndNameCol[0];
        FilterItem filterItem = new FilterItem(keyField, expression);
        filterItems.add(filterItem);
    }

    public String buildSearchFiterExpr(DSFieldDataSourceModel dataSourceModel, List<Object> filterValues, Map<ParameterModel, Object> parameterValueMap) throws DataSourceException {
        try {
            String dsName = dataSourceModel.getDsName();
            String dsFieldName = dataSourceModel.getDsFieldName();
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            DSField dsField = dataSetManager.findField(dsName, dataSourceModel.getDsType(), dsFieldName);
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyCol = keyAndNameCol[0];
            String nameCol = keyAndNameCol[1];
            StringBuilder sb = new StringBuilder();
            for (Object filterValue : filterValues) {
                sb.append("(");
                if (filterValue instanceof String) {
                    sb.append(keyCol).append(" LIKE ").append("\"%").append(filterValue).append("%\"").append(" OR ");
                    sb.append(nameCol).append(" LIKE ").append("\"%").append(filterValue).append("%\"");
                } else {
                    sb.append(keyCol).append(" = ").append(filterValue).append(" OR ");
                    sb.append(nameCol).append(" = ").append(filterValue);
                }
                sb.append(")");
                if (filterValues.indexOf(filterValue) == filterValues.size() - 1) continue;
                sb.append(" AND ");
            }
            return sb.toString();
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    public List<FilterItem> buildFilterItems(DSFieldDataSourceModel dataSourceModel) throws DataSourceException {
        try {
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            this.buildQueryFilter(dataSourceModel, filterItems);
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    private void buildQueryFilter(DSFieldDataSourceModel dataSourceModel, List<FilterItem> filterItems) throws DataSourceException {
        try {
            String dsName = dataSourceModel.getDsName();
            String dsFieldName = dataSourceModel.getDsFieldName();
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            DSField dsField = dataSetManager.findField(dsName, dataSourceModel.getDsType(), dsFieldName);
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            FilterItem filterItem = new FilterItem(keyAndNameCol[0], "");
            filterItems.add(filterItem);
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    public List<FilterItem> buildFilterItems(DSFieldDataSourceModel dataSourceModel, Map<ParameterModel, Object> parameterValueMap) throws DataSourceException {
        try {
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            this.buildQueryFilter(dataSourceModel, filterItems);
            this.buildCascadeQueryFilters(parameterValueMap, dataSourceModel, filterItems);
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }
}

