/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import java.util.List;

public class DimInfo {
    private String dimId;
    private String dimName;
    private List<String> dimDatas;

    public DimInfo() {
    }

    public DimInfo(String dimId, String dimName, List<String> dimDatas) {
        this.dimId = dimId;
        this.dimName = dimName;
        this.dimDatas = dimDatas;
    }

    public String getDimId() {
        return this.dimId;
    }

    public void setDimId(String dimId) {
        this.dimId = dimId;
    }

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
}

