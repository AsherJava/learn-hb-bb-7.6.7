/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.ColumnModelDefineImpl
 */
package com.jiuqi.np.dataengine.definitions;

import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.ColumnModelDefineImpl;

public class ColumnModelAdapter
extends ColumnModelDefineImpl {
    private static final long serialVersionUID = 1151506605779465838L;
    private DesignFieldDefine fieldDefine;
    private ColumnModelDefine refColumn;

    public ColumnModelAdapter(DesignFieldDefine fieldDefine, DataModelDefinitionsCache dataModelDefinitionsCache) {
        this.fieldDefine = fieldDefine;
        if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey()) && !this.getCode().equals("DATATIME")) {
            this.refColumn = dataModelDefinitionsCache.getDesignEntityColumn(fieldDefine.getEntityKey());
        }
    }

    public String getID() {
        return this.fieldDefine.getKey();
    }

    public String getTableID() {
        return this.fieldDefine.getOwnerTableKey();
    }

    public String getCode() {
        return this.fieldDefine.getCode();
    }

    public String getName() {
        return this.fieldDefine.getCode();
    }

    public String getTitle() {
        return this.fieldDefine.getTitle();
    }

    public ColumnModelType getColumnType() {
        return DataTypesConvert.DataTypeToColumnType(DataTypesConvert.fieldTypeToDataType(this.fieldDefine.getType()));
    }

    public int getDecimal() {
        return this.fieldDefine.getFractionDigits();
    }

    public String getReferTableID() {
        if (this.refColumn != null) {
            return this.refColumn.getTableID();
        }
        return null;
    }

    public boolean isNullAble() {
        return this.fieldDefine.getNullable();
    }

    public String getDefaultValue() {
        return this.fieldDefine.getDefaultValue();
    }

    public String getReferColumnID() {
        if (this.refColumn != null) {
            return this.refColumn.getID();
        }
        return null;
    }

    public DesignFieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.getID() == null ? 0 : this.getID().hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((Object)((Object)this)).getClass() != obj.getClass()) {
            return false;
        }
        ColumnModelDefineImpl other = (ColumnModelDefineImpl)obj;
        return !(this.getID() == null ? this.getID() != null : !this.getID().equals(other.getID()));
    }
}

