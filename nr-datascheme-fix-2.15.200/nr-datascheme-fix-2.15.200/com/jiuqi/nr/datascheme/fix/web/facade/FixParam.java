/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.web.facade;

import com.jiuqi.nr.datascheme.fix.common.DeployFixType;

public class FixParam {
    private DeployFixType fixType;
    private String fixTypeDesc;
    private int fixTypeValue;

    public FixParam() {
        this.fixType = DeployFixType.DO_NOT_FIX;
        this.fixTypeDesc = DeployFixType.DO_NOT_FIX.getTitle();
        this.fixTypeValue = DeployFixType.DO_NOT_FIX.getValue();
    }

    public FixParam(DeployFixType fixParamType) {
        this.fixType = fixParamType;
        this.fixTypeDesc = fixParamType.getTitle();
        this.fixTypeValue = fixParamType.getValue();
    }

    public DeployFixType getFixType() {
        return this.fixType;
    }

    public void setFixType(DeployFixType fixType) {
        this.fixType = fixType;
    }

    public String getFixTypeDesc() {
        return this.fixTypeDesc;
    }

    public void setFixTypeDesc(String fixTypeDesc) {
        this.fixTypeDesc = fixTypeDesc;
    }

    public int getFixTypeValue() {
        return this.fixTypeValue;
    }

    public void setFixTypeValue(int fixTypeValue) {
        this.fixTypeValue = fixTypeValue;
    }
}

