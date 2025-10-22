/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelImpl
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableType
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeDeployErrorEnum;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataFieldDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AbstractTableModelDeployObjBuilder;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelImpl;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AccountTableModelDeployObjBuilderImpl
extends AbstractTableModelDeployObjBuilder {
    @Override
    public DataTableType[] getDoForTableTypes() {
        return new DataTableType[]{DataTableType.ACCOUNT};
    }

    @Override
    protected List<TableModelDeployObj> buildAllAddTableModels(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo) throws ModelValidateException, JQException {
        ArrayList<TableModelDeployObj> tableInfos = new ArrayList<TableModelDeployObj>();
        this.addColumnsToTable(buildContext, dataTableDeployInfo, tableInfos, null, null);
        return tableInfos;
    }

    private TableModelDeployObj createTableModel(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo, boolean rpt) throws JQException, ModelValidateException {
        String newTableCode = rpt ? dataTableDeployInfo.getDataTable().getCode() + "_RPT" : dataTableDeployInfo.getDataTable().getCode();
        TableModelDeployObj tableModelDeployInfo = new TableModelDeployObj().updateDesignTableModel(buildContext, dataTableDeployInfo.getDataTable(), newTableCode);
        List<DataField> essentialDataFields = dataTableDeployInfo.getRequireDataFields();
        for (DataField essentialDataField : essentialDataFields) {
            if (rpt) {
                if ("FLOATORDER".equals(essentialDataField.getCode()) || essentialDataField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) continue;
                tableModelDeployInfo.addColumnModel(buildContext, essentialDataField);
                continue;
            }
            if ("DATATIME".equals(essentialDataField.getCode())) continue;
            tableModelDeployInfo.addColumnModel(buildContext, essentialDataField);
        }
        if (!rpt) {
            tableModelDeployInfo.addColumnModelDefine(this.createColumn(ACCOUNT_COLUMN.VALIDDATATIME));
            tableModelDeployInfo.addColumnModelDefine(this.createColumn(ACCOUNT_COLUMN.MODIFYTIME));
        }
        return tableModelDeployInfo;
    }

    private DesignColumnModelDefine createColumn(ACCOUNT_COLUMN column) {
        DesignColumnModelDefine columnModel = this.designDataModelService.createColumnModelDefine();
        columnModel.setTitle(column.getTitle());
        columnModel.setDesc(column.getTitle());
        columnModel.setCode(column.getCode());
        columnModel.setName(column.getCode());
        columnModel.setColumnType(column.getType());
        columnModel.setPrecision(column.getPrecision());
        columnModel.setDecimal(column.getDecimal());
        columnModel.setOrder(column.getOrder());
        return columnModel;
    }

    @Override
    protected void buildAddColumns(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo, List<TableModelDeployObj> tableInfos) throws ModelValidateException, JQException {
        TableModelDeployObj tableModel = tableInfos.get(0);
        TableModelDeployObj tableModelRpt = tableInfos.get(1);
        if (tableModel.getTableModelCode().length() > tableModelRpt.getTableModelCode().length()) {
            tableModel = tableInfos.get(1);
            tableModelRpt = tableInfos.get(0);
        }
        this.addColumnsToTable(buildContext, dataTableDeployInfo, tableInfos, tableModel, tableModelRpt);
    }

    @Override
    protected void buildDeleteAndUpdateColumns(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo, List<TableModelDeployObj> tableInfos) throws ModelValidateException, JQException {
        List<DataField> updateDataFields;
        Map<String, TableModelDeployObj> tableInfoMap = tableInfos.stream().collect(Collectors.toMap(TableModelDeployObj::getTableModelKey, v -> v));
        List<String> deleteDataFieldKeys = dataTableDeployInfo.getDeleteDataFieldKeys();
        if (null != deleteDataFieldKeys && !deleteDataFieldKeys.isEmpty()) {
            for (String designDataFieldKey : deleteDataFieldKeys) {
                List<DataFieldDeployInfoDO> deployInfos = dataTableDeployInfo.getDeployInfoByFieldKey(designDataFieldKey);
                for (DataFieldDeployInfoDO deployInfo : deployInfos) {
                    TableModelDeployObj tableModelDeployInfo = tableInfoMap.get(deployInfo.getTableModelKey());
                    if (null == tableModelDeployInfo) {
                        throw new JQException((ErrorEnum)DataSchemeDeployErrorEnum.DATATABLE_DEPLOY_PARAERROR);
                    }
                    tableModelDeployInfo.deleteColumnModel(deployInfo);
                }
            }
        }
        if (null != (updateDataFields = dataTableDeployInfo.getUpdateDataFields()) && !updateDataFields.isEmpty()) {
            for (DataField dataField : updateDataFields) {
                DataFieldDeployObj dataFieldDeployObj = dataTableDeployInfo.getDataField(dataField.getKey());
                for (DataFieldDeployInfoDO deployInfo : dataFieldDeployObj.getDeployInfos()) {
                    TableModelDeployObj tableModelDeployInfo = tableInfoMap.get(deployInfo.getTableModelKey());
                    if (null == tableModelDeployInfo) {
                        throw new JQException((ErrorEnum)DataSchemeDeployErrorEnum.DATATABLE_DEPLOY_PARAERROR);
                    }
                    tableModelDeployInfo.updateColumnModel(buildContext, dataField, deployInfo, dataFieldDeployObj.isCodeChanged());
                }
            }
        }
    }

    private void addColumnsToTable(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo, List<TableModelDeployObj> tableInfos, TableModelDeployObj tableModel, TableModelDeployObj tableModelRpt) throws JQException, ModelValidateException {
        if (null == tableModel) {
            tableModel = this.createTableModel(buildContext, dataTableDeployInfo, false);
            tableInfos.add(tableModel);
        }
        if (null == tableModelRpt) {
            tableModelRpt = this.createTableModel(buildContext, dataTableDeployInfo, true);
            tableInfos.add(tableModelRpt);
        }
        List<DataField> addDataFields = dataTableDeployInfo.getAddDataFields();
        for (DataField dataField : addDataFields) {
            if (DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind()) {
                if ("DATATIME".equals(dataField.getCode())) {
                    tableModelRpt.addColumnModel(buildContext, dataField);
                    continue;
                }
                tableModel.addColumnModel(buildContext, dataField);
                tableModelRpt.addColumnModel(buildContext, dataField);
                continue;
            }
            if (!dataField.isChangeWithPeriod()) {
                if ("DATATIME".equals(dataField.getCode())) continue;
                tableModel.addColumnModel(buildContext, dataField);
                continue;
            }
            if ("FLOATORDER".equals(dataField.getCode()) || dataField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) continue;
            tableModelRpt.addColumnModel(buildContext, dataField);
        }
    }

    @Override
    protected void buildExtendTable(DeployContext buildContext, DataTableDeployObj dataTableInfo, List<TableModelDeployObj> tableModelInfos) throws ModelValidateException {
        TableModelDeployObj tableModel = AccountTableModelDeployObjBuilderImpl.getTableModelDeployObj(tableModelInfos);
        if (null == tableModel) {
            return;
        }
        List oldColumns = null;
        TableModelDeployObj tableModelDeployInfo = null;
        if (DeployTableType.ADD == tableModel.getState()) {
            tableModelDeployInfo = this.createTableModelHisDeployInfo(tableModel);
            oldColumns = Collections.emptyList();
        } else {
            DesignTableModel oldTableModel = this.designDataModelService.getTableModel(tableModel.getTableModelKey());
            String oldTableCode = AccountTableModelDeployObjBuilderImpl.getHisTableName(oldTableModel.getCode());
            DesignTableModelDefine tableModelDefineHis = this.designDataModelService.getTableModelDefineByCode(oldTableCode);
            if (null == tableModelDefineHis) {
                oldColumns = Collections.emptyList();
                tableModelDeployInfo = this.createTableModelHisDeployInfo(tableModel);
            } else {
                oldColumns = oldTableModel.getColumns();
                DesignTableModelImpl tableModelHis = new DesignTableModelImpl(tableModelDefineHis, this.designDataModelService.getColumnModelDefinesByTable(tableModelDefineHis.getID()), this.designDataModelService.getIndexsByTable(tableModelDefineHis.getID()));
                tableModelDeployInfo = new TableModelDeployObj((DesignTableModel)tableModelHis, tableModel.getState());
            }
        }
        tableModelInfos.add(tableModelDeployInfo);
        this.updateTableModel(AccountTableModelDeployObjBuilderImpl.getHisTableName(tableModel.getTableModelCode()), tableModel, tableModelDeployInfo, oldColumns);
    }

    @NotNull
    private TableModelDeployObj createTableModelHisDeployInfo(TableModelDeployObj tableModel) throws ModelValidateException {
        TableModelDeployObj tableModelDeployInfo = new TableModelDeployObj(this.designDataModelService.createTableModel(), tableModel.getState());
        tableModelDeployInfo.addColumnModelDefine(this.createColumn(ACCOUNT_COLUMN.INVALIDDATATIME));
        tableModelDeployInfo.addColumnModelDefine(this.createColumn(ACCOUNT_COLUMN.VALIDTIME));
        return tableModelDeployInfo;
    }

    protected static TableModelDeployObj getTableModelDeployObj(List<TableModelDeployObj> tableModelInfos) {
        TableModelDeployObj tableModel = tableModelInfos.get(0).getTableModelCode().length() > tableModelInfos.get(1).getTableModelCode().length() ? tableModelInfos.get(1) : tableModelInfos.get(0);
        return tableModel;
    }

    public static String getHisTableName(String tableName) {
        return tableName + "_HIS";
    }

    public static String getRptTableName(String tableName) {
        return tableName + "_RPT";
    }

    private void updateTableModel(String tableCode, TableModelDeployObj sourceInfo, TableModelDeployObj targetInfo, List<DesignColumnModelDefine> oldColumns) throws ModelValidateException {
        DesignColumnModelDefine targetColumn;
        DesignTableModel source = sourceInfo.getDesignTableModel();
        DesignTableModel target = targetInfo.getDesignTableModel();
        String targetTableId = target.getID();
        Map<String, String> oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getID, IModelDefineItem::getCode));
        Map<String, DesignColumnModelDefine> sourceColumnMap = source.getColumns().stream().collect(Collectors.toMap(IModelDefineItem::getID, c -> c));
        Map<String, DesignColumnModelDefine> targetColumnMap = target.getColumns().stream().collect(Collectors.toMap(IModelDefineItem::getCode, c -> c));
        for (Map.Entry<String, DesignColumnModelDefine> entry : sourceColumnMap.entrySet()) {
            boolean add;
            DesignColumnModelDefine sourceColumn = entry.getValue();
            if (ACCOUNT_COLUMN.MODIFYTIME.getCode().equals(sourceColumn.getCode())) continue;
            targetColumn = targetColumnMap.get(oldColumnMap.get(entry.getKey()));
            boolean bl = add = null == targetColumn;
            if (add) {
                targetColumn = this.designDataModelService.createColumnModelDefine();
            }
            String targetColumnId = targetColumn.getID();
            BeanUtils.copyProperties(sourceColumn, targetColumn);
            targetColumn.setID(targetColumnId);
            targetColumn.setTableID(targetTableId);
            if (add) {
                targetInfo.addColumnModelDefine(targetColumn);
                continue;
            }
            targetInfo.updateColumnModelDefine(targetColumn);
        }
        List sourceDeleteColumns = source.getDeleteColumns();
        if (!CollectionUtils.isEmpty(sourceDeleteColumns)) {
            for (DesignColumnModelDefine sourceDeleteColumn : sourceDeleteColumns) {
                targetColumn = targetColumnMap.get(sourceDeleteColumn.getCode());
                if (null == targetColumn) continue;
                target.removeColumn(targetColumn);
            }
        }
        BeanUtils.copyProperties(source.getTableModelDefine(), target.getTableModelDefine());
        target.setID(targetTableId);
        target.setCode(tableCode);
        target.setName(tableCode);
        target.setTitle("\u53f0\u8d26\u4fe1\u606f\u53d8\u66f4\u8868");
        target.setDesc("\u53f0\u8d26\u4fe1\u606f\u53d8\u66f4\u8868");
        target.setKeys(null);
        target.setBizKeys(null);
    }

    @Override
    protected boolean checkBeforeBuild(DeployContext buildContext, DataTableDeployObj dataTableInfo) {
        if (DeployType.DELETE == dataTableInfo.getState()) {
            return true;
        }
        DataTable dataTable = dataTableInfo.getDataTable();
        boolean check = false;
        List<DataField> requireDataFields = dataTableInfo.getRequireDataFields();
        for (DataField designDataField : requireDataFields) {
            if (DataFieldKind.TABLE_FIELD_DIM != designDataField.getDataFieldKind()) continue;
            check = true;
            break;
        }
        if (!check) {
            String errorMsg = "\u53f0\u8d26\u8868\u5fc5\u987b\u5305\u542b\u8868\u5185\u7ef4\u5ea6";
            buildContext.getDeployResult().addCheckError(dataTable, errorMsg);
            LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a{}[{}]\u6267\u884c\u53c2\u6570\u68c0\u67e5\u5931\u8d25\uff1a{}\u3002", dataTable.getCode(), dataTable.getTitle(), errorMsg);
        }
        return check;
    }

    @Override
    protected void updateCode(DeployContext buildContext, List<TableModelDeployObj> tableModelDeployObjs, DataTableDeployObj dataTableInfo) {
        if (!dataTableInfo.isCodeChanged()) {
            return;
        }
        String oldCode = tableModelDeployObjs.get(0).getTableModelCode();
        for (TableModelDeployObj tableModelDeployObj : tableModelDeployObjs) {
            if (tableModelDeployObj.getTableModelCode().length() >= oldCode.length()) continue;
            oldCode = tableModelDeployObj.getTableModelCode();
        }
        String tableName = dataTableInfo.getDataTable().getCode();
        for (TableModelDeployObj tableModelDeployObj : tableModelDeployObjs) {
            if (tableModelDeployObj.getTableModelCode().equals(oldCode)) {
                tableModelDeployObj.updateTableCode(tableName);
                continue;
            }
            if (tableModelDeployObj.getTableModelCode().endsWith("_RPT")) {
                tableModelDeployObj.updateTableCode(AccountTableModelDeployObjBuilderImpl.getRptTableName(tableName));
                continue;
            }
            if (!tableModelDeployObj.getTableModelCode().endsWith("_HIS")) continue;
            tableModelDeployObj.updateTableCode(AccountTableModelDeployObjBuilderImpl.getHisTableName(tableName));
        }
    }

    public static enum ACCOUNT_COLUMN {
        VALIDDATATIME("\u751f\u6548\u65f6\u671f", "VALIDDATATIME", ColumnModelType.STRING, 10, 0, 3.0E12),
        INVALIDDATATIME("\u5931\u6548\u65f6\u671f", "INVALIDDATATIME", ColumnModelType.STRING, 10, 0, 3.000000000001E12),
        VALIDTIME("\u751f\u6548\u65f6\u95f4", "VALIDTIME", ColumnModelType.DATETIME, 0, 0, 3.000000000002E12),
        MODIFYTIME("\u4fee\u6539\u65f6\u95f4", "MODIFYTIME", ColumnModelType.DATETIME, 0, 0, 3.000000000003E12),
        DIGEST("\u4fe1\u606f\u6458\u8981", "DIGEST", ColumnModelType.STRING, 32, 0, 3.000000000004E12);

        private final String title;
        private final String code;
        private final ColumnModelType type;
        private final int precision;
        private final int decimal;
        private final double order;

        private ACCOUNT_COLUMN(String title, String code, ColumnModelType type, int precision, int decimal, double order) {
            this.title = title;
            this.code = code;
            this.type = type;
            this.precision = precision;
            this.decimal = decimal;
            this.order = order;
        }

        public String getTitle() {
            return this.title;
        }

        public String getCode() {
            return this.code;
        }

        public ColumnModelType getType() {
            return this.type;
        }

        public int getPrecision() {
            return this.precision;
        }

        public int getDecimal() {
            return this.decimal;
        }

        public double getOrder() {
            return this.order;
        }
    }
}

