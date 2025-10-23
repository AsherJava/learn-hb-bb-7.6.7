/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 */
package com.jiuqi.nr.datascheme.fix.core;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDataTableCheckResult;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import java.util.List;

public class DeployFixDataTable {
    private String dataTableKey;
    private DataTable rtdataTable;
    private DesignDataTable desDataTable;
    private List<DataFieldDO> dataFields;
    private List<DesignDataField> desDataFields;
    private List<DataFieldDeployInfoDO> deployInfos;
    private DeployFixDataTableCheckResult checkResult;
    private List<String> tableModelKey;

    public DeployFixDataTable() {
    }

    public DeployFixDataTable(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public DataTable getRtdataTable() {
        return this.rtdataTable;
    }

    public void setRtdataTable(DataTable rtdataTable) {
        this.rtdataTable = rtdataTable;
    }

    public List<DataFieldDO> getDataFields() {
        return this.dataFields;
    }

    public void setDataFields(List<DataFieldDO> dataFields) {
        this.dataFields = dataFields;
    }

    public DesignDataTable getDesDataTable() {
        return this.desDataTable;
    }

    public void setDesDataTable(DesignDataTable desDataTable) {
        this.desDataTable = desDataTable;
    }

    public List<DesignDataField> getDesDataFields() {
        return this.desDataFields;
    }

    public void setDesDataFields(List<DesignDataField> desDataFields) {
        this.desDataFields = desDataFields;
    }

    public List<DataFieldDeployInfoDO> getDeployInfos() {
        return this.deployInfos;
    }

    public void setDeployInfos(List<DataFieldDeployInfoDO> deployInfos) {
        this.deployInfos = deployInfos;
    }

    public DeployFixDataTableCheckResult getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(DeployFixDataTableCheckResult checkResult) {
        this.checkResult = checkResult;
    }

    public List<String> getTableModelKey() {
        return this.tableModelKey;
    }

    public void setTableModelKey(List<String> tableModelKey) {
        this.tableModelKey = tableModelKey;
    }
}

