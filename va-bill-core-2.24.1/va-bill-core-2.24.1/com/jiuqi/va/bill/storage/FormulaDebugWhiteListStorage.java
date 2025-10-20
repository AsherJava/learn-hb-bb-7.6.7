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

public class FormulaDebugWhiteListStorage {
    private static final Logger log = LoggerFactory.getLogger(FormulaDebugWhiteListStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = FormulaDebugWhiteListStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "FORMULA_DEBUG_WHITELIST");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("VER").comment("\u7248\u672c").NUMERIC(new Integer[]{19, 2});
        jtm.column("FORMULANAME").comment("\u516c\u5f0f\u540d\u79f0").VARCHAR(Integer.valueOf(200));
        jtm.index("FORMULANAME_UNIQUE").columns(new String[]{"FORMULANAME"}).unique();
        return jtm;
    }
}

