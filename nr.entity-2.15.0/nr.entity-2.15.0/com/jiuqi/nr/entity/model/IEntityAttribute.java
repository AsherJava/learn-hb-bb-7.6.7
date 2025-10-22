/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.entity.model;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.io.Serializable;

public interface IEntityAttribute
extends ColumnModelDefine,
Serializable {
    public boolean isSupportI18n();

    public String masked();
}

