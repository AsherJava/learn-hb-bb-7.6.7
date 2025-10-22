/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.impl.GroupDataValue;
import com.jiuqi.nr.datacrud.impl.RowData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GroupRowData
extends RowData
implements Comparable<GroupRowData> {
    private String groupKey = "";
    private final List<GroupDataValue> groupDataValues = new ArrayList<GroupDataValue>();
    private IRowData row;
    private final List<GroupRowData> children = new LinkedList<GroupRowData>();

    public List<GroupRowData> getChildren() {
        return this.children;
    }

    public void gatherValue(int index, AbstractData value) {
        GroupDataValue groupDataValue = this.groupDataValues.get(index);
        if (groupDataValue == null) {
            return;
        }
        groupDataValue.writeValue(value);
    }

    public List<GroupDataValue> getGroupDataValues() {
        return this.groupDataValues;
    }

    public GroupRowData gatherRow() {
        this.dataValues = new ArrayList<IDataValue>();
        for (GroupDataValue groupDataValue : this.groupDataValues) {
            this.dataValues.add(groupDataValue.gatherValue());
        }
        return this;
    }

    public IRowData getRow() {
        if (this.row == null) {
            return this;
        }
        return this.row;
    }

    public void setRow(IRowData row) {
        this.row = row;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    @Override
    public int compareTo(GroupRowData o) {
        return this.groupKey.compareTo(o.groupKey);
    }

    @Override
    public void setGroupingFlag(int groupingFlag) {
        if (this.row instanceof RowData) {
            ((RowData)this.row).setGroupingFlag(groupingFlag);
        }
    }

    public List<GroupRowData> traversal(boolean addRoot) {
        ArrayList<GroupRowData> result = new ArrayList<GroupRowData>();
        if (addRoot) {
            result.add(this);
        }
        this.traversalHelper(this.getChildren(), result);
        return result;
    }

    private void traversalHelper(List<GroupRowData> nodes, List<GroupRowData> result) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        nodes.sort(null);
        for (GroupRowData node : nodes) {
            result.add(node);
            this.traversalHelper(node.getChildren(), result);
        }
    }
}

