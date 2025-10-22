/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.IBaseMetaItem;

public interface UniversalFieldDefine
extends IBaseMetaItem {
    public String getCode();

    public String getDescription();

    public String getDefaultValue();

    public Integer getFractionDigits();

    public FieldType getType();

    public Integer getSize();

    public Boolean getNullable();

    public String getOwnerTableKey();

    public Boolean getUseAuthority();
}

