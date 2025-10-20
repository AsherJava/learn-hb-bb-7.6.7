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

public class BillRuleOptionItemStorage {
    private static final Logger log = LoggerFactory.getLogger(BillRuleOptionItemStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = BillRuleOptionItemStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "OPTION_BILLRULE_ITEM");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("PARENTID").VARCHAR(Integer.valueOf(36));
        jtm.column("VAL").comment("\u53c2\u6570\u6807\u8bc6").VARCHAR(Integer.valueOf(200));
        return jtm;
    }
}

