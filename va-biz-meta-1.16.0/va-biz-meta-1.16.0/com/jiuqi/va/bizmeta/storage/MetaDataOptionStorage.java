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
public class MetaDataOptionStorage
extends AMetaStorage {
    private static final String TABLE_NAME = "OPTION_META";

    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        Dialect.setGlobalAllowReservedWords((Boolean)true);
        JTableModel jTableModel = new JTableModel(tenantName, TABLE_NAME);
        jTableModel.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jTableModel.column("NAME").comment("\u53c2\u6570\u6807\u8bc6").VARCHAR(Integer.valueOf(100)).notNull();
        jTableModel.column("VAL").comment("\u53c2\u6570\u503c").NVARCHAR(Integer.valueOf(500)).notNull();
        jTableModel.column("MODIFYUSER").comment("\u4fee\u6539\u7528\u6237").VARCHAR(Integer.valueOf(50));
        jTableModel.column("MODIFYTIME").comment("\u4fee\u6539\u65f6\u95f4").TIMESTAMP();
        jTableModel.index("IDX_OPTION_META_NAME").columns(new String[]{"NAME"}).unique();
        return jTableModel;
    }
}

