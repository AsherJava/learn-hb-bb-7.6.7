/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;

public interface FormulaVariableDefine
extends IMetaItem {
    public String getCode();

    public String getTitle();

    public int getType();

    public int getValueType();

    public int getLength();

    public int getInitType();

    public String getFormSchemeKey();

    public String getInitValue();
}

