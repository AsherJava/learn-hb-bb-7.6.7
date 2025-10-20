/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.value.NamedElement;
import java.util.UUID;

public interface DataTableNode
extends NamedElement {
    public UUID getId();

    @Override
    public String getName();

    public String getTitle();

    public UUID getParentId();

    public DataTableType getTableType();
}

