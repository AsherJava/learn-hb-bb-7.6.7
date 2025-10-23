/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.authority.SummaryReportAuthService;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryDataCellDTO;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryFormulaDTO;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryReportDTO;
import com.jiuqi.nr.summary.internal.dto.SummaryReportDTO;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionDTO;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionGroupDTO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryFormulaService;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryReportService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionGroupService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionService;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.MainCell;
import com.jiuqi.nr.summary.model.formula.Formula;
import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportConfig;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.utils.SummaryReportUtil;
import com.jiuqi.nr.summary.vo.ReportCopyRequestParam;
import com.jiuqi.util.OrderGenerator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DesignSummarySolutionServiceImpl
implements IDesignSummarySolutionService {
    private static final Logger logger = LoggerFactory.getLogger(DesignSummarySolutionServiceImpl.class);
    @Autowired
    private ISummarySolutionGroupService solutionGroupService;
    @Autowired
    private ISummarySolutionService solutionService;
    @Autowired
    private IDesignSummaryReportService designReportService;
    @Autowired
    private IRuntimeSummaryReportService runtimeReportService;
    @Autowired
    private IDesignSummaryDataCellService designDataCellService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRuntimeSummarySolutionService runtimeSolutionService;
    @Autowired
    private IDesignSummaryFormulaService designSummaryFormulaService;
    @Autowired
    private SummaryReportAuthService summaryReportAuthService;

    @Override
    public void insertSummarySolutionGroup(SummarySolutionGroup solutionGroup) throws SummaryCommonException {
        this.solutionGroupService.insertSummarySolutionGroup(Convert.summarySolutionGroupConvert.VO2DTO(solutionGroup));
    }

    @Override
    public void updateSummarySolutionGroup(SummarySolutionGroup solutionGroup) throws SummaryCommonException {
        this.solutionGroupService.updateSummarySolutionGroup(Convert.summarySolutionGroupConvert.VO2DTO(solutionGroup));
    }

    @Override
    public void deleteSummarySolutionGroup(String groupKey) throws SummaryCommonException {
        this.solutionGroupService.deleteSummarySolutionGroupByKey(groupKey);
    }

    @Override
    public void deleteSummarySolutionGroups(List<String> groupKeys) throws SummaryCommonException {
        this.solutionGroupService.deleteSummarySolutionGroupByKeys(groupKeys);
    }

    @Override
    public SummarySolutionGroup getSummarySolutionGroup(String groupKey) {
        com.jiuqi.nr.summary.api.SummarySolutionGroup summarySolutionGroup = this.solutionGroupService.getSummarySolutionGroupByKey(groupKey);
        return Convert.summarySolutionGroupConvert.DTO2VO((SummarySolutionGroupDTO)summarySolutionGroup);
    }

    @Override
    public List<SummarySolutionGroup> getSummarySolutionGroupByGroup(String groupKey) {
        return this.solutionGroupService.getSummarySolutionGroupsByGroup(groupKey).stream().filter(solutionGroupDTO -> this.summaryReportAuthService.canReadGroup(solutionGroupDTO.getKey())).map(Convert.summarySolutionGroupConvert::DTO2VO).collect(Collectors.toList());
    }

    @Override
    public void insertSummarySolutionModel(SummarySolutionModel solutionModel) throws SummaryCommonException {
        this.solutionService.insertSummarySolution(Convert.summarySolutionModelConvert.VO2DTO(solutionModel));
    }

    @Override
    public List<SummarySolution> getSummarySolutionsByGroup(String groupKey) {
        return this.solutionService.getSummarySolutionsByGroup(groupKey, false).stream().filter(solutionDTO -> this.summaryReportAuthService.canReadSolution(solutionDTO.getKey())).map(Convert.summarySolutionConvert::DTO2VO).collect(Collectors.toList());
    }

    @Override
    public SummarySolutionModel getSummarySolutionModel(String solutionKey) {
        SummarySolutionDTO solutionDTO = this.solutionService.getSummarySolutionByKey(solutionKey, true);
        return Convert.summarySolutionModelConvert.DTO2VO(solutionDTO);
    }

    @Override
    public SummarySolution getSummarySolutionDefine(String solutionKey) {
        SummarySolutionDTO solutionDTO = this.solutionService.getSummarySolutionByKey(solutionKey, false);
        return Convert.summarySolutionConvert.DTO2VO(solutionDTO);
    }

    @Override
    public void deleteSummarySolution(String solutionKey) throws SummaryCommonException {
        this.solutionService.deleteSummarySolutionByKey(solutionKey);
    }

    @Override
    public void batchDeleteSummarySolutions(List<String> solutionKeys) throws SummaryCommonException {
        this.solutionService.batchDeleteSummarySolutions(solutionKeys);
    }

    @Override
    public SummaryReport getSummaryReport(String reportKey) {
        DesignSummaryReportDTO reportDTO = this.designReportService.getSummaryReportByKey(reportKey, false);
        return Convert.baseDesignSummaryReportConvert.DTO2VO(reportDTO);
    }

    @Override
    public SummaryReportModel getSummaryReportModel(String reportKey) {
        DesignSummaryReportDTO reportDTO = this.designReportService.getSummaryReportByKey(reportKey, true);
        SummaryReportModel reportModel = Convert.designSummaryReportModelConvert.DTO2VO(reportDTO);
        List<DesignSummaryDataCellDTO> dataCellDTOS = this.designDataCellService.getSummaryDataCellsByReport(reportKey);
        if (!CollectionUtils.isEmpty(dataCellDTOS)) {
            List<DataCell> dataCellVOS = dataCellDTOS.stream().map(Convert.designSummaryDataCellConvert::DTO2VO).collect(Collectors.toList());
            reportModel.setDataCells(dataCellVOS);
        }
        return reportModel;
    }

    @Override
    public List<SummaryReport> getSummaryReportsBySolution(String solutionKey) {
        List<SummaryReport> reportVOS = new ArrayList<SummaryReport>();
        List<DesignSummaryReportDTO> reporDTOS = this.designReportService.getSummaryReportsBySolution(solutionKey, false);
        if (!CollectionUtils.isEmpty(reporDTOS)) {
            reportVOS = reporDTOS.stream().filter(reportDTO -> this.summaryReportAuthService.canReadReport(reportDTO.getKey())).map(Convert.baseDesignSummaryReportConvert::DTO2VO).collect(Collectors.toList());
        }
        return reportVOS;
    }

    @Override
    public List<SummaryReport> getNotEmptySummaryReportsBySolution(String solutionKey) {
        List<SummaryReport> reportVOS = new ArrayList<SummaryReport>();
        List<DesignSummaryReportDTO> reporDTOS = this.designReportService.getSummaryReportsBySolution(solutionKey, true);
        if (!CollectionUtils.isEmpty(reporDTOS)) {
            reportVOS = reporDTOS.stream().filter(reportDTO -> this.summaryReportAuthService.canReadReport(reportDTO.getKey())).filter(reportDTO -> reportDTO.getGridData() != null).map(Convert.baseDesignSummaryReportConvert::DTO2VO).collect(Collectors.toList());
        }
        return reportVOS;
    }

    @Override
    public List<SummaryReportModel> getSummaryReportModelsBySolution(String solutionKey) {
        List<SummaryReportModel> reportModels = new ArrayList<SummaryReportModel>();
        List<SummaryReport> reports = this.getSummaryReportsBySolution(solutionKey);
        if (!CollectionUtils.isEmpty(reports)) {
            reportModels = reports.stream().map(report -> this.getSummaryReportModel(report.getKey())).collect(Collectors.toList());
        }
        return reportModels;
    }

    @Override
    public void copySummaryReport(ReportCopyRequestParam copyReqParam) throws SummaryCommonException {
        DesignSummaryReportDTO copyReportDTO = new DesignSummaryReportDTO();
        DesignSummaryReportDTO reportDTO = this.designReportService.getSummaryReportByKey(copyReqParam.getReportKey(), true);
        SummaryReportModel reportModel = Convert.designSummaryReportModelConvert.DTO2VO(reportDTO);
        String copyReportKey = this.copyReport(copyReportDTO, reportDTO, reportModel, copyReqParam);
        this.copyDataCells(copyReportKey, reportModel, copyReqParam);
        this.copyFormula(copyReportKey, copyReqParam);
    }

    private String copyReport(DesignSummaryReportDTO copyReportDTO, DesignSummaryReportDTO reportDTO, SummaryReportModel reportModel, ReportCopyRequestParam copyReqParam) throws SummaryCommonException {
        this.copyReportBasic(copyReportDTO, copyReqParam, reportDTO);
        this.copyReportConfig(copyReportDTO, reportModel);
        this.copyReportData(copyReportDTO, reportDTO);
        this.designReportService.insertSummaryReport(copyReportDTO);
        return copyReportDTO.getKey();
    }

    private void copyDataCells(String copyReportKey, SummaryReportModel reportModel, ReportCopyRequestParam copyReqParam) {
        SummaryReportConfig config = reportModel.getConfig();
        if (config != null) {
            List<SummaryFloatRegion> regions = config.getRegions();
            HashMap<Integer, Integer> floatRegionCellCountMap = new HashMap<Integer, Integer>();
            int fixCellCount = 0;
            regions.forEach(region -> floatRegionCellCountMap.put(region.getPosition(), 0));
            List<DesignSummaryDataCellDTO> dataCellDTOS = this.designDataCellService.getSummaryDataCellsByReport(copyReqParam.getReportKey());
            for (DesignSummaryDataCellDTO cellDTO : dataCellDTOS) {
                cellDTO.setKey(UUID.randomUUID().toString());
                cellDTO.setReportKey(copyReportKey);
                cellDTO.setReferDataFieldKey(null);
                cellDTO.setDataTableKey(null);
                cellDTO.setModifyTime(Instant.ofEpochMilli(new Date().getTime()));
                if (floatRegionCellCountMap.containsKey(cellDTO.getX())) {
                    int floatCellCount = (Integer)floatRegionCellCountMap.get(cellDTO.getX());
                    cellDTO.setFieldName("SR" + copyReqParam.getReportName() + "_F" + this.getZbNameCountPart(floatCellCount + 1));
                    floatRegionCellCountMap.put(cellDTO.getX(), floatCellCount + 1);
                    continue;
                }
                cellDTO.setFieldName("SR" + copyReqParam.getReportName() + this.getZbNameCountPart(++fixCellCount));
            }
            this.designDataCellService.batchInsertSummaryDataCell(dataCellDTOS);
        }
    }

    private void copyFormula(String copyReportKey, ReportCopyRequestParam copyReqParam) {
        ArrayList<DesignSummaryFormulaDTO> formulaDTOS = new ArrayList<DesignSummaryFormulaDTO>();
        List<Formula> formulas = this.getFormulasByReport(copyReqParam.getReportKey());
        for (int i = 0; i < formulas.size(); ++i) {
            Formula formula = formulas.get(i);
            formula.setKey(UUID.randomUUID().toString());
            formula.setRptId(copyReportKey);
            formula.setFmCode(copyReqParam.getReportName() + this.getFormulaCode(i + 1));
            if (StringUtils.hasLength(formula.getExpression())) {
                formula.setExpression(formula.getExpression().replace(copyReqParam.getOldReportName() + "[", copyReqParam.getReportName() + "["));
            }
            formula.setModifyTime(new Date());
            formulaDTOS.add(Convert.designSummaryFormulaConvert.VO2DTO(formula));
        }
        this.designSummaryFormulaService.batchInsertSummaryFormula(formulaDTOS);
    }

    private String getFormulaCode(int count) {
        if (count < 10) {
            return "00" + count;
        }
        if (count < 100) {
            return "0" + count;
        }
        return count + "";
    }

    private String getZbNameCountPart(int count) {
        StringBuilder sb = new StringBuilder();
        String countStr = count + "";
        for (int i = 0; i < 4 - countStr.length(); ++i) {
            sb.append("0");
        }
        sb.append(countStr);
        return sb.toString();
    }

    private void copyReportBasic(DesignSummaryReportDTO copyReportDTO, ReportCopyRequestParam copyReqParam, DesignSummaryReportDTO reportDTO) {
        copyReportDTO.setKey(UUID.randomUUID().toString());
        copyReportDTO.setName(copyReqParam.getReportName());
        copyReportDTO.setTitle(copyReqParam.getReportTitle());
        copyReportDTO.setSummarySolutionKey(reportDTO.getSummarySolutionKey());
        copyReportDTO.setSequenceType(reportDTO.getSequenceType());
        copyReportDTO.setFilter(reportDTO.getFilter());
        copyReportDTO.setPageConfig(reportDTO.getPageConfig());
        copyReportDTO.setModifyTime(Instant.ofEpochMilli(new Date().getTime()));
        copyReportDTO.setOrder(OrderGenerator.newOrder());
    }

    private void copyReportConfig(DesignSummaryReportDTO copyReportDTO, SummaryReportModel reportModel) throws SummaryCommonException {
        SummaryReportConfig copyConfig = new SummaryReportConfig();
        SummaryReportConfig config = reportModel.getConfig();
        if (config != null) {
            copyConfig.setFilter(config.getFilter());
            copyConfig.setSequenceType(config.getSequenceType());
            List<SummaryFloatRegion> regions = config.getRegions();
            if (!CollectionUtils.isEmpty(regions)) {
                regions.forEach(region -> region.setTableName(null));
            }
            copyConfig.setRegions(regions);
            List<MainCell> mainCells = config.getMainCells();
            mainCells.forEach(cell -> cell.setInnerDimZb(null));
            copyConfig.setMainCells(mainCells);
            copyConfig.setGuestCells(config.getGuestCells());
            copyConfig.setRowFilters(config.getRowFilters());
            copyConfig.setColFilters(config.getColFilters());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                copyReportDTO.setConfig(objectMapper.writeValueAsString((Object)copyConfig));
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
                throw new SummaryCommonException(SummaryErrorEnum.OBJMMAPER_WRITE_EXCEPTION, e.getMessage());
            }
        }
    }

    private void copyReportData(DesignSummaryReportDTO copyReportDTO, DesignSummaryReportDTO reportDTO) {
        copyReportDTO.setGridData(reportDTO.getGridData());
    }

    @Override
    public List<DataCell> getDataCellsByReport(String reportKey) {
        List<DataCell> dataCells = new ArrayList<DataCell>();
        List<DesignSummaryDataCellDTO> dataCellDTOS = this.designDataCellService.getSummaryDataCellsByReport(reportKey);
        if (!CollectionUtils.isEmpty(dataCellDTOS)) {
            dataCells = dataCellDTOS.stream().map(Convert.designSummaryDataCellConvert::DTO2VO).collect(Collectors.toList());
        }
        return dataCells;
    }

    @Override
    public boolean isDeployed(String solutionKey) {
        return this.runtimeDataSchemeService.getDataScheme(solutionKey) != null;
    }

    @Override
    public boolean isReportDeployed(String reportKey) {
        SummaryReportDTO reportDTO = this.runtimeReportService.getSummaryReportByKey(reportKey, false);
        return reportDTO != null;
    }

    @Override
    public void insertFormula(Formula formula) throws DBParaException {
        this.designSummaryFormulaService.insertSummaryFormula(Convert.designSummaryFormulaConvert.VO2DTO(formula));
    }

    @Override
    public void batchInsertFormula(List<Formula> formulas) throws DBParaException {
        List<DesignSummaryFormulaDTO> formulaDTOS = formulas.stream().map(formula -> {
            if (!StringUtils.hasLength(formula.getKey())) {
                formula.setKey(UUID.randomUUID().toString());
            }
            formula.setModifyTime(new Date());
            return Convert.designSummaryFormulaConvert.VO2DTO((Formula)formula);
        }).collect(Collectors.toList());
        this.designSummaryFormulaService.batchInsertSummaryFormula(formulaDTOS);
    }

    @Override
    public void updateFormula(Formula formula) throws DBParaException {
        this.designSummaryFormulaService.updateSummaryFormula(Convert.designSummaryFormulaConvert.VO2DTO(formula));
    }

    @Override
    public void batchUpdateFormula(List<Formula> formulas) throws DBParaException {
        List<DesignSummaryFormulaDTO> formulaDTOS = formulas.stream().map(formula -> {
            formula.setModifyTime(new Date());
            return Convert.designSummaryFormulaConvert.VO2DTO((Formula)formula);
        }).collect(Collectors.toList());
        this.designSummaryFormulaService.batchUpdateSummaryFormula(formulaDTOS);
    }

    @Override
    public void deleteFormulaByKey(String formulaKey) throws DBParaException {
        this.designSummaryFormulaService.deleteSummaryFormulaByKey(formulaKey);
    }

    @Override
    public void deleteFormulaByKeys(List<String> formulaKeys) throws DBParaException {
        this.designSummaryFormulaService.deleteSummaryFormulaByKeys(formulaKeys);
    }

    @Override
    public Formula getFormulaByKey(String key) {
        return Convert.designSummaryFormulaConvert.DTO2VO((DesignSummaryFormulaDTO)this.designSummaryFormulaService.getSummaryFormulaByKey(key));
    }

    @Override
    public List<Formula> getFormulasByReport(String reportKey) {
        return this.designSummaryFormulaService.getSummaryFormulasByReport(reportKey).stream().map(designSummaryFormula -> Convert.designSummaryFormulaConvert.DTO2VO((DesignSummaryFormulaDTO)designSummaryFormula)).collect(Collectors.toList());
    }

    @Override
    public List<Formula> getBJFormulasBySolution(String solutionKey) {
        return this.designSummaryFormulaService.getSummaryFormulasByReport(SummaryReportUtil.getBJFormulaRptId(solutionKey)).stream().map(designSummaryFormula -> Convert.designSummaryFormulaConvert.DTO2VO((DesignSummaryFormulaDTO)designSummaryFormula)).collect(Collectors.toList());
    }

    @Override
    public List<Formula> getFormulasBySolution(String solutionKey) {
        return this.designSummaryFormulaService.getSummaryFormulasBySolution(solutionKey).stream().map(designSummaryFormula -> Convert.designSummaryFormulaConvert.DTO2VO((DesignSummaryFormulaDTO)designSummaryFormula)).collect(Collectors.toList());
    }
}

