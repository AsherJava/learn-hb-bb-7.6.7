/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.definitions.DataDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bql.dataengine.adapt;

import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.nr.bql.dataengine.adapt.QueryColumnModelFinder;
import com.jiuqi.nr.bql.extend.model.QueryColumnModel;
import com.jiuqi.nr.bql.extend.model.QueryTableModel;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueryDataModelDefinitionsCache
extends DataModelDefinitionsCache {
    private QueryColumnModelFinder columnModelFinder;
    private IRuntimeDataSchemeService dataSchemeService;
    private IEntityMetaService entityMetaService;

    public QueryDataModelDefinitionsCache(ExecutorContext context, ReportFormulaParser parser, IRuntimeDataSchemeService dataSchemeService, IEntityMetaService entityMetaService, QueryColumnModelFinder columnModelFinder) {
        super(context, parser);
        this.dataSchemeService = dataSchemeService;
        this.entityMetaService = entityMetaService;
        if (columnModelFinder != null) {
            this.columnModelFinder = columnModelFinder;
        }
    }

    public TableModelRunInfo getTableInfo(String tableName) {
        try {
            TableModelRunInfo tableRunInfo = super.getTableInfo(tableName);
            if (tableRunInfo == null) {
                if (this.tableNameSearch.containsKey(tableName = tableName.toUpperCase())) {
                    return (TableModelRunInfo)this.tableNameSearch.get(tableName);
                }
                DataTable dataTable = this.dataSchemeService.getDataTableByCode(tableName);
                if (dataTable == null) {
                    return null;
                }
                List<ColumnModelDefine> allFields = this.getQueryColumnModels(dataTable);
                QueryTableModel tableDefine = new QueryTableModel(dataTable, allFields.size() == 0 ? null : ((QueryColumnModel)allFields.get(0)).getDeployInfo().getTableName());
                tableRunInfo = new TableModelRunInfo((TableModelDefine)tableDefine, allFields, (IColumnModelFinder)this.columnModelFinder);
                tableRunInfo.buildTableInfo(this.executorContext);
                this.tableNameSearch.put(tableName, tableRunInfo);
                this.tableCodeSearch.put(tableName, tableRunInfo);
                return tableRunInfo;
            }
            return super.getTableInfo(tableName);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableModelRunInfo getTableInfo(TableModelDefine tableDefine) {
        try {
            TableModelRunInfo tableRunInfo = super.getTableInfo(tableDefine);
            if (tableRunInfo == null) {
                return this.getTableInfo(tableDefine.getName());
            }
            return super.getTableInfo(tableDefine);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableModelRunInfo getTableInfoByCode(String tableCode) {
        TableModelRunInfo tableRunInfo = super.getTableInfoByCode(tableCode);
        if (tableRunInfo == null) {
            tableRunInfo = this.getTableInfo(tableCode);
        }
        return tableRunInfo;
    }

    public ColumnModelDefine findField(String fieldKey) {
        ColumnModelDefine columnModel = super.findField(fieldKey);
        if (columnModel == null) {
            try {
                DataField dataField = this.dataSchemeService.getDataField(fieldKey);
                if (dataField != null) {
                    columnModel = this.dataFieldToColumnModel(dataField);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return columnModel;
    }

    public TableModelDefine findTable(String tableKey) {
        TableModelDefine tableModel = super.findTable(tableKey);
        if (tableModel == null) {
            try {
                DataTable dataTable = this.dataSchemeService.getDataTable(tableKey);
                if (dataTable == null) {
                    return null;
                }
                List fields = this.dataSchemeService.getDataFieldByTable(dataTable.getKey());
                List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{((DataField)fields.get(0)).getKey()});
                String tableName = deployInfos.size() == 0 ? null : ((DataFieldDeployInfo)deployInfos.get(0)).getTableName();
                tableModel = new QueryTableModel(dataTable, tableName);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return tableModel;
    }

    public ColumnModelDefine getFieldByCodeInTable(String fieldCode, String tableKey) {
        ColumnModelDefine columnModel = super.getFieldByCodeInTable(fieldCode, tableKey);
        if (columnModel == null) {
            try {
                DataField dataField = this.dataSchemeService.getDataFieldByTableKeyAndCode(tableKey, fieldCode);
                columnModel = this.dataFieldToColumnModel(dataField);
            }
            catch (Exception e) {
                return null;
            }
        }
        return columnModel;
    }

    public TableModelDefine getTableModel(ColumnModelDefine columnModelDefine) {
        TableModelDefine tableModel = super.getTableModel(columnModelDefine);
        if (tableModel == null) {
            if (Objects.isNull(columnModelDefine)) {
                return null;
            }
            DataTable dataTable = this.dataSchemeService.getDataTable(columnModelDefine.getTableID());
            if (dataTable == null) {
                return null;
            }
            tableModel = new QueryTableModel(dataTable, ((QueryColumnModel)columnModelDefine).getDeployInfo().getTableName());
        }
        return tableModel;
    }

    public void setDataDefinitionsCache(DataDefinitionsCache dataDefinitionsCache) {
        this.dataDefinitionsCache = dataDefinitionsCache;
    }

    public List<ColumnModelDefine> getQueryColumnModels(DataTable dataTable) {
        List dataFields = this.dataSchemeService.getDataFieldByTable(dataTable.getKey());
        ArrayList<ColumnModelDefine> allFields = new ArrayList<ColumnModelDefine>();
        dataFields.forEach(dataField -> {
            QueryColumnModel columnModel = this.dataFieldToColumnModel((DataField)dataField);
            if (columnModel != null) {
                allFields.add(columnModel);
            }
        });
        return allFields;
    }

    private QueryColumnModel dataFieldToColumnModel(DataField dataField) {
        QueryColumnModel columnModel = null;
        List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()});
        if (deployInfos != null && deployInfos.size() > 0) {
            columnModel = new QueryColumnModel(dataField, (DataFieldDeployInfo)deployInfos.get(0));
            String entityId = dataField.getRefDataEntityKey();
            if (StringUtils.isNotEmpty((String)entityId)) {
                IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
                TableModelDefine tableModel = this.entityMetaService.getTableModel(entityId);
                if (entityModel != null) {
                    columnModel.setReferTableID(tableModel.getID());
                    columnModel.setReferColumnID(entityModel.getBizKeyField().getID());
                }
            }
        }
        return columnModel;
    }
}

