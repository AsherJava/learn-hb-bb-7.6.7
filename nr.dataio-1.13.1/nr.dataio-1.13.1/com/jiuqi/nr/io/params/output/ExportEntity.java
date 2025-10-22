/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.io.params.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.io.params.output.EntityDatas;
import java.util.List;

public class ExportEntity {
    @JsonProperty
    private List<EntityDatas> datas;
    private String tableName;
    private String title;
    private boolean defaultImport = false;

    public boolean isDefaultImport() {
        return this.defaultImport;
    }

    public void setDefaultImport(boolean defaultImport) {
        this.defaultImport = defaultImport;
    }

    public List<EntityDatas> getDatas() {
        return this.datas;
    }

    public void setDatas(List<EntityDatas> datas) {
        this.datas = datas;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

