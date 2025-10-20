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

public class WorkflowBusinessSchemeStorage {
    private static final Logger log = LoggerFactory.getLogger(WorkflowBusinessSchemeStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String schemeTableName = "WORKFLOW_BUSINESS_SCHEME";
        JTableModel schemeJtm = new JTableModel(tenantName, schemeTableName);
        try {
            WorkflowBusinessSchemeStorage.getCreateWorkflowBusinessScheme(schemeJtm);
            if (!jDialect.hasTable(schemeJtm)) {
                jDialect.createTable(schemeJtm);
            } else {
                jDialect.updateTable(schemeJtm);
            }
        }
        catch (JTableException e) {
            log.error("\u540c\u6b65{}\u8868\u7ed3\u6784\u5931\u8d25", (Object)schemeTableName, (Object)e);
        }
        String schemeDataTableName = "WORKFLOW_BUSINESS_SCHEME_DATA";
        JTableModel schemeDataJtm = new JTableModel(tenantName, schemeDataTableName);
        try {
            WorkflowBusinessSchemeStorage.getCreateWorkflowBusinessSchemeData(schemeDataJtm);
            if (!jDialect.hasTable(schemeDataJtm)) {
                jDialect.createTable(schemeDataJtm);
            } else {
                jDialect.updateTable(schemeDataJtm);
            }
        }
        catch (JTableException e) {
            log.error("\u540c\u6b65{}\u8868\u7ed3\u6784\u5931\u8d25", (Object)schemeDataTableName, (Object)e);
        }
    }

    private static void getCreateWorkflowBusinessScheme(JTableModel jtm) {
        jtm.column("ID").comment("\u4e3b\u952e").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").comment("\u6807\u8bc6").NVARCHAR(Integer.valueOf(50));
        jtm.column("TITLE").comment("\u540d\u79f0").NVARCHAR(Integer.valueOf(150));
        jtm.column("SCHEMETYPE").comment("\u7c7b\u578b").NVARCHAR(Integer.valueOf(20));
        jtm.column("BUSINESSCODE").comment("\u4e1a\u52a1\u6807\u8bc6").NVARCHAR(Integer.valueOf(64));
        jtm.column("MODIFYTIME").comment("\u4fee\u6539\u65f6\u95f4").TIMESTAMP();
        jtm.column("MODIFYUSER").comment("\u4fee\u6539\u7528\u6237").NVARCHAR(Integer.valueOf(36));
        jtm.column("REMARK").comment("\u65b9\u6848\u8bf4\u660e").NVARCHAR(Integer.valueOf(200));
        jtm.index("IDX_WBR_N_S").columns(new String[]{"NAME", "SCHEMETYPE"});
    }

    private static void getCreateWorkflowBusinessSchemeData(JTableModel jtm) {
        jtm.column("ID").comment("\u4e3b\u952e").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("SCHEMEDATA").comment("\u65b9\u6848\u4fe1\u606f").CLOB();
    }
}

