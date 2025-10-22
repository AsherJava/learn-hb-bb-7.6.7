/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.access.util;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NvwaDataEngineQueryUtil {
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    private static final Logger logger = LoggerFactory.getLogger(NvwaDataEngineQueryUtil.class);

    public ArrayKey convertMasterToArrayKey(String tableName, INvwaDataSet dataSet, DimensionValueSet masterKey) {
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        return dimensionChanger.getArrayKey(masterKey, dataSet.getRowKeyColumns());
    }

    public INvwaDataRow setRowKey(String tableName, INvwaDataRow row, DimensionValueSet masterKey) {
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        DimensionSet dimensions = masterKey.getDimensionSet();
        for (int index = 0; index < dimensions.size(); ++index) {
            String dimName = dimensions.get(index);
            ColumnModelDefine column = dimensionChanger.getColumn(dimName);
            row.setKeyValue(column, masterKey.getValue(dimName));
        }
        return row;
    }

    public String getDimensionName(String tableName, ColumnModelDefine column) {
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        if (dimensionChanger.getDimensionName(column) == null) {
            return column.getCode();
        }
        return dimensionChanger.getDimensionName(column);
    }

    public String getDimensionName(String tableName, String columnCode) {
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        if (dimensionChanger.getDimensionName(columnCode) == null) {
            return columnCode;
        }
        return dimensionChanger.getDimensionName(columnCode);
    }

    public DimensionValueSet convertRowKeyToDimensionValueSet(String tableName, ArrayKey arrayKey, List<ColumnModelDefine> rowKeyColumns) {
        DimensionValueSet newDimension = new DimensionValueSet();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        int index = 0;
        for (ColumnModelDefine rowKeyColumn : rowKeyColumns) {
            if (rowKeyColumn.getCode().endsWith("FORMSCHEMEKEY")) {
                ++index;
                continue;
            }
            String dimName = dimensionChanger.getDimensionName(rowKeyColumn);
            if (dimensionChanger.getDimensionName(rowKeyColumn) == null) {
                dimName = rowKeyColumn.getCode();
            }
            newDimension.setValue(dimName, arrayKey.get(index));
            ++index;
        }
        return newDimension;
    }

    public INvwaDataSet queryDataSetWithRowKey(String tableName, DimensionValueSet dimensionValueSet, List<ColumnModelDefine> allColumns, List<String> groupColumns, Map<String, Boolean> orderColumns, boolean readOnly) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        boolean group = !CollectionUtils.isEmpty(groupColumns);
        boolean order = !CollectionUtils.isEmpty(orderColumns);
        int index = 0;
        for (ColumnModelDefine columnModelDefine : allColumns) {
            Object value;
            String dimensionName;
            NvwaQueryColumn nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
            queryModel.getColumns().add(nvwaQueryColumn);
            String columnCode = columnModelDefine.getCode();
            if (group && groupColumns.contains(columnCode)) {
                queryModel.getGroupByColumns().add(index);
            }
            if (order && orderColumns.keySet().contains(columnCode)) {
                OrderByItem orderItemTime = new OrderByItem(columnModelDefine, orderColumns.get(columnCode).booleanValue());
                queryModel.getOrderByItems().add(orderItemTime);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dimensionValueSet.getValue(dimensionName)) != null) {
                queryModel.getColumnFilters().put(columnModelDefine, value);
            }
            ++index;
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataSet executeQueryWithRowKey = null;
        try {
            if (readOnly) {
                INvwaDataAccess dataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
                executeQueryWithRowKey = dataAccess.executeQueryWithRowKey(context);
            } else {
                INvwaUpdatableDataAccess nvwaDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
                executeQueryWithRowKey = nvwaDataAccess.executeQueryForUpdate(context);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return executeQueryWithRowKey;
    }

    public INvwaDataSet queryDataSet(String tableName, DimensionValueSet dimensionValueSet, List<ColumnModelDefine> allColumns, List<String> groupColumns, Map<String, Object> filterMap, Map<String, Boolean> orderColumns, boolean readOnly) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        boolean group = !CollectionUtils.isEmpty(groupColumns);
        boolean order = !CollectionUtils.isEmpty(orderColumns);
        int index = 0;
        for (ColumnModelDefine columnModelDefine : allColumns) {
            Object value;
            String dimensionName;
            NvwaQueryColumn nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
            queryModel.getColumns().add(nvwaQueryColumn);
            String columnCode = columnModelDefine.getCode();
            if (group && groupColumns.contains(columnCode)) {
                queryModel.getGroupByColumns().add(index);
            }
            if (order && orderColumns.keySet().contains(columnCode)) {
                OrderByItem orderItemTime = new OrderByItem(columnModelDefine, orderColumns.get(columnCode).booleanValue());
                queryModel.getOrderByItems().add(orderItemTime);
            }
            if (dimensionValueSet != null && (dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dimensionValueSet.getValue(dimensionName)) != null) {
                queryModel.getColumnFilters().put(columnModelDefine, value);
            }
            if (!CollectionUtils.isEmpty(filterMap) && filterMap.containsKey(columnCode)) {
                queryModel.getColumnFilters().put(columnModelDefine, filterMap.get(columnCode));
            }
            ++index;
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataSet executeQueryWithRowKey = null;
        try {
            if (readOnly) {
                INvwaDataAccess dataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
                executeQueryWithRowKey = dataAccess.executeQueryWithRowKey(context);
            } else {
                INvwaUpdatableDataAccess nvwaDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
                executeQueryWithRowKey = nvwaDataAccess.executeQueryForUpdate(context);
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5f02\u5e38\uff01", e);
        }
        return executeQueryWithRowKey;
    }

    public INvwaUpdatableDataSet queryUpdateDataSet(String tableName, DimensionValueSet dimensionValueSet, List<ColumnModelDefine> allColumns, Map<String, Object> filterMap) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        for (ColumnModelDefine columnModelDefine : allColumns) {
            Object value;
            String dimensionName;
            NvwaQueryColumn nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
            queryModel.getColumns().add(nvwaQueryColumn);
            String columnCode = columnModelDefine.getCode();
            if (dimensionValueSet != null && (dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dimensionValueSet.getValue(dimensionName)) != null) {
                queryModel.getColumnFilters().put(columnModelDefine, value);
            }
            if (CollectionUtils.isEmpty(filterMap) || !filterMap.containsKey(columnCode)) continue;
            queryModel.getColumnFilters().put(columnModelDefine, filterMap.get(columnCode));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataSet executeQueryWithRowKey = null;
        try {
            INvwaUpdatableDataAccess nvwaDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            executeQueryWithRowKey = nvwaDataAccess.executeQueryForUpdate(context);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5f02\u5e38\uff01", e);
        }
        return executeQueryWithRowKey;
    }

    public int delete(DimensionValueSet dimensionValue, String tableName, List<ColumnModelDefine> allFields) {
        int deleteRow = 0;
        try {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            for (ColumnModelDefine columnModelDefine : allFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            }
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
            DimensionSet dimensionSet = dimensionValue.getDimensionSet();
            for (int i = 0; i < dimensionSet.size(); ++i) {
                String name = dimensionSet.get(i);
                ColumnModelDefine column = dimensionChanger.getColumn(name);
                queryModel.getColumnFilters().put(column, dimensionValue.getValue(name));
            }
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator dataUpdate = updatableDataAccess.openForUpdate(context);
            dataUpdate.deleteAll();
            dataUpdate.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u5f02\u5e38\uff01", e);
            return deleteRow;
        }
        return ++deleteRow;
    }
}

