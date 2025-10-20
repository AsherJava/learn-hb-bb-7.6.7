/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.util.StringUtils;

public class I18nRunTimeFieldDefine
implements FieldDefine {
    private final FieldDefine fieldDefine;
    private String title;

    public I18nRunTimeFieldDefine(FieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public FieldValueType getValueType() {
        return this.fieldDefine.getValueType();
    }

    public FieldGatherType getGatherType() {
        return this.fieldDefine.getGatherType();
    }

    public Boolean getAllowUndefinedCode() {
        return this.fieldDefine.getAllowUndefinedCode();
    }

    public Boolean getAllowMultipleSelect() {
        return this.fieldDefine.getAllowMultipleSelect();
    }

    public FormatProperties getFormatProperties() {
        return this.fieldDefine.getFormatProperties();
    }

    public Integer getSecretLevel() {
        return this.fieldDefine.getSecretLevel();
    }

    public String getMeasureUnit() {
        return this.fieldDefine.getMeasureUnit();
    }

    public String getEntityKey() {
        return this.fieldDefine.getEntityKey();
    }

    public String getAlias() {
        return this.fieldDefine.getAlias();
    }

    public String getCode() {
        return this.fieldDefine.getCode();
    }

    public String getDescription() {
        return this.fieldDefine.getDescription();
    }

    public String getDefaultValue() {
        return this.fieldDefine.getDefaultValue();
    }

    public Integer getFractionDigits() {
        return this.fieldDefine.getFractionDigits();
    }

    public FieldType getType() {
        return this.fieldDefine.getType();
    }

    public Integer getSize() {
        return this.fieldDefine.getSize();
    }

    public Boolean getNullable() {
        return this.fieldDefine.getNullable();
    }

    public String getOwnerTableKey() {
        return this.fieldDefine.getOwnerTableKey();
    }

    public Boolean getUseAuthority() {
        return this.fieldDefine.getUseAuthority();
    }

    public String getKey() {
        return this.fieldDefine.getKey();
    }

    public String getOrder() {
        return this.fieldDefine.getOrder();
    }

    public String getVersion() {
        return this.fieldDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.fieldDefine.getOwnerLevelAndId();
    }

    public String getTitle() {
        return StringUtils.isEmpty((String)this.title) ? this.fieldDefine.getTitle() : this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

