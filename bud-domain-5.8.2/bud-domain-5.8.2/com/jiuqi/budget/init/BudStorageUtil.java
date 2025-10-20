/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.budget.init;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BudStorageUtil {
    private static final Logger logger = LoggerFactory.getLogger(BudStorageUtil.class);

    public static void syncTable(JTableModel jTableModel) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            if (!jDialect.hasTable(jTableModel)) {
                jDialect.createTable(jTableModel);
            } else {
                jDialect.updateTable(jTableModel);
            }
        }
        catch (Exception e) {
            logger.error("\u8868[{}]\u540c\u6b65\u5931\u8d25", (Object)jTableModel.getTableName());
            logger.error(e.getMessage(), e);
        }
    }
}

