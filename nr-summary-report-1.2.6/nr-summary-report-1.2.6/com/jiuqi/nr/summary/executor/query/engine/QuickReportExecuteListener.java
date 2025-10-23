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
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.quickreport.engine.IReportListener
 *  com.jiuqi.bi.quickreport.engine.ReportEngineException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.summary.executor.query.engine;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.summary.executor.query.QueryPageConfig;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.executor.query.engine.QueryExecutor;
import com.jiuqi.nr.summary.model.PageInfo;
import java.util.Map;
import org.springframework.util.ObjectUtils;

public class QuickReportExecuteListener
implements IReportListener {
    private final Map<String, SummaryDSModel> modelMap;
    private final DimensionValueSet dimensionValueSet;
    private final QueryPageConfig pageConfig;
    private QueryExecutor queryExecutor;

    public QuickReportExecuteListener(Map<String, SummaryDSModel> modelMap, DimensionValueSet dimensionValueSet, QueryPageConfig pageConfig) {
        this.modelMap = modelMap;
        this.pageConfig = pageConfig;
        this.queryExecutor = new QueryExecutor();
        this.dimensionValueSet = dimensionValueSet;
    }

    public DSModel openDSModel(String dsName) throws ReportEngineException {
        return this.modelMap.get(dsName);
    }

    public BIDataSet openDataSet(String dsName) throws ReportEngineException {
        SummaryDSModel dsModel = this.getDSModel(dsName);
        PageInfo pageInfo = this.getPageInfo(dsModel);
        try {
            MemoryDataSet<ColumnInfo> data = this.queryExecutor.query(dsModel, this.dimensionValueSet, pageInfo);
            return this.buildBIDataset(dsModel, data);
        }
        catch (Exception e) {
            throw new ReportEngineException((Throwable)e);
        }
    }

    private PageInfo getPageInfo(DSModel dsMode) {
        Integer rowNumber = this.getRowNumber(dsMode);
        PageInfo pageInfo = this.pageConfig.getPageInfo(rowNumber);
        if (ObjectUtils.isEmpty(pageInfo)) {
            pageInfo = this.pageConfig.getPageInfo(0);
        }
        if (!ObjectUtils.isEmpty(pageInfo)) {
            pageInfo = new PageInfo(pageInfo.getPageSize(), pageInfo.getPageIndex());
            this.pageConfig.getPageInfos().put(rowNumber, pageInfo);
        }
        return pageInfo;
    }

    private Integer getRowNumber(DSModel dsMode) {
        return ((SummaryDSModel)dsMode).getRow();
    }

    public BIDataSetImpl buildBIDataset(DSModel dsModel, MemoryDataSet<ColumnInfo> memoryDataSet) throws Exception {
        MemoryDataSet biMemoryDataSet = new MemoryDataSet();
        for (Column srcColumn : memoryDataSet.getMetadata().getColumns()) {
            BIDataSetFieldInfo biDataSetFieldInfo = this.newBIDataSetFieldInfo(dsModel, (Column<ColumnInfo>)srcColumn);
            biMemoryDataSet.getMetadata().addColumn(new Column(srcColumn.getName(), srcColumn.getDataType(), srcColumn.getTitle(), (Object)biDataSetFieldInfo));
        }
        for (int i = 0; i < memoryDataSet.size(); ++i) {
            biMemoryDataSet.add(memoryDataSet.getBuffer(i));
        }
        return new BIDataSetImpl(biMemoryDataSet, 0);
    }

    private BIDataSetFieldInfo newBIDataSetFieldInfo(DSModel dsModel, Column<ColumnInfo> srcColumn) {
        BIDataSetFieldInfo biDataSetFieldInfo = new BIDataSetFieldInfo(srcColumn.getName(), srcColumn.getDataType(), srcColumn.getTitle());
        if (!ObjectUtils.isEmpty(srcColumn.getInfo())) {
            for (String refDimCol : ((ColumnInfo)srcColumn.getInfo()).getRefDimCols()) {
                biDataSetFieldInfo.addRefDim(refDimCol);
            }
        }
        if (dsModel == null) {
            return biDataSetFieldInfo;
        }
        DSField dsField = dsModel.findField(srcColumn.getName());
        if (dsField == null) {
            return biDataSetFieldInfo;
        }
        biDataSetFieldInfo.loadFromDSField(dsField);
        return biDataSetFieldInfo;
    }

    private SummaryDSModel getDSModel(String dsName) {
        return this.modelMap.getOrDefault(dsName, null);
    }
}

