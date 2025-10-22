/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.source.basedata.IGRIterator;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupRowFilter;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public interface IGroupDataTable {
    public String[] getNodePath(IBaseNodeData var1);

    public IGRIterator getBreadthIterator(IGroupRowFilter var1);

    public IGroupDataRow findFirstDimRow();

    public IGroupDataRow findGroupRow(IBaseNodeData var1);

    public List<IGroupDataRow> getRootGroupRows();

    public List<IGroupDataRow> getChildGroupRows(IBaseNodeData var1);

    public int getAllChildCount(IBaseNodeData var1);

    public int getDirectChildCount(IBaseNodeData var1);
}

