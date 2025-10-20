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

public class WorkflowBusinessRelDraftStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String processTableName = "WORKFLOW_BUSINESS_REL_DRAFT";
        JTableModel jtm = new JTableModel(tenantName, processTableName);
        try {
            WorkflowBusinessRelDraftStorage.setColumn(jtm);
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

    private static void setColumn(JTableModel jtm) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("MODULENAME").NVARCHAR(Integer.valueOf(64)).comment("\u6a21\u5757\u540d");
        jtm.column("BUSINESSTYPE").NVARCHAR(Integer.valueOf(64)).comment("\u4e1a\u52a1\u7c7b\u578b");
        jtm.column("BUSINESSCODE").NVARCHAR(Integer.valueOf(64)).notNull().comment("\u4e1a\u52a1\u6807\u8bc6");
        jtm.column("WORKFLOWDEFINEKEY").NVARCHAR(Integer.valueOf(64)).notNull().comment("\u5de5\u4f5c\u6d41\u6807\u8bc6");
        jtm.column("WORKFLOWDEFINEVERSION").LONG().notNull().comment("\u5de5\u4f5c\u6d41\u7248\u672c");
        jtm.column("MODIFYTIME").TIMESTAMP().notNull().comment("\u4fee\u6539\u65f6\u95f4");
        jtm.column("RELVERSION").LONG().notNull().defaultValue("0").comment("\u7248\u672c");
        jtm.column("LOCKFLAG").INTEGER(new Integer[]{1}).defaultValue("0").comment("\u9501\u5b9a\u72b6\u6001\uff0c1\u9501\u5b9a\uff1b0\u6216null\u672a\u9501\u5b9a\u72b6\u6001");
        jtm.column("LOCKUSER").NVARCHAR(Integer.valueOf(50)).comment("\u9501\u5b9a\u7528\u6237\u6807\u8bc6");
        jtm.column("DESIGNSTATE").INTEGER(new Integer[]{1}).defaultValue("1").comment("\u8bbe\u8ba1\u72b6\u6001\uff0c0\u53d1\u5e03\uff0c1\u65b0\u589e\uff0c2\u4fee\u6539\uff0c3\u51b2\u7a81\uff0c4\u5220\u9664");
        jtm.index("IDX_WBRD_B_K_V").columns(new String[]{"BUSINESSCODE", "WORKFLOWDEFINEKEY", "WORKFLOWDEFINEVERSION"});
        jtm.index("IDX_WBRD_K_V").columns(new String[]{"WORKFLOWDEFINEKEY", "WORKFLOWDEFINEVERSION"});
    }
}

