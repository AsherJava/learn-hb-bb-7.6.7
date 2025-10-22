/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.dto;

import java.util.List;

public class DimValueParamDTO {
    private String dimName;
    private List<String> dimDatas;
    private int type;

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public List<String> getDimDatas() {
        return this.dimDatas;
    }

    public void setDimDatas(List<String> dimDatas) {
        this.dimDatas = dimDatas;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

