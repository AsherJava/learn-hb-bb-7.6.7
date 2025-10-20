/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.impl;

public class DataModelIndexDefine {
    private String name;
    private String[] columnsFieldKeys;
    private String[] columnsFields;
    private int indexType = 0;

    public DataModelIndexDefine(String code) {
        this.setName(code);
        this.setIndexType(0);
    }

    public String getName() {
        return this.name;
    }

    public String[] getColumnsFieldKeys() {
        return this.columnsFieldKeys;
    }

    public String[] getColumnsFields() {
        return this.columnsFields;
    }

    public int getIndexType() {
        return this.indexType;
    }

    public DataModelIndexDefine setName(String name) {
        this.name = name;
        return this;
    }

    public DataModelIndexDefine setColumnsFieldKeys(String ... columnsFieldKeys) {
        this.columnsFieldKeys = columnsFieldKeys;
        return this;
    }

    public DataModelIndexDefine Columns(String ... columnsFields) {
        this.columnsFields = columnsFields;
        return this;
    }

    public DataModelIndexDefine setIndexType(int indexType) {
        this.indexType = indexType;
        return this;
    }

    public int hashCode() {
        int index = 0;
        if (this.columnsFields == null || this.columnsFields.length < 1) {
            index = 0;
        } else {
            for (String s : this.columnsFields) {
                index += s.hashCode();
            }
        }
        return index;
    }

    public boolean equals(Object obj) {
        DataModelIndexDefine d = (DataModelIndexDefine)obj;
        if (d.getColumnsFields() != null && d.getColumnsFields().length > 0 && this.getColumnsFields() != null && this.getColumnsFields().length == d.getColumnsFields().length) {
            for (int i = 0; i < this.getColumnsFields().length; ++i) {
                if (this.getColumnsFields()[i].equals(d.getColumnsFields()[i])) continue;
                return false;
            }
            return true;
        }
        return false;
    }
}

