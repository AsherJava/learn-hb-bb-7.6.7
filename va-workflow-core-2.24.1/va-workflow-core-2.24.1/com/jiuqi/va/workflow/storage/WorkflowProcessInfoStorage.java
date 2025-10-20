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

public class WorkflowProcessInfoStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String processTableName = "WORKFLOW_PROCESS";
        JTableModel process = new JTableModel(tenantName, processTableName);
        try {
            WorkflowProcessInfoStorage.getCreateprocess(process);
            if (!jDialect.hasTable(process)) {
                jDialect.createTable(process);
            } else {
                jDialect.updateTable(process);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
        String processHistoryTableName = "WORKFLOW_PROCESS_HISTORY";
        JTableModel processHistory = new JTableModel(tenantName, processHistoryTableName);
        try {
            WorkflowProcessInfoStorage.getCreateprocessHistory(processHistory);
            if (!jDialect.hasTable(processHistory)) {
                jDialect.createTable(processHistory);
            } else {
                jDialect.updateTable(processHistory);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
    }

    private static void getCreateprocess(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("DEFINEKEY").NVARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("DEFINEVERSION").LONG();
        jtm.column("BIZMODULE").NVARCHAR(Integer.valueOf(36));
        jtm.column("BIZCODE").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("BIZTYPE").NVARCHAR(Integer.valueOf(64)).notNull();
        jtm.column("STARTUSER").NVARCHAR(Integer.valueOf(36));
        jtm.column("STARTUNITCODE").NVARCHAR(Integer.valueOf(36));
        jtm.column("STARTTIME").TIMESTAMP();
        jtm.column("STATUS").INTEGER(new Integer[]{1}).notNull();
        jtm.index("IDX_P_BIZCODE").columns(new String[]{"BIZCODE"});
    }

    private static void getCreateprocessHistory(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("DEFINEKEY").NVARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("DEFINEVERSION").LONG();
        jtm.column("BIZMODULE").NVARCHAR(Integer.valueOf(36));
        jtm.column("BIZCODE").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("BIZTYPE").NVARCHAR(Integer.valueOf(64)).notNull();
        jtm.column("STARTUSER").NVARCHAR(Integer.valueOf(36));
        jtm.column("STARTUNITCODE").NVARCHAR(Integer.valueOf(36));
        jtm.column("STARTTIME").TIMESTAMP();
        jtm.column("ENDTIME").TIMESTAMP();
        jtm.column("ENDSTATUS").INTEGER(new Integer[]{1});
        jtm.column("ENDREASON").NVARCHAR(Integer.valueOf(500));
        jtm.index("IDX_PH_BIZCODE").columns(new String[]{"BIZCODE"});
    }
}

