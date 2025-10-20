/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.BudTenantConfig
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.budget.init;

import com.jiuqi.budget.common.domain.BudTenantConfig;
import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.va.mapper.common.JTableModel;

public class JTableModelFactory {
    private JTableModelFactory() {
        throw new BudgetException("Utility class");
    }

    public static JTableModel createDefaultTable(String tableName) {
        JTableModel jTableModel = new JTableModel(BudTenantConfig.getTenantName(), tableName);
        jTableModel.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jTableModel.column("CODE").VARCHAR(Integer.valueOf(30)).notNull();
        jTableModel.column("NAME").NVARCHAR(Integer.valueOf(60)).notNull();
        jTableModel.column("PARENTID").VARCHAR(Integer.valueOf(36));
        jTableModel.column("ORDERNUM").NUMERIC(new Integer[]{19, 6}).notNull();
        jTableModel.column("REMARK").VARCHAR(Integer.valueOf(200));
        return jTableModel;
    }

    public static void createDefaultIndex(JTableModel jTableModel) {
        jTableModel.index("IND_".concat(jTableModel.getTableName()).concat("_").concat("CODE")).columns(new String[]{"CODE"}).unique();
        jTableModel.index("IND_".concat(jTableModel.getTableName()).concat("_").concat("PARENTID")).columns(new String[]{"PARENTID"});
    }
}

