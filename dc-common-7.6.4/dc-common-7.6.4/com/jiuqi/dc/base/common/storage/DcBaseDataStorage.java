/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.jdialect.model.ColumnModel
 */
package com.jiuqi.dc.base.common.storage;

import com.jiuqi.dc.base.common.storage.DcGeneralStorage;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.jdialect.model.ColumnModel;

public abstract class DcBaseDataStorage
extends DcGeneralStorage {
    @Override
    protected final JTableModel getJTableModel(String tenantName) {
        JTableModel jTableModel = super.getJTableModel(tenantName);
        jTableModel.column("CODE").NVARCHAR(Integer.valueOf(60)).setNullable(Boolean.valueOf(false));
        jTableModel.column("NAME").NVARCHAR(Integer.valueOf(100)).setNullable(Boolean.valueOf(false));
        jTableModel.column("SHORTNAME").NVARCHAR(Integer.valueOf(100));
        jTableModel.column("PARENTID").NVARCHAR(Integer.valueOf(36));
        jTableModel.column("PARENTCODE").NVARCHAR(Integer.valueOf(60));
        jTableModel.column("PARENTS").NVARCHAR(Integer.valueOf(400));
        ColumnModel stopFlag = jTableModel.column("STOPFLAG").INTEGER(new Integer[]{1});
        stopFlag.setNullable(Boolean.valueOf(false));
        stopFlag.setDefaultValue("0");
        jTableModel.column("ORDINAL").NUMERIC(new Integer[]{19, 6}).setNullable(Boolean.valueOf(false));
        jTableModel.column("CREATEUNITCODE").NVARCHAR(Integer.valueOf(60)).setNullable(Boolean.valueOf(false));
        jTableModel.column("CREATOR").NVARCHAR(Integer.valueOf(60)).setNullable(Boolean.valueOf(false));
        jTableModel.column("CREATETIME").TIMESTAMP().setNullable(Boolean.valueOf(false));
        jTableModel.column("MODIFYUSER").NVARCHAR(Integer.valueOf(60)).setNullable(Boolean.valueOf(false));
        jTableModel.column("MODIFYTIME").TIMESTAMP().setNullable(Boolean.valueOf(false));
        jTableModel.column("REMARK").NVARCHAR(Integer.valueOf(300));
        return jTableModel;
    }
}

