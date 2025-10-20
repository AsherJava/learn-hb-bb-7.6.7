/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.DataTableNode;
import com.jiuqi.va.biz.intf.value.ListContainer;
import java.util.UUID;

public interface DataTableNodeContainer<T extends DataTableNode>
extends ListContainer<T> {
    public T find(String var1);

    public T get(String var1);

    public T find(UUID var1);

    public T get(UUID var1);

    public T getMasterTable();

    public ListContainer<? extends T> getDetailTables(UUID var1);
}

