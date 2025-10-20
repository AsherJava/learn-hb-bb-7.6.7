/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.dc.base.common.storage;

import com.jiuqi.dc.base.common.storage.DcGeneralStorage;
import com.jiuqi.va.mapper.common.JTableModel;

public abstract class DcBusinessStorage
extends DcGeneralStorage {
    @Override
    protected final JTableModel getJTableModel(String tenantName) {
        JTableModel jTableModel = super.getJTableModel(tenantName);
        jTableModel.column("UNITCODE").NVARCHAR(Integer.valueOf(50)).setNullable(Boolean.valueOf(false));
        return jTableModel;
    }
}

