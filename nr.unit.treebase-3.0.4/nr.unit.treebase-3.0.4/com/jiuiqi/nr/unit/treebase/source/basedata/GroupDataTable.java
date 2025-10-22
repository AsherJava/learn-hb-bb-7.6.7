/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  com.jiuqi.nr.unit.treecommon.utils.UUID5Formatter
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeGroupField;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.ICommonEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeNodeCountPloy;
import com.jiuiqi.nr.unit.treebase.source.basedata.GRBreathExpandIterator;
import com.jiuiqi.nr.unit.treebase.source.basedata.GRBreathFirstIterator;
import com.jiuiqi.nr.unit.treebase.source.basedata.GroupDataRow;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGRIterator;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataTable;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupRowFilter;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import com.jiuqi.nr.unit.treecommon.utils.UUID5Formatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroupDataTable
implements IGroupDataTable {
    public static final String DIM_FIELD_CODE = "dim-field";
    public static final String DIM_ENTITY_ID = "dim-entity";
    private String firstDimRowKey;
    private List<IGroupDataRow> tree;
    private Map<String, GroupDataRow> dataMap;
    protected Map<String, Integer> allChildCountMap;
    protected Map<String, Integer> directChildCountMap;
    protected IUnitTreeEntityRowProvider dimRowProvider;
    protected ICommonEntityDataQuery entityRowQuery;
    protected UnitTreeNodeCountPloy nodeCountPloy;

    public GroupDataTable(IUnitTreeEntityRowProvider dimRowProvider, UnitTreeGroupField treeField, UnitTreeNodeCountPloy nodeCountPloy) {
        this.nodeCountPloy = nodeCountPloy;
        this.dimRowProvider = dimRowProvider;
        this.entityRowQuery = (ICommonEntityDataQuery)SpringBeanUtils.getBean(ICommonEntityDataQuery.class);
        this.initDataMap(treeField);
    }

    @Override
    public String[] getNodePath(IBaseNodeData rowData) {
        IGroupDataRow groupRow = this.findGroupRow(rowData);
        if (groupRow != null) {
            return groupRow.getParentsEntityKeyDataPath();
        }
        String[] nodePath = this.dimRowProvider.getNodePath(rowData);
        if (nodePath != null && nodePath.length > 0) {
            GroupDataRow row = this.findGroupRow(nodePath[0]);
            Object[] groupNodePath = row.getParentsEntityKeyDataPath();
            String[] path = (String[])JavaBeanUtils.concatArrays((Object[])groupNodePath, (Object[][])new String[][]{nodePath});
            return (String[])Arrays.stream(path).distinct().toArray(String[]::new);
        }
        return new String[0];
    }

    @Override
    public IGRIterator getBreadthIterator(IGroupRowFilter filter) {
        if (filter != null) {
            return new GRBreathExpandIterator(this.tree, filter);
        }
        return new GRBreathFirstIterator(this.tree);
    }

    @Override
    public IGroupDataRow findFirstDimRow() {
        return this.findGroupRow(this.firstDimRowKey);
    }

    @Override
    public IGroupDataRow findGroupRow(IBaseNodeData nodeData) {
        if (nodeData != null && StringUtils.isNotEmpty((String)nodeData.getKey())) {
            return this.findGroupRow(nodeData.getKey());
        }
        return null;
    }

    @Override
    public List<IGroupDataRow> getRootGroupRows() {
        return this.tree;
    }

    @Override
    public List<IGroupDataRow> getChildGroupRows(IBaseNodeData parent) {
        IGroupDataRow groupRow = this.findGroupRow(parent);
        if (groupRow != null) {
            return groupRow.getChildren();
        }
        return null;
    }

    @Override
    public int getAllChildCount(IBaseNodeData rowData) {
        if (!this.allChildCountMap.containsKey(rowData.getKey())) {
            int allChildCount = 0;
            List<IGroupDataRow> children = this.dataMap.get(rowData.getKey()).getChildren();
            GRBreathFirstIterator iterator = new GRBreathFirstIterator(children);
            block3: while (iterator.hasNext()) {
                IGroupDataRow next = iterator.next();
                if ("node@Group".equals(next.getRowType())) {
                    switch (this.nodeCountPloy) {
                        case COUNT_OF_LEAF: 
                        case COUNT_OF_LEAF_AND_NOT_CHA_E: {
                            if (!next.isLeaf()) continue block3;
                            ++allChildCount;
                            continue block3;
                        }
                    }
                    ++allChildCount;
                    continue;
                }
                if (!"node@Dim".equals(next.getRowType())) continue;
                BaseNodeDataImpl baseNodeData = new BaseNodeDataImpl();
                baseNodeData.setKey(next.getData().getEntityKeyData());
                baseNodeData.setCode(next.getData().getCode());
                baseNodeData.setTitle(next.getData().getTitle());
                allChildCount += this.dimRowProvider.getAllChildCount((IBaseNodeData)baseNodeData);
            }
            this.allChildCountMap.put(rowData.getKey(), allChildCount);
        }
        return this.allChildCountMap.get(rowData.getKey());
    }

    @Override
    public int getDirectChildCount(IBaseNodeData rowData) {
        if (!this.directChildCountMap.containsKey(rowData.getKey())) {
            this.directChildCountMap.put(rowData.getKey(), this.dataMap.get(rowData.getKey()).getChildren().size());
        }
        return this.directChildCountMap.get(rowData.getKey());
    }

    public GroupDataRow findGroupRow(String rowKey) {
        return this.dataMap.get(rowKey);
    }

    private void initDataMap(UnitTreeGroupField treeField) {
        this.dataMap = new HashMap<String, GroupDataRow>();
        List<IEntityRow> dimRootRows = this.dimRowProvider.getRootRows();
        List<GroupDataRow> childRows = this.madeGroupDataRow(new UnitTreeGroupField(DIM_ENTITY_ID, DIM_FIELD_CODE), dimRootRows);
        if (!childRows.isEmpty()) {
            this.firstDimRowKey = childRows.get(0).getEntityKeyData();
            for (UnitTreeGroupField parentField = treeField; parentField != null && childRows != null; parentField = parentField.getParentGroupField()) {
                childRows = this.buildGroupDataRows(parentField, childRows);
            }
        }
        this.initTree(this.dataMap, dimRootRows);
    }

    private void initTree(Map<String, GroupDataRow> dataMap, List<IEntityRow> rootRows) {
        this.tree = new ArrayList<IGroupDataRow>();
        HashSet rootRowKeys = new HashSet();
        this.allChildCountMap = new HashMap<String, Integer>();
        this.directChildCountMap = new HashMap<String, Integer>();
        GroupDataRow unGroupRow = GroupDataRow.getUnGroupRow();
        rootRows.forEach(row -> {
            GroupDataRow dataRow = this.findGroupRow(row.getEntityKeyData());
            String[] path = dataRow.getParentsEntityKeyDataPath();
            GroupDataRow rootRow = this.subTreeWithPath(dataMap, path);
            if (rootRow.isDimRootRow()) {
                rootRow.setParent(unGroupRow);
                unGroupRow.appendChild(rootRow);
            } else if (!rootRowKeys.contains(rootRow.getEntityKeyData())) {
                this.tree.add(rootRow);
                rootRowKeys.add(rootRow.getEntityKeyData());
            }
        });
        if (!unGroupRow.isLeaf()) {
            this.tree.add(unGroupRow);
            dataMap.put(unGroupRow.getEntityKeyData(), unGroupRow);
        }
    }

    private GroupDataRow subTreeWithPath(Map<String, GroupDataRow> dataMap, String[] path) {
        for (int idx = 0; idx < path.length; ++idx) {
            if (idx + 1 >= path.length) continue;
            dataMap.get(path[idx]).appendChild(dataMap.get(path[idx + 1]));
        }
        return dataMap.get(path[0]);
    }

    private List<GroupDataRow> buildGroupDataRows(UnitTreeGroupField parentField, List<GroupDataRow> childRows) {
        List<GroupDataRow> referRootRows;
        if (parentField.isOwnRefer()) {
            referRootRows = this.getOwnerParentRows(parentField, childRows);
        } else {
            List<GroupDataRow> parentRows = this.getReferParentRows(parentField, childRows);
            referRootRows = this.getReferRootRows(parentField, parentRows);
        }
        return referRootRows;
    }

    private List<GroupDataRow> getOwnerParentRows(UnitTreeGroupField parentField, List<GroupDataRow> childRows) {
        String ownFieldCode = parentField.getOwnFieldCode();
        childRows.forEach(row -> {
            String referValue = row.getAsString(ownFieldCode);
            if (StringUtils.isNotEmpty((String)referValue)) {
                String rowKey = UUID5Formatter.fromUTF8((String)referValue).toString();
                GroupDataRow parentDataRow = this.madeGroupDataRow(parentField, row.getData(), rowKey, referValue, referValue);
                row.setParent(parentDataRow);
            }
        });
        return null;
    }

    private List<GroupDataRow> getReferParentRows(UnitTreeGroupField parentField, List<GroupDataRow> childRows) {
        String referEntityId = parentField.getReferEntityId();
        String ownFieldCode = parentField.getOwnFieldCode();
        ArrayList<String> referParentRowKeys = new ArrayList<String>();
        childRows.forEach(row -> {
            String referRowKey = row.getAsString(ownFieldCode);
            if (StringUtils.isNotEmpty((String)referRowKey)) {
                referParentRowKeys.add(referRowKey);
                row.setParent(this.madeGroupDataRow(parentField, referRowKey));
            }
        });
        IEntityTable dataTable = this.entityRowQuery.makeIEntityTable(referEntityId, referParentRowKeys, AuthorityType.None);
        return this.madeGroupDataRow(parentField, dataTable.getAllRows());
    }

    private List<GroupDataRow> getReferRootRows(UnitTreeGroupField parentField, List<GroupDataRow> parentRows) {
        ArrayList<GroupDataRow> referRootRows = new ArrayList<GroupDataRow>();
        HashSet rootKeys = new HashSet();
        ArrayList<String> referPathKeys = new ArrayList<String>();
        parentRows.forEach(gpRow -> {
            String[] path = this.getEntityRowPath(gpRow.getData());
            rootKeys.add(path[0]);
            referPathKeys.addAll(Arrays.asList(path));
        });
        IEntityTable referDataTable = this.entityRowQuery.makeIEntityTable(parentField.getReferEntityId(), referPathKeys, AuthorityType.None);
        List referPathRows = referDataTable.getAllRows();
        for (IEntityRow row : referPathRows) {
            GroupDataRow dataRow = this.madeGroupDataRow(parentField, row);
            if (this.isParentNotNull(row)) {
                dataRow.setParent(this.madeGroupDataRow(parentField, row.getParentEntityKey()));
            }
            if (!rootKeys.contains(dataRow.getEntityKeyData())) continue;
            referRootRows.add(dataRow);
        }
        return referRootRows;
    }

    private String[] getEntityRowPath(IEntityRow row) {
        String[] stringArray;
        String currRowKey = row.getEntityKeyData();
        String[] path = row.getParentsEntityKeyDataPath();
        if (path != null && path.length > 0) {
            stringArray = path;
        } else {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = currRowKey;
        }
        path = stringArray;
        if (!Objects.equals(path[path.length - 1], currRowKey)) {
            path = Arrays.copyOf(path, path.length + 1);
            path[path.length - 1] = currRowKey;
        }
        return path;
    }

    private boolean isParentNotNull(IEntityRow row) {
        String parentEntityKey = row.getParentEntityKey();
        return StringUtils.isNotEmpty((String)parentEntityKey) && !parentEntityKey.equals("-");
    }

    private GroupDataRow madeGroupDataRow(UnitTreeGroupField treeField, String rowKey) {
        GroupDataRow dataRow = this.findGroupRow(rowKey);
        if (dataRow == null) {
            dataRow = new GroupDataRow(rowKey);
            this.dataMap.put(rowKey, dataRow);
        }
        dataRow.setReferEntityId(treeField.getReferEntityId());
        return dataRow;
    }

    private GroupDataRow madeGroupDataRow(UnitTreeGroupField treeField, IEntityRow row) {
        GroupDataRow dataRow = this.madeGroupDataRow(treeField, row.getEntityKeyData());
        dataRow.setCode(row.getCode());
        dataRow.setTitle(row.getTitle());
        dataRow.setData(row);
        return dataRow;
    }

    private GroupDataRow madeGroupDataRow(UnitTreeGroupField treeField, IEntityRow row, String rowKey, String code, String title) {
        GroupDataRow dataRow = this.madeGroupDataRow(treeField, rowKey);
        dataRow.setCode(code);
        dataRow.setTitle(title);
        dataRow.setData(row);
        return dataRow;
    }

    private List<GroupDataRow> madeGroupDataRow(UnitTreeGroupField treeField, List<IEntityRow> rows) {
        ArrayList<GroupDataRow> dataRows = new ArrayList<GroupDataRow>();
        if (rows != null && !rows.isEmpty()) {
            rows.forEach(r -> dataRows.add(this.madeGroupDataRow(treeField, (IEntityRow)r)));
        }
        return dataRows;
    }
}

