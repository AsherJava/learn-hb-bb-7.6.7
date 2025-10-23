/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.executor.save;

import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryDataCellDTO;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryReportDTO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.vo.SummaryReportModelVO;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SummaryReportSaveExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SummaryReportSaveExecutor.class);
    @Autowired
    private IDesignSummaryReportService designReportService;
    @Autowired
    private IDesignSummaryDataCellService designDataCellService;

    public void saveReport(SummaryReportModelVO reportModelVO) throws SummaryCommonException {
        SummaryReportModel reportModel = reportModelVO.getReportModel();
        DesignSummaryReportDTO designReport = this.designReportService.getSummaryReportByKey(reportModel.getKey(), true);
        if (designReport == null) {
            throw new SummaryCommonException(SummaryErrorEnum.SR_NOTEXIST);
        }
        DesignSummaryReportDTO reportDTO = Convert.designSummaryReportConvert.VO2DTO(reportModelVO);
        reportDTO.setOrder(designReport.getOrder());
        if (!StringUtils.hasLength(reportDTO.getSummarySolutionKey())) {
            reportDTO.setSummarySolutionKey(designReport.getSummarySolutionKey());
        }
        this.designReportService.updateSummaryReport(reportDTO, false);
        this.saveDataCells(reportModel);
    }

    private void saveDataCells(SummaryReportModel reportModel) throws SummaryCommonException {
        this.designDataCellService.deleteSummaryDataCellByReport(reportModel.getKey());
        List<DataCell> dataCells = reportModel.getDataCells();
        List<DesignSummaryDataCellDTO> dataCellDTOS = dataCells.stream().map(dataCellVO -> {
            DesignSummaryDataCellDTO dataCellDTO = Convert.designSummaryDataCellConvert.VO2DTO((DataCell)dataCellVO);
            dataCellDTO.setReportKey(reportModel.getKey());
            return dataCellDTO;
        }).collect(Collectors.toList());
        this.designDataCellService.batchInsertSummaryDataCell(dataCellDTOS);
    }
}

