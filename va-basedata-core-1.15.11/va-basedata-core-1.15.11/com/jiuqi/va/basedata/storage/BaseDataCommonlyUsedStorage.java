/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.basedata.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;

public class BaseDataCommonlyUsedStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = BaseDataCommonlyUsedStorage.getCreateJTM(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
            jtm = BaseDataCommonlyUsedStorage.getCreateFlagJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "BASEDATA_COMMONLY_USED");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("USERID").NVARCHAR(Integer.valueOf(100));
        jtm.column("DEFINENAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("OBJECTCODE").VARCHAR(Integer.valueOf(200));
        jtm.index("BASEDATACYUD_UEDE").columns(new String[]{"USERID", "DEFINENAME"});
        return jtm;
    }

    private static JTableModel getCreateFlagJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "BASEDATA_COMMONLY_FLAG");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("USERID").NVARCHAR(Integer.valueOf(100));
        jtm.column("DEFINENAME").NVARCHAR(Integer.valueOf(100));
        jtm.index("BASEDATACYFG_UEDE").columns(new String[]{"USERID", "DEFINENAME"});
        return jtm;
    }
}

