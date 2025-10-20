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
public class MetaDataVersionStorage
extends AMetaStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "META_VERSION");
        jtm.column("VERSIONNO").LONG().pkey();
        jtm.column("USERNAME").VARCHAR(Integer.valueOf(50));
        jtm.column("CREATETIME").TIMESTAMP();
        jtm.column("INFO").NVARCHAR(Integer.valueOf(500));
        return jtm;
    }
}

