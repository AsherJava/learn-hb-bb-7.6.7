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
public class MetaDataAuthStorage
extends AMetaStorage {
    private static final String TABLE_NAME = "AUTH_META";

    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        Dialect.setGlobalAllowReservedWords((Boolean)true);
        JTableModel jTableModel = new JTableModel(tenantName, TABLE_NAME);
        jTableModel.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jTableModel.column("BIZTYPE").INTEGER(new Integer[]{1}).comment("\u5173\u8054\u7c7b\u578b");
        jTableModel.column("BIZNAME").NVARCHAR(Integer.valueOf(100)).comment("\u5173\u8054\u6807\u8bc6");
        jTableModel.column("AUTHTYPE").INTEGER(new Integer[]{1}).comment("\u6743\u9650\u7c7b\u578b");
        jTableModel.column("UNIQUECODE").NVARCHAR(Integer.valueOf(100)).comment("\u5143\u6570\u636e\u6807\u8bc6");
        jTableModel.column("METATYPE").NVARCHAR(Integer.valueOf(8)).comment("\u5143\u6570\u636e\u7c7b\u578b");
        jTableModel.column("GROUPFLAG").INTEGER(new Integer[]{1}).comment("\u6570\u636e\u7c7b\u522b");
        jTableModel.column("AUTHFLAG").INTEGER(new Integer[]{1}).comment("\u8bbf\u95ee\u6743\u9650");
        jTableModel.column("ATAUTHORIZE").INTEGER(new Integer[]{1}).comment("\u6388\u6743\u6743\u9650");
        return jTableModel;
    }
}

