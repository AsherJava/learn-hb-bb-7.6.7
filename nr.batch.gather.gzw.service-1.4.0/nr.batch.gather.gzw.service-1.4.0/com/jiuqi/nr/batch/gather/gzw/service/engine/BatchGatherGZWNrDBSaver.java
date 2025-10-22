/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.common.StringLogger
 *  com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil
 *  com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBBuilder
 *  com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBSaver
 *  com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider
 *  com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.batch.gather.gzw.service.engine;

import com.jiuqi.nr.batch.gather.gzw.service.engine.BatchGatherGZNrDBBuilder;
import com.jiuqi.nr.batch.summary.common.StringLogger;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBBuilder;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBSaver;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchGatherGZWNrDBSaver
extends BatchSummaryNrDBSaver {
    protected final TargetDimProvider targetDimProvider;

    public BatchGatherGZWNrDBSaver(StringLogger logger, NrdbHelper nrdbHelper, SummaryScheme summaryScheme, ITableDBUtil tableDBUtil, DataModelService dataModelService, INvwaDataAccessProvider dataAccessProvider, TargetDimProvider targetDimProvider) {
        super(logger, nrdbHelper, summaryScheme, tableDBUtil, dataModelService, dataAccessProvider);
        this.targetDimProvider = targetDimProvider;
    }

    protected void appendGatherQueryColumn(SumTableModelInfo sumTableModel, List<NvwaQueryColumn> queryColumns) {
    }

    protected void putGatherColumnToSumTableColumnMap(SumTableModelInfo sumTableModel, Map<ColumnModelDefine, String> columnCodeMap) {
    }

    protected Map<String, Object> tempTableDataOtherColumnValues(SumTableModelInfo sumTableModel) {
        return new HashMap<String, Object>();
    }

    protected BatchSummaryNrDBBuilder getBatchSummaryNrDBBuilder() {
        return new BatchGatherGZNrDBBuilder();
    }

    protected void clearHistoryTableData(SumTableModelInfo sumTableModel, String period) throws Exception {
        HashMap<ColumnModelDefine, Object> columnFilters = new HashMap<ColumnModelDefine, Object>();
        columnFilters.put(sumTableModel.getDWColumn().getColumnModel(), this.targetDimProvider.getTargetDims(period));
        columnFilters.put(sumTableModel.getPeriodColumn().getColumnModel(), period);
        sumTableModel.getSituationColumns().forEach(column -> {
            if (column.isCorporate()) {
                Object defaultValue = column.getDefaultValue();
                if (defaultValue == null) {
                    throw new IllegalArgumentException("\u6c47\u603b\u65b9\u6848\uff1a" + this.summaryScheme.getCode() + "\uff0c\u7eac\u5ea6\uff1a" + column.getColumnModel().getCode() + "\uff0c\u9ed8\u8ba4\u503c\u4e0d\u80fd\u4e3a\u7a7a");
                }
                columnFilters.put(column.getColumnModel(), defaultValue);
            }
        });
        this.deleteDataRows(sumTableModel.getTableModel(), columnFilters);
    }
}

