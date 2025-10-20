/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO
 *  com.jiuqi.gcreport.investworkpaper.vo.QueryCondition
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.invest.investworkpaper.service;

import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO;
import com.jiuqi.gcreport.investworkpaper.vo.QueryCondition;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import java.util.Map;

public interface WorkPaperQueryTaskService {
    public List<InvestWorkPaperColumnVO> getInvestWorkPaperColumns(QueryCondition var1);

    public List<InvestWorkPaperDataVO> getInvestWorkPaperDatas(QueryCondition var1);

    public ExportExcelSheet exportInvestWorkPaperDatas(QueryCondition var1, Map<String, Object> var2, int var3);

    public Pagination<Map<String, Object>> listPentrationDatas(QueryCondition var1);
}

