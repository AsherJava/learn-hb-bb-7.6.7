/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dataresource.web.param;

import io.swagger.annotations.ApiModelProperty;

public class DataResourceQuery {
    @ApiModelProperty(value="\u5173\u952e\u5b57", allowEmptyValue=true)
    private String keyword;
    @ApiModelProperty(value="\u8d44\u6e90\u6811key")
    private String defineKey;
    private String period;

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDefineKey() {
        return this.defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String toString() {
        return "DataResourceQuery{keyword='" + this.keyword + '\'' + ", defineKey='" + this.defineKey + '\'' + '}';
    }
}

