/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import java.util.Collections;
import java.util.List;

public class DataFieldDeployObj {
    private DeployType state;
    private boolean codeChanged;
    private DataField dataField;
    List<DataFieldDeployInfoDO> deployInfos;

    public DataFieldDeployObj(DataField dataField, List<DataFieldDeployInfoDO> deployInfos, DeployType state) {
        this.state = state;
        this.dataField = dataField;
        this.deployInfos = deployInfos;
    }

    public DeployType getState() {
        return this.state;
    }

    public String getDataFieldKey() {
        return this.dataField.getKey();
    }

    public DataField getDataField() {
        return this.dataField;
    }

    public List<DataFieldDeployInfoDO> getDeployInfos() {
        return null != this.deployInfos ? this.deployInfos : Collections.emptyList();
    }

    public boolean isCodeChanged() {
        return this.codeChanged;
    }

    public void setCodeChanged(boolean codeChanged) {
        this.codeChanged = codeChanged;
    }
}

