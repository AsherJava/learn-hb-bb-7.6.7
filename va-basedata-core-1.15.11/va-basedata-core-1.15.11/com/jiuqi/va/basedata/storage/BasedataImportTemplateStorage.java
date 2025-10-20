/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.basedata.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;

public class BasedataImportTemplateStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialectUtil = JDialectUtil.getInstance();
        try {
            JTableModel jTableModel = BasedataImportTemplateStorage.getCreateJTM(tenantName);
            if (!jDialectUtil.hasTable(jTableModel)) {
                jDialectUtil.createTable(jTableModel);
            } else {
                jDialectUtil.updateTable(jTableModel);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "BASEDATA_IMPORT_TEMPLATE");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("CODE").VARCHAR(Integer.valueOf(100));
        jtm.column("NAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("FIXED").INTEGER(new Integer[]{1});
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.column("TEMPLATEDATA").CLOB();
        return jtm;
    }
}

