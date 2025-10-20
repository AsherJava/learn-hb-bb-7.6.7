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
public class BizResDataStorage
extends AMetaStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jTableModel = new JTableModel(tenantName, "BIZ_RES_DATA");
        jTableModel.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jTableModel.column("RESFILE").BLOB();
        return jTableModel;
    }
}

