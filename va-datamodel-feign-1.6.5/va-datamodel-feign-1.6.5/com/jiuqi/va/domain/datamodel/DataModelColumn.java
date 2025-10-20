/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.va.domain.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.io.Serializable;
import java.util.Arrays;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataModelColumn
implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(index=1)
    private String columnName;
    @JsonProperty(index=2)
    private String columnTitle;
    @JsonProperty(index=3)
    private DataModelType.ColumnType columnType;
    @JsonProperty(index=4)
    private Integer[] lengths;
    @JsonProperty(index=5)
    private Boolean nullable;
    @JsonProperty(index=6)
    private String defaultVal;
    @JsonProperty(index=7)
    private Integer mappingType;
    @JsonProperty(index=8)
    private String mapping;
    @JsonProperty(index=9)
    private Boolean pkey;
    @JsonProperty(index=10)
    private DataModelType.ColumnAttr columnAttr;
    @JsonIgnore
    private boolean locked = false;

    public void setLocked(boolean lock) {
        if (lock) {
            this.locked = true;
        }
    }

    private void validation() {
        if (this.locked) {
            throw new RuntimeException("\u975e\u6cd5\u7684\u64cd\u4f5c\uff0c\u7981\u6b62\u4fee\u6539\u7f13\u5b58\u6570\u636e\uff01");
        }
    }

    public DataModelColumn columnName(String columnName) {
        this.setColumnName(columnName);
        return this;
    }

    public DataModelColumn columnTitle(String columnTitle) {
        this.setColumnTitle(columnTitle);
        return this;
    }

    public DataModelColumn columnType(DataModelType.ColumnType columnType) {
        this.setColumnType(columnType);
        return this;
    }

    public DataModelColumn nullable(Boolean nullable) {
        this.setNullable(nullable);
        return this;
    }

    public DataModelColumn pkey(Boolean pkey) {
        this.setPkey(pkey);
        return this;
    }

    public DataModelColumn lengths(Integer ... lengths) {
        this.setLengths(lengths);
        return this;
    }

    public DataModelColumn defaultVal(String defaultVal) {
        this.setDefaultVal(defaultVal);
        return this;
    }

    public DataModelColumn mappingType(Integer mappingType) {
        this.setMappingType(mappingType);
        return this;
    }

    public DataModelColumn columnAttr(DataModelType.ColumnAttr columnAttr) {
        this.setColumnAttr(columnAttr);
        return this;
    }

    public DataModelColumn mapping(String mapping) {
        this.setMapping(mapping);
        return this;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.validation();
        this.columnName = columnName != null ? columnName.toUpperCase() : null;
    }

    public String getColumnTitle() {
        return this.columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.validation();
        this.columnTitle = columnTitle;
    }

    public DataModelType.ColumnType getColumnType() {
        return this.columnType;
    }

    public void setColumnType(DataModelType.ColumnType columnType) {
        this.validation();
        this.columnType = columnType;
        if (columnType == null) {
            return;
        }
        if (columnType == DataModelType.ColumnType.NUMERIC && this.lengths != null) {
            if (this.lengths.length > 1) {
                for (int i = 0; i < this.lengths.length; ++i) {
                    if (this.lengths[i] != null) continue;
                    this.lengths[i] = 0;
                }
            } else {
                Integer[] lens = new Integer[]{this.lengths[0] == null ? 1 : this.lengths[0], 0};
                this.lengths = lens;
            }
        }
    }

    public Integer[] getLengths() {
        return this.lengths;
    }

    public void setLengths(Integer ... lengths) {
        this.validation();
        if (lengths == null) {
            return;
        }
        if (this.columnType != null && this.columnType == DataModelType.ColumnType.NUMERIC) {
            this.lengths = new Integer[2];
            this.lengths[0] = lengths[0] == null ? 1 : lengths[0];
            this.lengths[1] = lengths.length > 1 ? Integer.valueOf(lengths[1] == null ? 0 : lengths[1]) : Integer.valueOf(0);
        } else {
            this.lengths = lengths;
            for (int i = 0; i < this.lengths.length; ++i) {
                if (this.lengths[i] != null) continue;
                this.lengths[i] = 0;
            }
        }
    }

    public Boolean isNullable() {
        return this.nullable == null ? Boolean.TRUE : this.nullable;
    }

    public Boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(Boolean nullable) {
        this.validation();
        this.nullable = nullable;
    }

    public Boolean isPkey() {
        return this.pkey == null ? Boolean.FALSE : this.pkey;
    }

    public Boolean getPkey() {
        return this.pkey;
    }

    public void setPkey(Boolean pkey) {
        this.validation();
        this.pkey = pkey;
    }

    public String getDefaultVal() {
        return this.defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.validation();
        this.defaultVal = defaultVal;
    }

    public Integer getMappingType() {
        return this.mappingType;
    }

    public void setMappingType(Integer mappingType) {
        this.validation();
        this.mappingType = mappingType;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.validation();
        this.mapping = mapping;
    }

    public DataModelType.ColumnAttr getColumnAttr() {
        return this.columnAttr;
    }

    public void setColumnAttr(DataModelType.ColumnAttr columnAttr) {
        this.validation();
        this.columnAttr = columnAttr;
    }

    public String toString() {
        return "DataModelColumn [columnName=" + this.columnName + ", columnTitle=" + this.columnTitle + ", columnType=" + (Object)((Object)this.columnType) + ", lengths=" + Arrays.toString((Object[])this.lengths) + ", nullable=" + this.nullable + ", pkey=" + this.pkey + ", defaultVal=" + this.defaultVal + ", mappingType=" + this.mappingType + ", mapping=" + this.mapping + ", columnAttr=" + (Object)((Object)this.columnAttr) + "]";
    }
}

