/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO
 *  com.jiuqi.gcreport.investworkpaper.vo.QueryCondition
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.gcreport.invest.investworkpaper.service.impl;

import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.invest.investworkpaper.investworkpaperfactory.GcInvestWorkPaperQueryFactory;
import com.jiuqi.gcreport.invest.investworkpaper.service.InvestWorkPaperService;
import com.jiuqi.gcreport.invest.investworkpaper.service.WorkPaperQueryTaskService;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO;
import com.jiuqi.gcreport.investworkpaper.vo.QueryCondition;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.np.log.LogHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InvestWorkPaperServiceImpl
implements InvestWorkPaperService {
    @Override
    public List<InvestWorkPaperColumnVO> getInvestWorkPaperColumns(QueryCondition condition) {
        WorkPaperQueryTaskService queryTask = GcInvestWorkPaperQueryFactory.getInstance(condition.getSelectedShowType());
        return queryTask.getInvestWorkPaperColumns(condition);
    }

    @Override
    public List<Map<String, String>> getInvestWorkPaperDatas(QueryCondition condition) {
        WorkPaperQueryTaskService queryTask = GcInvestWorkPaperQueryFactory.getInstance(condition.getSelectedShowType());
        List<InvestWorkPaperDataVO> dataVOList = queryTask.getInvestWorkPaperDatas(condition);
        String selectedShowTypeTitle = GcInvestWorkPaperQueryFactory.getShowTypeTitle(condition.getSelectedShowType());
        String showRuleDetails = condition.getShowRuleDetails() != false ? "\u663e\u793a\u89c4\u5219\u660e\u7ec6" : "\u4e0d\u663e\u793a\u89c4\u5219\u660e\u7ec6";
        LogHelper.info((String)"\u5408\u5e76-\u6295\u8d44\u5de5\u4f5c\u5e95\u7a3f", (String)String.format("\u67e5\u8be2-\u67e5\u770b\u7c7b\u578b%1$s-%2$s", selectedShowTypeTitle, showRuleDetails), (String)"");
        return dataVOList.stream().map(InvestWorkPaperDataVO::getOtherColumnsValue).collect(Collectors.toList());
    }

    @Override
    public Pagination<Map<String, Object>> listPentrationDatas(QueryCondition condition) {
        WorkPaperQueryTaskService queryTask = GcInvestWorkPaperQueryFactory.getInstance(condition.getSelectedShowType());
        return queryTask.listPentrationDatas(condition);
    }

    @Override
    public List<ExportExcelSheet> exportInvestWorkPaperDatas(QueryCondition queryCondition, Map<String, Object> cellStyleMap) {
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        int sheetNo = 0;
        if (!queryCondition.getExportAllTable().booleanValue()) {
            WorkPaperQueryTaskService queryTask = GcInvestWorkPaperQueryFactory.getInstance(queryCondition.getSelectedShowType());
            exportExcelSheets.add(queryTask.exportInvestWorkPaperDatas(queryCondition, cellStyleMap, sheetNo));
        } else {
            WorkPaperQueryTaskService fairValueQueryTask = GcInvestWorkPaperQueryFactory.getInstance("PUBLIC_VALUE_ADJUSTMENT");
            if (queryCondition.getShowTypes().contains("PUBLIC_VALUE_ADJUSTMENT")) {
                exportExcelSheets.add(fairValueQueryTask.exportInvestWorkPaperDatas(queryCondition, cellStyleMap, sheetNo));
            }
            WorkPaperQueryTaskService directInvestQueryTask = GcInvestWorkPaperQueryFactory.getInstance("DIRECT_INVESTMENT");
            if (queryCondition.getShowTypes().contains("DIRECT_INVESTMENT")) {
                exportExcelSheets.add(directInvestQueryTask.exportInvestWorkPaperDatas(queryCondition, cellStyleMap, ++sheetNo));
            }
            WorkPaperQueryTaskService inDirectQueryTask = GcInvestWorkPaperQueryFactory.getInstance("INDIRECT_INVESTMENT");
            if (queryCondition.getShowTypes().contains("INDIRECT_INVESTMENT")) {
                exportExcelSheets.add(inDirectQueryTask.exportInvestWorkPaperDatas(queryCondition, cellStyleMap, ++sheetNo));
            }
        }
        return exportExcelSheets;
    }
}

