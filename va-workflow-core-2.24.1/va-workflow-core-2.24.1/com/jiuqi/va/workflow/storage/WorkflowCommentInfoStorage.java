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

public class WorkflowCommentInfoStorage {
    private static final Logger log = LoggerFactory.getLogger(WorkflowCommentInfoStorage.class);
    public static final String TABLENAME = "WORKFLOW_COMMENT";

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        JTableModel jtm = new JTableModel(tenantName, TABLENAME);
        try {
            WorkflowCommentInfoStorage.getCreateWorkflowComment(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            log.error("\u540c\u6b65{}\u8868\u7ed3\u6784\u5931\u8d25", (Object)TABLENAME, (Object)e);
        }
    }

    private static void getCreateWorkflowComment(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("USERNAME").NVARCHAR(Integer.valueOf(36));
        jtm.column("COMMONCOMMENT").CLOB();
        jtm.column("CREATETIME").TIMESTAMP();
        jtm.column("ORDERNUM").LONG();
        jtm.column("TOPFLAG").INTEGER(new Integer[]{1});
        jtm.index("IDX_WF_COMM_USERNAME").columns(new String[]{"USERNAME"});
    }
}

