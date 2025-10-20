/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.workflow.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;

public class WorkflowProcessRejectNodeStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String tableName = "WORKFLOW_PROCESS_REJECT_NODE";
        JTableModel jTableModel = new JTableModel(tenantName, tableName);
        try {
            WorkflowProcessRejectNodeStorage.getCreateProcessRejectNode(jTableModel);
            if (!jDialect.hasTable(jTableModel)) {
                jDialect.createTable(jTableModel);
            } else {
                jDialect.updateTable(jTableModel);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
    }

    private static void getCreateProcessRejectNode(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("BIZCODE").NVARCHAR(Integer.valueOf(200));
        jtm.column("PROCESSDEFINEKEY").NVARCHAR(Integer.valueOf(36));
        jtm.column("PROCESSDEFINEVERSION").LONG();
        jtm.column("REJECTNODECODE").NVARCHAR(Integer.valueOf(50));
        jtm.column("BEREJECTEDNODECODE").NVARCHAR(Integer.valueOf(50));
        jtm.column("AGREENOTREAPPROVE").INTEGER(new Integer[]{1});
        jtm.column("SUBPROCESSBRANCH").NVARCHAR(Integer.valueOf(64));
        jtm.column("NODEID").NVARCHAR(Integer.valueOf(64));
        jtm.column("AGREENODEID").NVARCHAR(Integer.valueOf(64));
        jtm.column("DELETEDFLAG").INTEGER(new Integer[]{1});
        jtm.index("IDX_WPRN_BC").columns(new String[]{"BIZCODE"});
        jtm.index("IDX_WPRN_PK").columns(new String[]{"PROCESSDEFINEKEY"});
    }
}

