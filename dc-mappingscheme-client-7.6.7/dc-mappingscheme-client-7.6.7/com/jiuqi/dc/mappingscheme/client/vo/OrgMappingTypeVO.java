/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.vo;

public class OrgMappingTypeVO {
    private String code;
    private String name;
    private String advancedSql;

    public OrgMappingTypeVO() {
    }

    public OrgMappingTypeVO(String code, String name, String advancedSql) {
        this.code = code;
        this.name = name;
        this.advancedSql = advancedSql;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdvancedSql() {
        return this.advancedSql;
    }

    public void setAdvancedSql(String advancedSql) {
        this.advancedSql = advancedSql;
    }
}

