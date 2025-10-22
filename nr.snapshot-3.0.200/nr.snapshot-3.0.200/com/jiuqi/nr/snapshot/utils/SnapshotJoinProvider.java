/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.intf.JoinType
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinItem
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem
 */
package com.jiuqi.nr.snapshot.utils;

import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.intf.JoinType;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinItem;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem;
import java.io.Serializable;

public class SnapshotJoinProvider
implements Serializable,
ISqlJoinProvider {
    private static final long serialVersionUID = 1L;
    private String snapshotID = "ID";
    private String snapshotRelID = "SNAPSHOTID";

    public SqlJoinItem getSqlJoinItem(String srcTable, String desTable) {
        SqlJoinItem joinItem = new SqlJoinItem(srcTable, desTable);
        joinItem.setJoinType(JoinType.Left);
        SqlJoinOneItem recidItem = new SqlJoinOneItem(this.snapshotRelID, this.snapshotID);
        joinItem.addJoinItem(recidItem);
        return joinItem;
    }

    public JoinType getDefaultJoinType() {
        return JoinType.Left;
    }
}

