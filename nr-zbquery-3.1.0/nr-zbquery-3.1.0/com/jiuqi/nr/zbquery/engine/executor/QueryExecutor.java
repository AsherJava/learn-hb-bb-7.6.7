/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.model.PageInfo
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.bql.sdk.IBQLFactory
 *  com.jiuqi.nvwa.bql.sdk.IBQLQuery
 */
package com.jiuqi.nr.zbquery.engine.executor;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.model.PageInfo;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.zbquery.engine.executor.DataProcessor;
import com.jiuqi.nr.zbquery.engine.executor.QueryModelBuilder;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nvwa.bql.sdk.IBQLFactory;
import com.jiuqi.nvwa.bql.sdk.IBQLQuery;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class QueryExecutor {
    private ZBQueryModel queryModel;
    private IBQLFactory bqlFactory = (IBQLFactory)SpringBeanUtils.getBean(IBQLFactory.class);
    private QueryModelBuilder modelBuilder;
    private String cacheId;

    public QueryExecutor(String cacheId, ZBQueryModel queryModel) {
        this.queryModel = queryModel;
        this.cacheId = cacheId;
    }

    public MemoryDataSet<ColumnInfo> query(ConditionValues conditionValues) throws Exception {
        return this.query(conditionValues, null);
    }

    public MemoryDataSet<ColumnInfo> query(ConditionValues conditionValues, com.jiuqi.nr.zbquery.model.PageInfo pageInfo) throws Exception {
        this.init(conditionValues);
        this.modelBuilder.build();
        PageInfo _pageInfo = this._toPageInfo(pageInfo);
        IBQLQuery bqlQuery = this.bqlFactory.createQuery(StringUtils.isNotEmpty((String)this.cacheId) ? this.cacheId : Guid.newGuid(), this.modelBuilder.getQueryModel());
        MemoryDataSet<ColumnInfo> result = bqlQuery.query(_pageInfo);
        result = this.resetCols(result);
        DataProcessor dataProcessor = new DataProcessor(this.modelBuilder, result);
        dataProcessor.process();
        if (pageInfo != null) {
            pageInfo.setRecordSize(_pageInfo.getRecordSize());
        }
        return result;
    }

    public QueryModelBuilder getModelBuilder() {
        return this.modelBuilder;
    }

    private void init(ConditionValues conditionValues) throws Exception {
        this.modelBuilder = new QueryModelBuilder(this.queryModel, conditionValues);
    }

    /*
     * WARNING - void declaration
     */
    private MemoryDataSet<ColumnInfo> resetCols(MemoryDataSet<ColumnInfo> dataSet) throws Exception {
        Map<String, List<String>> childDimFields = this.modelBuilder.getChildDimFields();
        if (childDimFields.size() > 0) {
            ArrayList<Object> newColumns = new ArrayList<Object>();
            for (Column column : dataSet.getMetadata().getColumns()) {
                newColumns.add(column.clone());
            }
            for (String dimFullName : childDimFields.keySet()) {
                for (String string : childDimFields.get(dimFullName)) {
                    QueryField queryField = (QueryField)this.modelBuilder.getModelFinder().getQueryObject(string);
                    String fieldAlias = this.modelBuilder.getFullNameAliasMapper().get(string);
                    ColumnInfo colInfo = new ColumnInfo();
                    colInfo.setFieldType(1);
                    Column col = new Column(fieldAlias, queryField.getDataType(), queryField.getDisplayTitle(), (Object)colInfo);
                    newColumns.add(col);
                }
            }
            if (newColumns.size() > dataSet.getMetadata().size()) {
                void var7_11;
                final List<String> fieldAliases = this.modelBuilder.getFinalQueryFieldAliases();
                newColumns.sort(new Comparator<Column<ColumnInfo>>(){

                    @Override
                    public int compare(Column<ColumnInfo> o1, Column<ColumnInfo> o2) {
                        int cv = fieldAliases.indexOf(o1.getName()) - fieldAliases.indexOf(o2.getName());
                        if (cv > 0) {
                            return 1;
                        }
                        if (cv < 0) {
                            return -1;
                        }
                        return 0;
                    }
                });
                Metadata newMetadata = new Metadata();
                for (Column column : newColumns) {
                    newMetadata.addColumn(column);
                }
                int[] colIndexes = new int[dataSet.getMetadata().size()];
                boolean bl = false;
                while (var7_11 < colIndexes.length) {
                    colIndexes[var7_11] = newMetadata.indexOf(dataSet.getMetadata().getColumn((int)var7_11).getName());
                    ++var7_11;
                }
                MemoryDataSet memoryDataSet = new MemoryDataSet(ColumnInfo.class, newMetadata);
                memoryDataSet.setSize(dataSet.size());
                for (int i = 0; i < dataSet.size(); ++i) {
                    memoryDataSet.add();
                    Object[] newData = memoryDataSet.getBuffer(i);
                    Object[] data = dataSet.getBuffer(i);
                    for (int j = 0; j < colIndexes.length; ++j) {
                        newData[colIndexes[j]] = data[j];
                    }
                }
                dataSet = memoryDataSet;
            }
        }
        return dataSet;
    }

    private PageInfo _toPageInfo(com.jiuqi.nr.zbquery.model.PageInfo pageInfo) {
        if (pageInfo == null) {
            return null;
        }
        PageInfo _pageInfo = new PageInfo(pageInfo.getPageSize(), pageInfo.getPageIndex());
        _pageInfo.setRecordSize(pageInfo.getRecordSize());
        return _pageInfo;
    }
}

