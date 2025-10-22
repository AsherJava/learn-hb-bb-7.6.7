/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.datasource;

import java.util.HashMap;
import java.util.Map;

public class MappingMainDimTable {
    private String tableName;
    private String dimensionName;
    private Map<String, String> fieldMappings = new HashMap<String, String>();
    private Map<String, String> args;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getFieldMappings() {
        return this.fieldMappings;
    }

    public Map<String, String> getArgs() {
        if (this.args == null) {
            this.args = new HashMap<String, String>();
        }
        return this.args;
    }

    public boolean hasArgs() {
        return this.args != null;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.tableName == null ? 0 : this.tableName.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        MappingMainDimTable other = (MappingMainDimTable)obj;
        return !(this.tableName == null ? other.tableName != null : !this.tableName.equals(other.tableName));
    }

    public String toString() {
        return "MappingTable [tableName=" + this.tableName + ", fieldMappings=" + this.fieldMappings + ", args=" + this.args + "]";
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }
}

