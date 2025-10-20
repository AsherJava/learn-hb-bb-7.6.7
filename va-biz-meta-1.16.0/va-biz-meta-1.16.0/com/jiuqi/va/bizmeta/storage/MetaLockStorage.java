/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.bizmeta.storage;

import com.jiuqi.va.bizmeta.storage.AMetaStorage;
import com.jiuqi.va.mapper.common.JTableModel;
import org.springframework.stereotype.Component;

@Component
public class MetaLockStorage
extends AMetaStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "META_LOCK");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("UNIQUECODE").VARCHAR(Integer.valueOf(100));
        jtm.column("LOCKUSER").VARCHAR(Integer.valueOf(100));
        jtm.column("LOCKTIME").TIMESTAMP();
        jtm.index("IDX_UNIQUECODE").columns(new String[]{"UNIQUECODE"}).unique();
        return jtm;
    }
}

