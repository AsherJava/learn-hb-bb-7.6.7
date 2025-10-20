/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.bill.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillRuleOptionStorage {
    private static final Logger log = LoggerFactory.getLogger(BillRuleOptionStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = BillRuleOptionStorage.getCreateJTM(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "OPTION_BILLRULE");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").comment("\u53c2\u6570\u6807\u8bc6").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("VAL").comment("\u53c2\u6570\u503c").NVARCHAR(Integer.valueOf(500));
        jtm.column("MODIFYUSER").comment("\u4fee\u6539\u7528\u6237").VARCHAR(Integer.valueOf(50));
        jtm.column("MODIFYTIME").comment("\u4fee\u6539\u65f6\u95f4").TIMESTAMP();
        jtm.column("CONTRONFLAG").comment("\u7ba1\u63a7\u4e0b\u7ea7").INTEGER(new Integer[]{1});
        jtm.column("UNITCODE").comment("\u7ec4\u7ec7\u673a\u6784").VARCHAR(Integer.valueOf(100));
        return jtm;
    }
}

