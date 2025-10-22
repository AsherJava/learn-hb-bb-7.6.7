/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO
 *  com.jiuqi.gcreport.investworkpaper.vo.QueryCondition
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
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

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.investworkpaper.service.WorkPaperQueryTaskService;
import com.jiuqi.gcreport.invest.investworkpaper.service.impl.InvestWorkPaperQueryTask;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO;
import com.jiuqi.gcreport.investworkpaper.vo.QueryCondition;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GcIndirectInvestWorkPaperQueryTask
extends InvestWorkPaperQueryTask
implements WorkPaperQueryTaskService {
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;

    @Override
    public List<InvestWorkPaperColumnVO> getInvestWorkPaperColumns(QueryCondition condition) {
        ConsolidatedOptionVO optionVO = this.optionService.getOptionDataBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        Assert.notNull((Object)optionVO, GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.msg.getHead.noSystem"));
        ArrayList<InvestWorkPaperColumnVO> columns = new ArrayList<InvestWorkPaperColumnVO>();
        if (CollectionUtils.isEmpty((Collection)optionVO.getIndirectInvestShowColumns())) {
            columns.add(new InvestWorkPaperColumnVO("UNITCODE", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.unitCode"), "left", (Object)"left", 200));
        }
        this.getInvestPaperShowColumns(optionVO.getIndirectInvestShowColumns(), columns);
        if (condition.getShowRuleDetails().booleanValue()) {
            columns.add(new InvestWorkPaperColumnVO("OFFSETSCENARIO", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.offsetscenario"), "left", 400));
        }
        this.getInvestWorkPaperSubjectColumns(condition, optionVO.getIndirectInvestShowSubjects(), columns, RuleTypeEnum.INDIRECT_INVESTMENT);
        return columns;
    }

    @Override
    public List<InvestWorkPaperDataVO> getInvestWorkPaperDatas(QueryCondition condition) {
        List<AbstractUnionRule> rules = this.listCurrShowTypeAllRules(condition, RuleTypeEnum.INDIRECT_INVESTMENT);
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String baseUnitCode = tool.getDeepestBaseUnitId(condition.getOrgId());
        List<Map<String, Object>> investBills = this.getInvestBills(condition, false, baseUnitCode);
        ConsolidatedOptionVO optionVO = this.optionService.getOptionDataBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        Assert.notNull((Object)optionVO, GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.msg.getHead.noSystem"));
        return this.listInvestWorkPaperDataVOS(condition, rules, optionVO.getIndirectInvestShowSubjects(), optionVO.getIndirectInvestShowColumns(), investBills);
    }

    @Override
    public ExportExcelSheet exportInvestWorkPaperDatas(QueryCondition condition, Map<String, Object> cellStyleMap, int sheetNo) {
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(sheetNo), GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.export.indirectInvestment"), Integer.valueOf(2));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        List<InvestWorkPaperColumnVO> headerColumns = this.getInvestWorkPaperColumns(condition);
        this.createInvestWorkExcelHeader(exportExcelSheet, cellStyleMap, rowDatas, headerColumns);
        this.exportInvestWokrPaperDatsRange(exportExcelSheet, rowDatas, headerColumns, this.getInvestWorkPaperDatas(condition));
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    @Override
    public Pagination<Map<String, Object>> listPentrationDatas(QueryCondition condition) {
        List<AbstractUnionRule> rules = new ArrayList<AbstractUnionRule>();
        if (!condition.getShowRuleDetails().booleanValue()) {
            rules = this.listCurrShowTypeAllRules(condition, RuleTypeEnum.INDIRECT_INVESTMENT);
        }
        QueryParamsVO queryParamsVO = this.getOffsetQueryParams(condition, rules);
        Pagination offsetPage = this.offSetItemAdjustService.listOffsetEntrys(queryParamsVO, false);
        return this.listOffsetDatas(condition, (Pagination<Map<String, Object>>)offsetPage);
    }
}

