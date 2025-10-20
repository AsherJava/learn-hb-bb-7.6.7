/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.biz.storage;

import com.jiuqi.va.biz.storage.ABizStorage;
import com.jiuqi.va.mapper.common.JTableModel;
import org.springframework.stereotype.Component;

@Component
public class BizTempValue
extends ABizStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "BIZ_TEMP_VALUE");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("V").NVARCHAR(Integer.valueOf(200)).pkey();
        return jtm;
    }
}

