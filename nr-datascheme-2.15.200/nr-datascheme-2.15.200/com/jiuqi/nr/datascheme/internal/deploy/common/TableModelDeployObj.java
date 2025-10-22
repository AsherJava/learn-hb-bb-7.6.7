/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModel
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableType
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class TableModelDeployObj {
    private final DeployTableType state;
    DesignTableModel tableModel;
    private Map<String, DesignColumnModelDefine> allColumns;
    private final Map<DataField, DesignColumnModelDefine> addDataFields = new HashMap<DataField, DesignColumnModelDefine>();
    private final Map<DataField, DesignColumnModelDefine> updateDataFields = new HashMap<DataField, DesignColumnModelDefine>();
    private final Set<DataFieldDeployInfoDO> deleteDataFields = new HashSet<DataFieldDeployInfoDO>();

    public TableModelDeployObj() {
        this.state = DeployTableType.ADD;
    }

    public TableModelDeployObj(DesignTableModel tableModel, DeployTableType state) {
        this.tableModel = tableModel;
        this.state = state;
    }

    public TableModelDeployObj(DesignTableModel tableModel, List<DataFieldDeployInfoDO> deleteDeployInfos) {
        this.tableModel = tableModel;
        this.state = DeployTableType.DELETE;
        if (null != deleteDeployInfos) {
            this.deleteDataFields.addAll(deleteDeployInfos);
        }
    }

    public TableModelDeployObj(DeployContext context, DataTable dataTable, List<DataField> dataFields, List<DataFieldDeployInfoDO> deployInfos) throws JQException, ModelValidateException {
        HashMap<String, DataFieldDeployInfoDO> deployInfosMap = new HashMap<String, DataFieldDeployInfoDO>();
        for (DataFieldDeployInfoDO deployInfo : deployInfos) {
            deployInfosMap.put(deployInfo.getDataFieldKey(), deployInfo);
        }
        this.tableModel = DataSchemeDeployHelper.createTableModel(dataTable, dataTable.getCode());
        this.state = DeployTableType.ADD;
        DataFieldDeployInfoDO deployInfo = null;
        DesignColumnModelDefine columnModel = null;
        for (DataField dataField : dataFields) {
            deployInfo = (DataFieldDeployInfoDO)deployInfosMap.get(dataField.getKey());
            columnModel = DataSchemeDeployHelper.createColumnModel(context, this.getColumnNames(), dataField);
            columnModel.setID(deployInfo.getColumnModelKey());
            columnModel.setName(deployInfo.getFieldName());
            columnModel.setTableID(deployInfo.getTableModelKey());
            this.addColumnModelDefine(columnModel);
        }
        if (null != deployInfo) {
            this.tableModel.setID(deployInfo.getTableModelKey());
            this.tableModel.setCode(deployInfo.getTableName());
            this.tableModel.setName(deployInfo.getTableName());
        }
    }

    public TableModelDeployObj updateDesignTableModel(DeployContext buildContext, DataTable dataTable, String code) {
        if (null == this.tableModel) {
            this.tableModel = DataSchemeDeployHelper.createTableModel(dataTable, code);
        } else {
            this.tableModel.setTitle(dataTable.getTitle());
            this.tableModel.setDesc(dataTable.getDesc());
            String dataGroupKey = DataSchemeDeployHelper.getParentCatalogKey(dataTable.getDataSchemeKey(), dataTable.getDataGroupKey());
            this.tableModel.setCatalogID(dataGroupKey);
            this.tableModel.setUpdateTime(new Date());
        }
        if (DeployTableType.ADD == this.state) {
            this.tableModel.setSupportNrdb(true);
            this.tableModel.setStorageName(buildContext.getDataSchemeBizCode());
        }
        this.updateTableCode(code);
        return this;
    }

    public void updateTableCode(String tableCode) {
        if (!StringUtils.hasLength(tableCode)) {
            return;
        }
        this.tableModel.setCode(tableCode);
        this.tableModel.setName(tableCode.toUpperCase());
    }

    public void addColumnModelDefine(DesignColumnModelDefine columnModel) throws ModelValidateException {
        columnModel.setTableID(this.tableModel.getID());
        this.tableModel.addColumn(columnModel);
        this.getAllColumns().put(columnModel.getID(), columnModel);
    }

    public void updateColumnModelDefine(DesignColumnModelDefine columnModel) throws ModelValidateException {
        columnModel.setTableID(this.tableModel.getID());
        this.tableModel.modifyColumn(columnModel);
        this.getAllColumns().put(columnModel.getID(), columnModel);
    }

    private void removeColumnModelDefine(DesignColumnModelDefine columnModel) {
        this.tableModel.removeColumn(columnModel);
        this.getAllColumns().remove(columnModel.getID(), columnModel);
    }

    public void addColumnModel(DeployContext buildContext, DataField dataField) throws ModelValidateException, JQException {
        if (this.addDataFields.containsKey(dataField)) {
            return;
        }
        Set<String> columnNames = this.getColumnNames();
        DesignColumnModelDefine columnModel = DataSchemeDeployHelper.createColumnModel(buildContext, columnNames, dataField);
        columnModel.setTableID(this.getTableModelKey());
        this.addColumnModelDefine(columnModel);
        this.addDataFields.put(dataField, columnModel);
    }

    public void updateColumnModel(DeployContext buildContext, DataField dataField, DataFieldDeployInfoDO deployInfo, boolean codeChanged) throws ModelValidateException, JQException {
        DesignColumnModelDefine columnModel = this.getAllColumns().get(deployInfo.getColumnModelKey());
        if (null != columnModel) {
            DataSchemeDeployHelper.updateColumnModel(buildContext, columnModel, dataField, buildContext.isExistData(deployInfo.getTableName()), codeChanged ? () -> DataSchemeDeployHelper.getColumnName(this.getColumnNames(), dataField) : null);
            columnModel.setTableID(this.getTableModelKey());
            this.tableModel.modifyColumn(columnModel);
        } else {
            columnModel = DataSchemeDeployHelper.createColumnModel(buildContext, this.getColumnNames(), dataField);
            columnModel.setID(deployInfo.getColumnModelKey());
            columnModel.setName(deployInfo.getFieldName());
            columnModel.setTableID(this.getTableModelKey());
            this.addColumnModelDefine(columnModel);
        }
        this.updateDataFields.put(dataField, columnModel);
    }

    public void deleteColumnModel(DataFieldDeployInfoDO deployInfo) {
        DesignColumnModelDefine designColumnModelDefine = this.getAllColumns().get(deployInfo.getColumnModelKey());
        if (null != designColumnModelDefine) {
            this.removeColumnModelDefine(designColumnModelDefine);
        }
        this.deleteDataFields.add(deployInfo);
    }

    public DeployTableType getState() {
        return this.state;
    }

    public String getTableModelName() {
        return this.tableModel.getName();
    }

    public String getTableModelCode() {
        return this.tableModel.getCode();
    }

    public String getTableModelKey() {
        return this.tableModel.getID();
    }

    public boolean isFull() {
        return DataSchemeDeployHelper.splitTable(this.getAllColumns().values());
    }

    public TableModel getTableModel() {
        TableModel newTableModel = new TableModel(this.tableModel.getID(), this.tableModel.getCode());
        newTableModel.setTableModelDefine((TableModelDefine)this.tableModel);
        Collection<DesignColumnModelDefine> allColumnModels = this.getAllColumns().values();
        if (!allColumnModels.isEmpty()) {
            List columns = newTableModel.getColumns();
            columns.addAll(allColumnModels);
        }
        return newTableModel;
    }

    public DesignTableModel getDesignTableModel() {
        return this.tableModel;
    }

    private Set<String> getColumnNames() {
        return this.getAllColumns().values().stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
    }

    public Map<String, DesignColumnModelDefine> getAllColumns() {
        if (null == this.allColumns) {
            this.allColumns = new HashMap<String, DesignColumnModelDefine>();
            for (DesignColumnModelDefine column : this.tableModel.getColumns()) {
                this.allColumns.put(column.getID(), column);
            }
        }
        return this.allColumns;
    }

    public Collection<DataField> getAddDataFields() {
        return this.addDataFields.keySet();
    }

    public Collection<DataField> getUpdateDataFields() {
        return this.updateDataFields.keySet();
    }

    public Set<String> getDeleteDataFieldKeys() {
        return this.deleteDataFields.stream().map(DataFieldDeployInfo::getDataFieldKey).collect(Collectors.toSet());
    }

    public Set<String> getDirtyDataFieldKeys() {
        HashSet<String> keys = new HashSet<String>();
        keys.addAll(this.getAddDataFields().stream().map(Basic::getKey).collect(Collectors.toSet()));
        keys.addAll(this.getUpdateDataFields().stream().map(Basic::getKey).collect(Collectors.toSet()));
        keys.addAll(this.getDeleteDataFieldKeys());
        return keys;
    }

    public Collection<DataFieldDeployInfoDO> getUpdateDeployInfos() {
        ArrayList<DataFieldDeployInfoDO> deployInfos = new ArrayList<DataFieldDeployInfoDO>();
        for (Map.Entry<DataField, DesignColumnModelDefine> entry : this.updateDataFields.entrySet()) {
            DataFieldDeployInfoDO deployInfo = DataSchemeDeployHelper.createDeployInfo((DesignTableModelDefine)this.tableModel, entry.getValue(), entry.getKey());
            deployInfos.add(deployInfo);
        }
        return deployInfos;
    }

    public Collection<DataFieldDeployInfoDO> getAddDeployInfos() {
        ArrayList<DataFieldDeployInfoDO> deployInfos = new ArrayList<DataFieldDeployInfoDO>();
        for (Map.Entry<DataField, DesignColumnModelDefine> entry : this.addDataFields.entrySet()) {
            DataFieldDeployInfoDO deployInfo = DataSchemeDeployHelper.createDeployInfo((DesignTableModelDefine)this.tableModel, entry.getValue(), entry.getKey());
            deployInfos.add(deployInfo);
        }
        return deployInfos;
    }

    public Collection<DataFieldDeployInfoDO> getDeleteDeployInfos() {
        return this.deleteDataFields;
    }
}

