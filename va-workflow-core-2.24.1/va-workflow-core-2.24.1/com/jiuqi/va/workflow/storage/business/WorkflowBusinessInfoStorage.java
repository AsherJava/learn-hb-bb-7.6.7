/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.workflow.storage.business;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;

public class WorkflowBusinessInfoStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String processTableName = "WORKFLOW_BUSINESS_RELATION";
        JTableModel jtm = new JTableModel(tenantName, processTableName);
        try {
            WorkflowBusinessInfoStorage.getCreateWorkflowBusinessRelation(jtm);
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
        jtm.column("MODULENAME").NVARCHAR(Integer.valueOf(64));
        jtm.column("BUSINESSTYPE").NVARCHAR(Integer.valueOf(64));
        jtm.column("BUSINESSCODE").NVARCHAR(Integer.valueOf(64)).notNull();
        jtm.column("WORKFLOWDEFINEKEY").NVARCHAR(Integer.valueOf(64)).notNull();
        jtm.column("WORKFLOWDEFINEVERSION").LONG().notNull();
        jtm.column("CONFIG").CLOB();
        jtm.column("STOPFLAG").INTEGER(new Integer[]{1});
        jtm.column("RELVERSION").LONG().notNull().defaultValue("0").comment("\u7248\u672c");
        jtm.column("LOCKFLAG").INTEGER(new Integer[]{1}).defaultValue("0").comment("\u9501\u5b9a\u72b6\u6001\uff0c1\u9501\u5b9a\uff1b0\u6216null\u672a\u9501\u5b9a\u72b6\u6001");
        jtm.column("LOCKUSER").NVARCHAR(Integer.valueOf(50)).comment("\u9501\u5b9a\u7528\u6237\u6807\u8bc6");
        jtm.index("IDX_WBR_B_K_V").columns(new String[]{"BUSINESSCODE", "WORKFLOWDEFINEKEY", "WORKFLOWDEFINEVERSION"});
        jtm.index("IDX_WBR_K_V").columns(new String[]{"WORKFLOWDEFINEKEY", "WORKFLOWDEFINEVERSION"});
    }
}

