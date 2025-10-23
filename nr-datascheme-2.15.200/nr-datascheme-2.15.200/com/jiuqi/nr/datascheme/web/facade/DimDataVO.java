/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;

public class DimDataVO
extends BaseDataVO {
    private String tableKey;
    private Integer precision;

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public String toString() {
        return "DimDataVO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", tableKey='" + this.tableKey + '\'' + ", precision=" + this.precision + '}';
    }
}

