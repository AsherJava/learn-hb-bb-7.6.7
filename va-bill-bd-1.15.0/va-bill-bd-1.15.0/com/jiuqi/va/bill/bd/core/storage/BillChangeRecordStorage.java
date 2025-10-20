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
        JTableModel jtm = new JTableModel(tenantName, "BILLCHANGE_RECORD");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("VER").comment("\u7248\u672c").NUMERIC(new Integer[]{19, 2});
        jtm.column("CHANGEFILEDNAME").comment("\u53d8\u66f4\u5b57\u6bb5\u540d").VARCHAR(Integer.valueOf(100));
        jtm.column("CHANGEFILEDTITLE").comment("\u53d8\u66f4\u5b57\u6bb5\u6807\u9898").VARCHAR(Integer.valueOf(100));
        jtm.column("CHANGETYPE").comment("\u53d8\u66f4\u7c7b\u578b").INTEGER(new Integer[]{2});
        jtm.column("CREATETIME").comment("\u53d8\u66f4\u65f6\u95f4").TIMESTAMP();
        jtm.column("CHANGEBEFORE").comment("\u53d8\u66f4\u524d").VARCHAR(Integer.valueOf(100));
        jtm.column("CHANGEAFTER").comment("\u53d8\u66f4\u540e").VARCHAR(Integer.valueOf(100));
        jtm.column("CHANGEUSER").comment("\u53d8\u66f4\u4eba").VARCHAR(Integer.valueOf(36));
        jtm.column("SRCBILLCODE").comment("\u6e90\u5355\u636e\u7f16\u53f7").VARCHAR(Integer.valueOf(100));
        jtm.column("BILLCODE").comment("\u76ee\u6807\u5355\u636e\u7f16\u53f7").VARCHAR(Integer.valueOf(100));
        jtm.column("CHANGEREASON").comment("\u53d8\u66f4\u539f\u56e0").VARCHAR(Integer.valueOf(500));
        jtm.column("BILLTABLE").comment("\u767b\u8bb0\u5355\u8868\u540d").VARCHAR(Integer.valueOf(100));
        jtm.column("SRCBILLDEFINE").comment("\u53d8\u66f4\u5355\u5355\u636e\u5b9a\u4e49").VARCHAR(Integer.valueOf(60));
        jtm.column("BILLDEFINE").comment("\u88ab\u4fee\u6539\u5355\u636e\u5b9a\u4e49").VARCHAR(Integer.valueOf(60));
        return jtm;
    }
}

