/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.type.RelationType
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.type.RelationType;
import java.util.Arrays;
import java.util.Objects;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataTableRelDTO
implements DataTableRel {
    private static final long serialVersionUID = 8991350727799908945L;
    @NotNull(message="{key.notNull}")
    protected @NotNull(message="{key.notNull}") String key;
    @NotNull(message="{type.notNull}")
    protected @NotNull(message="{type.notNull}") RelationType type;
    @NotNull(message="{dataSchemeKey.notNull}")
    protected @NotNull(message="{dataSchemeKey.notNull}") String dataSchemeKey;
    @NotNull(message="{srcTableKey.notNull}")
    protected @NotNull(message="{srcTableKey.notNull}") String srcTableKey;
    @NotNull(message="{desTableKey.notNull}")
    protected @NotNull(message="{desTableKey.notNull}") String desTableKey;
    @NotNull(message="{srcFieldKeys.notNull}")
    protected @NotNull(message="{srcFieldKeys.notNull}") String[] srcFieldKeys;
    @NotNull(message="{desFieldKeys.notNull}")
    protected @NotNull(message="{desFieldKeys.notNull}") String[] desFieldKeys;

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
        DataTableRelDTO other = (DataTableRelDTO)obj;
        return Objects.equals(this.key, other.key);
    }

    public String toString() {
        return "DataTableRelDTO{key='" + this.key + '\'' + ", type=" + this.type + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", srcTableKey='" + this.srcTableKey + '\'' + ", desTableKey='" + this.desTableKey + '\'' + ", srcFieldKeys=" + Arrays.toString(this.srcFieldKeys) + ", desFieldKeys=" + Arrays.toString(this.desFieldKeys) + '}';
    }

    public static DataTableRelDTO valueOf(DataTableRel o) {
        if (null == o) {
            return null;
        }
        DataTableRelDTO t = new DataTableRelDTO();
        DataTableRelDTO.copyProperties(o, t);
        return t;
    }

    public static void copyProperties(DataTableRel o, DataTableRelDTO t) {
        t.setKey(o.getKey());
        t.setType(o.getType());
        t.setDataSchemeKey(o.getDataSchemeKey());
        t.setSrcTableKey(o.getSrcTableKey());
        t.setDesTableKey(o.getDesTableKey());
        t.setSrcFieldKeys(o.getSrcFieldKeys());
        t.setDesFieldKeys(o.getDesFieldKeys());
    }
}

