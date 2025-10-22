/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.nr.unit.uselector.filter.listselect.FTableUnitRow;
import java.util.List;

public class FTableConditionRow {
    public static final String TYPE_OF_CONDITION = "condition";
    public static final String MATCH_TYPE_OF_EXACT = "T";
    public static final String MATCH_TYPE_OF_FUZZY = "F";
    public static final String MATCH_TYPE_OF_UN = "N";
    private String key;
    private String code;
    private String title;
    private String type;
    private String matchType;
    private int order;
    private List<FTableUnitRow> children;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code != null ? code.trim() : "";
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title != null ? title.trim() : "";
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatchType() {
        return this.matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<FTableUnitRow> getChildren() {
        return this.children;
    }

    public void setChildren(List<FTableUnitRow> children) {
        this.children = children;
    }
}

