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

public class BIZ_SUBLIST_IMPORT_TEMPLATE {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String tableName = "BIZ_SUBLIST_IMPORT_TEMPLATE";
        JTableModel jtm = new JTableModel(tenantName, tableName);
        try {
            BIZ_SUBLIST_IMPORT_TEMPLATE.getCreateEditFieldConfigInfoStorage(jtm);
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

    private static void getCreateEditFieldConfigInfoStorage(JTableModel jtm) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("VER").NUMERIC(new Integer[]{19, 0});
        jtm.column("BILLTYPE").NVARCHAR(Integer.valueOf(64));
        jtm.column("TABLENAME").NVARCHAR(Integer.valueOf(64));
        jtm.column("TEMPLATEDATA").CLOB();
    }
}

