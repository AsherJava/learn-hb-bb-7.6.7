/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.authority.SummaryReportAuthService;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryFormulaDTO;
import com.jiuqi.nr.summary.internal.dto.SummaryDataCellDTO;
import com.jiuqi.nr.summary.internal.dto.SummaryReportDTO;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionDTO;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionGroupDTO;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryFormulaService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryReportService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionGroupService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionService;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.formula.Formula;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.utils.SummaryReportUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RuntimeSummarySolutionServiceImpl
implements IRuntimeSummarySolutionService {
    @Autowired
    private ISummarySolutionGroupService solutionGroupService;
    @Autowired
    private ISummarySolutionService solutionService;
    @Autowired
    private IRuntimeSummaryReportService runtimeReportService;
    @Autowired
    private IRuntimeSummaryDataCellService runtimeDataCellService;
    @Autowired
    private IRuntimeSummaryFormulaService runtimeFormulaService;
    @Autowired
    private SummaryReportAuthService summaryReportAuthService;

    @Override
    public SummarySolution getSummarySolutionDefine(String solutionKey) {
        SummarySolutionDTO solutionDTO = this.solutionService.getSummarySolutionByKey(solutionKey, false);
        return Convert.summarySolutionConvert.DTO2VO(solutionDTO);
    }

    @Override
    public SummarySolutionModel getSummarySolutionModel(String solutionKey) {
        SummarySolutionDTO solutionDTO = this.solutionService.getSummarySolutionByKey(solutionKey, true);
        return Convert.summarySolutionModelConvert.DTO2VO(solutionDTO);
    }

    @Override
    public SummaryReport getSummaryReportDefine(String reportKey) {
        SummaryReportDTO reportDTO = this.runtimeReportService.getSummaryReportByKey(reportKey, false);
        return Convert.summaryReportConvert.DTO2VO(reportDTO);
    }

    @Override
    public List<SummaryReport> getSummaryReportDefines(List<String> reportKeys) {
        List<SummaryReportDTO> reportDTOS = this.runtimeReportService.getSummaryReportsByKeys(reportKeys, false);
        if (!CollectionUtils.isEmpty(reportDTOS)) {
            return reportDTOS.stream().filter(reportDTO -> this.summaryReportAuthService.canReadReport(reportDTO.getKey())).map(Convert.summaryReportConvert::DTO2VO).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public SummaryReportModel getSummaryReportModel(String reportKey) {
        SummaryReportDTO reportDTO = this.runtimeReportService.getSummaryReportByKey(reportKey, true);
        SummaryReportModel reportModel = Convert.summaryReportModelConvert.DTO2VO(reportDTO);
        List<SummaryDataCellDTO> dataCellDTOS = this.runtimeDataCellService.getSummaryDataCellsByReport(reportKey);
        if (!CollectionUtils.isEmpty(dataCellDTOS)) {
            List<DataCell> dataCellVOS = dataCellDTOS.stream().map(Convert.summaryDataCellConvert::DTO2VO).collect(Collectors.toList());
            reportModel.setDataCells(dataCellVOS);
        }
        return reportModel;
    }

    @Override
    public List<SummaryReport> getSummaryReportDefinesBySolu(String solutionKey) {
        List<SummaryReportDTO> reportDTOS = this.runtimeReportService.getSummaryReportsBySolution(solutionKey, false);
        if (!CollectionUtils.isEmpty(reportDTOS)) {
            return reportDTOS.stream().filter(reportDTO -> this.summaryReportAuthService.canReadReport(reportDTO.getKey())).map(Convert.summaryReportConvert::DTO2VO).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<SummaryReport> getSummaryReportDefinesBySolus(List<String> solutionKeys) {
        List<SummaryReportDTO> reportDTOS = this.runtimeReportService.getSummaryReportsBySolutions(solutionKeys, false);
        if (!CollectionUtils.isEmpty(reportDTOS)) {
            return reportDTOS.stream().filter(reportDTO -> this.summaryReportAuthService.canReadReport(reportDTO.getKey())).map(Convert.summaryReportConvert::DTO2VO).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<SummaryReportModel> getSummaryReportModelsBySolu(String solutionKey) {
        List<SummaryReportDTO> reportDTOS = this.runtimeReportService.getSummaryReportsBySolution(solutionKey, true).stream().filter(reportDTO -> this.summaryReportAuthService.canReadReport(reportDTO.getKey())).collect(Collectors.toList());
        ArrayList<SummaryReportModel> reportModels = new ArrayList<SummaryReportModel>();
        if (!CollectionUtils.isEmpty(reportDTOS)) {
            reportDTOS.forEach(reportDTO -> {
                List<SummaryDataCellDTO> dataCellDTOS = this.runtimeDataCellService.getSummaryDataCellsByReport(reportDTO.getKey());
                SummaryReportModel reportModel = Convert.summaryReportModelConvert.DTO2VO((SummaryReportDTO)reportDTO);
                if (!CollectionUtils.isEmpty(dataCellDTOS)) {
                    List<DataCell> dataCellVOS = dataCellDTOS.stream().map(Convert.summaryDataCellConvert::DTO2VO).collect(Collectors.toList());
                    reportModel.setDataCells(dataCellVOS);
                    reportModels.add(reportModel);
                }
            });
        }
        return reportModels;
    }

    @Override
    public com.jiuqi.nr.summary.model.group.SummarySolutionGroup getSummarySolutionGroup(String groupKey) {
        SummarySolutionGroup summarySolutionGroup = this.solutionGroupService.getSummarySolutionGroupByKey(groupKey);
        return Convert.summarySolutionGroupConvert.DTO2VO((SummarySolutionGroupDTO)summarySolutionGroup);
    }

    @Override
    public List<com.jiuqi.nr.summary.model.group.SummarySolutionGroup> getSummarySolutionGroupByGroup(String groupKey) {
        return this.solutionGroupService.getSummarySolutionGroupsByGroup(groupKey).stream().filter(solutionGroupDTO -> this.summaryReportAuthService.canReadGroup(solutionGroupDTO.getKey())).map(Convert.summarySolutionGroupConvert::DTO2VO).collect(Collectors.toList());
    }

    @Override
    public List<SummarySolution> getSummarySolutionDefinesByGroup(String groupKey) {
        List<SummarySolutionDTO> sumSolutions = this.solutionService.getSummarySolutionsByGroup(groupKey, false);
        return sumSolutions.stream().filter(solutionDTO -> this.summaryReportAuthService.canReadSolution(solutionDTO.getKey())).map(Convert.summarySolutionConvert::DTO2VO).collect(Collectors.toList());
    }

    @Override
    public List<SummarySolution> getSummarySolutionDefinesByGroups(List<String> groupKeys) {
        List<SummarySolutionDTO> sumSolutions = this.solutionService.getSummarySolutionsByGroups(groupKeys, false);
        return sumSolutions.stream().filter(solutionDTO -> this.summaryReportAuthService.canReadSolution(solutionDTO.getKey())).map(Convert.summarySolutionConvert::DTO2VO).collect(Collectors.toList());
    }

    @Override
    public Formula getFormulaByKey(String key) {
        return Convert.summaryFormulaConvert.DTO2VO((DesignSummaryFormulaDTO)this.runtimeFormulaService.getSummaryFormulaByKey(key));
    }

    @Override
    public List<Formula> getFormulasByReport(String reportKey) {
        return this.runtimeFormulaService.getSummaryFormulasByReport(reportKey).stream().map(designSummaryFormula -> Convert.designSummaryFormulaConvert.DTO2VO((DesignSummaryFormulaDTO)designSummaryFormula)).collect(Collectors.toList());
    }

    @Override
    public List<Formula> getBJFormulasBySolution(String solutionKey) {
        return this.runtimeFormulaService.getSummaryFormulasByReport(SummaryReportUtil.getBJFormulaRptId(solutionKey)).stream().map(designSummaryFormula -> Convert.designSummaryFormulaConvert.DTO2VO((DesignSummaryFormulaDTO)designSummaryFormula)).collect(Collectors.toList());
    }

    @Override
    public List<Formula> getFormulasBySolution(String solutionKey) {
        return this.runtimeFormulaService.getSummaryFormulasBySolution(solutionKey).stream().map(designSummaryFormula -> Convert.designSummaryFormulaConvert.DTO2VO((DesignSummaryFormulaDTO)designSummaryFormula)).collect(Collectors.toList());
    }
}

