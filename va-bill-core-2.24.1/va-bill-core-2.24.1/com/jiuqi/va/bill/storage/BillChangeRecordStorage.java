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

public class BillChangeRecordStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = BillChangeRecordStorage.getCreateJTM(tenantName);
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

    public static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "BILL_CHANGEREC");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("BILLCODE").VARCHAR(Integer.valueOf(60));
        jtm.column("OPTUSER").VARCHAR(Integer.valueOf(36));
        jtm.column("OPTTIME").TIMESTAMP();
        jtm.column("OPTTYPE").INTEGER(new Integer[]{2});
        jtm.column("REASON").VARCHAR(Integer.valueOf(200));
        jtm.index("IDX_BILL_CHANGEREC_BILLCODE").columns(new String[]{"BILLCODE"});
        return jtm;
    }
}

