/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.billcode.storage;

import com.jiuqi.va.billcode.storage.AStorage;
import com.jiuqi.va.mapper.common.JTableModel;
import org.springframework.stereotype.Component;

@Component
public class MdBillCodeRuleStorage
extends AStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "MD_BILLCODE_RULE");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("UNIQUECODE").VARCHAR(Integer.valueOf(100));
        jtm.column("RULEDATA").CLOB();
        jtm.column("CONSTANT").VARCHAR(Integer.valueOf(20));
        jtm.column("GENERATEOPT").INTEGER(new Integer[]{10}).defaultValue("0");
        jtm.index("MBCR_UNIQUECODE").columns(new String[]{"UNIQUECODE"}).unique();
        return jtm;
    }
}

