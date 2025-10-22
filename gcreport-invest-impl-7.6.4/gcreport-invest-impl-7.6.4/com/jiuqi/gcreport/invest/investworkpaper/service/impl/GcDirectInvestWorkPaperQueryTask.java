/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO
 *  com.jiuqi.gcreport.investworkpaper.vo.QueryCondition
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 */
package com.jiuqi.gcreport.invest.investworkpaper.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.investworkpaper.service.WorkPaperQueryTaskService;
import com.jiuqi.gcreport.invest.investworkpaper.service.impl.InvestWorkPaperQueryTask;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO;
import com.jiuqi.gcreport.investworkpaper.vo.QueryCondition;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GcDirectInvestWorkPaperQueryTask
extends InvestWorkPaperQueryTask
implements WorkPaperQueryTaskService {
    private Logger logger = LoggerFactory.getLogger(GcDirectInvestWorkPaperQueryTask.class);
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskCacheService;
    @Autowired
    private ConsolidatedSubjectService subjectService;

    @Override
    public List<InvestWorkPaperColumnVO> getInvestWorkPaperColumns(QueryCondition condition) {
        ConsolidatedOptionVO optionVO = this.optionService.getOptionDataBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        Assert.notNull((Object)optionVO, GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.msg.getHead.noSystem"));
        ArrayList<InvestWorkPaperColumnVO> columns = new ArrayList<InvestWorkPaperColumnVO>();
        this.getInvestPaperShowColumns(optionVO.getDirectInvestShowColumns(), columns);
        columns.add(new InvestWorkPaperColumnVO("COMPREEQUITYRATIO", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.compreequityratio"), "right"));
        columns.add(new InvestWorkPaperColumnVO("FEWSHAREHOLDER", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.fewshareholder"), "right"));
        if (condition.getShowRuleDetails().booleanValue()) {
            columns.add(new InvestWorkPaperColumnVO("OFFSETSCENARIO", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.offsetscenario"), "left", (Object)"", 400));
        }
        this.getInvestWorkPaperSubjectColumns(condition, optionVO.getDirectInvestShowSubjects(), columns, RuleTypeEnum.DIRECT_INVESTMENT);
        return columns;
    }

    @Override
    public List<InvestWorkPaperDataVO> getInvestWorkPaperDatas(QueryCondition condition) {
        String reportSystemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        ConsolidatedOptionVO optionVO = this.optionService.getOptionData(reportSystemId);
        List<AbstractUnionRule> allRules = this.getAllUnionRules(condition);
        QueryParamsVO queryParamsVO = this.convertQueryParams(condition, allRules);
        List<Map<String, Object>> offsetDatas = this.sumOffsetValueGroupByRule(queryParamsVO);
        List<Map<String, Object>> allShareHolderOffsets = this.getShareOffsetGroup(optionVO, offsetDatas, reportSystemId);
        List<AbstractUnionRule> currRules = allRules.stream().filter(rule -> RuleTypeEnum.DIRECT_INVESTMENT.getCode() == rule.getRuleType()).collect(Collectors.toList());
        List<String> currRuleIds = currRules.stream().map(AbstractUnionRule::getId).collect(Collectors.toList());
        Map<String, List<Map<String, Object>>> offsetGroups = this.getOffsetGroups(condition, currRuleIds, offsetDatas);
        return this.listInvestWorkPaperDataVOS(condition, optionVO, allShareHolderOffsets, currRules, offsetGroups, reportSystemId);
    }

    private List<InvestWorkPaperDataVO> listInvestWorkPaperDataVOS(QueryCondition condition, ConsolidatedOptionVO optionVO, List<Map<String, Object>> allShareHolderOffsets, List<AbstractUnionRule> currRules, Map<String, List<Map<String, Object>>> offsetGroups, String reportSystemId) {
        ArrayList<InvestWorkPaperDataVO> investWorkPaperDataVOList = new ArrayList<InvestWorkPaperDataVO>();
        HashMap<String, BigDecimal> totalOffsetValue = new HashMap<String, BigDecimal>();
        List<String> subjectColumnCodeList = this.listSubjectColumnCodes(condition, currRules, optionVO.getDirectInvestShowSubjects());
        Map<String, Set<String>> subjectAllChildrenCacheMap = this.getAllChildrenSubjectCacheMap(subjectColumnCodeList, reportSystemId);
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String baseUnitCode = tool.getDeepestBaseUnitId(condition.getOrgId());
        if (StringUtils.isEmpty((String)baseUnitCode)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.msg.getData.noLocalUnit"));
        }
        List<Map<String, Object>> investBills = this.getInvestBills(condition, baseUnitCode);
        Set<String> investNumberFieldSet = this.listInvestNumberFieldSet();
        for (Map<String, Object> investBill : investBills) {
            if (condition.getShowRuleDetails().booleanValue()) {
                BigDecimal totalFewShareHolder = BigDecimal.ZERO;
                for (AbstractUnionRule rule : currRules) {
                    InvestWorkPaperDataVO vo = this.initDirectInvestWorkPaperDataVo(optionVO, tool, investBill, allShareHolderOffsets, investNumberFieldSet, rule.getId());
                    if (vo == null) continue;
                    totalFewShareHolder = totalFewShareHolder.add(new BigDecimal(vo.getFewShareHolder()));
                    vo.setRuleId(rule.getId());
                    vo.setRuleTitle(rule.getLocalizedName());
                    this.setOffsetValue(offsetGroups, subjectColumnCodeList, vo, subjectAllChildrenCacheMap, totalOffsetValue);
                    investWorkPaperDataVOList.add(vo);
                }
                investWorkPaperDataVOList.add(this.getTotalRow(optionVO, totalOffsetValue, tool, investNumberFieldSet, investBill, totalFewShareHolder));
                continue;
            }
            InvestWorkPaperDataVO vo = this.initDirectInvestWorkPaperDataVo(optionVO, tool, investBill, allShareHolderOffsets, investNumberFieldSet, null);
            if (vo == null) continue;
            this.setOffsetValue(offsetGroups, subjectColumnCodeList, vo, subjectAllChildrenCacheMap, new HashMap<String, BigDecimal>());
            investWorkPaperDataVOList.add(vo);
        }
        return investWorkPaperDataVOList;
    }

    private InvestWorkPaperDataVO getTotalRow(ConsolidatedOptionVO optionVO, Map<String, BigDecimal> totalOffsetValue, GcOrgCenterService tool, Set<String> investNumberFieldSet, Map<String, Object> investBill, BigDecimal totalFewShareHolder) {
        InvestWorkPaperDataVO totalRow = this.getTotalWorkPaperRow(totalOffsetValue, optionVO.getDirectInvestShowColumns(), tool, investBill, investNumberFieldSet);
        totalRow.setFewShareHolder(totalFewShareHolder.toString());
        totalRow.setCompreEquityRatio(String.valueOf(investBill.get("COMPREEQUITYRATIO") == null ? Integer.valueOf(0) : investBill.get("COMPREEQUITYRATIO")));
        List showColumns = optionVO.getDirectInvestShowColumns();
        int columnnumbers = CollectionUtils.isEmpty((Collection)showColumns) ? 6 : showColumns.size() + 1;
        totalRow.getOtherColumnsValue().put("COLUMNNUMBERS", String.valueOf(columnnumbers));
        return totalRow;
    }

    private List<Map<String, Object>> getShareOffsetGroup(ConsolidatedOptionVO optionVO, List<Map<String, Object>> offsetDatas, String reportSystemId) {
        List shareholderShowSubjects = optionVO.getFewShareholderShowSubjects();
        List shareHolderShowSubjectIds = shareholderShowSubjects.stream().map(subject -> (String)subject.get("code")).collect(Collectors.toList());
        Set childrenCodes = this.subjectService.listAllChildrenCodesContainsSelf(shareHolderShowSubjectIds, reportSystemId);
        return offsetDatas.stream().filter(item -> childrenCodes.contains(item.get("SUBJECTCODE"))).collect(Collectors.toList());
    }

    private List<AbstractUnionRule> getAllUnionRules(QueryCondition condition) {
        ArrayList<String> ruleTypes = new ArrayList<String>();
        ruleTypes.add(RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode());
        ruleTypes.add(RuleTypeEnum.DIRECT_INVESTMENT.getCode());
        ruleTypes.add(RuleTypeEnum.INDIRECT_INVESTMENT.getCode());
        return this.listAbstractUnionRules(condition, ruleTypes);
    }

    private Map<String, List<Map<String, Object>>> getOffsetGroups(QueryCondition condition, List<String> currRuleIds, List<Map<String, Object>> offsetDatas) {
        List<Map<String, Object>> currOffsetDatas = offsetDatas.stream().filter(item -> currRuleIds.contains(item.get("RULEID"))).collect(Collectors.toList());
        return this.groupOffsetDats(condition, currOffsetDatas);
    }

    private InvestWorkPaperDataVO initDirectInvestWorkPaperDataVo(ConsolidatedOptionVO optionVO, GcOrgCenterService tool, Map<String, Object> investBill, List<Map<String, Object>> allShareHolderOffsets, Set<String> numberFieldSet, String ruleId) {
        List showColumns = optionVO.getDirectInvestShowColumns();
        InvestWorkPaperDataVO vo = this.initInvestWorkPaperDataVo(showColumns, tool, investBill, numberFieldSet);
        if (vo == null) {
            return null;
        }
        int columnnumbers = CollectionUtils.isEmpty((Collection)showColumns) ? 6 : showColumns.size() + 1;
        vo.getOtherColumnsValue().put("COLUMNNUMBERS", String.valueOf(columnnumbers));
        vo.setCompreEquityRatio(String.valueOf(investBill.get("COMPREEQUITYRATIO") == null ? Integer.valueOf(0) : investBill.get("COMPREEQUITYRATIO")));
        List<Map<String, Object>> shareOffsetDatas = allShareHolderOffsets.stream().filter(item -> !(!investBill.get("INVESTEDUNIT_ID").equals(item.get("UNITID")) && !investBill.get("INVESTEDUNIT_ID").equals(item.get("OPPUNITID")) || !StringUtils.isEmpty((String)ruleId) && !ruleId.equals(item.get("RULEID")))).collect(Collectors.toList());
        BigDecimal shareOffsetValue = this.sumShareOffsetDatas(shareOffsetDatas);
        vo.setFewShareHolder(shareOffsetValue.toString());
        return vo;
    }

    private BigDecimal sumShareOffsetDatas(List<Map<String, Object>> shareOffsetDatas) {
        BigDecimal creditValue = BigDecimal.ZERO;
        BigDecimal debitValue = BigDecimal.ZERO;
        for (Map<String, Object> offset : shareOffsetDatas) {
            creditValue = creditValue.add(new BigDecimal(offset.get("CREDITVALUE").toString()));
            debitValue = debitValue.add(new BigDecimal(offset.get("DEBITVALUE").toString()));
        }
        return creditValue.subtract(debitValue);
    }

    @Override
    public ExportExcelSheet exportInvestWorkPaperDatas(QueryCondition condition, Map<String, Object> cellStyleMap, int sheetNo) {
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(sheetNo), GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.export.directInvest"), Integer.valueOf(2));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        List<InvestWorkPaperColumnVO> headerColumns = this.getInvestWorkPaperColumns(condition);
        this.createInvestWorkExcelHeader(exportExcelSheet, cellStyleMap, rowDatas, headerColumns);
        this.exportInvestWokrPaperDatsRange(exportExcelSheet, rowDatas, headerColumns, this.getInvestWorkPaperDatas(condition));
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    @Override
    public Pagination<Map<String, Object>> listPentrationDatas(QueryCondition condition) {
        List<Object> rules = new ArrayList();
        if (condition.getColumnProp().equals("FEWSHAREHOLDER")) {
            rules = this.getAllUnionRules(condition);
        } else if (!condition.getShowRuleDetails().booleanValue()) {
            rules = this.listCurrShowTypeAllRules(condition, RuleTypeEnum.DIRECT_INVESTMENT);
        }
        QueryParamsVO queryParamsVO = this.getOffsetQueryParams(condition, rules);
        String reportSystemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        List<Object> shareHolderShowSubjectIds = new ArrayList();
        if (condition.getColumnProp().equals("FEWSHAREHOLDER")) {
            ConsolidatedOptionVO optionVO = this.optionService.getOptionData(reportSystemId);
            List shareholderShowSubjects = optionVO.getFewShareholderShowSubjects();
            shareHolderShowSubjectIds = shareholderShowSubjects.stream().map(subject -> (String)subject.get("code")).collect(Collectors.toList());
            queryParamsVO.setSubjectCodes(shareHolderShowSubjectIds);
        }
        Pagination offsetPage = this.offSetItemAdjustService.listOffsetEntrys(queryParamsVO, false);
        if ("2".equals(condition.getPentrateType()) && condition.getColumnProp().equals("FEWSHAREHOLDER")) {
            Set subjectCodes = this.subjectService.listAllChildrenCodesContainsSelf(shareHolderShowSubjectIds, reportSystemId);
            List<Map<String, Object>> offsetDats = offsetPage.getContent();
            offsetDats = offsetDats.stream().filter(offsetData -> subjectCodes.contains(offsetData.get("SUBJECTCODE")) && (StringUtils.isEmpty((String)condition.getRuleId()) || condition.getRuleId().equals(offsetData.get("RULEID")))).collect(Collectors.toList());
            this.pageOffset(condition, (Pagination<Map<String, Object>>)offsetPage, offsetDats);
            return offsetPage;
        }
        return this.listOffsetDatas(condition, (Pagination<Map<String, Object>>)offsetPage);
    }

    @Override
    public List<Map<String, Object>> sumOffsetValueGroupByRule(QueryParamsVO queryParamsVO) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        String offsetValueStr = "\nsum(record.offset_DEBIT) as debitValue,sum(record.offset_Credit) as creditValue  ";
        String selectFields = " record.unitid,record.oppUnitId,record.ruleId,record.subjectCode,record.subjectOrient," + offsetValueStr;
        String groupStr = " record.unitid,record.oppUnitId,record.ruleId,record.subjectCode,record.subjectOrient";
        return this.offsetCoreService.sumOffsetValueGroupBy(queryParamsDTO, selectFields, groupStr);
    }
}

