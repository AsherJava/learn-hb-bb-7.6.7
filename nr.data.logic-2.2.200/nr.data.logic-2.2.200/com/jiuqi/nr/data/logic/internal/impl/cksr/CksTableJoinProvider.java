/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.intf.JoinType
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinItem
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem
 */
package com.jiuqi.nr.data.logic.internal.impl.cksr;

import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.intf.JoinType;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinItem;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem;

public class CksTableJoinProvider
implements ISqlJoinProvider {
    public SqlJoinItem getSqlJoinItem(String srcTable, String desTable) {
        SqlJoinItem sqlJoinItem = new SqlJoinItem(srcTable, desTable);
        sqlJoinItem.setJoinType(JoinType.Left);
        SqlJoinOneItem sqlJoinOneItem = new SqlJoinOneItem("CKS_REC_KEY", "CKS_S_REC_KEY");
        sqlJoinItem.addJoinItem(sqlJoinOneItem);
        return sqlJoinItem;
    }

    public JoinType getDefaultJoinType() {
        return JoinType.Left;
    }
}

