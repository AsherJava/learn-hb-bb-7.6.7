/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 */
package com.jiuqi.nr.zbquery.engine.executor;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.zbquery.engine.executor.DataBuffer;
import com.jiuqi.nr.zbquery.engine.executor.QueryModelBuilder;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.DisplayContent;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.util.QueryModelFinder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class DataProcessor {
    private QueryModelBuilder modelBuilder;
    private MemoryDataSet<ColumnInfo> data;
    private List<IProcessor> processors;
    private Set<String> childDimParentAtrrs;

    public DataProcessor(QueryModelBuilder modelBuilder, MemoryDataSet<ColumnInfo> data) {
        this.modelBuilder = modelBuilder;
        this.data = data;
    }

    public void process() throws Exception {
        if (this.data == null || this.data.size() == 0) {
            return;
        }
        this.processors = new ArrayList<IProcessor>();
        this.childDimParentAtrrs = new HashSet<String>();
        this.generateChildDimProcessor();
        this.generateRelatedDimProcessor();
        this.generateBizkeyOrderProcessor();
        if (this.processors.size() > 0) {
            for (int i = 0; i < this.data.size(); ++i) {
                DataRow row = this.data.get(i);
                for (IProcessor processor : this.processors) {
                    processor.process(row);
                }
            }
        }
    }

    private void generateRelatedDimProcessor() throws Exception {
        for (int i = 0; i < this.data.getMetadata().size(); ++i) {
            QueryField queryField;
            QueryObject queryObject;
            Column column = this.data.getMetadata().getColumn(i);
            String fullName = this.modelBuilder.getAliasFullNameMapper().get(column.getName());
            if (fullName == null || this.childDimParentAtrrs.contains(fullName) || (queryObject = this.modelBuilder.getModelFinder().getQueryObject(fullName)) == null || !(queryObject instanceof QueryField) || !queryObject.isVisible() || !StringUtils.isNotEmpty((String)(queryField = (QueryField)queryObject).getRelatedDimension())) continue;
            this.processors.add(new RelatedDimProcessor(i, queryField.getRelatedDimension(), "PARENTCODE".equalsIgnoreCase(queryField.getName()), queryField.getDisplayContent(), this.modelBuilder.getConditionTimekey(), this.getPeriodView()));
        }
    }

    private void generateChildDimProcessor() throws Exception {
        Map<String, List<String>> childDimFields = this.modelBuilder.getChildDimFields();
        QueryModelFinder modelFinder = this.modelBuilder.getModelFinder();
        for (String dimFullName : childDimFields.keySet()) {
            List<String> fieldFullNames = childDimFields.get(dimFullName);
            if (fieldFullNames == null || fieldFullNames.size() <= 0) continue;
            String[] fieldNames = new String[fieldFullNames.size()];
            int[] fieldIndices = new int[fieldFullNames.size()];
            for (int i = 0; i < fieldFullNames.size(); ++i) {
                String fieldFullName = fieldFullNames.get(i);
                QueryObject queryFiled = modelFinder.getQueryObject(fieldFullName);
                fieldNames[i] = queryFiled.getName();
                String fieldAlias = this.modelBuilder.getFullNameAliasMapper().get(fieldFullName);
                fieldIndices[i] = this.data.getMetadata().indexOf(fieldAlias);
            }
            QueryDimension childDim = modelFinder.getQueryDimension(dimFullName);
            FieldGroup childDimGroup = (FieldGroup)modelFinder.getQueryObject(dimFullName);
            DimensionAttributeField dimAtrr = childDimGroup.getDimAttribute();
            String dimAtrrAlias = this.modelBuilder.getFullNameAliasMapper().get(dimAtrr.getFullName());
            this.processors.add(new ChildDimProcessor(childDim.getName(), this.data.getMetadata().indexOf(dimAtrrAlias), fieldIndices, fieldNames, this.modelBuilder.getConditionTimekey(), this.getPeriodView()));
            this.childDimParentAtrrs.add(dimAtrr.getFullName());
        }
    }

    private void generateBizkeyOrderProcessor() throws Exception {
        if (this.modelBuilder.getModelFinder().getQueryModel().getOption().isQueryDetailRecord() && StringUtils.isNotEmpty((String)this.modelBuilder.getBizkeyOrder())) {
            int index = this.data.getMetadata().indexOf(this.modelBuilder.getFullNameAliasMapper().get(this.modelBuilder.getBizkeyOrder()));
            BizkeyOrderProcessor processor = new BizkeyOrderProcessor(index);
            this.processors.add(processor);
        }
    }

    private String getPeriodView() {
        QueryDimension periodDim = this.modelBuilder.getModelFinder().getPeriodDimension();
        if (periodDim != null) {
            return BqlTimeDimUtils.getPeriodEntityId((String)periodDim.getName());
        }
        return null;
    }

    private static interface IProcessor {
        public void process(DataRow var1) throws Exception;
    }

    private class BizkeyOrderProcessor
    implements IProcessor {
        private int index;
        private int count = 0;

        public BizkeyOrderProcessor(int index) {
            this.index = index;
        }

        @Override
        public void process(DataRow row) throws Exception {
            if (row.wasNull(this.index)) {
                row.setString(this.index, "bizkeyorder" + this.count++);
            }
        }
    }

    private class ChildDimProcessor
    implements IProcessor {
        private int keyIndex;
        private int[] fieldIndices;
        private DataBuffer dataBuffer;

        public ChildDimProcessor(String dimName, int keyIndex, int[] fieldIndices, String[] fieldNames, String period, String periodView) throws Exception {
            this.keyIndex = keyIndex;
            this.fieldIndices = fieldIndices;
            this.dataBuffer = new DataBuffer(dimName, fieldNames, period, periodView);
        }

        @Override
        public void process(DataRow row) throws Exception {
            String key = row.getString(this.keyIndex);
            String[] values = this.dataBuffer.getValues(key);
            if (values != null) {
                for (int i = 0; i < this.fieldIndices.length; ++i) {
                    row.setString(this.fieldIndices[i], values[i]);
                }
            }
        }
    }

    private class RelatedDimProcessor
    implements IProcessor {
        private int keyIndex;
        private DataBuffer dataBuffer;
        private DisplayContent displayContent;
        private boolean isParentField;

        public RelatedDimProcessor(int keyIndex, String dimName, boolean isParentField, DisplayContent displayContent, String period, String periodView) throws Exception {
            this.keyIndex = keyIndex;
            this.dataBuffer = new DataBuffer(dimName, new String[]{"CODE", "NAME"}, period, periodView);
            this.displayContent = displayContent;
            this.isParentField = isParentField;
        }

        @Override
        public void process(DataRow row) throws Exception {
            String key = row.getString(this.keyIndex);
            if (this.isParentField && this.displayContent == DisplayContent.CODE) {
                return;
            }
            String[] values = this.dataBuffer.getValues(key);
            if (values != null) {
                String content;
                String string = content = this.isParentField ? key : values[0];
                if (this.displayContent == DisplayContent.TITLE) {
                    content = values[1];
                } else if (this.displayContent == DisplayContent.CODE_TITLE) {
                    content = content + "|" + values[1];
                }
                row.setString(this.keyIndex, content);
            }
        }
    }
}

