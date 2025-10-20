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

public class WorkflowDetectionStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String detectionTableName = "WORKFLOW_DETECTION";
        JTableModel detectiontable = new JTableModel(tenantName, detectionTableName);
        try {
            WorkflowDetectionStorage.getCreateDetectionTable(detectiontable);
            if (!jDialect.hasTable(detectiontable)) {
                jDialect.createTable(detectiontable);
            } else {
                jDialect.updateTable(detectiontable);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
        String detectionHistoryTableName = "WORKFLOW_DETECTION_HISTORY";
        JTableModel detectionHistoryTable = new JTableModel(tenantName, detectionHistoryTableName);
        try {
            WorkflowDetectionStorage.getCreateDetectionHistoryTable(detectionHistoryTable);
            if (!jDialect.hasTable(detectionHistoryTable)) {
                jDialect.createTable(detectionHistoryTable);
            } else {
                jDialect.updateTable(detectionHistoryTable);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
        String detectionDataTableName = "WORKFLOW_DETECTION_DATA";
        JTableModel detectionDataTable = new JTableModel(tenantName, detectionDataTableName);
        try {
            WorkflowDetectionStorage.getCreateDetectionDataTable(detectionDataTable);
            if (!jDialect.hasTable(detectionDataTable)) {
                jDialect.createTable(detectionDataTable);
            } else {
                jDialect.updateTable(detectionDataTable);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
        String detectionResultTableName = "WORKFLOW_DETECTION_RESULT";
        JTableModel detectionResultTable = new JTableModel(tenantName, detectionResultTableName);
        try {
            WorkflowDetectionStorage.getCreateDetectionResultTable(detectionResultTable);
            if (!jDialect.hasTable(detectionResultTable)) {
                jDialect.createTable(detectionResultTable);
            } else {
                jDialect.updateTable(detectionResultTable);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
    }

    private static void getCreateDetectionTable(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("WORKFLOWDEFINEKEY").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("BIZDEFINE").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("OPERATETIME").TIMESTAMP();
        jtm.column("OPERATOR").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.index("IDX_WFD_WORKFLOWDEFINEKEY").columns(new String[]{"WORKFLOWDEFINEKEY"});
        jtm.index("IDX_WFD_BIZDEFINE").columns(new String[]{"BIZDEFINE"});
    }

    private static void getCreateDetectionHistoryTable(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("WORKFLOWDEFINEKEY").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("BIZDEFINE").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("OPERATETIME").TIMESTAMP();
        jtm.column("OPERATOR").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.index("IDX_WFDH_WORKFLOWDEFINEKEY").columns(new String[]{"WORKFLOWDEFINEKEY"});
        jtm.index("IDX_WFDH_BIZDEFINE").columns(new String[]{"BIZDEFINE"});
    }

    public static void getCreateDetectionDataTable(JTableModel jtm) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("DETECTIONDATA").CLOB();
    }

    public static void getCreateDetectionResultTable(JTableModel jtm) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("DETECTIONRESULT").CLOB();
    }
}

