/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffSetItemAdjustReportService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcOffSetItemAdjustReportServiceImpl
extends GcOffSetItemAdjustServiceAbstract
implements GcOffSetItemAdjustReportService {
    @Autowired
    private InputDataDao inputdataDao;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcOffSetAppOffsetService gcOffSetAppOffsetService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Map<String, Object>> relateRecords(String recordId, String taskId, List<String> otherColumnCodes) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        EntNativeSqlDefaultDao<InputDataEO> inputDataDao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        InputDataEO inputData = new InputDataEO();
        inputData.setId(recordId);
        inputData.setBizkeyOrder(recordId);
        InputDataEO inputDataEO = (InputDataEO)inputDataDao.selectByEntity((BaseEntity)inputData);
        if (inputDataEO == null || ReportOffsetStateEnum.OFFSET.getValue().equals(inputDataEO.getOffsetState())) {
            return new ArrayList<Map<String, Object>>();
        }
        List<Object> unOffsetRecords = new ArrayList<Map>();
        String ruleTitle = "";
        if (null == inputDataEO.getUnionRuleId()) {
            unOffsetRecords.add(inputDataEO.getFields());
            ArrayList<String> otherShowColumns = new ArrayList<String>();
            otherShowColumns.add("AREACODE");
            otherShowColumns.add("YWBKCODE");
            otherShowColumns.add("GCYWLXCODE");
            otherShowColumns.add("TZYZMSCODE");
            otherShowColumns.add("PROJECTTITLE");
            otherShowColumns.addAll(otherColumnCodes.stream().filter(columnCode -> !otherShowColumns.contains(columnCode)).collect(Collectors.toList()));
            Map fieldCode2DictTableMap = this.initFieldCode2DictTableMap(otherShowColumns);
            boolean showDictCode = this.optionService.getOptionItemBooleanBySchemeId(this.getSchemeKey(inputDataEO.getPeriod(), inputDataEO.getTaskId()), null, "showDictCode");
            this.setOtherShowColumnDictTitle(inputDataEO.getFields(), otherShowColumns, fieldCode2DictTableMap, showDictCode);
        } else {
            boolean offsetGroupingContainsDc;
            QueryParamsVO queryParamsVO = this.relateRecordsInitParam(inputDataEO);
            queryParamsVO.setDataSourceCode(DataSourceEnum.INPUT_DATA.getCode());
            queryParamsVO.setActionCode("query");
            queryParamsVO.setPageCode(TabSelectEnum.NOT_OFFSET_PAGE.getCode());
            queryParamsVO.setFilterMethod(FilterMethodEnum.AMT.getCode());
            HashMap<String, List<String>> filterCondition = new HashMap<String, List<String>>();
            filterCondition.put("ruleId", Collections.singletonList(inputDataEO.getUnionRuleId()));
            queryParamsVO.setFilterCondition(filterCondition);
            unOffsetRecords = this.gcOffSetAppOffsetService.listOffsetRecordsForAction(queryParamsVO).getContent();
            AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)inputDataEO.getUnionRuleId());
            HashSet<String> ruleSubjectCodes = new HashSet<String>();
            if (null != rule) {
                ruleTitle = rule.getLocalizedName();
                if (RuleTypeEnum.FLEXIBLE.getCode().equals(rule.getRuleType())) {
                    FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)rule;
                    List offsetGroupingFields = flexibleRuleDTO.getOffsetGroupingField();
                    this.flexRuleAllSrcSubjectCodes(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr(), flexibleRuleDTO, ruleSubjectCodes);
                    offsetGroupingContainsDc = offsetGroupingFields.contains("DC");
                } else {
                    offsetGroupingContainsDc = false;
                }
            } else {
                offsetGroupingContainsDc = false;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList filteredRecordIds = new ArrayList();
            unOffsetRecords = unOffsetRecords.stream().filter(record -> {
                if (rule != null && rule.getRuleType().equals(RuleTypeEnum.FLEXIBLE.getCode()) && !ruleSubjectCodes.contains(record.get("SUBJECTCODE"))) {
                    filteredSubjectCodes.add(record.get("SUBJECTCODE"));
                    return false;
                }
                if (!offsetGroupingContainsDc) {
                    return true;
                }
                if (null != inputDataEO.getUnionRuleId() && null != record.get("UNIONRULEID") && !inputDataEO.getUnionRuleId().equals(record.get("UNIONRULEID"))) {
                    return false;
                }
                String unitId = (String)record.get("MDCODE");
                String oppUnitId = (String)record.get("OPPUNITID");
                Integer dc = (Integer)record.get("DC");
                if (inputDataEO.getUnitId().equals(unitId) && inputDataEO.getOppUnitId().equals(oppUnitId) && !inputDataEO.getDc().equals(dc)) {
                    filteredRecordIds.add(record.get("ID"));
                    return false;
                }
                if (inputDataEO.getOppUnitId().equals(unitId) && inputDataEO.getUnitId().equals(oppUnitId) && inputDataEO.getDc().equals(dc)) {
                    filteredRecordIds.add(record.get("ID"));
                    return false;
                }
                return true;
            }).sorted((record1, record2) -> {
                int result = MapUtils.compareInt((Map)record2, (Map)record1, (Object)"DC");
                if (result != 0) {
                    return result;
                }
                Double amt1 = ConverterUtils.getAsDouble((Object)((String)record1.get("AMT")).replace(",", ""));
                Double amt2 = ConverterUtils.getAsDouble((Object)((String)record2.get("AMT")).replace(",", ""));
                return amt1.compareTo(amt2);
            }).collect(Collectors.toList());
            Map fieldCode2DictTableMap = this.initFieldCode2DictTableMap(otherColumnCodes, tableName);
            ConsolidatedOptionVO optionVO = this.optionService.getOptionDataBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
            boolean showDictCode = optionVO != null && "1".equals(optionVO.getShowDictCode());
            unOffsetRecords.forEach(record -> this.setOtherShowColumnDictTitle((Map)record, otherColumnCodes, fieldCode2DictTableMap, showDictCode));
            this.logger.info("\u539f\u59cb\u8bb0\u5f55Id:{},\u6570\u636e\u6e90\u8fc7\u6ee4\u6389\u7684\u79d1\u76ee:{},\u501f\u8d37\u65b9\u5411\u8fc7\u6ee4\u6389\u7684\u8bb0\u5f55id\uff1a{}", recordId, CollectionUtils.toString(arrayList), CollectionUtils.toString(filteredRecordIds));
        }
        HashMap unitId2Title = new HashMap(32);
        HashMap subject2TitleCache = new HashMap(32);
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataEO.getTaskId(), inputDataEO.getPeriod());
        Set<String> numberColumnSet = this.getNumberColumnSetByColumnCodes(otherColumnCodes, tableName);
        for (Map map : unOffsetRecords) {
            map.put("UNITTITLE", this.getUnitTitle((String)map.get("MDCODE"), unitId2Title));
            map.put("OPPUNITTITLE", this.getUnitTitle((String)map.get("OPPUNITID"), unitId2Title));
            this.setSubjectTitle(systemId, map, subject2TitleCache, "SUBJECTTITLE", "SUBJECTCODE");
            map.put("RULETITLE", ruleTitle);
            for (String column : numberColumnSet) {
                map.put(column, NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble(map.get(column))));
            }
        }
        return unOffsetRecords;
    }

    private Set<String> getNumberColumnSetByColumnCodes(List<String> otherColumnCodes, String tableName) {
        Map key2IdentityMap = this.inputdataDao.queryUnOffsetColumnSelect(tableName).stream().collect(Collectors.toMap(DesignFieldDefineVO::getKey, Function.identity(), (o1, o2) -> o1));
        HashSet<String> numberColumnSet = new HashSet<String>();
        for (String column : otherColumnCodes) {
            DesignFieldDefineVO designFieldDefineVO;
            if (!key2IdentityMap.containsKey(column) || !ColumnModelType.BIGDECIMAL.equals((Object)(designFieldDefineVO = (DesignFieldDefineVO)key2IdentityMap.get(column)).getType()) && !ColumnModelType.DOUBLE.equals((Object)designFieldDefineVO.getType())) continue;
            numberColumnSet.add(column);
        }
        return numberColumnSet;
    }

    private void flexRuleAllSrcSubjectCodes(String schemeId, String periodStr, FlexibleRuleDTO flexibleRuleDTO, Set<String> ruleSubjectCodes) {
        ArrayList ruleSubjectCodeLists = new ArrayList();
        ruleSubjectCodeLists.addAll(flexibleRuleDTO.getSrcDebitSubjectCodeList());
        ruleSubjectCodeLists.addAll(flexibleRuleDTO.getSrcCreditSubjectCodeList());
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(schemeId, periodStr);
        if (systemId == null) {
            throw new BusinessRuntimeException("\u4efb\u52a1\u4e0e\u5408\u5e76\u4f53\u7cfb\u5173\u8054\u5173\u7cfb\u4e22\u5931\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
        ruleSubjectCodes.addAll(this.consolidatedSubjectService.listAllChildrenCodesContainsSelf(ruleSubjectCodeLists, systemId));
    }

    @Override
    public ExportExcelSheet exportRelateRecords(ExportContext context, String recordId, QueryParamsVO paramsVO) {
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.sheet"), Integer.valueOf(1));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(paramsVO.getTaskId());
        Set<String> numberColumnSet = this.getNumberColumnSetByColumnCodes(paramsVO.getOtherShowColumns(), tableName);
        this.excelHead(context, exportExcelSheet, rowDatas, paramsVO, numberColumnSet);
        if (context.isTemplateExportFlag()) {
            exportExcelSheet.getRowDatas().addAll(rowDatas);
            return exportExcelSheet;
        }
        List<Map<String, Object>> relateRecords = this.relateRecords(recordId, paramsVO.getTaskId(), paramsVO.getOtherShowColumns());
        for (Map<String, Object> record : relateRecords) {
            this.excelOneRow(rowDatas, record, paramsVO, numberColumnSet);
        }
        this.excelSumRow(rowDatas, relateRecords, paramsVO, numberColumnSet);
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    private void excelOneRow(List<Object[]> rowDatas, Map<String, Object> record, QueryParamsVO paramsVO, Set<String> numberColumnSet) {
        int col = 0;
        Object[] dataRow = new Object[rowDatas.get(0).length];
        dataRow[col++] = String.valueOf(rowDatas.size());
        dataRow[col++] = String.valueOf(MapUtils.getStr(record, (Object)"RULETITLE"));
        dataRow[col++] = String.valueOf(MapUtils.getStr(record, (Object)"UNITTITLE"));
        dataRow[col++] = String.valueOf(MapUtils.getStr(record, (Object)"OPPUNITTITLE"));
        dataRow[col++] = String.valueOf(MapUtils.getStr(record, (Object)"SUBJECTTITLE"));
        boolean isDebit = OrientEnum.C.getValue() != MapUtils.getInt(record, (Object)"DC");
        String uncheckedAmt = MapUtils.getStr(record, (Object)"AMT").replace(",", "");
        if ("".equals(uncheckedAmt)) {
            uncheckedAmt = "0";
        }
        if (isDebit) {
            dataRow[col++] = Double.parseDouble(uncheckedAmt);
            dataRow[col++] = null;
        } else {
            dataRow[col++] = null;
            dataRow[col++] = Double.parseDouble(uncheckedAmt);
        }
        dataRow[col++] = String.valueOf(MapUtils.getStr(record, (Object)"MEMO"));
        for (String otherShowColumn : paramsVO.getOtherShowColumns()) {
            if (numberColumnSet.contains(otherShowColumn)) {
                String numberValue = MapUtils.getStr(record, (Object)otherShowColumn).replace(",", "");
                if ("".equals(numberValue)) {
                    numberValue = "0";
                }
                dataRow[col++] = Double.parseDouble(numberValue);
                continue;
            }
            dataRow[col++] = String.valueOf(MapUtils.getStr(record, (Object)otherShowColumn));
        }
        rowDatas.add(dataRow);
    }

    private void excelSumRow(List<Object[]> rowDatas, List<Map<String, Object>> relateRecords, QueryParamsVO paramsVO, Set<String> numberColumnSet) {
        int i;
        Object[] dataRow = new Object[rowDatas.get(0).length];
        rowDatas.add(dataRow);
        double sumDebit = 0.0;
        double sumCredit = 0.0;
        HashMap<String, Double> otherColumnKeyToSumValueMap = new HashMap<String, Double>();
        for (Map<String, Object> record : relateRecords) {
            boolean isDebit = OrientEnum.C.getValue() != MapUtils.getInt(record, (Object)"DC");
            String uncheckedAmt = MapUtils.getStr(record, (Object)"AMT").replace(",", "");
            if (!"".equals(uncheckedAmt)) {
                if (isDebit) {
                    sumDebit += Double.parseDouble(uncheckedAmt);
                } else {
                    sumCredit += Double.parseDouble(uncheckedAmt);
                }
            }
            for (String column : numberColumnSet) {
                String columnValue = MapUtils.getStr(record, (Object)column).replace(",", "");
                if ("".equals(columnValue)) continue;
                if (!otherColumnKeyToSumValueMap.containsKey(column)) {
                    otherColumnKeyToSumValueMap.put(column, 0.0);
                }
                otherColumnKeyToSumValueMap.put(column, (Double)otherColumnKeyToSumValueMap.get(column) + Double.parseDouble(columnValue));
            }
        }
        int col = 0;
        dataRow[col++] = GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.col.summary");
        for (i = 0; i < 4; ++i) {
            dataRow[col++] = "--";
        }
        dataRow[col++] = sumDebit;
        dataRow[col++] = sumCredit;
        dataRow[col++] = "--";
        for (i = 0; i < paramsVO.getOtherShowColumns().size(); ++i) {
            String numberColumn = (String)paramsVO.getOtherShowColumns().get(i);
            dataRow[col++] = numberColumnSet.contains(numberColumn) ? otherColumnKeyToSumValueMap.get(numberColumn) : "--";
        }
    }

    private void excelHead(ExportContext context, ExportExcelSheet exportExcelSheet, List<Object[]> rowDatas, QueryParamsVO paramsVO, Set<String> numberColumnSet) {
        Object[] titles = new String[]{GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.col.index"), GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.col.rule"), GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.col.unit"), GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.col.oppunit"), GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.col.subject"), GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.col.debit"), GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.col.credit"), GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.relate.export.col.memo")};
        titles = (String[])ArrayUtils.addAll((Object[])titles, (Object[])paramsVO.getOtherShowColumnTitles().toArray());
        rowDatas.add(titles);
        CellStyle contentAmt = (CellStyle)context.getVarMap().get("contentAmt");
        exportExcelSheet.getContentCellStyleCache().put(5, contentAmt);
        exportExcelSheet.getContentCellTypeCache().put(5, CellType.NUMERIC);
        exportExcelSheet.getContentCellStyleCache().put(6, contentAmt);
        exportExcelSheet.getContentCellTypeCache().put(6, CellType.NUMERIC);
        for (int i = 0; i < paramsVO.getOtherShowColumns().size(); ++i) {
            if (!numberColumnSet.contains(paramsVO.getOtherShowColumns().get(i))) continue;
            exportExcelSheet.getContentCellStyleCache().put(8 + i, contentAmt);
            exportExcelSheet.getContentCellTypeCache().put(8 + i, CellType.NUMERIC);
        }
    }

    private QueryParamsVO relateRecordsInitParam(InputDataEO inputDataEO) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        LinkedList<String> unitIdList = new LinkedList<String>();
        unitIdList.add(inputDataEO.getUnitId());
        queryParamsVO.setUnitIdList(unitIdList);
        LinkedList<String> oppUnitIdList = new LinkedList<String>();
        oppUnitIdList.add(inputDataEO.getOppUnitId());
        queryParamsVO.setOppUnitIdList(oppUnitIdList);
        queryParamsVO.setTaskId(inputDataEO.getTaskId());
        queryParamsVO.setSchemeId(this.getSchemeKey(inputDataEO.getPeriod(), inputDataEO.getTaskId()));
        queryParamsVO.setPeriodStr(inputDataEO.getPeriod());
        queryParamsVO.setPageSize(-1);
        ArrayList<String> ruleIds = new ArrayList<String>();
        ruleIds.add(inputDataEO.getUnionRuleId());
        queryParamsVO.setRules(ruleIds);
        queryParamsVO.setCurrency(inputDataEO.getCurrency());
        queryParamsVO.setQueryAllColumns(true);
        queryParamsVO.setSelectAdjustCode((String)inputDataEO.getFieldValue("ADJUST"));
        queryParamsVO.setOrgType(DimensionUtils.getDwEntitieTableByTaskKey((String)inputDataEO.getTaskId()));
        return queryParamsVO;
    }

    private String getSchemeKey(String periodStr, String taskId) {
        String schemeKey = null;
        try {
            schemeKey = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, taskId).getSchemeKey();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return schemeKey;
    }
}

