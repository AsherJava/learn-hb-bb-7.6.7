/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.batch.summary.service.ext.unittree.BSEntityRowImpl;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.Collections;
import java.util.List;

public class MSEntityRowProvider
implements IUnitTreeEntityRowProvider {
    private SummaryScheme scheme;
    private IEntityTable entityTable;
    private BSEntityRowImpl schemeEntityRow;

    public MSEntityRowProvider(SummaryScheme scheme, IEntityTable entityTable) {
        this.scheme = scheme;
        this.entityTable = entityTable;
        this.schemeEntityRow = new BSEntityRowImpl(scheme.getKey(), scheme.getCode(), scheme.getTitle());
        List rootRows = entityTable.getRootRows();
        this.schemeEntityRow.setHasChildren(rootRows.size() != 0);
        this.schemeEntityRow.setLeaf(rootRows.size() == 0);
    }

    public List<IEntityRow> getAllRows() {
        List allRows = this.entityTable.getAllRows();
        allRows.add(0, this.schemeEntityRow);
        return allRows;
    }

    public List<IEntityRow> getRootRows() {
        return Collections.singletonList(this.schemeEntityRow);
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        String rowKey = parent.getKey();
        if (this.isSchemeNode(parent)) {
            return this.entityTable.getRootRows();
        }
        return this.entityTable.getChildRows(rowKey);
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        String rowKey = rowData.getKey();
        if (this.isSchemeNode(rowData)) {
            return new String[]{this.schemeEntityRow.getEntityKeyData()};
        }
        return (String[])JavaBeanUtils.concatArrays((Object[])new String[]{this.schemeEntityRow.getEntityKeyData()}, (Object[][])new String[][]{this.entityTable.getParentsEntityKeyDataPath(rowKey)});
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        if (this.isSchemeNode(rowData)) {
            return this.schemeEntityRow;
        }
        return this.entityTable.findByEntityKey(rowData.getKey());
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        String rowKey = parent.getKey();
        if (this.isSchemeNode(parent)) {
            return this.entityTable.getRootRows().size();
        }
        return this.entityTable.getDirectChildCount(rowKey);
    }

    private boolean isSchemeNode(IBaseNodeData rowData) {
        String rowKey = rowData.getKey();
        return this.scheme.getKey().equals(rowKey);
    }
}

