/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.dto.fetch.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.QueryConfigInfo;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FetchRequestFloatSettingDTO {
    private String queryType;
    private QueryConfigInfo queryConfigInfo;

    public FetchRequestFloatSettingDTO() {
    }

    public FetchRequestFloatSettingDTO(String queryType, QueryConfigInfo queryConfigInfo) {
        this.queryType = queryType;
        this.queryConfigInfo = queryConfigInfo;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public QueryConfigInfo getQueryConfigInfo() {
        return this.queryConfigInfo;
    }

    public void setQueryConfigInfo(QueryConfigInfo queryConfigInfo) {
        this.queryConfigInfo = queryConfigInfo;
    }

    public String toString() {
        return "FetchRequestFloatSettingDTO [queryType=" + this.queryType + ", queryConfigInfo=" + this.queryConfigInfo + "]";
    }
}

