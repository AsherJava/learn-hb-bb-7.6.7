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

public class WorkflowMetaRelationStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String processTableName = "WORKFLOW_META_RELATION";
        JTableModel jtm = new JTableModel(tenantName, processTableName);
        try {
            WorkflowMetaRelationStorage.getCreateWorkflowMetaRelation(jtm);
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

    private static void getCreateWorkflowMetaRelation(JTableModel jtm) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("WORKFLOWDEFINEKEY").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("WORKFLOWDEFINEVERSION").INTEGER(new Integer[]{11}).notNull();
        jtm.column("METAVERSION").LONG().notNull();
        jtm.index("IDX_WMR_K_WV").columns(new String[]{"WORKFLOWDEFINEKEY", "WORKFLOWDEFINEVERSION"});
        jtm.index("IDX_WMR_K_MV").columns(new String[]{"WORKFLOWDEFINEKEY", "METAVERSION"});
    }
}

