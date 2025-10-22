/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class DataAssistImpl
implements IDataAssist {
    private ExecutorContext context;
    private DefinitionsCache cache;
    private DataDefinitionsCache dataDefinitionsCache;
    private DataModelDefinitionsCache dataModelDefinitionsCache;

    public DataAssistImpl(ExecutorContext context) {
        try {
            this.context = context;
            this.cache = new DefinitionsCache(context);
            this.dataDefinitionsCache = this.cache.getDataDefinitionsCache();
            this.dataModelDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        }
        catch (Exception e) {
            this.cache = null;
            this.dataDefinitionsCache = null;
            this.dataModelDefinitionsCache = null;
        }
    }

    @Override
    public String getDimensionName(EntityViewDefine viewDefine) {
        if (this.dataModelDefinitionsCache == null) {
            return "";
        }
        return this.dataModelDefinitionsCache.getDimensionName(viewDefine);
    }

    @Override
    public FieldDefine getDimensionField(String tableName, String dimensionName) {
        if (this.dataModelDefinitionsCache == null) {
            return null;
        }
        ColumnModelDefine field = this.dataModelDefinitionsCache.getDimensionField(tableName, dimensionName);
        if (field != null) {
            return this.dataModelDefinitionsCache.getFieldDefine(field);
        }
        return null;
    }

    @Override
    public String getDimensionName(FieldDefine fieldDefine) {
        if (this.dataModelDefinitionsCache == null) {
            return null;
        }
        ColumnModelDefine columnModel = this.dataModelDefinitionsCache.getColumnModel(fieldDefine);
        TableModelDefine tableModel = this.dataModelDefinitionsCache.getTableModel(columnModel);
        TableModelRunInfo tableRunInfo = this.dataModelDefinitionsCache.getTableInfo(tableModel.getName());
        if (tableRunInfo == null) {
            return null;
        }
        return this.dataModelDefinitionsCache.getDimensionName(columnModel);
    }

    @Override
    public ReportFormulaParser createFormulaParser(boolean isJQReportModel) {
        return this.cache.getFormulaParser(this.context);
    }

    @Override
    public void setDesignTimeData(boolean designTimeData, IDataDefinitionDesignTimeController designTimeController) {
        if (designTimeData && designTimeController != null) {
            this.dataDefinitionsCache.setDesignTimeController(designTimeController);
        }
    }

    @Override
    public DimensionSet getDimensionsByTableName(String tableName) {
        if (this.dataModelDefinitionsCache == null) {
            return null;
        }
        TableModelRunInfo tableRunInfo = this.dataModelDefinitionsCache.getTableInfo(tableName);
        if (tableRunInfo == null) {
            return null;
        }
        return tableRunInfo.getDimensions();
    }

    @Override
    public DimensionSet getDimensions(String tableKey) {
        if (this.dataModelDefinitionsCache == null) {
            return null;
        }
        TableModelDefine tableDefine = this.dataModelDefinitionsCache.findTable(tableKey);
        if (tableDefine == null) {
            return null;
        }
        return this.getDimensionsByTableName(tableDefine.getName());
    }

    public void setQueryParam(QueryParam queryParam) {
    }
}

