/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.stat.tree;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.dataset.stat.tree.StatDataRecord;
import com.jiuqi.bi.dataset.stat.tree.StatTreeBuilder;
import com.jiuqi.bi.dataset.stat.tree.StatTreeNode;
import com.jiuqi.bi.dataset.stat.tree.TreeNodeMsValue;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TreeHierarchyStatProcessor {
    private BIDataSetImpl dataset;
    private BIDataSetImpl referDimDataset;
    private List<String> msFields;
    private List<Integer> srcShowDimIndexs;
    private Set<Integer> srcInDimIndexs;
    private List<Integer> srcMsIndexs;
    private int unitCodeColumnIndex;
    private int destCodeColumnIndex;
    private int refCodeIndex;
    private int refParentIndex;

    public TreeHierarchyStatProcessor(BIDataSetImpl dataset, BIDataSetImpl refDimDataset, List<String> msFields) {
        this.dataset = dataset;
        this.referDimDataset = refDimDataset;
        this.msFields = msFields;
    }

    public BIDataSet stat() throws BIDataSetException {
        long start = System.currentTimeMillis();
        this.initEnv();
        Metadata<BIDataSetFieldInfo> newMeta = this.createRsMetadata();
        StatTreeNode root = StatTreeBuilder.build(this.referDimDataset, this.refCodeIndex, this.refParentIndex);
        List<StatTreeNode> children = root.getChildren();
        for (StatTreeNode child : children) {
            this.setDataRecordOnNode(child);
        }
        Iterator<StatTreeNode> itor = root.iterator();
        MemoryDataSet newDs = new MemoryDataSet(BIDataSetFieldInfo.class, newMeta);
        String pColName = this.referDimDataset.getMetadata().getColumn(this.refParentIndex).getName();
        boolean containParentCol = false;
        if (newMeta.contains(pColName) && !this.dataset.getMetadata().contains(pColName)) {
            containParentCol = true;
        }
        while (itor.hasNext()) {
            StatTreeNode node = itor.next();
            List<StatDataRecord> records = node.getDataRecords();
            String code = node.getDataRow().getString(this.refCodeIndex);
            for (StatDataRecord record : records) {
                Object[] rowdata = record.toDatasetRow(node, containParentCol ? this.refParentIndex : -1, this.unitCodeColumnIndex);
                try {
                    rowdata[this.destCodeColumnIndex] = code;
                    newDs.add(rowdata);
                }
                catch (DataSetException dataSetException) {}
            }
        }
        TimeKeyBuilder.buildTimeKey((MemoryDataSet<BIDataSetFieldInfo>)newDs);
        BIDataSetImpl newDataset = new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)newDs);
        newDataset.setLogger(this.dataset.getLogger());
        newDataset.setParameterEnv(this.dataset.getEnhancedParameterEnv());
        long end = System.currentTimeMillis();
        this.dataset.getOrDefaultLogger().debug("\u6811\u5f62\u6c47\u603b\u6267\u884c\u5b8c\u6210\uff0c\u6d88\u8017\u603b\u65f6\u95f4\uff1a" + (double)(end - start) / 1000.0 + " s");
        return newDataset;
    }

    private List<StatDataRecord> setDataRecordOnNode(StatTreeNode node) throws BIDataSetException {
        List<StatDataRecord> records = node.getDataRecords();
        if (!records.isEmpty()) {
            return records;
        }
        BIDataRow refRow = node.getDataRow();
        String code = refRow.getString(this.refCodeIndex);
        if (node.isLeaf()) {
            int[] rows;
            for (int row : rows = this.dataset.find(new int[]{this.unitCodeColumnIndex}, new Object[]{code})) {
                Object[] rowdata = this.dataset.getRowData(row);
                List<TreeNodeMsValue> msValues = this.buildMsValue(row);
                ArrayList<Object> dimColValues = new ArrayList<Object>();
                for (int col : this.srcShowDimIndexs) {
                    dimColValues.add(rowdata[col]);
                }
                StatDataRecord record = new StatDataRecord(dimColValues, msValues);
                records.add(record);
            }
        } else {
            HashSet<String> keyset = new HashSet<String>();
            List<StatTreeNode> children = node.getChildren();
            for (StatTreeNode child : children) {
                List<StatDataRecord> childRecords = this.setDataRecordOnNode(child);
                for (StatDataRecord rd : childRecords) {
                    String key = this.buildStatDataRecordKey(rd);
                    if (keyset.contains(key)) continue;
                    records.add(rd.clone());
                    keyset.add(key);
                }
            }
        }
        return records;
    }

    private String buildStatDataRecordKey(StatDataRecord record) {
        int size = record.getDimColSize();
        if (size == 1 && this.destCodeColumnIndex == 0) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            if (i == this.destCodeColumnIndex) continue;
            b.append(record.getDimValue(i)).append("|");
        }
        return b.toString();
    }

    private void initEnv() throws BIDataSetException {
        this.srcShowDimIndexs = new ArrayList<Integer>();
        this.srcInDimIndexs = new HashSet<Integer>();
        Metadata<BIDataSetFieldInfo> srcMetadata = this.dataset.getMetadata();
        Metadata<BIDataSetFieldInfo> refMetadata = this.referDimDataset.getMetadata();
        List refColumns = refMetadata.getColumns();
        for (Object column : refColumns) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            if (info.getFieldType() == FieldType.GENERAL_DIM) {
                Column srcColumn = srcMetadata.find(column.getName());
                if (srcColumn == null && !StringUtils.isNotEmpty((String)info.getMessageAlias())) {
                    srcColumn = srcMetadata.find(info.getMessageAlias());
                }
                if (srcColumn == null) {
                    for (Column c : srcMetadata.getColumns()) {
                        String msg = ((BIDataSetFieldInfo)c.getInfo()).getMessageAlias();
                        if (!StringUtils.isNotEmpty((String)msg) || !msg.equals(column.getName()) && !msg.equals(info.getMessageAlias())) continue;
                        srcColumn = c;
                        break;
                    }
                }
                if (srcColumn == null) continue;
                this.srcInDimIndexs.add(srcColumn.getIndex());
                continue;
            }
            if (info.getFieldType() == FieldType.TIME_DIM) {
                throw new BIDataSetException("\u53c2\u7167\u7ef4\u5ea6\u4e2d\u51fa\u73b0\u65f6\u95f4\u5b57\u6bb5\uff1a" + column.getName());
            }
            if (info.getFieldType() != FieldType.MEASURE) continue;
            throw new BIDataSetException("\u53c2\u7167\u7ef4\u5ea6\u4e2d\u51fa\u73b0\u5ea6\u91cf\u5b57\u6bb5\uff1a" + column.getName());
        }
        if (this.srcInDimIndexs.isEmpty()) {
            throw new BIDataSetException("\u6570\u636e\u8868\u4e2d\u7684\u5217\u4e0e\u7ef4\u5ea6\u8868\u5217\u540d\u79f0\u4e0d\u5339\u914d");
        }
        DSHierarchy hier = this.getHierarchy(refMetadata);
        this.unitCodeColumnIndex = srcMetadata.indexOf(hier.getKeyFieldName());
        if (this.unitCodeColumnIndex == -1) {
            for (Column column : srcMetadata.getColumns()) {
                if (!hier.getKeyFieldName().equals(((BIDataSetFieldInfo)column.getInfo()).getMessageAlias())) continue;
                this.unitCodeColumnIndex = column.getIndex();
                break;
            }
        }
        this.refCodeIndex = refMetadata.indexOf(hier.getKeyFieldName());
        if (this.refCodeIndex == -1) {
            for (Column column : refMetadata.getColumns()) {
                if (!hier.getKeyFieldName().equals(((BIDataSetFieldInfo)column.getInfo()).getMessageAlias())) continue;
                this.refCodeIndex = column.getIndex();
                break;
            }
        }
        this.refParentIndex = refMetadata.indexOf(hier.getParentFieldName());
        for (Column column : srcMetadata) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            if (info.getFieldType() == null || !info.getFieldType().isDimField() || info.getName().equals("SYS_TIMEKEY") || info.getName().equals("SYS_ROWNUM")) continue;
            this.srcShowDimIndexs.add(column.getIndex());
            if (column.getIndex() != this.unitCodeColumnIndex) continue;
            this.destCodeColumnIndex = this.srcShowDimIndexs.size() - 1;
        }
        List<Column<BIDataSetFieldInfo>> msInfos = this.getMsFieldInfos();
        this.srcMsIndexs = new ArrayList<Integer>();
        for (int i = 0; i < msInfos.size(); ++i) {
            this.srcMsIndexs.add(msInfos.get(i).getIndex());
        }
    }

    private Metadata<BIDataSetFieldInfo> createRsMetadata() {
        Object info;
        Metadata<BIDataSetFieldInfo> srcMeta = this.dataset.getMetadata();
        Metadata newMeta = new Metadata();
        List srcColumns = srcMeta.getColumns();
        for (Column column : srcColumns) {
            info = (BIDataSetFieldInfo)column.getInfo();
            if (((BIDataSetFieldInfo)info).getName().equals("SYS_TIMEKEY") || ((BIDataSetFieldInfo)info).getName().equals("SYS_ROWNUM") || ((BIDataSetFieldInfo)info).getFieldType() == FieldType.MEASURE) continue;
            newMeta.addColumn(column.clone());
        }
        Column parentCol = null;
        List srcHiers = (List)srcMeta.getProperties().get("HIERARCHY");
        if (srcHiers != null && !srcHiers.isEmpty()) {
            for (DSHierarchy h : srcHiers) {
                if (h.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
                parentCol = srcMeta.find(h.getParentFieldName());
                break;
            }
        }
        if (parentCol == null) {
            parentCol = this.referDimDataset.getMetadata().getColumn(this.refParentIndex).clone();
            newMeta.addColumn(parentCol);
        }
        info = this.srcMsIndexs.iterator();
        while (info.hasNext()) {
            int col = info.next();
            Column column = srcMeta.getColumn(col);
            column = column.clone();
            newMeta.addColumn(column);
        }
        DSHierarchy hier = new DSHierarchy();
        hier.setType(DSHierarchyType.PARENT_HIERARCHY);
        hier.setName("HIER_" + parentCol.getName());
        hier.getLevels().add(newMeta.getColumn(this.destCodeColumnIndex).getName());
        hier.setParentFieldName(parentCol.getName());
        newMeta.getProperties().put("HIERARCHY", Arrays.asList(hier));
        Object v = srcMeta.getProperties().get("FiscalMonth");
        if (v != null) {
            newMeta.getProperties().put("FiscalMonth", v);
        }
        return newMeta;
    }

    private DSHierarchy getHierarchy(Metadata<BIDataSetFieldInfo> metadata) throws BIDataSetException {
        List hierarchies = (List)metadata.getProperties().get("HIERARCHY");
        if (hierarchies == null || hierarchies.isEmpty()) {
            throw new BIDataSetException("\u6570\u636e\u96c6\u5fc5\u987b\u662f\u4e00\u4e2a\u5305\u542b\u7236\u5b50\u5c42\u7ea7\u7684\u6570\u636e\u96c6");
        }
        DSHierarchy hier = null;
        for (DSHierarchy h : hierarchies) {
            if (h.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
            hier = h;
            break;
        }
        if (hier == null) {
            throw new BIDataSetException("\u53c2\u7167\u6570\u636e\u96c6\u4e0d\u5305\u542b\u7236\u5b50\u5c42\u6b21\u4fe1\u606f");
        }
        return hier;
    }

    private List<Column<BIDataSetFieldInfo>> getMsFieldInfos() {
        ArrayList<Column<BIDataSetFieldInfo>> msInfos = new ArrayList<Column<BIDataSetFieldInfo>>();
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        if (this.msFields == null || this.msFields.isEmpty()) {
            List columns = metadata.getColumns();
            for (Column column : columns) {
                BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
                if (info.getFieldType() != FieldType.MEASURE) continue;
                msInfos.add((Column<BIDataSetFieldInfo>)column);
            }
        } else {
            for (String fieldName : this.msFields) {
                Column column = metadata.find(fieldName);
                msInfos.add((Column<BIDataSetFieldInfo>)column);
            }
        }
        return msInfos;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private List<TreeNodeMsValue> buildMsValue(int msInRowNum) throws BIDataSetException {
        ArrayList<TreeNodeMsValue> list = new ArrayList<TreeNodeMsValue>();
        Object[] rowData = this.dataset.getRowData(msInRowNum);
        for (Integer col : this.srcMsIndexs) {
            void var7_7;
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)this.dataset.getMetadata().getColumn(col.intValue()).getInfo();
            Object var7_8 = null;
            int valType = info.getValType();
            AggregationType aggrType = info.getAggregation();
            if (aggrType == AggregationType.SUM) {
                if (valType == DataType.INTEGER.value()) {
                    TreeNodeMsValue.IntSumNodeValue intSumNodeValue = new TreeNodeMsValue.IntSumNodeValue();
                } else {
                    if (valType != DataType.DOUBLE.value()) throw new BIDataSetException("\u4e0d\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + valType);
                    TreeNodeMsValue.DoubleSumNodeValue doubleSumNodeValue = new TreeNodeMsValue.DoubleSumNodeValue();
                }
            } else if (aggrType == AggregationType.AVG) {
                if (valType == DataType.INTEGER.value()) {
                    TreeNodeMsValue.IntAvgNodeValue intAvgNodeValue = new TreeNodeMsValue.IntAvgNodeValue();
                } else {
                    if (valType != DataType.DOUBLE.value()) throw new BIDataSetException("\u4e0d\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + valType);
                    TreeNodeMsValue.DoubleAvgNodeValue doubleAvgNodeValue = new TreeNodeMsValue.DoubleAvgNodeValue();
                }
            } else if (aggrType == AggregationType.MAX) {
                TreeNodeMsValue.MaxNodeValue maxNodeValue = new TreeNodeMsValue.MaxNodeValue(valType);
            } else if (aggrType == AggregationType.MIN) {
                TreeNodeMsValue.MinNodeValue minNodeValue = new TreeNodeMsValue.MinNodeValue(valType);
            } else {
                if (aggrType != AggregationType.COUNT) throw new BIDataSetException("\u672a\u6307\u5b9a\u5b57\u6bb5\u6c47\u603b\u65b9\u5f0f\uff1a" + info.getName());
                TreeNodeMsValue.CountNodeValue countNodeValue = new TreeNodeMsValue.CountNodeValue();
            }
            var7_7.setDataRowValue(rowData[col]);
            list.add((TreeNodeMsValue)var7_7);
        }
        return list;
    }
}

