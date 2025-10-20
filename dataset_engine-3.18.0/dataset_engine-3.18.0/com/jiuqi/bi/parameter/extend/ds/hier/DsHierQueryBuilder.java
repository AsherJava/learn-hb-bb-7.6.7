/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 */
package com.jiuqi.bi.parameter.extend.ds.hier;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.parameter.DataSetManagerUtils;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.ds.field.DSFieldDataSourceModel;
import com.jiuqi.bi.parameter.extend.ds.hier.DSHierarchyDataSourceModel;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DsHierQueryBuilder {
    private Map<String, FilterItem> cascadeFilterItemMap = new HashMap<String, FilterItem>();

    public List<FilterItem> buildFilterItems(ParameterEngineEnv env, DSHierarchyDataSourceModel dataSourceModel, MemoryDataSet<ParameterColumnInfo> values, Map<ParameterModel, Object> parameterValueMap) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSHierarchy dsHierarchy = dataSetManager.findHierarchy(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsHierarchyName());
            List<String> levels = dsHierarchy.getLevels();
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            for (String levelName : levels) {
                FilterItem filterItem = new FilterItem();
                filterItem.setFieldName(levelName);
                filterItem.setKeyList(this.getKeyList(values, levelName, dataSourceModel));
                filterItems.add(filterItem);
            }
            this.buildCascadeQueryFilters(env, parameterValueMap, filterItems, dataSourceModel);
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    private List<Object> getKeyList(MemoryDataSet<ParameterColumnInfo> values, String levelName, DSHierarchyDataSourceModel dataSourceModel) throws BIDataSetNotFoundException, BIDataSetException {
        ArrayList<Object> keyList = new ArrayList<Object>();
        Metadata metaData = values.getMetadata();
        int index = metaData.indexOf(dataSourceModel.getDsHierarchyName());
        if (index < 0) {
            index = metaData.indexOf(levelName);
        }
        if (index != -1) {
            for (DataRow row : values) {
                keyList.add(row.getValue(index));
            }
        }
        return keyList;
    }

    public List<FilterItem> buildFilterItems(ParameterEngineEnv env, DSHierarchyDataSourceModel dataSourceModel, Map<ParameterModel, Object> parameterValueMap) throws DataSourceException {
        try {
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSHierarchy dsHierarchy = dataSetManager.findHierarchy(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsHierarchyName());
            if (dsHierarchy.getType().equals((Object)DSHierarchyType.PARENT_HIERARCHY)) {
                this.buildParentSonFilterItemsForAllValues(dsHierarchy, filterItems, dataSetManager);
            } else {
                this.buildNormalHierFilterItems(dsHierarchy, filterItems, dataSetManager);
            }
            this.buildCascadeQueryFilters(env, parameterValueMap, filterItems, dataSourceModel);
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u9020\u6570\u636e\u96c6\u5c42\u6b21\u67e5\u8be2\u5bf9\u8c61\u51fa\u9519," + e.getMessage(), e);
        }
    }

    private void buildNormalHierFilterItems(DSHierarchy dsHier, List<FilterItem> filterItems, IDataSetManager dataSetManager) throws DataSourceException {
        try {
            List<String> levels = dsHier.getLevels();
            String fieldName = levels.get(0);
            FilterItem filterItem = new FilterItem();
            filterItem.setFieldName(fieldName);
            filterItems.add(filterItem);
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    private void buildParentSonFilterItemsForAllValues(DSHierarchy dsHierarchy, List<FilterItem> filterItems, IDataSetManager dataSetManager) {
    }

    private void buildParentSonFilterItems(DSHierarchy dsHierarchy, List<FilterItem> filterItems, IDataSetManager dataSetManager) {
        String parentFieldName = dsHierarchy.getParentFieldName();
        FilterItem parentFieldFilterItem = new FilterItem();
        parentFieldFilterItem.setFieldName(parentFieldName);
        ArrayList<Object> keyList = new ArrayList<Object>();
        keyList.add("");
        keyList.add(null);
        parentFieldFilterItem.setKeyList(keyList);
        filterItems.add(parentFieldFilterItem);
    }

    public List<FilterItem> buildFilterItems(ParameterEngineEnv env, DSHierarchyDataSourceModel dataSourceModel, Map<String, String> parentValueMap, int level, Map<ParameterModel, Object> parameterValueMap) throws DataSourceException {
        try {
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSHierarchy dsHierarchy = dataSetManager.findHierarchy(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsHierarchyName());
            if (dsHierarchy.getType().equals((Object)DSHierarchyType.PARENT_HIERARCHY)) {
                this.buildParentSonFilterItems(dsHierarchy, filterItems, dataSetManager, parentValueMap);
            } else if (dsHierarchy.getType().equals((Object)DSHierarchyType.CODE_HIERARCHY)) {
                this.buildStructureCodeFilterItems(dsHierarchy, filterItems, dataSetManager, parentValueMap);
            } else {
                this.buildNormalFilterItems(dsHierarchy, filterItems, dataSetManager, parentValueMap, level, dataSourceModel);
            }
            this.buildCascadeQueryFilters(env, parameterValueMap, filterItems, dataSourceModel);
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u9020\u6570\u636e\u96c6\u5c42\u6b21\u67e5\u8be2\u5bf9\u8c61\u51fa\u9519," + e.getMessage(), e);
        }
    }

    private void buildNormalFilterItems(DSHierarchy dsHierarchy, List<FilterItem> filterItems, IDataSetManager dataSetManager, Map<String, String> parentValueMap, int level, DSHierarchyDataSourceModel dataSourceModel) throws BIDataSetNotFoundException, BIDataSetException {
        List<String> levels = dsHierarchy.getLevels();
        if (levels.size() < level + 1) {
            return;
        }
        for (int i = 0; i < level; ++i) {
            String parentFieldName = levels.get(i);
            String parentValue = parentValueMap.get(parentFieldName);
            FilterItem filterItem = new FilterItem(parentFieldName, new ArrayList<Object>());
            filterItem.getKeyList().add(DataSourceUtils.formatDSParentValue(parentValue, parentFieldName, dataSourceModel));
            filterItems.add(filterItem);
        }
    }

    private void buildStructureCodeFilterItems(DSHierarchy dsHierarchy, List<FilterItem> filterItems, IDataSetManager dataSetManager, Map<String, String> parentValueMap) {
        List<String> levels = dsHierarchy.getLevels();
        String fieldName = levels.get(0);
        FilterItem filterItem = new FilterItem();
        filterItem.setFieldName(fieldName);
        ArrayList<Object> keyList = new ArrayList<Object>();
        keyList.add(parentValueMap.get(fieldName));
        filterItem.setKeyList(keyList);
        filterItems.add(filterItem);
    }

    private void buildParentSonFilterItems(DSHierarchy dsHierarchy, List<FilterItem> filterItems, IDataSetManager dataSetManager, Map<String, String> parentValueMap) {
        List<String> levels = dsHierarchy.getLevels();
        String fieldName = levels.get(0);
        String parentFieldName = dsHierarchy.getParentFieldName();
        FilterItem parentFieldFilterItem = new FilterItem();
        parentFieldFilterItem.setFieldName(parentFieldName);
        ArrayList<Object> keyList = new ArrayList<Object>();
        keyList.add(parentValueMap.get(fieldName));
        parentFieldFilterItem.setKeyList(keyList);
        filterItems.add(parentFieldFilterItem);
    }

    public List<FilterItem> buildFilterItems(DSHierarchyDataSourceModel dataSourceModel, ParameterEngineEnv env) throws DataSourceException {
        try {
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSHierarchy dsHierarchy = dataSetManager.findHierarchy(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsHierarchyName());
            if (dsHierarchy.getType().equals((Object)DSHierarchyType.PARENT_HIERARCHY)) {
                this.buildParentSonFilterItems(dsHierarchy, filterItems, dataSetManager);
            } else {
                this.buildNormalHierFilterItems(dsHierarchy, filterItems, dataSetManager);
            }
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u9020\u6570\u636e\u96c6\u5c42\u6b21\u67e5\u8be2\u5bf9\u8c61\u51fa\u9519," + e.getMessage(), e);
        }
    }

    public String buildSearchFilterExpr(DSHierarchyDataSourceModel dataSourceModel, List<Object> values, ParameterEngineEnv env) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSHierarchy dsHierarchy = dataSetManager.findHierarchy(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsHierarchyName());
            if (dsHierarchy.getType().equals((Object)DSHierarchyType.PARENT_HIERARCHY)) {
                return this.buildParentSonSearchFilterExpr(values, dsHierarchy, dsName, dataSourceModel.getDsType(), dataSetManager);
            }
            return this.buildNormalHierSearchFilterExpr(values, dsHierarchy, dsName, dataSourceModel.getDsType(), dataSetManager);
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u9020\u641c\u7d22\u8868\u8fbe\u5f0f\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private String buildNormalHierSearchFilterExpr(List<Object> values, DSHierarchy dsHierarchy, String dsName, String dsType, IDataSetManager dataSetManager) throws DataSourceException {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> levels = dsHierarchy.getLevels();
            for (String level : levels) {
                DSField dsFiled = dataSetManager.findField(dsName, dsType, level);
                for (Object value : values) {
                    String keyField = dsFiled.getKeyField();
                    String nameField = dsFiled.getNameField();
                    if (value instanceof String) {
                        sb.append(keyField).append(" LIKE ").append("\"%").append(value).append("%\"").append(" OR ");
                        sb.append(nameField).append(" LIKE ").append("\"%").append(value).append("%\"");
                    } else {
                        sb.append(keyField).append(" = ").append(value).append(" OR ");
                        sb.append(nameField).append(" = ").append(value);
                    }
                    if (values.indexOf(value) == values.size() - 1) continue;
                    sb.append(" AND ");
                }
                if (levels.indexOf(level) == levels.size() - 1) continue;
                sb.append(" OR ");
            }
            return sb.toString();
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    private String buildParentSonSearchFilterExpr(List<Object> values, DSHierarchy dsHierarchy, String dsName, String dsType, IDataSetManager dataSetManager) throws DataSourceException {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> levels = dsHierarchy.getLevels();
            String fieldName = levels.get(0);
            DSField dsFiled = dataSetManager.findField(dsName, dsType, fieldName);
            String keyField = dsFiled.getKeyField();
            String nameField = dsFiled.getNameField();
            for (Object value : values) {
                sb.append("(");
                if (value instanceof String) {
                    sb.append(keyField).append(" LIKE ").append("\"%").append(value).append("%\"").append(" OR ");
                    sb.append(nameField).append(" LIKE ").append("\"%").append(value).append("%\"");
                } else {
                    sb.append(keyField).append(" = ").append(value).append(" OR ");
                    sb.append(nameField).append(" = ").append(value);
                }
                sb.append(")");
                if (values.indexOf(value) == values.size() - 1) continue;
                sb.append(" AND ");
            }
            return sb.toString();
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    public List<FilterItem> buildFilterItemsForAllValues(ParameterEngineEnv env, DSHierarchyDataSourceModel dataSourceModel, Map<ParameterModel, Object> parameterValueMap) throws DataSourceException {
        try {
            ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSHierarchy dsHierarchy = dataSetManager.findHierarchy(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsHierarchyName());
            if (dsHierarchy.getType().equals((Object)DSHierarchyType.PARENT_HIERARCHY)) {
                this.buildParentSonFilterItemsForAllValues(dsHierarchy, filterItems, dataSetManager);
                this.buildCascadeQueryFilters(env, parameterValueMap, filterItems, dataSourceModel);
            } else {
                List<String> levels = dsHierarchy.getLevels();
                for (String fieldName : levels) {
                    FilterItem filterItem = new FilterItem();
                    filterItem.setFieldName(fieldName);
                    filterItems.add(filterItem);
                }
                this.buildCascadeQueryFilters(env, parameterValueMap, filterItems, dataSourceModel);
            }
            return filterItems;
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u9020\u6570\u636e\u96c6\u5c42\u6b21\u67e5\u8be2\u5bf9\u8c61\u51fa\u9519," + e.getMessage(), e);
        }
    }

    public void buildCascadeQueryFilters(ParameterEngineEnv env, Map<ParameterModel, Object> parameterValueMap, List<FilterItem> filterItems, DSHierarchyDataSourceModel currDataSourceModel) throws DataSourceException {
        if (parameterValueMap == null || parameterValueMap.size() == 0) {
            return;
        }
        this.cascadeFilterItemMap.clear();
        for (ParameterModel parameterModel : parameterValueMap.keySet()) {
            DSHierarchyDataSourceModel dsHierDataSourceModel;
            MemoryDataSet values;
            SmartSelector ss;
            Object cascadeValue;
            DataSourceModel cascadeDataSourceModel = parameterModel.getDataSourceModel();
            if (cascadeDataSourceModel.getType().equals("com.jiuqi.bi.datasource.dsfield")) {
                DSFieldDataSourceModel dsFieldDataSourceModel = (DSFieldDataSourceModel)cascadeDataSourceModel;
                if (!dsFieldDataSourceModel.getDsName().equals(currDataSourceModel.getDsName())) continue;
                cascadeValue = parameterValueMap.get(parameterModel);
                if (cascadeValue instanceof SmartSelector) {
                    ss = (SmartSelector)cascadeValue;
                    try {
                        cascadeValue = ss.getFilterValueInMemory(env, parameterModel.getName());
                    }
                    catch (ParameterException e) {
                        throw new DataSourceException(e);
                    }
                }
                if ((values = (MemoryDataSet)cascadeValue).size() == 0) continue;
                this.buildCascadeQueryFilter((MemoryDataSet<ParameterColumnInfo>)values, (DSFieldDataSourceModel)cascadeDataSourceModel, filterItems);
                continue;
            }
            if (!cascadeDataSourceModel.getType().equals("com.jiuqi.bi.datasource.dshier") || !(dsHierDataSourceModel = (DSHierarchyDataSourceModel)cascadeDataSourceModel).getHireachyType().equals((Object)ParameterHierarchyType.PARENT_SON) && !dsHierDataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.STRUCTURECODE) || !dsHierDataSourceModel.getDsName().equals(currDataSourceModel.getDsName())) continue;
            cascadeValue = parameterValueMap.get(parameterModel);
            if (cascadeValue instanceof SmartSelector) {
                ss = (SmartSelector)cascadeValue;
                try {
                    cascadeValue = ss.getFilterValueInMemory(env, parameterModel.getName());
                }
                catch (ParameterException e) {
                    throw new DataSourceException(e);
                }
            }
            if ((values = (MemoryDataSet)cascadeValue).size() == 0) continue;
            this.buildCascadeQueryFilterForHier((MemoryDataSet<ParameterColumnInfo>)values, dsHierDataSourceModel, filterItems);
        }
    }

    private void buildCascadeQueryFilterForHier(MemoryDataSet<ParameterColumnInfo> values, DSHierarchyDataSourceModel dsHierDataSourceModel, List<FilterItem> filterItems) throws DataSourceException {
        try {
            FilterItem filterItem;
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dsHierDataSourceModel.getDsName();
            DSHierarchy dsHier = dataSetManager.findHierarchy(dsHierDataSourceModel.getDsName(), dsHierDataSourceModel.getDsType(), dsHierDataSourceModel.getDsHierarchyName());
            String dsFieldName = dsHier.getLevels().get(0);
            DSField dsField = dataSetManager.findField(dsName, dsHierDataSourceModel.getDsType(), dsFieldName);
            if (dsField == null) {
                return;
            }
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyField = keyAndNameCol[0];
            ArrayList<Object> keyList = new ArrayList<Object>();
            for (DataRow row : values) {
                keyList.add(row.getValue(values.getMetadata().indexOf(keyField)));
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

    private void buildCascadeQueryFilter(MemoryDataSet<ParameterColumnInfo> values, DSFieldDataSourceModel dataSourceModel, List<FilterItem> filterItems) throws DataSourceException {
        try {
            FilterItem filterItem;
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = dataSourceModel.getDsName();
            DSField dsField = dataSetManager.findField(dsName, dataSourceModel.getDsType(), dataSourceModel.getDsFieldName());
            if (dsField == null) {
                return;
            }
            String keyField = dsField.getKeyField();
            ArrayList<Object> keyList = new ArrayList<Object>();
            for (DataRow row : values) {
                keyList.add(row.getValue(values.getMetadata().indexOf(keyField)));
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
}

