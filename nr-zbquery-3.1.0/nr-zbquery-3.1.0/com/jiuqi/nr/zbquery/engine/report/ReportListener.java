/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.BIDataSetImpl
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.quickreport.engine.IReportListener
 *  com.jiuqi.bi.quickreport.engine.ReportEngineException
 */
package com.jiuqi.nr.zbquery.engine.report;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModelBuilder;
import com.jiuqi.nr.zbquery.engine.executor.QueryExecutor;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.PageInfo;

public class ReportListener
implements IReportListener {
    protected final QueryDSModelBuilder dsModelBuilder;
    private ConditionValues conditionValues;
    private PageInfo pageInfo;
    private QueryExecutor queryExecutor;

    public ReportListener(String cacheId, QueryDSModelBuilder dsModelBuilder, ConditionValues conditionValues, PageInfo pageInfo) {
        this.dsModelBuilder = dsModelBuilder;
        this.conditionValues = conditionValues;
        this.pageInfo = pageInfo;
        this.queryExecutor = new QueryExecutor(cacheId, dsModelBuilder.getZbQueryModel());
    }

    public DSModel openDSModel(String dsName) throws ReportEngineException {
        return this.dsModelBuilder.getDSModel();
    }

    public BIDataSet openDataSet(String dsName) throws ReportEngineException {
        try {
            MemoryDataSet<ColumnInfo> data = this.queryExecutor.query(this.conditionValues, this.pageInfo);
            return this.buildBIDataset(data);
        }
        catch (Exception e) {
            throw new ReportEngineException((Throwable)e);
        }
    }

    private BIDataSetImpl buildBIDataset(MemoryDataSet<ColumnInfo> memoryDataSet) throws Exception {
        MemoryDataSet biMemoryDataSet = new MemoryDataSet();
        for (Column srcColumn : memoryDataSet.getMetadata().getColumns()) {
            BIDataSetFieldInfo biDataSetFieldInfo = this.newBIDataSetFieldInfo((Column<ColumnInfo>)srcColumn);
            biMemoryDataSet.getMetadata().addColumn(new Column(srcColumn.getName(), srcColumn.getDataType(), srcColumn.getTitle(), (Object)biDataSetFieldInfo));
        }
        for (int i = 0; i < memoryDataSet.size(); ++i) {
            biMemoryDataSet.add(memoryDataSet.getBuffer(i));
        }
        BIDataSetImpl ds = new BIDataSetImpl(biMemoryDataSet, 0);
        Column col = ds.getMetadata().find("SYS_ROWNUM");
        if (col != null) {
            ((BIDataSetFieldInfo)col.getInfo()).setFieldType(FieldType.GENERAL_DIM);
        }
        return ds;
    }

    private BIDataSetFieldInfo newBIDataSetFieldInfo(Column<ColumnInfo> srcColumn) {
        BIDataSetFieldInfo biDataSetFieldInfo = new BIDataSetFieldInfo(srcColumn.getName(), srcColumn.getDataType(), srcColumn.getTitle());
        for (String refDimCol : ((ColumnInfo)srcColumn.getInfo()).getRefDimCols()) {
            biDataSetFieldInfo.addRefDim(refDimCol);
        }
        DSField dsField = this.dsModelBuilder.getDSModel().findField(srcColumn.getName());
        if (dsField == null) {
            return biDataSetFieldInfo;
        }
        biDataSetFieldInfo.loadFromDSField(dsField);
        return biDataSetFieldInfo;
    }
}

