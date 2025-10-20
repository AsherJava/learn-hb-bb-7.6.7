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

public class AuthBillActionStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = AuthBillActionStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "AUTH_BILL_ACTION");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("BIZTYPE").INTEGER(new Integer[]{1});
        jtm.column("BIZNAME").VARCHAR(Integer.valueOf(100));
        jtm.column("AUTHTYPE").INTEGER(new Integer[]{1});
        jtm.column("DEFINENAME").VARCHAR(Integer.valueOf(100));
        jtm.column("ACTNAME").VARCHAR(Integer.valueOf(100));
        jtm.column("AUTHFLAG").INTEGER(new Integer[]{1});
        jtm.index("AHBILLAC_BBDAA").columns(new String[]{"BIZTYPE", "BIZNAME", "DEFINENAME", "ACTNAME", "AUTHTYPE"}).unique();
        return jtm;
    }
}

