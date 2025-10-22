/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.lwtree.query;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.lwtree.para.ITreeParamsInitializer;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryerImpl;
import java.util.ArrayList;
import java.util.List;

public class WithoutLeafNodeQuery
extends IEntityRowQueryerImpl {
    public WithoutLeafNodeQuery(ITreeParamsInitializer loadInfo) {
        super(loadInfo);
    }

    @Override
    public List<IEntityRow> getChildRows(String entKey) {
        ArrayList<IEntityRow> childRows = new ArrayList<IEntityRow>(0);
        IEntityTable resultSet = this.getIEntityTable();
        List superChildRows = entKey != null ? resultSet.getChildRows(entKey) : resultSet.getRootRows();
        for (IEntityRow row : superChildRows) {
            List cRows = resultSet.getChildRows(row.getEntityKeyData());
            if (cRows == null || cRows.size() == 0) continue;
            childRows.add(row);
        }
        return childRows;
    }
}

