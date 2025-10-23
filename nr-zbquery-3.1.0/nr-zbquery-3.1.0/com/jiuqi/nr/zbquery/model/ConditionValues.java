/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.zbquery.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.zbquery.model.FilterField;
import com.jiuqi.nr.zbquery.model.TableHeaderOrderField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionValues {
    @JsonProperty(value="conditions")
    private Map<String, String[]> conditions = new HashMap<String, String[]>();
    @JsonProperty(value="bindings")
    private Map<String, String> bindings = new HashMap<String, String>();
    private TableHeaderOrderField tableHeaderOrder;
    private List<FilterField> tableHeaderFilters;

    public void putValue(String name, String ... value) {
        this.conditions.put(name, value);
    }

    public void putBinding(String name, String binding) {
        this.bindings.put(name, binding);
    }

    public String getValue(String name) {
        String[] values = this.conditions.get(name);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return null;
    }

    public String[] getValues(String name) {
        return this.conditions.get(name);
    }

    public String getBinding(String name) {
        return this.bindings.get(name);
    }

    public TableHeaderOrderField getTableHeaderOrder() {
        return this.tableHeaderOrder;
    }

    public void setTableHeaderOrder(TableHeaderOrderField tableHeaderOrder) {
        this.tableHeaderOrder = tableHeaderOrder;
    }

    public List<FilterField> getTableHeaderFilters() {
        return this.tableHeaderFilters;
    }

    public void setTableHeaderFilters(List<FilterField> tableHeaderFilters) {
        this.tableHeaderFilters = tableHeaderFilters;
    }

    public boolean contains(String name) {
        return this.conditions.containsKey(name);
    }

    public boolean containsBinding(String name) {
        return this.bindings.containsKey(name);
    }

    public String[] names() {
        return this.conditions.keySet().toArray(new String[this.conditions.size()]);
    }

    public void remove(String name) {
        this.conditions.remove(name);
        this.bindings.remove(name);
    }

    public void clear() {
        this.conditions.clear();
        this.bindings.clear();
    }
}

