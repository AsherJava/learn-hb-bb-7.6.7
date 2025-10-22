/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTableMap
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataTableMap;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_TABLE_MAP")
public class DataTableMapDO
implements DataTableMap {
    private static final long serialVersionUID = -5400071041873057784L;
    @DBAnno.DBField(dbField="MAP_TABLE_CODE")
    protected String tableCode;
    @DBAnno.DBField(dbField="MAP_TABLE_KEY", isPk=true)
    protected String tableKey;
    @DBAnno.DBField(dbField="MAP_SRC_KEY")
    protected String srcKey;
    @DBAnno.DBField(dbField="MAP_SRC_TYPE")
    protected String srcType;
    @DBAnno.DBField(dbField="MAP_SCHEME_KEY")
    protected String schemeKey;
    @DBAnno.DBField(dbField="MAP_SRC_CODE")
    protected String srcCode;

    public DataTableMapDO() {
    }

    public DataTableMapDO(String tableKey, String tableCode, String srcKey, String srcCode, String srcType) {
        this.tableCode = tableCode;
        this.srcCode = srcCode;
        this.srcKey = srcKey;
        this.srcType = srcType;
        this.tableKey = tableKey;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public String getSrcKey() {
        return this.srcKey;
    }

    public String getSrcType() {
        return this.srcType;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public void setSrcKey(String srcKey) {
        this.srcKey = srcKey;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getSrcCode() {
        return this.srcCode;
    }

    public void setSrcCode(String srcCode) {
        this.srcCode = srcCode;
    }

    public DataTableMapDO clone() {
        try {
            return (DataTableMapDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.tableKey == null ? 0 : this.tableKey.hashCode());
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
        DataTableMapDO other = (DataTableMapDO)obj;
        return !(this.tableKey == null ? other.tableKey != null : !this.tableKey.equals(other.tableKey));
    }

    public String toString() {
        return "DataTableMapDO [tableCode=" + this.tableCode + ", tableKey=" + this.tableKey + ", srcKey=" + this.srcKey + ", srcType=" + this.srcType + ", schemeKey=" + this.schemeKey + "]";
    }
}

