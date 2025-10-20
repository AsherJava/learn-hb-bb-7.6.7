/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.DataTableNode;
import com.jiuqi.va.biz.intf.data.DataTableType;
import java.util.UUID;

public interface DataTableInfo
extends DataTableNode {
    @Override
    public UUID getId();

    @Override
    public String getName();

    @Override
    public String getTitle();

    @Override
    public DataTableType getTableType();

    public String getTableName();

    @Override
    public UUID getParentId();

    public boolean isRequired();

    public boolean isReadonly();

    public boolean isAddRow();

    public boolean isDelRow();

    public boolean isSingle();

    public boolean isFixed();

    public boolean isEnableFilter();

    public boolean isFilterCondition();

    public boolean isHideCkeckRowNum();
}

