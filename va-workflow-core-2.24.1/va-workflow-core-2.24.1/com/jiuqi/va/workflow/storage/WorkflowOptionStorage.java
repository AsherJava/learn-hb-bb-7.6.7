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

public class WorkflowOptionStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = WorkflowOptionStorage.getCreateJTM(tenantName);
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

    public static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "OPTION_WORKFLOW");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").comment("\u53c2\u6570\u6807\u8bc6").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("VAL").comment("\u53c2\u6570\u503c").NVARCHAR(Integer.valueOf(1000)).notNull();
        jtm.column("MODIFYUSER").comment("\u4fee\u6539\u7528\u6237").VARCHAR(Integer.valueOf(50));
        jtm.column("MODIFYTIME").comment("\u4fee\u6539\u65f6\u95f4").TIMESTAMP();
        jtm.index("IDX_OW_NAME").columns(new String[]{"NAME"}).unique();
        return jtm;
    }
}

