/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.navigation.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;

public class NavigationTableStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = NavigationTableStorage.getCreateJTM(tenantName);
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

    private static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "GC_NAVIGATIONCONFIG");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("RECVER").LONG();
        jtm.column("CODE").VARCHAR(Integer.valueOf(50));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(50));
        jtm.column("CONFIGVALUE").CLOB();
        jtm.column("BACKIMG").CLOB();
        jtm.index("GC_NAVIGATIONCONFIG_INDEX").columns(new String[]{"ID", "CODE"}).unique();
        return jtm;
    }
}

