/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.bill.bd.core.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;

public class ApplyRegMapGroupStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = ApplyRegMapGroupStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "APPLYREG_MP_GRP");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("VER").comment("\u7248\u672c").NUMERIC(new Integer[]{19, 2});
        jtm.column("NAME").comment("\u8868\u6807\u8bc6").VARCHAR(Integer.valueOf(100));
        jtm.column("TITLE").comment("\u8868\u6807\u9898").VARCHAR(Integer.valueOf(100));
        jtm.column("CREATETIME").comment("\u521b\u5efa\u65f6\u95f4").DATE();
        jtm.column("MODIFYTIME").comment("\u4fee\u6539\u65f6\u95f4").DATE();
        jtm.column("MODIFYUSER").comment("\u4fee\u6539\u4eba").VARCHAR(Integer.valueOf(32));
        jtm.column("DESCRIPTION").comment("\u63cf\u8ff0").VARCHAR(Integer.valueOf(400));
        jtm.index("APPLYREG_MP_GRP_NAME").columns(new String[]{"NAME"}).unique();
        return jtm;
    }
}

