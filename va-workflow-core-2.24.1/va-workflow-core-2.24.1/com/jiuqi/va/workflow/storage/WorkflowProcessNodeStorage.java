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

public class WorkflowProcessNodeStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String processTableName = "WORKFLOW_PROCESS_NODE";
        JTableModel process = new JTableModel(tenantName, processTableName);
        try {
            WorkflowProcessNodeStorage.getCreateprocessNode(process);
            if (!jDialect.hasTable(process)) {
                jDialect.createTable(process);
            } else {
                jDialect.updateTable(process);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
    }

    private static void getCreateprocessNode(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NODEID").NVARCHAR(Integer.valueOf(64)).notNull();
        jtm.column("NODECODE").NVARCHAR(Integer.valueOf(64));
        jtm.column("PROCESSNODENAME").NVARCHAR(Integer.valueOf(64)).notNull();
        jtm.column("PROCESSID").NVARCHAR(Integer.valueOf(36));
        jtm.column("COUNTERSIGNFLAG").INTEGER(new Integer[]{1});
        jtm.column("RECEIVETIME").TIMESTAMP();
        jtm.column("COMPLETETIME").TIMESTAMP();
        jtm.column("COMPLETEUSERID").NVARCHAR(Integer.valueOf(36));
        jtm.column("COMPLETEUSERTYPE").INTEGER(new Integer[]{1});
        jtm.column("APPROVALFLAG").NVARCHAR(Integer.valueOf(36));
        jtm.column("COMPLETEUSERCODE").NVARCHAR(Integer.valueOf(36));
        jtm.column("COMPLETEUSERNAME").NVARCHAR(Integer.valueOf(36));
        jtm.column("COMPLETEUSERUNITCODE").NVARCHAR(Integer.valueOf(36));
        jtm.column("COMPLETEUNITCODE").NVARCHAR(Integer.valueOf(36));
        jtm.column("COMPLETERESULT").NVARCHAR(Integer.valueOf(36));
        jtm.column("COMMENTCOLOR").INTEGER(new Integer[]{1});
        jtm.column("NODEMODULE").NVARCHAR(Integer.valueOf(36));
        jtm.column("SYSCODE").NVARCHAR(Integer.valueOf(100));
        jtm.column("ORDERNUM").LONG();
        jtm.column("REJECTSTATUS").INTEGER(new Integer[]{1});
        jtm.column("REJECTSKIPFLAG").INTEGER(new Integer[]{1});
        jtm.column("BIZID").NVARCHAR(Integer.valueOf(200));
        jtm.column("BIZCODE").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("BIZDEFINE").NVARCHAR(Integer.valueOf(100));
        jtm.column("BIZUNITCODE").NVARCHAR(Integer.valueOf(200));
        jtm.column("DELEGATEUSER").NVARCHAR(Integer.valueOf(36));
        jtm.column("HIDDENFLAG").INTEGER(new Integer[]{1});
        jtm.column("PGWNODEID").NVARCHAR(Integer.valueOf(64));
        jtm.column("PGWBRANCH").NVARCHAR(Integer.valueOf(64));
        jtm.column("NODEGROUP").NVARCHAR(Integer.valueOf(200));
        jtm.column("REVIEWMODE").NVARCHAR(Integer.valueOf(10));
        jtm.column("SUBPROCESSNODEID").NVARCHAR(Integer.valueOf(64));
        jtm.column("SUBPROCESSBRANCH").NVARCHAR(Integer.valueOf(64));
        jtm.column("COMPLETECOMMENT").CLOB();
        jtm.column("IGNOREFLAG").INTEGER(new Integer[]{1});
        jtm.index("IDX_WPN_BI").columns(new String[]{"BIZID"});
        jtm.index("IDX_WPN_BC").columns(new String[]{"BIZCODE"});
        jtm.index("IDX_WPN_PI").columns(new String[]{"PROCESSID"});
    }
}

