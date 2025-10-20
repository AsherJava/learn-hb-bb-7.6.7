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

public class WorkflowDelegateInfoStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = WorkflowDelegateInfoStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "WORKFLOW_DELEGATE_INFO");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("DELEGATETITLE").comment("\u59d4\u6258\u6807\u9898").VARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("DELEGATEUSER").comment("\u59d4\u6258\u4eba").VARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("CREATER").comment("\u521b\u5efa\u4eba").VARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("CREATETIME").comment("\u521b\u5efa\u65f6\u95f4").TIMESTAMP().notNull();
        jtm.column("AGENTUSER").comment("\u4ee3\u7406\u4eba").CLOB().notNull();
        jtm.column("VALDATE").comment("\u751f\u6548\u65e5\u671f").TIMESTAMP().notNull();
        jtm.column("INVALDATE").comment("\u5931\u6548\u65e5\u671f").TIMESTAMP();
        jtm.column("DELECOMMENT").comment("\u59d4\u6258\u610f\u89c1").VARCHAR(Integer.valueOf(500));
        jtm.column("HISDELEFLAG").comment("\u5386\u53f2\u4ee3\u529e\u8f6c\u59d4\u6258\u6807\u8bc6").INTEGER(new Integer[]{1});
        jtm.column("DELECONDITION").comment("\u59d4\u6258\u6761\u4ef6").CLOB();
        jtm.column("ENABLEFLAG").comment("\u542f\u7528\u6807\u8bc6").INTEGER(new Integer[]{1});
        jtm.column("BIZTYPE").comment("\u4e1a\u52a1\u5927\u7c7b").NVARCHAR(Integer.valueOf(16));
        jtm.column("STAFFCODE").comment("\u804c\u5458\u7f16\u7801").NVARCHAR(Integer.valueOf(500));
        jtm.index("IDX_WDI_DELEGATEUSER").columns(new String[]{"DELEGATEUSER"});
        return jtm;
    }
}

