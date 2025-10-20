/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.dc.base.common.storage;

import com.jiuqi.dc.base.common.storage.DcBaseStorage;
import com.jiuqi.dc.base.common.storage.intf.SyncTableCtx;
import com.jiuqi.va.mapper.common.JTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class DcGeneralStorage
extends DcBaseStorage {
    @Override
    public List<JTableModel> getJTableModels(SyncTableCtx ctx) {
        ArrayList<JTableModel> jtms = new ArrayList<JTableModel>();
        JTableModel jTableModel = this.getJTableModel(ctx.getTenantName());
        this.getTableColumns(jTableModel);
        jtms.add(jTableModel);
        return jtms;
    }

    protected JTableModel getJTableModel(String tenantName) {
        String tableName = this.getTableName();
        return this.createBasicStorage(tenantName, tableName);
    }

    protected abstract String getTableName();

    protected abstract void getTableColumns(JTableModel var1);
}

