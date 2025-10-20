/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeException
 */
package com.jiuqi.bi.dataset.tree;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.tree.CodeTreeBuilder;
import com.jiuqi.bi.dataset.tree.DSTreeItem;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeException;
import java.util.List;

public final class ParentTreeBuilder
extends CodeTreeBuilder {
    private int parentIndex;

    public ParentTreeBuilder(BIDataSet dataSet, DSHierarchy hierarchy, String retField) {
        super(dataSet, hierarchy, retField);
    }

    @Override
    protected List<String> createDimList() throws BIDataSetException {
        List<String> dimList = super.createDimList();
        Column parentField = this.dataSet.getMetadata().find(this.hierarchy.getParentFieldName());
        if (parentField == null) {
            throw new BIDataSetException("\u5c42\u7ea7\u5f15\u7528\u7684\u7236\u4ee3\u7801\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + this.hierarchy.getParentFieldName());
        }
        this.parentIndex = dimList.size();
        dimList.add(this.hierarchy.getParentFieldName());
        return dimList;
    }

    @Override
    protected TreeBuilder createTreeBuilder(ObjectVistor visitor) throws BIDataSetException {
        try {
            return TreeBuilderFactory.createParentTreeBuilder((ObjectVistor)visitor);
        }
        catch (TreeException e) {
            throw new BIDataSetException("\u521b\u5efa\u7236\u4ee3\u7801\u6811\u5f62\u6784\u9020\u5668\u5931\u8d25\u3002", e);
        }
    }

    @Override
    protected DSTreeItem createTreeItem(BIDataRow row) {
        DSTreeItem item = super.createTreeItem(row);
        item.setParent(row.getString(this.parentIndex));
        return item;
    }
}

