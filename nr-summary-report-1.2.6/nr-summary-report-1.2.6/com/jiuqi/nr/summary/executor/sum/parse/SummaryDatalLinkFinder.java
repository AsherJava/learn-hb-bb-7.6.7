/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.summary.executor.sum.parse;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.summary.executor.sum.SumBeanSet;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryParam;
import com.jiuqi.nr.summary.executor.sum.parse.SummaryReportInfo;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SummaryDatalLinkFinder
implements IDataModelLinkFinder {
    private RuntimeSummaryParam param;
    private SumBeanSet beanSet;
    private Map<String, SummaryReportInfo> cache = new HashMap<String, SummaryReportInfo>();

    public SummaryDatalLinkFinder(RuntimeSummaryParam param, SumBeanSet beanSet) {
        this.param = param;
        this.beanSet = beanSet;
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        SummaryReportInfo summaryReportInfo = this.getReportInfo(reportInfo.getReportName());
        if (summaryReportInfo == null) {
            return null;
        }
        return summaryReportInfo.getCells().get(new Position(colIndex, rowIndex));
    }

    public ReportInfo findReportInfo(String reportName) {
        SummaryReportInfo summaryReportInfo = this.getReportInfo(reportName);
        if (summaryReportInfo == null) {
            return null;
        }
        return summaryReportInfo.getReportInfo();
    }

    private SummaryReportInfo getReportInfo(String reportName) {
        SummaryReportModel report;
        SummaryReportInfo summaryReportInfo = this.cache.get(reportName);
        if (summaryReportInfo == null && (report = this.param.findReprotModel(this.beanSet, reportName)) != null) {
            ReportInfo info = new ReportInfo(report.getKey(), report.getName(), report.getTitle(), null, null);
            SummaryReportModelHelper helper = new SummaryReportModelHelper(report);
            List<DataCell> cells = helper.getDataCells();
            HashSet<Integer> validRows = new HashSet<Integer>();
            HashSet<Integer> validCols = new HashSet<Integer>();
            summaryReportInfo = new SummaryReportInfo(info);
            for (DataCell cell : cells) {
                validRows.add(cell.getRowNum());
                validCols.add(cell.getColNum());
                List deployInfos = this.beanSet.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{cell.getSummaryZb().getFieldKey()});
                if (deployInfos == null || deployInfos.isEmpty()) continue;
                ColumnModelDefine columnModel = this.beanSet.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
                DataModelLinkColumn column = new DataModelLinkColumn(columnModel);
                column.setReportInfo(info);
                column.setDataPosition(new Position(cell.getColNum(), cell.getRowNum()));
                column.setGridPosition(new Position(cell.getY(), cell.getX()));
                summaryReportInfo.getCells().put(column.getDataPosition(), column);
            }
            info.setValidCols(validCols.stream().mapToInt(Integer::intValue).toArray());
            info.setValidRows(validRows.stream().mapToInt(Integer::intValue).toArray());
            this.cache.put(reportName, summaryReportInfo);
        }
        return summaryReportInfo;
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        return null;
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        return this.findReportInfo(reportName);
    }

    public DataModelLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        return null;
    }

    public List<ReportInfo> findAllRelatedReportInfo(String reportName) {
        return null;
    }

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext context, String linkAlias, String dimensionName, List<Object> unitKeys) {
        return null;
    }

    public List<Object> findRelatedUnitKey(ExecutorContext context, String linkAlias, String dimensionName, Object unitKey) {
        return null;
    }

    public String getRelatedUnitDimName(ExecutorContext context, String linkAlias, String dimensionName) {
        return null;
    }

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataModelLinkColumn dataModelLinkColumn) {
        return null;
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        return true;
    }
}

