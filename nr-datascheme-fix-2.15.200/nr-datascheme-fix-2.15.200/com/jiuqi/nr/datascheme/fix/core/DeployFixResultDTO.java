/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.core;

import com.jiuqi.nr.datascheme.fix.common.DeployFixResultType;
import com.jiuqi.nr.datascheme.fix.common.DeployFixType;
import com.jiuqi.nr.datascheme.fix.core.DeployExCheckResultDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixParamDTO;
import java.util.List;

public class DeployFixResultDTO
extends DeployExCheckResultDTO {
    protected DeployFixResultType fixResultType;
    protected String fixResultDesc;
    protected List<String> newTableName;
    protected DeployFixType fixType;
    protected boolean isTransfer;

    public DeployFixResultType getFixResultType() {
        return this.fixResultType;
    }

    public void setFixResultType(DeployFixResultType fixResultType) {
        this.fixResultType = fixResultType;
    }

    public String getFixResultDesc() {
        return this.fixResultDesc;
    }

    public void setFixResultDesc(String fixResultDesc) {
        this.fixResultDesc = fixResultDesc;
    }

    public List<String> getNewTableName() {
        return this.newTableName;
    }

    public void setNewTableName(List<String> newTableName) {
        this.newTableName = newTableName;
    }

    public DeployFixType getFixType() {
        return this.fixType;
    }

    public void setFixType(DeployFixType fixType) {
        this.fixType = fixType;
    }

    public boolean isTransfer() {
        return this.isTransfer;
    }

    public void setTransfer(boolean transfer) {
        this.isTransfer = transfer;
    }

    public DeployFixResultDTO() {
    }

    public DeployFixResultDTO(DeployFixParamDTO fixParam, DeployFixResultType fixResultType) {
        this.dataSchemeKey = fixParam.getDataSchemeKey();
        this.dataTableKey = fixParam.getDataTableKey();
        this.dataTableCode = fixParam.getDataTableCode();
        this.dataTableTitle = fixParam.getDataTableTitle();
        this.exDesc = fixParam.getExDesc();
        this.fixResultType = fixResultType;
        this.fixResultDesc = fixResultType.getTitle();
        this.fixType = fixParam.getFixType();
    }
}

