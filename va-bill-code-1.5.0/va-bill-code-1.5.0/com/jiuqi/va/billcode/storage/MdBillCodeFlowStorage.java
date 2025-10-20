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
public class MdBillCodeFlowStorage
extends AStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "MD_BILLCODE_FLOW");
        jtm.column("DIMENSIONS").VARCHAR(Integer.valueOf(100)).pkey();
        jtm.column("FLOWNUMBER").LONG();
        return jtm;
    }
}

