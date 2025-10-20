/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import java.io.Serializable;

public class ParseSqlParamDTO
implements Serializable {
    private static final long serialVersionUID = 6416470567425469685L;
    private String dataSourceCode;
    private String advancedSql;

    public String getDataSourceCode() {
        return this.dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getAdvancedSql() {
        return this.advancedSql;
    }

    public void setAdvancedSql(String advancedSql) {
        this.advancedSql = advancedSql;
    }
}

