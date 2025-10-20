/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineMerge;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import java.util.UUID;

public class DataDefineMerge {
    public static void merge(DataDefineImpl target, DataDefineImpl base) {
        ((ListContainerImpl)((Object)base.getTables())).stream().forEach(o -> {
            DataTableDefineImpl table = (DataTableDefineImpl)((DataTableNodeContainerImpl)target.getTables()).find(o.getName());
            UUID parentId = o.getParentId();
            if (parentId != null) {
                parentId = ((DataTableDefineImpl)((DataTableNodeContainerImpl)target.getTables()).find(((DataTableDefineImpl)((DataTableNodeContainerImpl)base.getTables()).get(parentId)).getName())).getId();
            }
            if (table == null) {
                target.addTable((DataTableDefineImpl)o);
                o.setParentId(parentId);
            } else {
                DataTableDefineMerge.merge(table, o);
                table.setParentId(parentId);
            }
        });
    }
}

