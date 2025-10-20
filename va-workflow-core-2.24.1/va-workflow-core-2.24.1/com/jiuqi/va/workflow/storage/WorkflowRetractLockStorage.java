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

public class WorkflowRetractLockStorage {
    private static final Logger log = LoggerFactory.getLogger(WorkflowRetractLockStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String tableName = "WORKFLOW_RETRACT_LOCK";
        JTableModel jtm = new JTableModel(tenantName, tableName);
        try {
            WorkflowRetractLockStorage.getCreateWorkflowRetractLock(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            log.error("\u540c\u6b65{}\u8868\u7ed3\u6784\u5931\u8d25", (Object)tableName, (Object)e);
        }
    }

    private static void getCreateWorkflowRetractLock(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("BIZCODE").NVARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("LOCKNODE").NVARCHAR(Integer.valueOf(64)).notNull();
        jtm.column("SUBPROCESSBRANCH").NVARCHAR(Integer.valueOf(64));
        jtm.column("LOCKCOUNT").INTEGER(new Integer[]{1});
        jtm.column("USERID").NVARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("LOCKTIME").TIMESTAMP().notNull();
        jtm.column("LOCKDESCRIBE").NVARCHAR(Integer.valueOf(200));
        jtm.index("IDX_WRL_BIZCODE").columns(new String[]{"BIZCODE"});
    }
}

