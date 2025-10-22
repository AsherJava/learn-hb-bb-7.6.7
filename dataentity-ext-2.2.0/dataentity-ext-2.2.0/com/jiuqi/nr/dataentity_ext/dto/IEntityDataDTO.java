/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.dto;

import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow;

public interface IEntityDataDTO
extends IEntityDataRow {
    public String[] getPath();

    public EntityDataType getType();

    public boolean isLeaf();

    public boolean isRoot();
}

