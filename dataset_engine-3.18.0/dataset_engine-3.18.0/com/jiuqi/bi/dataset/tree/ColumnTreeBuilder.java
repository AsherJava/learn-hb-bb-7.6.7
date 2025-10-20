/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.tree;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSTreeItem;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.tree.DSTreeBuilder;
import com.jiuqi.bi.dataset.tree.DSTreeItem;
import com.jiuqi.bi.util.StringUtils;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public final class ColumnTreeBuilder
extends DSTreeBuilder {
    private List<LevelInfo> levels;
    private Locale locale = Locale.getDefault();

    public ColumnTreeBuilder(BIDataSet dataSet, DSHierarchy hierarchy, String retField) {
        super(dataSet, hierarchy, retField);
        this.levels = new ArrayList<LevelInfo>();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public IDSTreeItem build() throws BIDataSetException {
        this.createLevels();
        return this.createTree();
    }

    private void createLevels() throws BIDataSetException {
        ArrayList<String> dimList = new ArrayList<String>();
        for (String fieldName : this.hierarchy.getLevels()) {
            LevelInfo level = this.createLevel(fieldName, dimList);
            this.levels.add(level);
        }
    }

    private LevelInfo createLevel(String fieldName, List<String> dimList) throws BIDataSetException {
        LevelInfo level = new LevelInfo();
        Column field = this.dataSet.getMetadata().find(fieldName);
        if (field == null) {
            throw new BIDataSetException("\u5217\u95f4\u5c42\u7ea7\u5f15\u7528\u7684\u5b57\u6bb5\u5728\u6570\u636e\u96c6\u4e2d\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        BIDataSetFieldInfo info = (BIDataSetFieldInfo)field.getInfo();
        if (info.getKeyField().equals(info.getNameField())) {
            level.codeIndex = dimList.size();
            level.titleIndex = dimList.size();
            dimList.add(info.getName());
        } else {
            level.codeIndex = dimList.size();
            dimList.add(info.getKeyField());
            level.titleIndex = dimList.size();
            dimList.add(info.getNameField());
        }
        ArrayList<String> measureList = new ArrayList<String>(1);
        if (!StringUtils.isEmpty((String)this.retField)) {
            Column measureField = this.dataSet.getMetadata().find(this.retField);
            if (measureField == null) {
                throw new BIDataSetException("\u6307\u5b9a\u7684\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + this.retField);
            }
            if (((BIDataSetFieldInfo)measureField.getInfo()).getFieldType() != FieldType.MEASURE) {
                throw new BIDataSetException("\u6307\u5b9a\u7684\u7edf\u8ba1\u5b57\u6bb5\u4e0d\u662f\u5ea6\u91cf\u7c7b\u578b\u7684\u5b57\u6bb5\uff1a" + this.retField);
            }
            measureList.add(this.retField);
        }
        level.data = this.dataSet.aggregate(dimList, measureList, false);
        if (!StringUtils.isEmpty((String)this.retField)) {
            level.valueIndex = level.data.getMetadata().find(this.retField).getIndex();
        }
        return level;
    }

    private IDSTreeItem createTree() throws BIDataSetException {
        DSTreeItem root = new DSTreeItem();
        Stack<FilterItem> filters = new Stack<FilterItem>();
        this.createTree(root, filters, 0, this.locale);
        return root;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void createTree(DSTreeItem parent, Stack<FilterItem> filters, int levelIndex, Locale locale) throws BIDataSetException {
        if (levelIndex >= this.levels.size()) {
            return;
        }
        LevelInfo level = this.levels.get(levelIndex);
        BIDataSet curDS = level.data.filter(filters);
        FilterItem curFilter = new FilterItem();
        Column codeField = (Column)level.data.getMetadata().getColumns().get(level.codeIndex);
        curFilter.setFieldName(codeField.getName());
        filters.push(curFilter);
        Column column = level.data.getMetadata().getColumn(level.titleIndex);
        Format format = DSUtils.generateFormat(level.data.getMetadata(), (BIDataSetFieldInfo)column.getInfo(), locale);
        try {
            for (BIDataRow row : curDS) {
                String codeValue = row.getString(level.codeIndex);
                if (StringUtils.isEmpty((String)codeValue)) continue;
                DSTreeItem item = new DSTreeItem();
                item.setCode(codeValue);
                Object title = row.getValue(level.titleIndex);
                if (title != null) {
                    item.setTitle(format == null ? title.toString() : format.format(title));
                }
                if (level.valueIndex >= 0) {
                    item.setValue(row.wasNull(level.valueIndex) ? null : Double.valueOf(row.getDouble(level.valueIndex)));
                }
                parent.getChildren().add(item);
                curFilter.setKeyValue(row.getValue(level.codeIndex));
                this.createTree(item, filters, levelIndex + 1, locale);
            }
        }
        finally {
            filters.pop();
        }
    }

    private static final class LevelInfo {
        public int codeIndex;
        public int titleIndex;
        public int valueIndex = -1;
        public BIDataSet data;

        private LevelInfo() {
        }
    }
}

