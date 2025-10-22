/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.batch.summary.service.ext.unittree.BSEntityRowImpl;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.Collections;
import java.util.List;

public class FSEntityRowProvider
implements IUnitTreeEntityRowProvider {
    private BSEntityRowImpl dimEntityRow;
    private IEntityTable entityTable;
    private FormSchemeDefine formSchemeDefine;

    public FSEntityRowProvider(FormSchemeDefine formSchemeDefine, IEntityTable entityTable) {
        this.entityTable = entityTable;
        this.formSchemeDefine = formSchemeDefine;
        this.dimEntityRow = new BSEntityRowImpl(formSchemeDefine.getKey(), formSchemeDefine.getFormSchemeCode(), formSchemeDefine.getTitle());
        List rootRows = entityTable.getRootRows();
        this.dimEntityRow.setHasChildren(rootRows.size() != 0);
        this.dimEntityRow.setLeaf(rootRows.size() == 0);
    }

    public List<IEntityRow> getAllRows() {
        List allRows = this.entityTable.getAllRows();
        allRows.add(0, this.dimEntityRow);
        return allRows;
    }

    public List<IEntityRow> getRootRows() {
        return Collections.singletonList(this.dimEntityRow);
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        String rowKey = parent.getKey();
        if (this.isMainDimDefine(parent)) {
            return this.entityTable.getRootRows();
        }
        return this.entityTable.getChildRows(rowKey);
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        String rowKey = rowData.getKey();
        if (this.isMainDimDefine(rowData)) {
            return new String[]{this.dimEntityRow.getEntityKeyData()};
        }
        return (String[])JavaBeanUtils.concatArrays((Object[])new String[]{this.dimEntityRow.getEntityKeyData()}, (Object[][])new String[][]{this.entityTable.getParentsEntityKeyDataPath(rowKey)});
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        if (this.isMainDimDefine(rowData)) {
            return this.dimEntityRow;
        }
        return this.entityTable.findByEntityKey(rowData.getKey());
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        String rowKey = parent.getKey();
        if (this.isMainDimDefine(parent)) {
            return this.entityTable.getRootRows().size();
        }
        return this.entityTable.getDirectChildCount(rowKey);
    }

    private boolean isMainDimDefine(IBaseNodeData parent) {
        return this.formSchemeDefine.getKey().equals(parent.getKey());
    }
}

