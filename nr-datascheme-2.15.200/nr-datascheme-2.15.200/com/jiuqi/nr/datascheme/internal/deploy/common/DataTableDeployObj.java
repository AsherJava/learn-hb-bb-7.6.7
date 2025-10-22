/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.event.RefreshTable
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.event.RefreshTable;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataFieldDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataTableDeployObj {
    private DeployType state;
    private boolean codeChanged;
    private boolean bizkeysChanged;
    private DataTable dataTable;
    private Set<String> tableModelKeys = new HashSet<String>();
    private List<DataField> requireDataFields = new ArrayList<DataField>();
    private Map<String, DataFieldDeployObj> dataFields = new HashMap<String, DataFieldDeployObj>();

    public DataTableDeployObj(DesignDataTable designDataTable, DataTable dataTable) {
        if (null == designDataTable) {
            this.state = DeployType.DELETE;
            this.dataTable = dataTable;
        } else if (null == dataTable) {
            this.state = DeployType.ADD;
            this.dataTable = designDataTable;
        } else {
            this.state = DataSchemeDeployHelper.compareDataTable((DataTable)designDataTable, dataTable);
            this.dataTable = designDataTable;
            this.codeChanged = !designDataTable.getCode().equals(dataTable.getCode());
            this.bizkeysChanged = !Arrays.equals(designDataTable.getBizKeys(), dataTable.getBizKeys());
        }
    }

    public DeployType getState() {
        return this.state;
    }

    public String getDataTableKey() {
        return this.dataTable.getKey();
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public RefreshTable getRefreshTable() {
        return new RefreshTable(this.dataTable.getKey(), this.dataTable.getCode());
    }

    public Set<String> getTableModelKeys() {
        return this.tableModelKeys;
    }

    public List<DataField> getRequireDataFields() {
        return this.requireDataFields;
    }

    private void updateObj(DataField dataField, List<DataFieldDeployInfoDO> deployInfos, DeployType fieldState) {
        if (DataSchemeDeployHelper.isRequiredField(dataField)) {
            if (DeployType.DELETE != fieldState) {
                this.requireDataFields.add(dataField);
                this.requireDataFields.sort(DataSchemeDeployHelper.DATAFIELD_COMPARATOR);
            }
            if (null != deployInfos) {
                for (DataFieldDeployInfoDO info : deployInfos) {
                    this.tableModelKeys.add(info.getTableModelKey());
                }
            }
        }
        if (DeployType.NONE == this.state) {
            this.state = DeployType.NONE != fieldState ? DeployType.UPDATE_NODPLOY : this.state;
        } else if (DeployType.ADD == this.state) {
            this.state = DeployType.ADD != fieldState ? DeployType.UPDATE_NODPLOY : this.state;
        }
    }

    public DataFieldDeployObj addDataField(DesignDataField designDataField, DataField dataField, List<DataFieldDeployInfoDO> deployInfos) {
        DataFieldDeployObj dataFieldDeployObj;
        if (null == designDataField) {
            dataFieldDeployObj = new DataFieldDeployObj(dataField, deployInfos, DeployType.DELETE);
            this.updateObj(dataField, deployInfos, DeployType.DELETE);
        } else if (null == dataField) {
            dataFieldDeployObj = new DataFieldDeployObj((DataField)designDataField, deployInfos, DeployType.ADD);
            this.updateObj((DataField)designDataField, deployInfos, DeployType.ADD);
        } else {
            DeployType fieldState = DataSchemeDeployHelper.compareDataField(designDataField, dataField);
            dataFieldDeployObj = new DataFieldDeployObj((DataField)designDataField, deployInfos, fieldState);
            dataFieldDeployObj.setCodeChanged(!designDataField.getCode().equals(dataField.getCode()));
            this.updateObj((DataField)designDataField, deployInfos, fieldState);
        }
        this.dataFields.put(dataFieldDeployObj.getDataFieldKey(), dataFieldDeployObj);
        return dataFieldDeployObj;
    }

    public List<DataField> getAddDataFields() {
        return this.dataFields.values().stream().filter(f -> DeployType.ADD == f.getState()).map(DataFieldDeployObj::getDataField).sorted().collect(Collectors.toList());
    }

    public List<DataField> getUpdateDataFields() {
        return this.dataFields.values().stream().filter(f -> DeployType.UPDATE == f.getState()).map(DataFieldDeployObj::getDataField).collect(Collectors.toList());
    }

    public List<DataField> getUpdateNodployDataFields() {
        return this.dataFields.values().stream().filter(f -> DeployType.UPDATE_NODPLOY == f.getState()).map(DataFieldDeployObj::getDataField).collect(Collectors.toList());
    }

    public List<DataField> getDeleteDataFields() {
        return this.dataFields.values().stream().filter(f -> DeployType.DELETE == f.getState()).map(DataFieldDeployObj::getDataField).collect(Collectors.toList());
    }

    public List<String> getDeleteDataFieldKeys() {
        return this.dataFields.values().stream().filter(f -> DeployType.DELETE == f.getState()).map(DataFieldDeployObj::getDataFieldKey).collect(Collectors.toList());
    }

    public DataFieldDeployObj getDataField(String dataFieldKey) {
        return this.dataFields.get(dataFieldKey);
    }

    public List<DataFieldDeployInfoDO> getDeployInfoByTableModelKey(String tableModelKey) {
        ArrayList<DataFieldDeployInfoDO> deployInfos = new ArrayList<DataFieldDeployInfoDO>();
        for (DataFieldDeployObj dataField : this.dataFields.values()) {
            deployInfos.addAll(dataField.getDeployInfos());
        }
        return deployInfos.stream().filter(d -> tableModelKey.equals(d.getTableModelKey())).collect(Collectors.toList());
    }

    public List<DataFieldDeployInfoDO> getDeployInfoByFieldKey(String fieldKey) {
        return this.dataFields.get(fieldKey).getDeployInfos();
    }

    public boolean isCodeChanged() {
        return this.codeChanged;
    }

    public boolean isBizkeysChanged() {
        return this.bizkeysChanged;
    }
}

