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
public class BizResGroupStorage
extends AMetaStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jTableModel = new JTableModel(tenantName, "BIZ_RES_GROUP");
        jTableModel.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jTableModel.column("VER").LONG();
        jTableModel.column("NAME").NVARCHAR(Integer.valueOf(60));
        jTableModel.column("TITLE").NVARCHAR(Integer.valueOf(60));
        jTableModel.column("PARENTNAME").NVARCHAR(Integer.valueOf(60));
        jTableModel.index("IDX_BIZ_BRG_NAME").columns(new String[]{"NAME"}).unique();
        return jTableModel;
    }
}

