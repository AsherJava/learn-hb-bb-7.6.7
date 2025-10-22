/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.dataentity.entity;

import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;

public interface IDataEntity {
    public DataEntityType type();

    public IDataEntityRow getAllRow();

    public IDataEntityRow findByCode(String var1);

    public IDataEntityRow findAllByCode(String var1);

    public IDataEntityRow getAllChildRows(String var1);

    public IDataEntityRow findByEntityKey(String var1);

    public IDataEntityRow getRootRows();

    public IDataEntityRow getChildRows(String var1);

    public IEntityTable getEntityTable();
}

