/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.executor.deploy.AbstractExecutor;
import com.jiuqi.nr.summary.executor.deploy.DeployContext;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryDataCellDTO;
import com.jiuqi.nr.summary.internal.dto.SummaryDataCellDTO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryDataCellService;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.SummaryZb;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

class DataCellExecutor
extends AbstractExecutor {
    private final IDesignSummaryDataCellService designDataCellService;
    private final IRuntimeSummaryDataCellService runtimeDataCellService;

    public DataCellExecutor(DeployContext context, AsyncTaskMonitor monitor, IDesignSummaryDataCellService designDataCellService, IRuntimeSummaryDataCellService runtimeDataCellService) {
        super(context, monitor);
        this.designDataCellService = designDataCellService;
        this.runtimeDataCellService = runtimeDataCellService;
    }

    @Override
    public boolean execute() {
        this.processCellAndRegionBelongTable();
        this.processRunTimeDataCell();
        return false;
    }

    private void processCellAndRegionBelongTable() {
        SummaryReportModel designReportModel = this.context.getDesignReportModel();
        List<DataCell> dataCells = Objects.requireNonNull(designReportModel).getDataCells();
        Map<Integer, String> dataTableMap = this.context.getDataTableMap();
        ArrayList<DesignSummaryDataCellDTO> dataCellDTOS = new ArrayList<DesignSummaryDataCellDTO>();
        for (DataCell cell : dataCells) {
            SummaryZb summaryZb = cell.getSummaryZb();
            if (!StringUtils.hasLength(summaryZb.getTableName())) {
                int x = cell.getX();
                if (dataTableMap.get(x) != null) {
                    summaryZb.setTableName(dataTableMap.get(x));
                } else {
                    summaryZb.setTableName(dataTableMap.get(-1));
                }
            }
            DesignSummaryDataCellDTO dataCellDTO = Convert.designSummaryDataCellConvert.VO2DTO(cell);
            dataCellDTO.setReportKey(designReportModel.getKey());
            dataCellDTOS.add(dataCellDTO);
        }
        if (!CollectionUtils.isEmpty(dataCellDTOS)) {
            try {
                this.designDataCellService.deleteSummaryDataCellByReport(designReportModel.getKey());
                this.designDataCellService.batchInsertSummaryDataCell(dataCellDTOS);
            }
            catch (SummaryCommonException e) {
                this.error((Throwable)((Object)e));
            }
        }
    }

    private void processRunTimeDataCell() {
        String reportKey = Objects.requireNonNull(this.context.getDesignReportModel()).getKey();
        try {
            this.runtimeDataCellService.deleteSummaryDataCellByReport(reportKey);
            List<DesignSummaryDataCellDTO> dataCells = this.designDataCellService.getSummaryDataCellsByReport(reportKey);
            if (!CollectionUtils.isEmpty(dataCells)) {
                List<SummaryDataCellDTO> dataCellDTOS = dataCells.stream().map(SummaryDataCellDTO::new).collect(Collectors.toList());
                this.runtimeDataCellService.batchInsertSummaryDataCell(dataCellDTOS);
                this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6c47\u603b\u65b9\u6848:\u540c\u6b65\u4e86\u3010%s\u3011\u4e2a\u5355\u5143\u683c\u5230\u8fd0\u884c\u671f", String.valueOf(dataCellDTOS.size()));
            }
        }
        catch (SummaryCommonException e) {
            this.error((Throwable)((Object)e));
        }
    }
}

