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

public class MaintainBillExceptionStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = MaintainBillExceptionStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "CREATEBILL_EXCEPTION");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("VER").comment("\u7248\u672c").NUMERIC(new Integer[]{19, 2});
        jtm.column("ORDINAL").comment("\u6392\u5e8f").NUMERIC(new Integer[]{19, 6});
        jtm.column("CREATETYPE").comment("\u751f\u5355\u7c7b\u578b").INTEGER(new Integer[]{1});
        jtm.column("CREATETIME").comment("\u521b\u5efa\u65f6\u95f4").TIMESTAMP();
        jtm.column("SRCDEFINECODE").comment("\u6e90\u5355\u5355\u636e\u5b9a\u4e49code").VARCHAR(Integer.valueOf(100));
        jtm.column("SRCDEFINENAME").comment("\u6e90\u5355\u5355\u636e\u5b9a\u4e49name").VARCHAR(Integer.valueOf(100));
        jtm.column("CONFIGNAME").comment("\u6620\u5c04\u914d\u7f6e\u6807\u8bc6").VARCHAR(Integer.valueOf(100));
        jtm.column("SRCBILLCODE").comment("\u6e90\u5355\u5355\u636e\u7f16\u53f7").VARCHAR(Integer.valueOf(60));
        jtm.column("SRCMASTERID").comment("\u6e90\u5355\u5355\u636e\u4e3b\u8868id").VARCHAR(Integer.valueOf(36));
        jtm.column("SRCDETAILBILLID").comment("\u6e90\u5355\u5355\u636e\u5b50\u8868id").VARCHAR(Integer.valueOf(36));
        jtm.column("DEFINECODE").comment("\u76ee\u6807\u5355\u636e\u5b9a\u4e49code").VARCHAR(Integer.valueOf(100));
        jtm.column("DEFINENAME").comment("\u76ee\u6807\u5355\u636e\u5b9a\u4e49name").VARCHAR(Integer.valueOf(100));
        jtm.column("BILLCODE").comment("\u76ee\u6807\u5355\u636e\u7f16\u53f7").VARCHAR(Integer.valueOf(60));
        jtm.column("MASTERID").comment("\u76ee\u6807\u5355\u636e\u4e3b\u8868id").VARCHAR(Integer.valueOf(36));
        jtm.column("CREATEBILLSTATE").comment("\u751f\u5355\u72b6\u6001").INTEGER(new Integer[]{2});
        jtm.column("CACHESYNCDISABLE").comment("\u540c\u6b65\u7f13\u5b58").INTEGER(new Integer[]{1});
        jtm.column("MEMO").comment("\u5931\u8d25\u539f\u56e0").VARCHAR(Integer.valueOf(1000));
        return jtm;
    }
}

