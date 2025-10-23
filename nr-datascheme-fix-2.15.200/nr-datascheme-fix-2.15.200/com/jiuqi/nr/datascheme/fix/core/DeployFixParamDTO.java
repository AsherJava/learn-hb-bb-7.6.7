/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.core;

import com.jiuqi.nr.datascheme.fix.common.DeployFixType;
import com.jiuqi.nr.datascheme.fix.core.DeployExCheckResultDTO;
import com.jiuqi.nr.datascheme.fix.web.facade.FixParamVO;

public class DeployFixParamDTO
extends DeployExCheckResultDTO {
    protected DeployFixType fixType;
    protected String fixDesc;

    public String getFixDesc() {
        return this.fixDesc;
    }

    public void setFixDesc(String fixDesc) {
        this.fixDesc = fixDesc;
    }

    public DeployFixType getFixType() {
        return this.fixType;
    }

    public void setFixType(DeployFixType fixType) {
        this.fixType = fixType;
    }

    public DeployFixParamDTO(FixParamVO fixParam, DeployFixType fixType) {
        this.dataSchemeKey = fixParam.getDataSchemeKey();
        this.dataTableKey = fixParam.getDataTableKey();
        this.dataTableCode = fixParam.getDataTableCode();
        this.dataTableTitle = fixParam.getDataTableTitle();
        this.exType = fixParam.getExType();
        this.exDesc = fixParam.getExDesc();
        this.fixType = fixType;
        this.tmKeyAndLtName = fixParam.getTmKeyAndLtName();
        this.fixDesc = fixType.getTitle();
    }

    public DeployFixParamDTO(DeployExCheckResultDTO exResult, DeployFixType fixType) {
        this.dataSchemeKey = exResult.getDataSchemeKey();
        this.dataTableKey = exResult.getDataTableKey();
        this.dataTableCode = exResult.getDataTableCode();
        this.dataTableTitle = exResult.getDataTableTitle();
        this.exType = exResult.getExType();
        this.exDesc = exResult.getExDesc();
        this.fixType = fixType;
        this.tmKeyAndLtName = exResult.getTmKeyAndLtName();
        this.fixDesc = fixType.getTitle();
    }

    public DeployFixParamDTO() {
    }
}

