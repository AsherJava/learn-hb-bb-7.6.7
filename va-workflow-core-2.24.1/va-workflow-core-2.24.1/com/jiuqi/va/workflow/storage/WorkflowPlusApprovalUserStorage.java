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

public class WorkflowPlusApprovalUserStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = WorkflowPlusApprovalUserStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "WORKFLOW_PLUSAPPROVAL_USER");
        jtm.column("USERNAME").comment("\u7528\u6237").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("COMMONUSER").comment("\u5e38\u7528\u52a0\u7b7e\u4eba").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("UNITCODE").comment("\u52a0\u7b7e\u4eba\u7ec4\u7ec7\u7ed3\u6784").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("CREATETIME").comment("\u521b\u5efa\u65f6\u95f4").TIMESTAMP().notNull();
        jtm.column("STAFFCODE").comment("\u804c\u5458objectcode").VARCHAR(Integer.valueOf(200));
        return jtm;
    }
}

