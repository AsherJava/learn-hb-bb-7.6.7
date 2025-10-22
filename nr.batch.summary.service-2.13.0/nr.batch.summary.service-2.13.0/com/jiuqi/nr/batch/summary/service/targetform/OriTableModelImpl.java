/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.nr.batch.summary.service.enumeration.SummaryFunction;
import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumnImpl;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumnImpl;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OriTableModelImpl
implements OriTableModelInfo {
    private boolean simpleTable;
    private String tableName;
    private TableModelDefine oriTableModel;
    private final TableModelDefine tableModel;
    private BSTableColumn dwColumn;
    private BSTableColumn periodColumn;
    private List<BSBizKeyColumn> situationColumns = new ArrayList<BSBizKeyColumn>();
    private List<BSBizKeyColumn> bizKeyColumns = new ArrayList<BSBizKeyColumn>();
    private List<BSBizKeyColumn> buildColumns = new ArrayList<BSBizKeyColumn>();
    private List<BSTableColumn> zbColumns = new ArrayList<BSTableColumn>();
    private DataScheme dataScheme;
    private SubDatabaseTableNamesProvider subDatabaseTableNamesProvider;

    public OriTableModelImpl(TableModelDefine oriTableModel, TableModelDefine tableModel, Map<String, Object> qjDimJustValueMap, DataScheme dataScheme, DataModelService dataModelService, IRuntimeDataSchemeService dataSchemeService, SubDatabaseTableNamesProvider subDatabaseTableNamesProvider) {
        this.tableModel = tableModel;
        this.oriTableModel = oriTableModel;
        this.dataScheme = dataScheme;
        this.subDatabaseTableNamesProvider = subDatabaseTableNamesProvider;
        this.init(dataModelService, dataSchemeService, qjDimJustValueMap);
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public TableModelDefine getOriTableModel() {
        return this.oriTableModel;
    }

    @Override
    public boolean isSimpleTable() {
        return this.simpleTable;
    }

    public void setSimpleTable(boolean simpleTable) {
        this.simpleTable = simpleTable;
    }

    @Override
    public TableModelDefine getTableModel() {
        return this.tableModel;
    }

    @Override
    public BSTableColumn getDWColumn() {
        return this.dwColumn;
    }

    @Override
    public BSTableColumn getPeriodColumn() {
        return this.periodColumn;
    }

    @Override
    public List<BSBizKeyColumn> getSituationColumns() {
        return this.situationColumns;
    }

    @Override
    public List<BSBizKeyColumn> getBizKeyColumns() {
        return this.bizKeyColumns;
    }

    @Override
    public List<BSBizKeyColumn> getBuildColumns() {
        return this.buildColumns;
    }

    @Override
    public List<BSTableColumn> getZBColumns() {
        return this.zbColumns;
    }

    private void init(DataModelService dataModelService, IRuntimeDataSchemeService dataSchemeService, Map<String, Object> qjDimJustValueMap) {
        HashMap<String, DataFieldDeployInfo> dataField2DeployInfo = new HashMap<String, DataFieldDeployInfo>();
        List deployFields = dataSchemeService.getDeployInfoByTableModelKey(this.oriTableModel.getID());
        deployFields.forEach(e -> dataField2DeployInfo.put(e.getDataFieldKey(), (DataFieldDeployInfo)e));
        List dataFields = dataSchemeService.getDataFields(deployFields.stream().map(DataFieldDeployInfo::getDataFieldKey).collect(Collectors.toList()));
        List<DataField> dimensionFields = dataFields.stream().filter(e -> e.getDataFieldKind() == DataFieldKind.PUBLIC_FIELD_DIM).collect(Collectors.toList());
        this.dwColumn = this.getDWColumn(dataModelService, dimensionFields, dataField2DeployInfo);
        this.periodColumn = this.getPeriodColumn(dataModelService, dimensionFields, dataField2DeployInfo);
        this.situationColumns = this.getSituationColumn(dataModelService, dimensionFields, dataField2DeployInfo, qjDimJustValueMap);
        this.bizKeyColumns = this.getBizKeyColumns(dataModelService, dataFields, dataField2DeployInfo);
        this.buildColumns = this.getBuildColumns(dataModelService, dataFields, dataField2DeployInfo);
        this.zbColumns = this.getZBColumns(dataModelService, dataFields, dataField2DeployInfo);
    }

    private BSTableColumn getDWColumn(DataModelService dataModelService, List<DataField> dimensionFields, Map<String, DataFieldDeployInfo> dataField2DeployInfo) {
        for (DataField dataField : dimensionFields) {
            ColumnModelDefine columnByDataField = this.getColumnByDataField(dataModelService, dataField, dataField2DeployInfo);
            if (!"MDCODE".equals(columnByDataField.getName())) continue;
            BSTableColumnImpl dwColumn = new BSTableColumnImpl(columnByDataField);
            dwColumn.setSQLGroupFunc(SummaryFunction.MIN);
            return dwColumn;
        }
        return null;
    }

    private BSTableColumn getPeriodColumn(DataModelService dataModelService, List<DataField> dimensionFields, Map<String, DataFieldDeployInfo> dataField2DeployInfo) {
        for (DataField dataField : dimensionFields) {
            ColumnModelDefine columnByDataField = this.getColumnByDataField(dataModelService, dataField, dataField2DeployInfo);
            if (!"DATATIME".equals(columnByDataField.getName())) continue;
            BSTableColumnImpl dwColumn = new BSTableColumnImpl(columnByDataField);
            dwColumn.setSQLGroupFunc(SummaryFunction.MIN);
            return dwColumn;
        }
        return null;
    }

    private List<BSBizKeyColumn> getSituationColumn(DataModelService dataModelService, List<DataField> dimensionFields, Map<String, DataFieldDeployInfo> dataField2DeployInfo, Map<String, Object> qjDimJustValueMap) {
        ArrayList<BSBizKeyColumn> situationColumns = new ArrayList<BSBizKeyColumn>();
        for (DataField dataField : dimensionFields) {
            ColumnModelDefine columnByDataField = this.getColumnByDataField(dataModelService, dataField, dataField2DeployInfo);
            if ("MDCODE".equals(columnByDataField.getName()) || "DATATIME".equals(columnByDataField.getName())) continue;
            BSBizKeyColumnImpl dwColumn = new BSBizKeyColumnImpl(columnByDataField);
            dwColumn.setIsCorporate(qjDimJustValueMap.containsKey(columnByDataField.getName()));
            dwColumn.setSQLGroupFunc(SummaryFunction.MIN);
            if (dwColumn.isCorporate()) {
                dwColumn.setDefaultValue(qjDimJustValueMap.get(columnByDataField.getName()));
            }
            situationColumns.add(dwColumn);
        }
        return situationColumns;
    }

    private List<BSBizKeyColumn> getBizKeyColumns(DataModelService dataModelService, List<DataField> dataFields, Map<String, DataFieldDeployInfo> dataField2DeployInfo) {
        List<DataField> bizKeyFields = dataFields.stream().filter(e -> e.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM).collect(Collectors.toList());
        List<ColumnModelDefine> columnModels = this.getColumnsByDataFields(dataModelService, bizKeyFields, dataField2DeployInfo);
        return columnModels.stream().map(col -> {
            BSBizKeyColumnImpl column = new BSBizKeyColumnImpl((ColumnModelDefine)col);
            column.setSQLGroupFunc(SummaryFunction.MIN);
            return column;
        }).collect(Collectors.toList());
    }

    private List<BSBizKeyColumn> getBuildColumns(DataModelService dataModelService, List<DataField> dataFields, Map<String, DataFieldDeployInfo> dataField2DeployInfo) {
        List<DataField> buildFields = dataFields.stream().filter(e -> e.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD).collect(Collectors.toList());
        List<ColumnModelDefine> columnModels = this.getColumnsByDataFields(dataModelService, buildFields, dataField2DeployInfo);
        return columnModels.stream().map(col -> {
            BSBizKeyColumnImpl column = new BSBizKeyColumnImpl((ColumnModelDefine)col);
            column.setSQLGroupFunc(SummaryFunction.MIN);
            return column;
        }).collect(Collectors.toList());
    }

    private List<BSTableColumn> getZBColumns(DataModelService dataModelService, List<DataField> dataFields, Map<String, DataFieldDeployInfo> dataField2DeployInfo) {
        List<DataField> zbFields = dataFields.stream().filter(e -> e.getDataFieldKind() == DataFieldKind.FIELD_ZB || e.getDataFieldKind() == DataFieldKind.FIELD).collect(Collectors.toList());
        List<Object> zbColumns = this.getColumnsByDataFields(dataModelService, zbFields, dataField2DeployInfo);
        zbColumns = zbColumns.stream().filter(this::isSummaryColumn).collect(Collectors.toList());
        return zbColumns.stream().map(col -> {
            BSTableColumnImpl dwColumn = new BSTableColumnImpl((ColumnModelDefine)col);
            dwColumn.setSQLGroupFunc(SummaryFunction.valueOfType(col.getAggrType()));
            return dwColumn;
        }).collect(Collectors.toList());
    }

    private boolean isSummaryColumn(ColumnModelDefine column) {
        ColumnModelType fieldType = column.getColumnType();
        AggrType gatherType = column.getAggrType();
        return AggrType.NONE != gatherType && (fieldType == ColumnModelType.DOUBLE || fieldType == ColumnModelType.BIGDECIMAL || fieldType == ColumnModelType.INTEGER) || fieldType == ColumnModelType.STRING && (gatherType == AggrType.COUNT || gatherType == AggrType.MAX || gatherType == AggrType.MIN) || fieldType == ColumnModelType.BOOLEAN && (gatherType == AggrType.MAX || gatherType == AggrType.MIN) || fieldType == ColumnModelType.DATETIME && (gatherType == AggrType.MAX || gatherType == AggrType.MIN);
    }

    protected List<ColumnModelDefine> getColumnsByDataFields(DataModelService dataModelService, List<DataField> dataFields, Map<String, DataFieldDeployInfo> dataField2DeployInfo) {
        return this.subDatabaseTableNamesProvider.getSubDatabaseTableColumns(this.dataScheme.getKey(), dataFields.stream().map(Basic::getKey).collect(Collectors.toList()));
    }

    protected ColumnModelDefine getColumnByDataField(DataModelService dataModelService, DataField dataField, Map<String, DataFieldDeployInfo> dataField2DeployInfo) {
        Map subDatabaseFieldKey2Columns = this.subDatabaseTableNamesProvider.getSubDatabaseFieldKey2Columns(this.dataScheme.getKey(), Collections.singletonList(dataField.getKey()));
        List columnModelDefines = (List)subDatabaseFieldKey2Columns.get(dataField.getKey());
        return columnModelDefines.stream().filter(colMode -> colMode.getTableID().equals(this.tableModel.getID())).findAny().orElse(null);
    }
}

