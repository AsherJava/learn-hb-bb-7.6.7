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
public class BizResInfoStorage
extends AMetaStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jTableModel = new JTableModel(tenantName, "BIZ_RES_INFO");
        jTableModel.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jTableModel.column("VER").LONG();
        jTableModel.column("GROUPNAME").NVARCHAR(Integer.valueOf(60));
        jTableModel.column("RESNAME").NVARCHAR(Integer.valueOf(60));
        jTableModel.column("FILESIZE").NUMERIC(new Integer[]{10, 2});
        jTableModel.column("UPLOADTIME").TIMESTAMP();
        jTableModel.column("ETAG").NVARCHAR(Integer.valueOf(60));
        return jTableModel;
    }
}

