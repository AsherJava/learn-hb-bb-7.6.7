/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.common;

import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;

public class InitSchemeDataVO {
    private DataSchemeDTO dataScheme;
    private String initSchemeData;
    private String saveType;

    public DataSchemeDTO getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DataSchemeDTO dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getInitSchemeData() {
        return this.initSchemeData;
    }

    public void setInitSchemeData(String initSchemeData) {
        this.initSchemeData = initSchemeData;
    }

    public String getSaveType() {
        return this.saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }
}

