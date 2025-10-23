/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.common.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nr.summary.api.core.IObjectConvert;
import com.jiuqi.nr.summary.executor.query.QueryPageConfig;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryDataCellDTO;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryFormulaDTO;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryReportDTO;
import com.jiuqi.nr.summary.internal.dto.SummaryDataCellDTO;
import com.jiuqi.nr.summary.internal.dto.SummaryFormulaDTO;
import com.jiuqi.nr.summary.internal.dto.SummaryReportDTO;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionDTO;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionGroupDTO;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryDataCellDO;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryFormulaDO;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryReportDO;
import com.jiuqi.nr.summary.internal.entity.SummaryDataCellDO;
import com.jiuqi.nr.summary.internal.entity.SummaryFormulaDO;
import com.jiuqi.nr.summary.internal.entity.SummaryReportDO;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionDO;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionGroupDO;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.SummaryZb;
import com.jiuqi.nr.summary.model.formula.Formula;
import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportConfig;
import com.jiuqi.nr.summary.model.report.SummaryReportData;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.CommitState;
import com.jiuqi.nr.summary.model.soulution.DimensionData;
import com.jiuqi.nr.summary.model.soulution.SourceDimensionRange;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.model.soulution.TargetDimensionRange;
import com.jiuqi.nr.summary.vo.SummaryReportModelVO;
import com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class Convert {
    private static final Logger logger = LoggerFactory.getLogger(Convert.class);
    public static IObjectConvert<SummarySolutionGroupDO, SummarySolutionGroupDTO, SummarySolutionGroup> summarySolutionGroupConvert = new SummarySolutionGroupConvert();
    public static IObjectConvert<SummarySolutionDO, SummarySolutionDTO, SummarySolution> summarySolutionConvert = new SummarySolutionConvert();
    public static IObjectConvert<SummarySolutionDO, SummarySolutionDTO, SummarySolutionModel> summarySolutionModelConvert = new SummarySolutionModelConvert();
    public static IObjectConvert<DesignSummaryReportDO, DesignSummaryReportDTO, SummaryReport> baseDesignSummaryReportConvert = new BaseDesignSummaryReportConvert();
    public static IObjectConvert<DesignSummaryReportDO, DesignSummaryReportDTO, SummaryReportModel> designSummaryReportModelConvert = new DesignSummaryReportModelConvert();
    public static IObjectConvert<DesignSummaryReportDO, DesignSummaryReportDTO, SummaryReportModelVO> designSummaryReportConvert = new DesignSummaryReportConvert();
    public static IObjectConvert<SummaryReportDO, SummaryReportDTO, SummaryReport> summaryReportConvert = new SummaryReportConvert();
    public static IObjectConvert<SummaryReportDO, SummaryReportDTO, SummaryReportModel> summaryReportModelConvert = new SummaryReportModelConvert();
    public static IObjectConvert<DesignSummaryDataCellDO, DesignSummaryDataCellDTO, DataCell> designSummaryDataCellConvert = new DesignSummaryDataCellConvert();
    public static IObjectConvert<SummaryDataCellDO, SummaryDataCellDTO, DataCell> summaryDataCellConvert = new SummaryDataCellConvert();
    public static IObjectConvert<DesignSummaryFormulaDO, DesignSummaryFormulaDTO, Formula> designSummaryFormulaConvert = new DesignSummaryFormulaConvert();
    public static IObjectConvert<SummaryFormulaDO, SummaryFormulaDTO, Formula> summaryFormulaConvert = new SummaryFormulaConvert();

    private static class SummaryFormulaConvert
    implements IObjectConvert<SummaryFormulaDO, SummaryFormulaDTO, Formula> {
        private SummaryFormulaConvert() {
        }

        @Override
        public SummaryFormulaDTO DO2DTO(SummaryFormulaDO formulaDO) {
            if (ObjectUtils.isEmpty(formulaDO)) {
                return null;
            }
            SummaryFormulaDTO formulaDTO = new SummaryFormulaDTO();
            formulaDTO.setKey(formulaDO.getKey());
            formulaDTO.setName(formulaDO.getName());
            formulaDTO.setSummarySolutionKey(formulaDO.getSummarySolutionKey());
            formulaDTO.setSummaryReportKey(formulaDO.getSummaryReportKey());
            formulaDTO.setUseCalculate(formulaDO.useCalculate());
            formulaDTO.setUseCheck(formulaDO.useCheck());
            formulaDTO.setCheckType(formulaDO.getCheckType());
            formulaDTO.setExpression(formulaDO.getExpression());
            formulaDTO.setDesc(formulaDO.getDesc());
            formulaDTO.setModifyTime(formulaDO.getModifyTime());
            formulaDTO.setOrder(formulaDO.getOrder());
            return formulaDTO;
        }

        @Override
        public SummaryFormulaDO DTO2DO(SummaryFormulaDTO formulaDTO) {
            if (ObjectUtils.isEmpty(formulaDTO)) {
                return null;
            }
            SummaryFormulaDO formulaDO = new SummaryFormulaDO();
            formulaDO.setKey(formulaDTO.getKey());
            formulaDO.setName(formulaDTO.getName());
            formulaDO.setSummarySolutionKey(formulaDTO.getSummarySolutionKey());
            formulaDO.setSummaryReportKey(formulaDTO.getSummaryReportKey());
            formulaDO.setUseCalculate(formulaDTO.useCalculate());
            formulaDO.setUseCheck(formulaDTO.useCheck());
            formulaDO.setCheckType(formulaDTO.getCheckType());
            formulaDO.setExpression(formulaDTO.getExpression());
            formulaDO.setDesc(formulaDTO.getDesc());
            formulaDO.setModifyTime(formulaDTO.getModifyTime());
            formulaDO.setOrder(formulaDTO.getOrder());
            return formulaDO;
        }

        @Override
        public Formula DTO2VO(SummaryFormulaDTO objectDTO) {
            return null;
        }

        @Override
        public SummaryFormulaDTO VO2DTO(Formula objectVO) {
            return null;
        }
    }

    private static class DesignSummaryFormulaConvert
    implements IObjectConvert<DesignSummaryFormulaDO, DesignSummaryFormulaDTO, Formula> {
        private DesignSummaryFormulaConvert() {
        }

        @Override
        public DesignSummaryFormulaDTO DO2DTO(DesignSummaryFormulaDO formulaDO) {
            if (ObjectUtils.isEmpty(formulaDO)) {
                return null;
            }
            DesignSummaryFormulaDTO formulaDTO = new DesignSummaryFormulaDTO();
            formulaDTO.setKey(formulaDO.getKey());
            formulaDTO.setName(formulaDO.getName());
            formulaDTO.setSummarySolutionKey(formulaDO.getSummarySolutionKey());
            formulaDTO.setSummaryReportKey(formulaDO.getSummaryReportKey());
            formulaDTO.setUseCalculate(formulaDO.useCalculate());
            formulaDTO.setUseCheck(formulaDO.useCheck());
            formulaDTO.setCheckType(formulaDO.getCheckType());
            formulaDTO.setExpression(formulaDO.getExpression());
            formulaDTO.setDesc(formulaDO.getDesc());
            formulaDTO.setModifyTime(formulaDO.getModifyTime());
            formulaDTO.setOrder(formulaDO.getOrder());
            return formulaDTO;
        }

        @Override
        public DesignSummaryFormulaDO DTO2DO(DesignSummaryFormulaDTO formulaDTO) {
            if (ObjectUtils.isEmpty(formulaDTO)) {
                return null;
            }
            DesignSummaryFormulaDO formulaDO = new DesignSummaryFormulaDO();
            formulaDO.setKey(formulaDTO.getKey());
            formulaDO.setName(formulaDTO.getName());
            formulaDO.setSummarySolutionKey(formulaDTO.getSummarySolutionKey());
            formulaDO.setSummaryReportKey(formulaDTO.getSummaryReportKey());
            formulaDO.setUseCalculate(formulaDTO.useCalculate());
            formulaDO.setUseCheck(formulaDTO.useCheck());
            formulaDO.setCheckType(formulaDTO.getCheckType());
            formulaDO.setExpression(formulaDTO.getExpression());
            formulaDO.setDesc(formulaDTO.getDesc());
            formulaDO.setModifyTime(formulaDTO.getModifyTime());
            formulaDO.setOrder(formulaDTO.getOrder());
            return formulaDO;
        }

        @Override
        public Formula DTO2VO(DesignSummaryFormulaDTO objectDTO) {
            if (ObjectUtils.isEmpty(objectDTO)) {
                return null;
            }
            Formula formula = new Formula();
            formula.setKey(objectDTO.getKey());
            formula.setFmCode(objectDTO.getName());
            formula.setSolutionId(objectDTO.getSummarySolutionKey());
            formula.setRptId(objectDTO.getSummaryReportKey());
            formula.setCalc(objectDTO.useCalculate());
            formula.setCheck(objectDTO.useCheck());
            formula.setCheckType(objectDTO.getCheckType());
            formula.setExpression(objectDTO.getExpression());
            formula.setDesc(objectDTO.getDesc());
            formula.setModifyTime(objectDTO.getModifyTime() == null ? null : new Date(objectDTO.getModifyTime().toEpochMilli()));
            return formula;
        }

        @Override
        public DesignSummaryFormulaDTO VO2DTO(Formula objectVO) {
            if (ObjectUtils.isEmpty(objectVO)) {
                return null;
            }
            DesignSummaryFormulaDTO summaryFormulaDTO = new DesignSummaryFormulaDTO();
            summaryFormulaDTO.setKey(objectVO.getKey());
            summaryFormulaDTO.setName(objectVO.getFmCode());
            summaryFormulaDTO.setSummarySolutionKey(objectVO.getSolutionId());
            summaryFormulaDTO.setSummaryReportKey(objectVO.getRptId());
            summaryFormulaDTO.setUseCalculate(objectVO.isCalc());
            summaryFormulaDTO.setUseCheck(objectVO.isCheck());
            summaryFormulaDTO.setCheckType(objectVO.getCheckType());
            summaryFormulaDTO.setExpression(objectVO.getExpression());
            summaryFormulaDTO.setDesc(objectVO.getDesc());
            summaryFormulaDTO.setModifyTime(objectVO.getModifyTime() == null ? null : Instant.ofEpochMilli(objectVO.getModifyTime().getTime()));
            return summaryFormulaDTO;
        }
    }

    private static class SummaryDataCellConvert
    implements IObjectConvert<SummaryDataCellDO, SummaryDataCellDTO, DataCell> {
        private SummaryDataCellConvert() {
        }

        @Override
        public SummaryDataCellDTO DO2DTO(SummaryDataCellDO dataCellDO) {
            SummaryDataCellDTO dataCellDTO = new SummaryDataCellDTO();
            dataCellDTO.setKey(dataCellDO.getKey());
            dataCellDTO.setReportKey(dataCellDTO.getReportKey());
            dataCellDTO.setX(dataCellDO.getX());
            dataCellDTO.setY(dataCellDO.getY());
            dataCellDTO.setRowNum(dataCellDO.getRowNum());
            dataCellDTO.setColNum(dataCellDO.getColNum());
            dataCellDTO.setExpression(dataCellDO.getExpression());
            dataCellDTO.setExpressionTitle(dataCellDO.getExpressionTitle());
            dataCellDTO.setReferDataFieldKey(dataCellDO.getReferDataFieldKey());
            dataCellDTO.setGatherType(dataCellDO.getGatherType());
            dataCellDTO.setModifyTime(dataCellDO.getModifyTime());
            dataCellDTO.setFieldName(dataCellDO.getFieldName());
            dataCellDTO.setFieldTitle(dataCellDO.getFieldTitle());
            dataCellDTO.setDataTableKey(dataCellDO.getDataTableKey());
            dataCellDTO.setFieldType(dataCellDO.getFieldType());
            dataCellDTO.setPrecision(dataCellDO.getPrecision());
            dataCellDTO.setDecimal(dataCellDO.getDecimal());
            return dataCellDTO;
        }

        @Override
        public SummaryDataCellDO DTO2DO(SummaryDataCellDTO dataCellDTO) {
            SummaryDataCellDO dataCellDO = new SummaryDataCellDO();
            dataCellDO.setKey(dataCellDTO.getKey());
            dataCellDO.setReportKey(dataCellDTO.getReportKey());
            dataCellDO.setX(dataCellDTO.getX());
            dataCellDO.setY(dataCellDTO.getY());
            dataCellDO.setRowNum(dataCellDTO.getRowNum());
            dataCellDO.setColNum(dataCellDTO.getColNum());
            dataCellDO.setExpression(dataCellDTO.getExpression());
            dataCellDO.setExpressionTitle(dataCellDTO.getExpressionTitle());
            dataCellDO.setReferDataFieldKey(dataCellDTO.getReferDataFieldKey());
            dataCellDO.setGatherType(dataCellDTO.getGatherType());
            dataCellDO.setModifyTime(dataCellDTO.getModifyTime());
            dataCellDO.setFieldName(dataCellDTO.getFieldName());
            dataCellDO.setFieldTitle(dataCellDTO.getFieldTitle());
            dataCellDO.setDataTableKey(dataCellDTO.getDataTableKey());
            dataCellDO.setFieldType(dataCellDTO.getFieldType());
            dataCellDO.setPrecision(dataCellDTO.getPrecision());
            dataCellDO.setDecimal(dataCellDTO.getDecimal());
            return dataCellDO;
        }

        @Override
        public DataCell DTO2VO(SummaryDataCellDTO dataCellDTO) {
            DataCell dataCell = new DataCell();
            dataCell.setKey(dataCellDTO.getKey());
            dataCell.setX(dataCellDTO.getX());
            dataCell.setY(dataCellDTO.getY());
            dataCell.setRowNum(dataCellDTO.getRowNum());
            dataCell.setColNum(dataCellDTO.getColNum());
            dataCell.setExp(dataCellDTO.getExpression());
            dataCell.setExpTitle(dataCellDTO.getExpressionTitle());
            dataCell.setSummaryMode(dataCellDTO.getGatherType());
            dataCell.setModifyTime(new Date(dataCellDTO.getModifyTime().toEpochMilli()));
            SummaryZb summaryZb = new SummaryZb();
            summaryZb.setFieldKey(dataCellDTO.getReferDataFieldKey());
            summaryZb.setName(dataCellDTO.getFieldName());
            summaryZb.setTitle(dataCellDTO.getFieldTitle());
            summaryZb.setTableName(dataCellDTO.getDataTableKey());
            summaryZb.setDataType(dataCellDTO.getFieldType());
            summaryZb.setPrecision(dataCellDTO.getPrecision());
            summaryZb.setDecimal(dataCellDTO.getDecimal());
            dataCell.setSummaryZb(summaryZb);
            return dataCell;
        }

        @Override
        public SummaryDataCellDTO VO2DTO(DataCell dataCellVO) {
            return designSummaryDataCellConvert.VO2DTO(dataCellVO);
        }
    }

    private static class DesignSummaryDataCellConvert
    implements IObjectConvert<DesignSummaryDataCellDO, DesignSummaryDataCellDTO, DataCell> {
        private DesignSummaryDataCellConvert() {
        }

        @Override
        public DesignSummaryDataCellDTO DO2DTO(DesignSummaryDataCellDO dataCellDO) {
            DesignSummaryDataCellDTO dataCellDTO = new DesignSummaryDataCellDTO();
            dataCellDTO.setKey(dataCellDO.getKey());
            dataCellDTO.setReportKey(dataCellDO.getReportKey());
            dataCellDTO.setX(dataCellDO.getX());
            dataCellDTO.setY(dataCellDO.getY());
            dataCellDTO.setRowNum(dataCellDO.getRowNum());
            dataCellDTO.setColNum(dataCellDO.getColNum());
            dataCellDTO.setExpression(dataCellDO.getExpression());
            dataCellDTO.setExpressionTitle(dataCellDO.getExpressionTitle());
            dataCellDTO.setReferDataFieldKey(dataCellDO.getReferDataFieldKey());
            dataCellDTO.setGatherType(dataCellDO.getGatherType());
            dataCellDTO.setModifyTime(dataCellDO.getModifyTime());
            dataCellDTO.setFieldName(dataCellDO.getFieldName());
            dataCellDTO.setFieldTitle(dataCellDO.getFieldTitle());
            dataCellDTO.setDataTableKey(dataCellDO.getDataTableKey());
            dataCellDTO.setFieldType(dataCellDO.getFieldType());
            dataCellDTO.setPrecision(dataCellDO.getPrecision());
            dataCellDTO.setDecimal(dataCellDO.getDecimal());
            return dataCellDTO;
        }

        @Override
        public DesignSummaryDataCellDO DTO2DO(DesignSummaryDataCellDTO dataCellDTO) {
            DesignSummaryDataCellDO dataCellDO = new DesignSummaryDataCellDO();
            dataCellDO.setKey(StringUtils.hasLength(dataCellDTO.getKey()) ? dataCellDTO.getKey() : UUID.randomUUID().toString());
            dataCellDO.setReportKey(dataCellDTO.getReportKey());
            dataCellDO.setX(dataCellDTO.getX());
            dataCellDO.setY(dataCellDTO.getY());
            dataCellDO.setRowNum(dataCellDTO.getRowNum());
            dataCellDO.setColNum(dataCellDTO.getColNum());
            dataCellDO.setExpression(dataCellDTO.getExpression());
            dataCellDO.setExpressionTitle(dataCellDTO.getExpressionTitle());
            dataCellDO.setReferDataFieldKey(dataCellDTO.getReferDataFieldKey());
            dataCellDO.setGatherType(dataCellDTO.getGatherType());
            dataCellDO.setModifyTime(dataCellDTO.getModifyTime());
            dataCellDO.setFieldName(dataCellDTO.getFieldName());
            dataCellDO.setFieldTitle(dataCellDTO.getFieldTitle());
            dataCellDO.setDataTableKey(dataCellDTO.getDataTableKey());
            dataCellDO.setFieldType(dataCellDTO.getFieldType());
            dataCellDO.setPrecision(dataCellDTO.getPrecision());
            dataCellDO.setDecimal(dataCellDTO.getDecimal());
            dataCellDO.setModifyTime(dataCellDTO.getModifyTime() == null ? Instant.now() : dataCellDTO.getModifyTime());
            return dataCellDO;
        }

        @Override
        public DataCell DTO2VO(DesignSummaryDataCellDTO dataCellDTO) {
            return summaryDataCellConvert.DTO2VO(dataCellDTO);
        }

        @Override
        public DesignSummaryDataCellDTO VO2DTO(DataCell dataCellVO) {
            DesignSummaryDataCellDTO dataCellDTO = new DesignSummaryDataCellDTO();
            dataCellDTO.setKey(StringUtils.hasLength(dataCellVO.getKey()) ? dataCellVO.getKey() : UUID.randomUUID().toString());
            dataCellDTO.setX(dataCellVO.getX());
            dataCellDTO.setY(dataCellVO.getY());
            dataCellDTO.setRowNum(dataCellVO.getRowNum());
            dataCellDTO.setColNum(dataCellVO.getColNum());
            dataCellDTO.setExpression(dataCellVO.getExp());
            dataCellDTO.setExpressionTitle(dataCellVO.getExpTitle());
            dataCellDTO.setGatherType(dataCellVO.getSummaryMode());
            SummaryZb summaryZb = dataCellVO.getSummaryZb();
            dataCellDTO.setReferDataFieldKey(summaryZb.getFieldKey());
            dataCellDTO.setFieldName(summaryZb.getName());
            dataCellDTO.setFieldTitle(summaryZb.getTitle());
            dataCellDTO.setDataTableKey(summaryZb.getTableName());
            dataCellDTO.setFieldType(summaryZb.getDataType());
            dataCellDTO.setPrecision(summaryZb.getPrecision());
            dataCellDTO.setDecimal(summaryZb.getDecimal());
            dataCellDTO.setModifyTime(dataCellDTO.getModifyTime() == null ? Instant.now() : dataCellDTO.getModifyTime());
            return dataCellDTO;
        }
    }

    private static class SummaryReportConvert
    implements IObjectConvert<SummaryReportDO, SummaryReportDTO, SummaryReport> {
        private SummaryReportConvert() {
        }

        @Override
        public SummaryReportDTO DO2DTO(SummaryReportDO reportDO) {
            SummaryReportDTO reportDTO = new SummaryReportDTO();
            reportDTO.setKey(reportDO.getKey());
            reportDTO.setName(reportDO.getName());
            reportDTO.setTitle(reportDO.getTitle());
            reportDTO.setSummarySolutionKey(reportDO.getSummarySolutionKey());
            reportDTO.setFilter(reportDO.getFilter());
            reportDTO.setConfig(reportDO.getConfig());
            reportDTO.setGridData(reportDO.getGridData());
            reportDTO.setPageConfig(reportDO.getPageConfig());
            reportDTO.setModifyTime(reportDO.getModifyTime());
            reportDTO.setOrder(reportDO.getOrder());
            return reportDTO;
        }

        @Override
        public SummaryReportDO DTO2DO(SummaryReportDTO reportDTO) {
            SummaryReportDO reportDO = new SummaryReportDO();
            reportDO.setKey(reportDTO.getKey());
            reportDO.setName(reportDTO.getName());
            reportDO.setTitle(reportDTO.getTitle());
            reportDO.setSummarySolutionKey(reportDTO.getSummarySolutionKey());
            reportDO.setFilter(reportDTO.getFilter());
            reportDO.setConfig(reportDTO.getConfig());
            reportDO.setGridData(reportDTO.getGridData());
            reportDO.setPageConfig(reportDTO.getPageConfig());
            reportDO.setModifyTime(reportDTO.getModifyTime());
            reportDO.setOrder(reportDTO.getOrder());
            return reportDO;
        }

        @Override
        public SummaryReport DTO2VO(SummaryReportDTO reportDTO) {
            SummaryReport summaryReport = new SummaryReport();
            summaryReport.setKey(reportDTO.getKey());
            summaryReport.setName(reportDTO.getName());
            summaryReport.setTitle(reportDTO.getTitle());
            summaryReport.setSolution(reportDTO.getSummarySolutionKey());
            summaryReport.setModifyTime(new Date(reportDTO.getModifyTime().toEpochMilli()));
            summaryReport.setOrder(reportDTO.getOrder());
            return summaryReport;
        }

        @Override
        public SummaryReportDTO VO2DTO(SummaryReport objectVO) {
            return null;
        }
    }

    private static class DesignSummaryReportConvert
    implements IObjectConvert<DesignSummaryReportDO, DesignSummaryReportDTO, SummaryReportModelVO> {
        private DesignSummaryReportConvert() {
        }

        @Override
        public DesignSummaryReportDTO DO2DTO(DesignSummaryReportDO reportDO) {
            DesignSummaryReportDTO reportDTO = new DesignSummaryReportDTO();
            reportDTO.setKey(reportDO.getKey());
            reportDTO.setName(reportDO.getName());
            reportDTO.setTitle(reportDO.getTitle());
            reportDTO.setSummarySolutionKey(reportDO.getSummarySolutionKey());
            reportDTO.setFilter(reportDO.getFilter());
            reportDTO.setConfig(reportDO.getConfig());
            reportDTO.setGridData(reportDO.getGridData());
            reportDTO.setPageConfig(reportDO.getPageConfig());
            reportDTO.setModifyTime(reportDO.getModifyTime());
            reportDTO.setOrder(reportDO.getOrder());
            return reportDTO;
        }

        @Override
        public DesignSummaryReportDO DTO2DO(DesignSummaryReportDTO reportDTO) {
            DesignSummaryReportDO reportDO = new DesignSummaryReportDO();
            reportDO.setKey(reportDTO.getKey());
            reportDO.setName(reportDTO.getName());
            reportDO.setTitle(reportDTO.getTitle());
            reportDO.setSummarySolutionKey(reportDTO.getSummarySolutionKey());
            reportDO.setFilter(reportDTO.getFilter());
            reportDO.setConfig(reportDTO.getConfig());
            reportDO.setGridData(reportDTO.getGridData());
            reportDO.setPageConfig(reportDTO.getPageConfig());
            reportDO.setModifyTime(reportDTO.getModifyTime());
            reportDO.setOrder(reportDTO.getOrder());
            return reportDO;
        }

        @Override
        public SummaryReportModelVO DTO2VO(DesignSummaryReportDTO objectDTO) {
            return null;
        }

        @Override
        public DesignSummaryReportDTO VO2DTO(SummaryReportModelVO reportVO) {
            SummaryReportModel reportModel = reportVO.getReportModel();
            DesignSummaryReportDTO reportDTO = new DesignSummaryReportDTO();
            reportDTO.setKey(reportModel.getKey());
            reportDTO.setName(reportModel.getName());
            reportDTO.setTitle(reportModel.getTitle());
            reportDTO.setSummarySolutionKey(reportVO.getSolutionKey());
            reportDTO.setSequenceType(reportModel.getConfig().getSequenceType());
            reportDTO.setFilter(reportModel.getConfig().getFilter());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GridData gridData;
                reportDTO.setConfig(objectMapper.writeValueAsString((Object)reportModel.getConfig()));
                if (!ObjectUtils.isEmpty(reportVO.getCellBook()) && !ObjectUtils.isEmpty(gridData = CellBookGriddataConverter.cellBookToGridData((CellSheet)((CellSheet)reportVO.getCellBook().getSheets().get(0)), (GridData)new GridData()))) {
                    reportDTO.setGridData(objectMapper.writeValueAsString((Object)new SummaryReportData(gridData)));
                }
                reportDTO.setPageConfig(objectMapper.writeValueAsString((Object)reportModel.getPageConfig()));
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
            return reportDTO;
        }
    }

    private static class SummaryReportModelConvert
    implements IObjectConvert<SummaryReportDO, SummaryReportDTO, SummaryReportModel> {
        private SummaryReportModelConvert() {
        }

        @Override
        public SummaryReportDTO DO2DTO(SummaryReportDO objectDO) {
            return null;
        }

        @Override
        public SummaryReportDO DTO2DO(SummaryReportDTO objectDTO) {
            return null;
        }

        @Override
        public SummaryReportModel DTO2VO(SummaryReportDTO modelDTO) {
            SummaryReportModel modelVO = new SummaryReportModel();
            modelVO.setKey(modelDTO.getKey());
            modelVO.setName(modelDTO.getName());
            modelVO.setTitle(modelDTO.getTitle());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                if (StringUtils.hasLength(modelDTO.getConfig())) {
                    modelVO.setConfig((SummaryReportConfig)objectMapper.readValue(modelDTO.getConfig(), SummaryReportConfig.class));
                }
                if (StringUtils.hasLength(modelDTO.getGridData())) {
                    modelVO.setReportData((SummaryReportData)objectMapper.readValue(modelDTO.getGridData(), SummaryReportData.class));
                }
                if (StringUtils.hasLength(modelDTO.getPageConfig())) {
                    modelVO.setPageConfig((QueryPageConfig)objectMapper.readValue(modelDTO.getPageConfig(), QueryPageConfig.class));
                }
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
            return modelVO;
        }

        @Override
        public SummaryReportDTO VO2DTO(SummaryReportModel reportModelVO) {
            SummaryReportDTO reportDTO = new SummaryReportDTO();
            reportDTO.setKey(reportModelVO.getKey());
            reportDTO.setName(reportModelVO.getName());
            reportDTO.setTitle(reportModelVO.getTitle());
            reportDTO.setSequenceType(reportModelVO.getConfig().getSequenceType());
            reportDTO.setFilter(reportModelVO.getConfig().getFilter());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                reportDTO.setConfig(objectMapper.writeValueAsString((Object)reportModelVO.getConfig()));
                reportDTO.setGridData(objectMapper.writeValueAsString((Object)reportModelVO.getReportData()));
                reportDTO.setPageConfig(objectMapper.writeValueAsString((Object)reportModelVO.getPageConfig()));
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
            return reportDTO;
        }
    }

    private static class DesignSummaryReportModelConvert
    implements IObjectConvert<DesignSummaryReportDO, DesignSummaryReportDTO, SummaryReportModel> {
        private DesignSummaryReportModelConvert() {
        }

        @Override
        public DesignSummaryReportDTO DO2DTO(DesignSummaryReportDO modelDO) {
            return designSummaryReportConvert.DO2DTO(modelDO);
        }

        @Override
        public DesignSummaryReportDO DTO2DO(DesignSummaryReportDTO modelDTO) {
            return designSummaryReportConvert.DTO2DO(modelDTO);
        }

        @Override
        public SummaryReportModel DTO2VO(DesignSummaryReportDTO modelDTO) {
            return summaryReportModelConvert.DTO2VO(modelDTO);
        }

        @Override
        public DesignSummaryReportDTO VO2DTO(SummaryReportModel reportModelVO) {
            DesignSummaryReportDTO reportDTO = new DesignSummaryReportDTO();
            reportDTO.setKey(reportModelVO.getKey());
            reportDTO.setName(reportModelVO.getName());
            reportDTO.setTitle(reportModelVO.getTitle());
            reportDTO.setSequenceType(reportModelVO.getConfig().getSequenceType());
            reportDTO.setFilter(reportModelVO.getConfig().getFilter());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                reportDTO.setConfig(objectMapper.writeValueAsString((Object)reportModelVO.getConfig()));
                reportDTO.setGridData(objectMapper.writeValueAsString((Object)reportModelVO.getReportData()));
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
            return reportDTO;
        }
    }

    private static class BaseDesignSummaryReportConvert
    implements IObjectConvert<DesignSummaryReportDO, DesignSummaryReportDTO, SummaryReport> {
        private BaseDesignSummaryReportConvert() {
        }

        @Override
        public DesignSummaryReportDTO DO2DTO(DesignSummaryReportDO reportDO) {
            DesignSummaryReportDTO reportDTO = new DesignSummaryReportDTO();
            reportDTO.setKey(reportDO.getKey());
            reportDTO.setName(reportDO.getName());
            reportDTO.setTitle(reportDO.getTitle());
            reportDTO.setSummarySolutionKey(reportDO.getSummarySolutionKey());
            reportDTO.setFilter(reportDO.getFilter());
            reportDTO.setConfig(reportDO.getConfig());
            reportDTO.setGridData(reportDO.getGridData());
            reportDTO.setModifyTime(reportDO.getModifyTime());
            reportDTO.setOrder(reportDO.getOrder());
            return reportDTO;
        }

        @Override
        public DesignSummaryReportDO DTO2DO(DesignSummaryReportDTO reportDTO) {
            DesignSummaryReportDO reportDO = new DesignSummaryReportDO();
            reportDO.setKey(reportDTO.getKey());
            reportDO.setName(reportDTO.getName());
            reportDO.setTitle(reportDTO.getTitle());
            reportDO.setSummarySolutionKey(reportDTO.getSummarySolutionKey());
            reportDO.setFilter(reportDTO.getFilter());
            reportDO.setConfig(reportDTO.getConfig());
            reportDO.setGridData(reportDTO.getGridData());
            reportDO.setModifyTime(reportDTO.getModifyTime());
            reportDO.setOrder(reportDTO.getOrder());
            return reportDO;
        }

        @Override
        public SummaryReport DTO2VO(DesignSummaryReportDTO reportDTO) {
            SummaryReport reportVO = new SummaryReport();
            reportVO.setKey(reportDTO.getKey());
            reportVO.setName(reportDTO.getName());
            reportVO.setTitle(reportDTO.getTitle());
            reportVO.setSolution(reportDTO.getSummarySolutionKey());
            reportVO.setModifyTime(new Date(reportDTO.getModifyTime().toEpochMilli()));
            reportVO.setOrder(reportDTO.getOrder());
            return reportVO;
        }

        @Override
        public DesignSummaryReportDTO VO2DTO(SummaryReport reportVO) {
            DesignSummaryReportDTO reportDTO = new DesignSummaryReportDTO();
            reportDTO.setKey(reportVO.getKey());
            reportDTO.setName(reportVO.getName());
            reportDTO.setTitle(reportVO.getTitle());
            reportDTO.setSummarySolutionKey(reportVO.getSolution());
            reportDTO.setOrder(reportVO.getOrder());
            return reportDTO;
        }
    }

    private static class SummarySolutionModelConvert
    implements IObjectConvert<SummarySolutionDO, SummarySolutionDTO, SummarySolutionModel> {
        private SummarySolutionModelConvert() {
        }

        @Override
        public SummarySolutionDTO DO2DTO(SummarySolutionDO solutionDO) {
            SummarySolutionDTO solutionDTO = new SummarySolutionDTO();
            solutionDTO.setKey(solutionDO.getKey());
            solutionDTO.setName(solutionDO.getName());
            solutionDTO.setTitle(solutionDO.getTitle());
            solutionDTO.setGroup(solutionDO.getGroup());
            solutionDTO.setMainTask(solutionDO.getMainTask());
            solutionDTO.setTaskConfigData(solutionDO.getTaskConfigData());
            solutionDTO.setTargetDimension(solutionDO.getTargetDimension());
            solutionDTO.setModifyTime(solutionDO.getModifyTime());
            solutionDTO.setOrder(solutionDO.getOrder());
            return solutionDTO;
        }

        @Override
        public SummarySolutionDO DTO2DO(SummarySolutionDTO solutionDTO) {
            SummarySolutionDO solutionDO = new SummarySolutionDO();
            solutionDO.setKey(solutionDTO.getKey());
            solutionDO.setName(solutionDTO.getName());
            solutionDO.setTitle(solutionDTO.getTitle());
            solutionDO.setGroup(solutionDTO.getGroup());
            solutionDO.setMainTask(solutionDTO.getMainTask());
            solutionDO.setTaskConfigData(solutionDTO.getTaskConfigData());
            solutionDO.setTargetDimension(solutionDTO.getTargetDimension());
            solutionDO.setModifyTime(solutionDTO.getModifyTime());
            solutionDO.setOrder(solutionDTO.getOrder());
            return solutionDO;
        }

        @Override
        public SummarySolutionModel DTO2VO(SummarySolutionDTO solutionDTO) {
            SummarySolutionModel solutionVO = new SummarySolutionModel();
            solutionVO.setKey(solutionDTO.getKey());
            solutionVO.setName(solutionDTO.getName());
            solutionVO.setTitle(solutionDTO.getTitle());
            solutionVO.setGroup(solutionDTO.getGroup());
            solutionVO.setMainTask(solutionDTO.getMainTask());
            solutionVO.setTargetDimension(solutionDTO.getTargetDimension());
            String taskConfigData = solutionDTO.getTaskConfigData();
            JSONObject taskConfigDataJson = new JSONObject(taskConfigData);
            String relationTasks = taskConfigDataJson.optString("relationTasks");
            if (StringUtils.hasLength(relationTasks)) {
                solutionVO.setRelationTasks(Arrays.asList(relationTasks.split(",")));
            }
            int targetDimensionRangeValue = taskConfigDataJson.optInt("targetDimensionRange");
            TargetDimensionRange targetDimensionRange = TargetDimensionRange.valueOf(targetDimensionRangeValue);
            solutionVO.setTargetDimensionRange(targetDimensionRange);
            if (targetDimensionRange == TargetDimensionRange.LIST) {
                String targetDimensionValues = taskConfigDataJson.optString("targetDimensionValues");
                if (StringUtils.hasLength(targetDimensionValues)) {
                    ArrayList<String> targetDimensionValueList = new ArrayList<String>();
                    JSONArray jsonArray = new JSONArray(targetDimensionValues);
                    jsonArray.forEach(value -> targetDimensionValueList.add(value.toString()));
                    solutionVO.setTargetDimensionValues(targetDimensionValueList);
                }
            } else if (targetDimensionRange == TargetDimensionRange.CONDITION) {
                String targetDimensionFilter = taskConfigDataJson.optString("targetDimensionFilter");
                solutionVO.setTargetDimensionFilter(targetDimensionFilter);
            }
            int sourceDimensionRangeValue = taskConfigDataJson.optInt("sourceDimensionRange");
            SourceDimensionRange sourceDimensionRange = SourceDimensionRange.valueOf(sourceDimensionRangeValue);
            solutionVO.setSourceDimensionRange(sourceDimensionRange);
            if (sourceDimensionRange == SourceDimensionRange.LIST) {
                String sourceDimensionValues = taskConfigDataJson.optString("sourceDimensionValues");
                if (StringUtils.hasLength(sourceDimensionValues)) {
                    ArrayList<String> sourceDimensionValueList = new ArrayList<String>();
                    JSONArray jsonArray = new JSONArray(sourceDimensionValues);
                    jsonArray.forEach(value -> sourceDimensionValueList.add(value.toString()));
                    solutionVO.setSourceDimensionValues(sourceDimensionValueList);
                }
            } else if (sourceDimensionRange == SourceDimensionRange.CONDITION) {
                solutionVO.setSourceDimensionFilter(taskConfigDataJson.optString("sourceDimensionFilter"));
            }
            int commitState = taskConfigDataJson.optInt("commitState");
            solutionVO.setCommitState(CommitState.valueOf(commitState));
            ArrayList<DimensionData> sceneDims = new ArrayList<DimensionData>();
            String dimDataRanges = taskConfigDataJson.optString("dimDataRange");
            if (StringUtils.hasLength(dimDataRanges)) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JSONArray dimDataArr = new JSONArray(dimDataRanges);
                    for (Object dimData : dimDataArr) {
                        sceneDims.add((DimensionData)objectMapper.readValue(dimData.toString(), DimensionData.class));
                    }
                    solutionVO.setDimDataRange(sceneDims);
                }
                catch (JsonProcessingException e) {
                    logger.error(e.getMessage(), e);
                }
            } else {
                solutionVO.setDimDataRange(sceneDims);
            }
            return solutionVO;
        }

        @Override
        public SummarySolutionDTO VO2DTO(SummarySolutionModel solutionVO) {
            SummarySolutionDTO solutionDTO = new SummarySolutionDTO();
            solutionDTO.setKey(solutionVO.getKey());
            solutionDTO.setName(solutionVO.getName());
            solutionDTO.setTitle(solutionVO.getTitle());
            solutionDTO.setGroup(solutionVO.getGroup());
            solutionDTO.setMainTask(solutionVO.getMainTask());
            solutionDTO.setTaskConfigData(this.buildTaskConfigData(solutionVO));
            solutionDTO.setTargetDimension(solutionVO.getTargetDimension());
            Date modifyTime = solutionVO.getModifyTime();
            solutionDTO.setModifyTime(modifyTime == null ? null : Instant.ofEpochMilli(solutionVO.getModifyTime().getTime()));
            solutionDTO.setOrder(solutionVO.getOrder());
            return solutionDTO;
        }

        private String buildTaskConfigData(SummarySolutionModel solutionVO) {
            JSONObject taskConfigJson = new JSONObject();
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<String> relationTasks = solutionVO.getRelationTasks();
                if (!CollectionUtils.isEmpty(relationTasks)) {
                    taskConfigJson.put("relationTasks", (Object)String.join((CharSequence)",", relationTasks));
                }
                TargetDimensionRange targetDimensionRange = solutionVO.getTargetDimensionRange();
                taskConfigJson.put("targetDimensionRange", targetDimensionRange.value());
                if (targetDimensionRange == TargetDimensionRange.LIST) {
                    List<String> targetDimensionValues = solutionVO.getTargetDimensionValues();
                    if (!CollectionUtils.isEmpty(targetDimensionValues)) {
                        taskConfigJson.put("targetDimensionValues", (Object)mapper.writeValueAsString(targetDimensionValues));
                    }
                } else if (targetDimensionRange == TargetDimensionRange.CONDITION) {
                    taskConfigJson.put("targetDimensionFilter", (Object)solutionVO.getTargetDimensionFilter());
                }
                SourceDimensionRange sourceDimensionRange = solutionVO.getSourceDimensionRange();
                taskConfigJson.put("sourceDimensionRange", sourceDimensionRange.value());
                if (sourceDimensionRange == SourceDimensionRange.LIST) {
                    List<String> sourceDimensionValues = solutionVO.getSourceDimensionValues();
                    if (!CollectionUtils.isEmpty(sourceDimensionValues)) {
                        taskConfigJson.put("sourceDimensionValues", (Object)mapper.writeValueAsString(sourceDimensionValues));
                    }
                } else if (sourceDimensionRange == SourceDimensionRange.CONDITION) {
                    taskConfigJson.put("sourceDimensionFilter", (Object)solutionVO.getSourceDimensionFilter());
                }
                taskConfigJson.put("commitState", solutionVO.getCommitState().value());
                List<DimensionData> dimDataRange = solutionVO.getDimDataRange();
                if (!CollectionUtils.isEmpty(dimDataRange)) {
                    taskConfigJson.put("dimDataRange", (Object)mapper.writeValueAsString(dimDataRange));
                }
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
            return taskConfigJson.toString();
        }
    }

    private static class SummarySolutionConvert
    implements IObjectConvert<SummarySolutionDO, SummarySolutionDTO, SummarySolution> {
        private SummarySolutionConvert() {
        }

        @Override
        public SummarySolutionDTO DO2DTO(SummarySolutionDO solutionDO) {
            return summarySolutionModelConvert.DO2DTO(solutionDO);
        }

        @Override
        public SummarySolutionDO DTO2DO(SummarySolutionDTO objectDTO) {
            return null;
        }

        @Override
        public SummarySolution DTO2VO(SummarySolutionDTO solutionDTO) {
            SummarySolution solutionVO = new SummarySolution();
            solutionVO.setKey(solutionDTO.getKey());
            solutionVO.setName(solutionDTO.getName());
            solutionVO.setTitle(solutionDTO.getTitle());
            solutionVO.setGroup(solutionDTO.getGroup());
            solutionVO.setMainTask(solutionDTO.getMainTask());
            solutionVO.setTargetDimension(solutionDTO.getTargetDimension());
            solutionVO.setOrder(solutionDTO.getOrder());
            solutionVO.setModifyTime(new Date(solutionDTO.getModifyTime().toEpochMilli()));
            return solutionVO;
        }

        @Override
        public SummarySolutionDTO VO2DTO(SummarySolution objectVO) {
            return null;
        }
    }

    private static class SummarySolutionGroupConvert
    implements IObjectConvert<SummarySolutionGroupDO, SummarySolutionGroupDTO, SummarySolutionGroup> {
        private SummarySolutionGroupConvert() {
        }

        @Override
        public SummarySolutionGroupDTO DO2DTO(SummarySolutionGroupDO groupDO) {
            SummarySolutionGroupDTO groupDTO = new SummarySolutionGroupDTO();
            groupDTO.setKey(groupDO.getKey());
            groupDTO.setTitle(groupDO.getTitle());
            groupDTO.setParent(groupDO.getParent());
            groupDTO.setDesc(groupDO.getDesc());
            groupDTO.setModifyTime(groupDO.getModifyTime());
            groupDTO.setOrder(groupDO.getOrder());
            return groupDTO;
        }

        @Override
        public SummarySolutionGroupDO DTO2DO(SummarySolutionGroupDTO groupDTO) {
            SummarySolutionGroupDO groupDO = new SummarySolutionGroupDO();
            groupDO.setKey(groupDTO.getKey());
            groupDO.setTitle(groupDTO.getTitle());
            groupDO.setParent(groupDTO.getParent());
            groupDO.setDesc(groupDTO.getDesc());
            groupDO.setModifyTime(groupDTO.getModifyTime());
            groupDO.setOrder(groupDTO.getOrder());
            return groupDO;
        }

        @Override
        public SummarySolutionGroup DTO2VO(SummarySolutionGroupDTO groupDTO) {
            SummarySolutionGroup groupVO = new SummarySolutionGroup();
            groupVO.setKey(groupDTO.getKey());
            groupVO.setTitle(groupDTO.getTitle());
            groupVO.setParent(groupDTO.getParent());
            groupVO.setOrder(groupDTO.getOrder());
            groupVO.setModifyTime(new Date(groupDTO.getModifyTime().toEpochMilli()));
            return groupVO;
        }

        @Override
        public SummarySolutionGroupDTO VO2DTO(SummarySolutionGroup groupVO) {
            SummarySolutionGroupDTO groupDTO = new SummarySolutionGroupDTO();
            groupDTO.setKey(groupVO.getKey());
            groupDTO.setTitle(groupVO.getTitle());
            groupDTO.setParent(groupVO.getParent());
            groupDTO.setOrder(groupVO.getOrder());
            Date modifyTime = groupVO.getModifyTime();
            groupDTO.setModifyTime(modifyTime == null ? null : Instant.ofEpochMilli(groupVO.getModifyTime().getTime()));
            return groupDTO;
        }
    }
}

