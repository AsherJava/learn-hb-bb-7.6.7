/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.jdialect.Dialect
 */
package com.jiuqi.va.bizmeta.storage;

import com.jiuqi.va.bizmeta.storage.AMetaStorage;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.jdialect.Dialect;
import org.springframework.stereotype.Component;

@Component
public class BizViewTemplateStorage
extends AMetaStorage {
    private static final String TABLE_NAME = "BIZ_VIEW_TEMPLATE";

    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        Dialect.setGlobalAllowReservedWords((Boolean)true);
        JTableModel jTableModel = new JTableModel(tenantName, TABLE_NAME);
        jTableModel.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jTableModel.column("VER").LONG();
        jTableModel.column("NAME").NVARCHAR(Integer.valueOf(50));
        jTableModel.column("TITLE").NVARCHAR(Integer.valueOf(50));
        jTableModel.column("TEMPLATE").CLOB();
        jTableModel.column("BIZTYPE").NVARCHAR(Integer.valueOf(50));
        jTableModel.column("CREATETIME").comment("\u521b\u5efa\u65f6\u95f4").TIMESTAMP().notNull();
        jTableModel.index("IDX_BIZ_BVT_NAME").columns(new String[]{"NAME"}).unique();
        return jTableModel;
    }
}

