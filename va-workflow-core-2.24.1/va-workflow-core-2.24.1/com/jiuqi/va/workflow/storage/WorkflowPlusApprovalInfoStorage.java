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

public class WorkflowPlusApprovalInfoStorage {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowPlusApprovalInfoStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = WorkflowPlusApprovalInfoStorage.getCreateJTM(tenantName);
            if (jDialect.hasTable(jtm)) {
                jDialect.updateTable(jtm);
            } else {
                jDialect.createTable(jtm);
            }
        }
        catch (JTableException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "WORKFLOW_PLUSAPPROVAL_INFO");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("USERNAME").comment("\u52a0\u7b7e\u4eba").VARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("APPROVALUSER").comment("\u88ab\u52a0\u7b7e\u4eba").VARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("APPROVALUNITCODE").comment("\u88ab\u52a0\u7b7e\u4eba\u7ec4\u7ec7\u673a\u6784").VARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("PROCESSID").comment("\u6d41\u7a0b\u5b9e\u4f8b\u6807\u8bc6").VARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("NODEID").comment("\u8282\u70b9id").NVARCHAR(Integer.valueOf(64)).notNull();
        jtm.column("NODECODE").comment("\u8282\u70b9\u6807\u8bc6").VARCHAR(Integer.valueOf(64)).notNull();
        jtm.column("CREATETIME").comment("\u52a0\u7b7e\u65f6\u95f4").TIMESTAMP().notNull();
        jtm.column("APPROVALCOMMENT").comment("\u52a0\u7b7e\u8bf4\u660e").VARCHAR(Integer.valueOf(500));
        jtm.column("STAFFCODE").comment("\u804c\u5458objectcode").VARCHAR(Integer.valueOf(200));
        jtm.column("PLUSSIGNAPPROVALFLAG").comment("\u52a0\u7b7e\u4eba\u662f\u5426\u53c2\u4e0e\u5ba1\u6279").INTEGER(new Integer[]{1});
        jtm.index("IDX_WPI_P_N").columns(new String[]{"PROCESSID", "NODECODE"});
        return jtm;
    }
}

