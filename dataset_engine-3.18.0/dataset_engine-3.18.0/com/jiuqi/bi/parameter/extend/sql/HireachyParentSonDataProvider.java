/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeException
 *  com.jiuqi.bi.util.tree.TreeNode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 */
package com.jiuqi.bi.parameter.extend.sql;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.ParameterUtils;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.sql.AbstractSQLDataSourceDataProvider;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeException;
import com.jiuqi.bi.util.tree.TreeNode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

class HireachyParentSonDataProvider
extends AbstractSQLDataSourceDataProvider {
    private TreeNode root;
    private String pkey = "none";

    HireachyParentSonDataProvider() {
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env, boolean isFirstLevel) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> dataset = this.buildEmptyDataset(true);
        this.loadDataTree(parameterModel, env, null);
        TreeNode[] children = this.root.getChildren();
        if (isFirstLevel) {
            for (TreeNode tn : children) {
                DataRow newRow = dataset.add();
                this.fillDataRowFromTreeNode(newRow, tn);
            }
        } else {
            LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
            for (TreeNode tn : children) {
                queue.add(tn);
            }
            while (!queue.isEmpty()) {
                TreeNode[] sub;
                TreeNode first = (TreeNode)queue.poll();
                DataRow newRow = dataset.add();
                this.fillDataRowFromTreeNode(newRow, first);
                for (TreeNode sn : sub = first.getChildren()) {
                    queue.add(sn);
                }
            }
        }
        return dataset;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> quickGetChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env, int maxRecordSize, boolean isFirstLevel) throws DataSourceException {
        TreeNode[] children;
        if (isFirstLevel) {
            return this.filterChoiceValues(parameterModel, env, true);
        }
        LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
        this.loadDataTree(parameterModel, env, null);
        for (TreeNode tn : children = this.root.getChildren()) {
            queue.add(tn);
        }
        MemoryDataSet<ParameterColumnInfo> dataset = this.buildEmptyDataset(true);
        int counter = 0;
        while (!queue.isEmpty()) {
            TreeNode[] sub;
            TreeNode first = (TreeNode)queue.poll();
            DataRow newRow = dataset.add();
            this.fillDataRowFromTreeNode(newRow, first);
            if (++counter > maxRecordSize) break;
            for (TreeNode sn : sub = first.getChildren()) {
                queue.add(sn);
            }
        }
        return dataset;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> getAllValues(String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        TreeNode[] children;
        TreeNode treeRoot;
        MemoryDataSet<ParameterColumnInfo> dataset = this.buildEmptyDataset(true);
        MemoryDataSet<ParameterColumnInfo> allValues = this.querySql(env, new HashMap<String, Object>(), filterExpr, -1);
        try {
            treeRoot = this.buildTree(allValues);
        }
        catch (TreeException e) {
            throw new DataSourceException(e);
        }
        for (TreeNode tn : children = treeRoot.getChildren()) {
            DataRow newRow = dataset.add();
            this.fillDataRowFromTreeNode(newRow, tn);
        }
        return dataset;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> queryFirstDefaultValue(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> dataset = this.buildEmptyDataset(true);
        this.loadDataTree(parameterModel, env, null);
        TreeNode[] children = this.root.getChildren();
        if (children.length > 0) {
            DataRow newRow = dataset.add();
            if (parameterModel.isOrderReverse()) {
                this.fillDataRowFromTreeNode(newRow, children[children.length - 1]);
            } else {
                this.fillDataRowFromTreeNode(newRow, children[0]);
            }
        }
        return dataset;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> getChildrenValue(ParameterEngineEnv env, String parent, String filter, boolean isAllSubLevel) throws DataSourceException {
        ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
        MemoryDataSet<ParameterColumnInfo> dataset = this.buildEmptyDataset(true);
        this.loadDataTree(parameterModel, env, filter);
        TreeNode parentNode = this.findTreeNodeByKey(this.root, parent, 0);
        if (parentNode == null) {
            return dataset;
        }
        TreeNode[] children = parentNode.getChildren();
        if (isAllSubLevel) {
            LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
            for (TreeNode treeNode : children) {
                queue.add(treeNode);
            }
            while (!queue.isEmpty()) {
                TreeNode[] sub;
                DataRow newRow = dataset.add();
                TreeNode first = (TreeNode)queue.poll();
                this.fillDataRowFromTreeNode(newRow, first);
                for (TreeNode sn : sub = first.getChildren()) {
                    queue.add(sn);
                }
            }
        } else {
            for (TreeNode tn : children) {
                DataRow dataRow = dataset.add();
                this.fillDataRowFromTreeNode(dataRow, tn);
            }
        }
        return dataset;
    }

    TreeNode buildTree(MemoryDataSet<ParameterColumnInfo> allValues) throws TreeException {
        TreeBuilder treeBuilder = TreeBuilderFactory.createParentTreeBuilder((ObjectVistor)new ParentTreeNodeValueVistor(0, 2));
        int total = allValues.size();
        ArrayList<MemoryDataRow> values = new ArrayList<MemoryDataRow>(total);
        for (int i = 0; i < total; ++i) {
            values.add(new MemoryDataRow(allValues.getBuffer(i)));
        }
        return treeBuilder.build(values.iterator());
    }

    private void fillDataRowFromTreeNode(DataRow row, TreeNode tn) {
        DataRow treeRow = (DataRow)tn.getItem();
        row.setString(0, treeRow.getString(0));
        row.setString(1, treeRow.getString(1));
        row.setString(2, treeRow.getString(2));
        row.setInt(3, tn.getChildren().length > 0 ? 0 : -1);
    }

    private void loadDataTree(ParameterModel parameterModel, ParameterEngineEnv env, String filter) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> allValues;
        Map<ParameterModel, Object> map;
        try {
            map = ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env);
        }
        catch (ParameterException e1) {
            throw new DataSourceException(e1);
        }
        String newKey = this.buildPkey(map);
        if (newKey.equals(this.pkey) && this.root != null) {
            return;
        }
        this.pkey = newKey;
        HashMap<String, Object> fieldFilters = new HashMap<String, Object>();
        List<ParameterDependMember> deps = parameterModel.getDepends();
        for (ParameterDependMember dep : deps) {
            ParameterModel pm = env.getParameterModel(dep.getParameterName());
            Object value = map.get(pm);
            String fieldName = dep.getDatasourceFieldName();
            if (!StringUtils.isNotEmpty((String)fieldName) || value == null) continue;
            if (value instanceof MemoryDataSet) {
                MemoryDataSet memory = (MemoryDataSet)value;
                ArrayList list = new ArrayList();
                memory.iterator().forEachRemaining(c -> list.add(c.getValue(0)));
                fieldFilters.put(fieldName, list);
                continue;
            }
            fieldFilters.put(fieldName, value);
        }
        DataSourceFilterMode filterMode = parameterModel.getDataSourceFilterMode();
        if (filterMode == DataSourceFilterMode.ALL || env.getQueryProperty("initAllValues") != null) {
            allValues = this.querySql(env, fieldFilters, filter, -1);
        } else {
            List<String> appointKeyValues = this.buildAppointFilterKeyValues(parameterModel.getDataSourceValues());
            if (appointKeyValues == null || appointKeyValues.isEmpty()) {
                allValues = this.buildEmptyDataset(this.isTreeHierarchy());
            } else {
                fieldFilters.put(this.getKeyColName(env), appointKeyValues);
                allValues = this.querySql(env, fieldFilters, filter, -1);
            }
        }
        try {
            this.root = this.buildTree(allValues);
        }
        catch (TreeException e) {
            throw new DataSourceException(e);
        }
    }

    private TreeNode findTreeNodeByKey(TreeNode root, String keyValue, int keyColIdx) {
        TreeNode[] children;
        LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
        for (TreeNode tn : children = root.getChildren()) {
            queue.add(tn);
        }
        while (!queue.isEmpty()) {
            TreeNode[] sub;
            TreeNode first = (TreeNode)queue.poll();
            DataRow row = (DataRow)first.getItem();
            if (keyValue.equals(row.getString(keyColIdx))) {
                return first;
            }
            for (TreeNode sn : sub = first.getChildren()) {
                queue.add(sn);
            }
        }
        return null;
    }

    private String buildPkey(Map<ParameterModel, Object> map) {
        StringBuilder buf = new StringBuilder();
        for (ParameterModel pm : map.keySet()) {
            buf.append(pm.getName()).append(":").append(map.get(pm));
            buf.append(";");
        }
        return buf.toString();
    }

    private class ParentTreeNodeValueVistor
    implements ObjectVistor {
        private int keyColIdx;
        private int parentColIdx;

        public ParentTreeNodeValueVistor(int keyColIdx, int parentColIdx) {
            this.keyColIdx = keyColIdx;
            this.parentColIdx = parentColIdx;
        }

        public String getCode(Object arg0) {
            DataRow row = (DataRow)arg0;
            return row.getString(this.keyColIdx);
        }

        public String getParentCode(Object arg0) {
            DataRow row = (DataRow)arg0;
            return row.getString(this.parentColIdx);
        }
    }
}

