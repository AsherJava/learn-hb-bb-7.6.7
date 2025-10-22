/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.List;

public class SSEntityRowProvider
implements IUnitTreeEntityRowProvider {
    private IEntityTable entityTable;

    public SSEntityRowProvider(IEntityTable entityTable) {
        this.entityTable = entityTable;
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    public int getDirectChildCount(IBaseNodeData rowData) {
        return this.entityTable.getDirectChildCount(rowData.getKey());
    }

    public List<IEntityRow> getAllRows() {
        return this.entityTable.getAllRows();
    }

    public List<IEntityRow> getRootRows() {
        return this.entityTable.getRootRows();
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        return this.entityTable.getChildRows(parent.getKey());
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        String rowKey = rowData.getKey();
        Object[] path = this.entityTable.getParentsEntityKeyDataPath(rowKey);
        return (String[])JavaBeanUtils.concatArrays((Object[])path, (Object[][])new String[][]{{rowKey}});
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        return this.entityTable.findByEntityKey(rowData.getKey());
    }
}

