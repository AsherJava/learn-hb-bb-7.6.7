/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeException
 *  com.jiuqi.bi.util.tree.TreeNode
 */
package com.jiuqi.bi.dataset.tree;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.IDSTreeItem;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.tree.DSTreeBuilder;
import com.jiuqi.bi.dataset.tree.DSTreeItem;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeException;
import com.jiuqi.bi.util.tree.TreeNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class CodeTreeBuilder
extends DSTreeBuilder {
    private int codeIndex;
    private int titleIndex;
    private int valueIndex;

    public CodeTreeBuilder(BIDataSet dataSet, DSHierarchy hierarchy, String retField) {
        super(dataSet, hierarchy, retField);
    }

    @Override
    public IDSTreeItem build() throws BIDataSetException {
        TreeNode root;
        BIDataSet aggrDS = this.aggregateDataSet();
        TreeBuilder builder = this.createTreeBuilder(new TreeItemVisitor());
        TreeItemIterator itr = new TreeItemIterator(aggrDS.iterator());
        try {
            root = builder.build((Iterator)itr);
        }
        catch (TreeException e) {
            throw new BIDataSetException("\u6784\u9020\u6811\u5f62\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return this.createTree(root);
    }

    protected List<String> createDimList() throws BIDataSetException {
        ArrayList<String> dimList = new ArrayList<String>();
        if (this.hierarchy.getLevels().size() != 1) {
            throw new BIDataSetException("\u5c42\u7ea7\u6a21\u578b\u5b9a\u4e49\u9519\u8bef\uff0c\u5f15\u7528\u7684\u5b57\u6bb5\u9519\u8bef\uff1a" + this.hierarchy);
        }
        String fieldName = this.hierarchy.getLevels().get(0);
        Column field = this.dataSet.getMetadata().find(fieldName);
        if (field == null) {
            throw new BIDataSetException("\u5c42\u7ea7\u5f15\u7528\u7684\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        BIDataSetFieldInfo fieldInfo = (BIDataSetFieldInfo)field.getInfo();
        if (fieldInfo.getKeyField().equalsIgnoreCase(fieldInfo.getNameField())) {
            dimList.add(fieldInfo.getName());
            this.codeIndex = 0;
            this.titleIndex = 0;
        } else {
            dimList.add(fieldInfo.getKeyField());
            dimList.add(fieldInfo.getNameField());
            this.codeIndex = 0;
            this.titleIndex = 1;
        }
        return dimList;
    }

    protected TreeBuilder createTreeBuilder(ObjectVistor visitor) throws BIDataSetException {
        try {
            return TreeBuilderFactory.createStructTreeBuilder((ObjectVistor)visitor, (String)this.hierarchy.getCodePattern(), (boolean)true);
        }
        catch (TreeException e) {
            throw new BIDataSetException("\u521b\u5efa\u7ed3\u6784\u7f16\u7801\u6811\u5f62\u6784\u9020\u5668\u5931\u8d25\u3002", e);
        }
    }

    protected DSTreeItem createTreeItem(BIDataRow row) {
        DSTreeItem item = new DSTreeItem();
        item.setCode(row.getString(this.codeIndex));
        item.setTitle(row.getString(this.titleIndex));
        if (this.valueIndex >= 0) {
            item.setValue(row.wasNull(this.valueIndex) ? null : Double.valueOf(row.getDouble(this.valueIndex)));
        }
        return item;
    }

    private BIDataSet aggregateDataSet() throws BIDataSetException {
        List<String> dimList = this.createDimList();
        List<String> measureList = this.createMeasureList(dimList);
        return this.dataSet.aggregate(dimList, measureList, false);
    }

    private List<String> createMeasureList(List<String> dimList) throws BIDataSetException {
        ArrayList<String> measureList = new ArrayList<String>();
        if (StringUtils.isEmpty((String)this.retField)) {
            this.valueIndex = -1;
            return measureList;
        }
        Column measureField = this.dataSet.getMetadata().find(this.retField);
        if (measureField == null) {
            throw new BIDataSetException("\u6307\u5b9a\u7684\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + this.retField);
        }
        if (((BIDataSetFieldInfo)measureField.getInfo()).getFieldType() != FieldType.MEASURE) {
            throw new BIDataSetException("\u6307\u5b9a\u7684\u7edf\u8ba1\u5b57\u6bb5\u4e0d\u662f\u5ea6\u91cf\u7c7b\u578b\u7684\u5b57\u6bb5\uff1a" + this.retField);
        }
        measureList.add(this.retField);
        this.valueIndex = dimList.size();
        return measureList;
    }

    private IDSTreeItem createTree(TreeNode root) {
        DSTreeItem rootItem = new DSTreeItem();
        root.setItem((Object)rootItem);
        Stack<TreeNode> nodes = new Stack<TreeNode>();
        nodes.push(root);
        while (!nodes.isEmpty()) {
            TreeNode curNode = (TreeNode)nodes.pop();
            DSTreeItem curItem = (DSTreeItem)curNode.getItem();
            for (int i = 0; i < curNode.size(); ++i) {
                TreeNode nextNode = curNode.get(i);
                curItem.getChildren().add((DSTreeItem)nextNode.getItem());
                nodes.push(nextNode);
            }
        }
        return rootItem;
    }

    private final class TreeItemIterator
    implements Iterator<IDSTreeItem> {
        private Iterator<BIDataRow> rowItr;

        public TreeItemIterator(Iterator<BIDataRow> rowItr) {
            this.rowItr = rowItr;
        }

        @Override
        public boolean hasNext() {
            return this.rowItr.hasNext();
        }

        @Override
        public IDSTreeItem next() {
            BIDataRow row = this.rowItr.next();
            return CodeTreeBuilder.this.createTreeItem(row);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class TreeItemVisitor
    implements ObjectVistor {
        private TreeItemVisitor() {
        }

        public String getParentCode(Object o) {
            return ((DSTreeItem)o).getParent();
        }

        public String getCode(Object o) {
            return ((DSTreeItem)o).getCode();
        }
    }
}

