/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.common.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataEngineAdapter {
    private static final Logger logger = LoggerFactory.getLogger(DataEngineAdapter.class);
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IDimensionProvider dimensionProvider;
    private final List<String> filterDim = Arrays.asList("BZ1");

    public DimensionChanger getDimensionChanger(String tableName) {
        DimensionChanger dimensionChanger = new DimensionChanger(tableName);
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        String bizKeys = tableModel.getBizKeys();
        String[] keyFieldIds = bizKeys.split(";");
        HashMap<String, ColumnModelDefine> dimensionSearch = new HashMap<String, ColumnModelDefine>();
        ColumnModelDefine periodField = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), "DATATIME");
        for (int i = 0; i < keyFieldIds.length; ++i) {
            ColumnModelDefine keyField = this.dataModelService.getColumnModelDefineByID(keyFieldIds[i]);
            if (keyField == null) continue;
            String dimension = null;
            if (periodField != null && keyField.getID().equals(periodField.getID()) || "PERIOD".equals(keyField.getName())) {
                dimension = "DATATIME";
            } else if (keyFieldIds.length == 1 && tableModel.getCode().startsWith("NR_PERIOD_")) {
                dimension = "DATATIME";
            } else {
                if (this.filterDim.contains(keyField.getName())) continue;
                dimension = this.getDimensionNameByField(executorContext, keyField);
            }
            if (dimensionSearch.containsKey(dimension)) {
                String newDimension;
                int index = 1;
                while (dimensionSearch.containsKey(newDimension = String.format("%s_%s", dimension, index++))) {
                }
                dimension = newDimension;
            }
            if (StringUtils.isEmpty((String)dimension)) {
                dimension = keyField.getCode();
            }
            dimensionSearch.put(dimension, keyField);
            dimensionChanger.addDimensionNameColumn(dimension, keyField.getCode(), keyField);
        }
        return dimensionChanger;
    }

    private String getDimensionNameByField(ExecutorContext executorContext, ColumnModelDefine keyField) {
        if (StringUtils.isNotEmpty((String)keyField.getReferTableID())) {
            return this.dimensionProvider.getFieldDimensionName(executorContext, keyField);
        }
        if (StringUtils.isNotEmpty((String)keyField.getReferColumnID())) {
            ColumnModelDefine referColumn = this.dataModelService.getColumnModelDefineByID(keyField.getReferColumnID());
            if (StringUtils.isEmpty((String)referColumn.getReferTableID())) {
                TableModelDefine tableModel = this.dataModelService.getTableModelDefineById(referColumn.getTableID());
                return tableModel.getCode();
            }
            return this.dimensionProvider.getFieldDimensionName(executorContext, referColumn);
        }
        if (keyField.getCode().equals("BIZKEYORDER")) {
            return "RECORDKEY";
        }
        return keyField.getCode();
    }
}

