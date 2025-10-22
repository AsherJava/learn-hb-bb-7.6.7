/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.provider;

public class DimensionColumn {
    private String name;
    private int dataType;
    private int index;

    public DimensionColumn(String name, int dataType, int index) {
        this.name = name;
        this.dataType = dataType;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public int getDataType() {
        return this.dataType;
    }

    public int getIndex() {
        return this.index;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
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
        DimensionColumn other = (DimensionColumn)obj;
        return !(this.name == null ? other.name != null : !this.name.equals(other.name));
    }

    public String toString() {
        return "DimensionColumn [name=" + this.name + ", dataType=" + this.dataType + ", index=" + this.index + "]+\n";
    }
}

