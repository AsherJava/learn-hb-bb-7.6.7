/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import java.io.Serializable;
import java.util.List;

public class FieldSearchQuery
implements Serializable {
    private String keyword;
    private String scheme;
    private List<String> schemes;
    private String table;
    private List<String> tables;
    private Integer kind = DataFieldKind.FIELD.getValue() | DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue();
    private Integer skip;
    private Integer limit;
    private String order;

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getKind() {
        return this.kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getSkip() {
        return this.skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getSchemes() {
        return this.schemes;
    }

    public void setSchemes(List<String> schemes) {
        this.schemes = schemes;
    }

    public List<String> getTables() {
        return this.tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

