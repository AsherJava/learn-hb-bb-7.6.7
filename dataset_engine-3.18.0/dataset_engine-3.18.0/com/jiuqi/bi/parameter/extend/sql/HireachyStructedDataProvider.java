/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeException
 *  com.jiuqi.bi.util.tree.TreeNode
 */
package com.jiuqi.bi.parameter.extend.sql;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.extend.sql.HireachyParentSonDataProvider;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeException;
import com.jiuqi.bi.util.tree.TreeNode;
import java.util.ArrayList;

class HireachyStructedDataProvider
extends HireachyParentSonDataProvider {
    HireachyStructedDataProvider() {
    }

    @Override
    TreeNode buildTree(MemoryDataSet<ParameterColumnInfo> allValues) throws TreeException {
        String structCode = this.dataSourceModel.getStructureCode();
        TreeBuilder treeBuilder = TreeBuilderFactory.createStructTreeBuilder((ObjectVistor)new StructTreeNodeValueVistor(0), (String)structCode, (boolean)true);
        int total = allValues.size();
        ArrayList<MemoryDataRow> values = new ArrayList<MemoryDataRow>(total);
        for (int i = 0; i < total; ++i) {
            values.add(new MemoryDataRow(allValues.getBuffer(i)));
        }
        return treeBuilder.build(values.iterator());
    }

    private class StructTreeNodeValueVistor
    implements ObjectVistor {
        private int keyColIdx;

        public StructTreeNodeValueVistor(int keyColIdx) {
            this.keyColIdx = keyColIdx;
        }

        public String getCode(Object arg0) {
            DataRow row = (DataRow)arg0;
            return row.getString(this.keyColIdx);
        }

        public String getParentCode(Object arg0) {
            throw new RuntimeException();
        }
    }
}

