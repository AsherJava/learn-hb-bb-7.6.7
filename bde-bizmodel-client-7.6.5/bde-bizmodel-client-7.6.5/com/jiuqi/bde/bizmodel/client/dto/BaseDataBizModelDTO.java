/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.dto;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import java.util.List;

public class BaseDataBizModelDTO
extends BizModelDTO {
    private String baseDataDefine;
    private List<String> fetchFields;
    private String baseDataTableName;
    private String fetchFieldNames;

    public String getBaseDataDefine() {
        return this.baseDataDefine;
    }

    public void setBaseDataDefine(String baseDataDefine) {
        this.baseDataDefine = baseDataDefine;
    }

    public List<String> getFetchFields() {
        return this.fetchFields;
    }

    public void setFetchFields(List<String> fetchFields) {
        this.fetchFields = fetchFields;
    }

    public String getBaseDataTableName() {
        return this.baseDataTableName;
    }

    public void setBaseDataTableName(String baseDataTableName) {
        this.baseDataTableName = baseDataTableName;
    }

    public String getFetchFieldNames() {
        return this.fetchFieldNames;
    }

    public void setFetchFieldNames(String fetchFieldNames) {
        this.fetchFieldNames = fetchFieldNames;
    }
}

