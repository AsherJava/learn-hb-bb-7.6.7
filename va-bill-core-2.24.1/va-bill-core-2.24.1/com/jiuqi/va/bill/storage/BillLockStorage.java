/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.bill.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;

public class BillLockStorage {
    private static final String TABLE_NAME = "BILL_LOCK";

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        JTableModel jtm = new JTableModel(tenantName, TABLE_NAME);
        try {
            BillLockStorage.getCreateBillLock(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
    }

    private static void getCreateBillLock(JTableModel jtm) {
        jtm.column("BILLCODE").VARCHAR(Integer.valueOf(60)).pkey();
    }
}

