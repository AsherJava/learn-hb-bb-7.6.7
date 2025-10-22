/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.type.RelationType
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.type.RelationType;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.util.Arrays;
import java.util.Objects;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_TABLE_REL")
public class DataTableRelDO
implements DataTableRel {
    private static final long serialVersionUID = 2246514638768317261L;
    @DBAnno.DBField(dbField="DTR_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="DTR_TYPE", tranWith="transRelationType", dbType=Integer.class, appType=RelationType.class)
    protected RelationType type;
    @DBAnno.DBField(dbField="DTR_DS_KEY")
    protected String dataSchemeKey;
    @DBAnno.DBField(dbField="DTR_SRCTABLE_KEY")
    protected String srcTableKey;
    @DBAnno.DBField(dbField="DTR_DESTTABLE_KEY")
    protected String desTableKey;
    @DBAnno.DBField(dbField="DTR_SRCFIELD_KEYS", tranWith="transStringArray", dbType=String.class, appType=String[].class)
    protected String[] srcFieldKeys;
    @DBAnno.DBField(dbField="DTR_DESTFIELD_KEYS", tranWith="transStringArray", dbType=String.class, appType=String[].class)
    protected String[] desFieldKeys;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RelationType getType() {
        return this.type;
    }

    public void setType(RelationType type) {
        this.type = type;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getSrcTableKey() {
        return this.srcTableKey;
    }

    public void setSrcTableKey(String srcTableKey) {
        this.srcTableKey = srcTableKey;
    }

    public String getDesTableKey() {
        return this.desTableKey;
    }

    public void setDesTableKey(String desTableKey) {
        this.desTableKey = desTableKey;
    }

    public String[] getSrcFieldKeys() {
        return this.srcFieldKeys;
    }

    public void setSrcFieldKeys(String[] srcFieldKeys) {
        this.srcFieldKeys = srcFieldKeys;
    }

    public String[] getDesFieldKeys() {
        return this.desFieldKeys;
    }

    public void setDesFieldKeys(String[] desFieldKeys) {
        this.desFieldKeys = desFieldKeys;
    }

    public int hashCode() {
        return Objects.hash(this.key);
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
        DataTableRelDO other = (DataTableRelDO)obj;
        return Objects.equals(this.key, other.key);
    }

    public String toString() {
        return "DataTableRelDO{key='" + this.key + '\'' + ", type=" + this.type + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", srcTableKey='" + this.srcTableKey + '\'' + ", desTableKey='" + this.desTableKey + '\'' + ", srcFieldKeys=" + Arrays.toString(this.srcFieldKeys) + ", desFieldKeys=" + Arrays.toString(this.desFieldKeys) + '}';
    }

    public static DataTableRelDO valueOf(DataTableRel o) {
        if (null == o) {
            return null;
        }
        DataTableRelDO t = new DataTableRelDO();
        DataTableRelDO.copyProperties(o, t);
        return t;
    }

    public static void copyProperties(DataTableRel o, DataTableRelDO t) {
        t.setKey(o.getKey());
        t.setType(o.getType());
        t.setDataSchemeKey(o.getDataSchemeKey());
        t.setSrcTableKey(o.getSrcTableKey());
        t.setDesTableKey(o.getDesTableKey());
        t.setSrcFieldKeys(o.getSrcFieldKeys());
        t.setDesFieldKeys(o.getDesFieldKeys());
    }
}

