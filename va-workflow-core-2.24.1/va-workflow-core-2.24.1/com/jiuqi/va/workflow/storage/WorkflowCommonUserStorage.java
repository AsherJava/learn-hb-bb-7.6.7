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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowCommonUserStorage {
    private static final Logger log = LoggerFactory.getLogger(WorkflowCommonUserStorage.class);
    public static final String TABLE_NAME = "WORKFLOW_COMMON_USER";

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = new JTableModel(tenantName, TABLE_NAME);
            WorkflowCommonUserStorage.setColumn(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            log.error("\u540c\u6b65{}\u8868\u7ed3\u6784\u5931\u8d25", (Object)TABLE_NAME, (Object)e);
        }
    }

    private static void setColumn(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("USERID").NVARCHAR(Integer.valueOf(36)).comment("\u7528\u6237Id").notNull();
        jtm.column("WORKFLOWDEFINEKEY").NVARCHAR(Integer.valueOf(64)).comment("\u5de5\u4f5c\u552f\u4e00\u6807\u8bc6").notNull();
        jtm.column("NODECODE").NVARCHAR(Integer.valueOf(64)).comment("\u5de5\u4f5c\u6d41\u5ba1\u6279\u8282\u70b9\u6807\u8bc6").notNull();
        jtm.column("COMMONUSERID").NVARCHAR(Integer.valueOf(36)).comment("\u5e38\u7528\u5ba1\u6279\u4ebaId").notNull();
        jtm.column("CREATETIME").TIMESTAMP().comment("\u521b\u5efa\u65f6\u95f4").notNull();
        jtm.index("IDX_WCU_UWNC").columns(new String[]{"USERID", "WORKFLOWDEFINEKEY", "NODECODE", "COMMONUSERID"}).unique();
    }
}

