/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bql.dataengine.adapt;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bql.extend.model.QueryColumnModel;
import com.jiuqi.nr.bql.extend.model.QueryTableModel;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.List;

public class QueryColumnModelFinder
implements IColumnModelFinder {
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private IEntityMetaService entityMetaService;
    private IColumnModelFinder finder;

    public QueryColumnModelFinder(IRuntimeDataSchemeService runtimeDataSchemeService, IEntityMetaService entityMetaService, IColumnModelFinder finder) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.entityMetaService = entityMetaService;
        this.finder = finder;
    }

    public ColumnModelDefine findColumnModelDefine(ExecutorContext context, String fieldCode) throws Exception {
        IFmlExecEnvironment env;
        ColumnModelDefine columnModel = this.finder.findColumnModelDefine(context, fieldCode);
        if (columnModel == null && (env = context.getEnv()) instanceof ReportFmlExecEnvironment) {
            ReportFmlExecEnvironment rptEnv = (ReportFmlExecEnvironment)env;
            String currentDataScheme = rptEnv.getDataScehmeKey();
            columnModel = this.findColumnModelDefine(context, currentDataScheme, fieldCode);
        }
        return columnModel;
    }

    public ColumnModelDefine findColumnModelDefine(ExecutorContext context, String fieldCode, int periodType) throws Exception {
        return this.finder.findColumnModelDefine(context, fieldCode, periodType);
    }

    public ColumnModelDefine findColumnModelDefine(ExecutorContext context, String dataScheme, String fieldCode) throws Exception {
        ColumnModelDefine columnModel = this.finder.findColumnModelDefine(context, dataScheme, fieldCode);
        if (columnModel == null) {
            DataField dataField = this.runtimeDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(dataScheme, fieldCode);
            if (dataField == null) {
                return null;
            }
            columnModel = this.dataFieldToColumnModel(dataField);
        }
        return columnModel;
    }

    public ColumnModelDefine findColumnModelDefine(FieldDefine fieldDefine) throws Exception {
        ColumnModelDefine columnModel = this.finder.findColumnModelDefine(fieldDefine);
        if (columnModel == null) {
            columnModel = this.dataFieldToColumnModel((DataField)fieldDefine);
        }
        return columnModel;
    }

    public FieldDefine findFieldDefine(ColumnModelDefine columnModelDefine) throws Exception {
        FieldDefine fieldDefine = this.finder.findFieldDefine(columnModelDefine);
        if (fieldDefine == null) {
            fieldDefine = RuntimeDefinitionTransfer.toFieldDefine((DataField)this.runtimeDataSchemeService.getDataField(columnModelDefine.getID()));
        }
        return fieldDefine;
    }

    public FieldDefine findFieldDefineByColumnId(String columnId) {
        FieldDefine fieldDefine = this.finder.findFieldDefineByColumnId(columnId);
        if (fieldDefine == null) {
            fieldDefine = RuntimeDefinitionTransfer.toFieldDefine((DataField)this.runtimeDataSchemeService.getDataField(columnId));
        }
        return fieldDefine;
    }

    public List<ColumnModelDefine> getAllColumnModelsByTableKey(ExecutorContext context, String tableKey) throws Exception {
        ArrayList<QueryColumnModel> allFields = this.finder.getAllColumnModelsByTableKey(context, tableKey);
        if (allFields == null || allFields.size() == 0) {
            List dataFields = this.runtimeDataSchemeService.getDataFieldByTable(tableKey);
            allFields = new ArrayList<QueryColumnModel>();
            for (DataField dataField : dataFields) {
                QueryColumnModel columnModel = this.dataFieldToColumnModel(dataField);
                if (columnModel == null) continue;
                allFields.add(columnModel);
            }
        }
        return allFields;
    }

    public TableModelDefine getTableModelByTableKey(ExecutorContext context, String tableKey) throws Exception {
        TableModelDefine tableModel = this.finder.getTableModelByTableKey(context, tableKey);
        if (tableModel == null) {
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
            if (dataTable == null) {
                return null;
            }
            List fields = this.runtimeDataSchemeService.getDataFieldByTable(dataTable.getKey());
            List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{((DataField)fields.get(0)).getKey()});
            String tableName = deployInfos.size() == 0 ? null : ((DataFieldDeployInfo)deployInfos.get(0)).getTableName();
            tableModel = new QueryTableModel(dataTable, tableName);
        }
        return tableModel;
    }

    public PeriodType getPeriodType(TableModelDefine tableModelDefine) {
        return this.finder.getPeriodType(tableModelDefine);
    }

    private QueryColumnModel dataFieldToColumnModel(DataField dataField) {
        QueryColumnModel columnModel = null;
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()});
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

