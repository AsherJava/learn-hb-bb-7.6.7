/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.tree;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.IDSTreeItem;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;

public abstract class DSTreeBuilder {
    protected final BIDataSet dataSet;
    protected final DSHierarchy hierarchy;
    protected final String retField;

    public DSTreeBuilder(BIDataSet dataSet, DSHierarchy hierarchy, String retField) {
        this.dataSet = dataSet;
        this.hierarchy = hierarchy;
        this.retField = retField;
    }

    public abstract IDSTreeItem build() throws BIDataSetException;
}

