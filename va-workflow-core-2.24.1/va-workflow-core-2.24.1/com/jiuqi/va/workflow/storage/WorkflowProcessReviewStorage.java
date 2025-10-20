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

public class WorkflowProcessReviewStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String processTableName = "WORKFLOW_PROCESS_REVIEW";
        JTableModel jtm = new JTableModel(tenantName, processTableName);
        try {
            WorkflowProcessReviewStorage.getCreateWorkflowBusinessRelation(jtm);
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

    private static void getCreateWorkflowBusinessRelation(JTableModel jtm) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("BIZTYPE").NVARCHAR(Integer.valueOf(36));
        jtm.column("BIZCODE").NVARCHAR(Integer.valueOf(200));
        jtm.column("USERNAME").NVARCHAR(Integer.valueOf(36));
        jtm.column("MODIFYTIME").DATE();
        jtm.column("REJECTPROCESSID").NVARCHAR(Integer.valueOf(64));
        jtm.column("REJECTNODEID").NVARCHAR(Integer.valueOf(64));
        jtm.column("REJECTEDNODEID").NVARCHAR(Integer.valueOf(64));
        jtm.column("REVIEWINFO").NVARCHAR(Integer.valueOf(500));
    }
}

