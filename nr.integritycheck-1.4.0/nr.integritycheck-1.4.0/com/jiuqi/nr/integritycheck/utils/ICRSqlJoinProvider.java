/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.intf.JoinType
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinItem
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem
 */
package com.jiuqi.nr.integritycheck.utils;

import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.intf.JoinType;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinItem;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem;
import java.io.Serializable;

public class ICRSqlJoinProvider
implements Serializable,
ISqlJoinProvider {
    private static final long serialVersionUID = 1L;
    private String dataSchemeBizCode;

    public ICRSqlJoinProvider(String dataSchemeBizCode) {
        this.dataSchemeBizCode = dataSchemeBizCode;
    }

    public SqlJoinItem getSqlJoinItem(String srcTable, String desTable) {
        String ICRTableName = "NR_ICR_" + this.dataSchemeBizCode;
        String ICDTableName = "NR_ICD_" + this.dataSchemeBizCode;
        if (ICRTableName.equals(srcTable) && ICDTableName.equals(desTable)) {
            SqlJoinItem joinItem = new SqlJoinItem(srcTable, desTable);
            joinItem.setJoinType(JoinType.Left);
            SqlJoinOneItem recidItem = new SqlJoinOneItem("RECID", "RECID");
            joinItem.addJoinItem(recidItem);
            return joinItem;
        }
        return null;
    }

    public JoinType getDefaultJoinType() {
        return JoinType.Left;
    }
}

