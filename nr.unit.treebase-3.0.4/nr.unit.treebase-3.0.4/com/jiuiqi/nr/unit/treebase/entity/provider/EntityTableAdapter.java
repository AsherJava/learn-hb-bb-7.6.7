/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuiqi.nr.unit.treebase.entity.provider;

import com.jiuiqi.nr.unit.treebase.entity.provider.IEntityTableAdapter;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.List;

public class EntityTableAdapter
implements IEntityTableAdapter {
    private final IEntityTable dataTable;

    public EntityTableAdapter(IEntityTable dataTable) {
        this.dataTable = dataTable;
    }

    @Override
    public int getDirectChildCount(String parentKey) {
        return this.dataTable.getDirectChildCount(parentKey);
    }

    @Override
    public int getAllChildCount(String parentKey) {
        return this.dataTable.getAllChildCount(parentKey);
    }

    @Override
    public String[] getNodePath(String entityRowKey) {
        IEntityRow row = this.dataTable.findByEntityKey(entityRowKey);
        if (row != null) {
            return (String[])JavaBeanUtils.appendElement((Object[])row.getParentsEntityKeyDataPath(), (Object[])new String[]{row.getEntityKeyData()});
        }
        return new String[0];
    }

    @Override
    public List<IEntityRow> getAllRows() {
        return this.dataTable.getAllRows();
    }

    @Override
    public List<IEntityRow> getRootRows() {
        return this.dataTable.getRootRows();
    }

    @Override
    public List<IEntityRow> getChildRows(String parentKey) {
        return this.dataTable.getChildRows(parentKey);
    }

    @Override
    public List<IEntityRow> getAllChildRows(String parentKey) {
        return this.dataTable.getAllChildRows(parentKey);
    }
}

