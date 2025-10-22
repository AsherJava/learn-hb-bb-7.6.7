/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.common.EnumTransUtils
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.common.EnumTransUtils;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class FmlTraceFieldDefine
implements FieldDefine {
    private final ColumnModelDefine columnModelDefine;

    public FmlTraceFieldDefine(ColumnModelDefine columnModelDefine) {
        this.columnModelDefine = columnModelDefine;
    }

    public FieldValueType getValueType() {
        return null;
    }

    public FieldGatherType getGatherType() {
        return null;
    }

    public Boolean getAllowUndefinedCode() {
        return null;
    }

    public Boolean getAllowMultipleSelect() {
        return null;
    }

    public FormatProperties getFormatProperties() {
        return null;
    }

    public Integer getSecretLevel() {
        return null;
    }

    public String getMeasureUnit() {
        return null;
    }

    public String getEntityKey() {
        return null;
    }

    public String getAlias() {
        return null;
    }

    public String getCode() {
        return this.columnModelDefine.getCode();
    }

    public String getDescription() {
        return null;
    }

    public String getDefaultValue() {
        return null;
    }

    public Integer getFractionDigits() {
        return null;
    }

    public FieldType getType() {
        return EnumTransUtils.valueOf((ColumnModelType)this.columnModelDefine.getColumnType());
    }

    public Integer getSize() {
        return null;
    }

    public Boolean getNullable() {
        return null;
    }

    public String getOwnerTableKey() {
        return null;
    }

    public Boolean getUseAuthority() {
        return null;
    }

    public String getKey() {
        return this.columnModelDefine.getID();
    }

    public String getTitle() {
        return this.columnModelDefine.getTitle();
    }

    public String getOrder() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }
}

