/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.investworkpaper.api.InvestWorkPaperClient
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO
 *  com.jiuqi.gcreport.investworkpaper.vo.QueryCondition
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.investworkpaper.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.invest.investworkpaper.service.InvestWorkPaperService;
import com.jiuqi.gcreport.investworkpaper.api.InvestWorkPaperClient;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO;
import com.jiuqi.gcreport.investworkpaper.vo.QueryCondition;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvestWorkPaperController
implements InvestWorkPaperClient {
    @Autowired
    private InvestWorkPaperService workPaperService;

    public BusinessResponseEntity<List<InvestWorkPaperColumnVO>> getInvestWorkPaperColumns(QueryCondition condition) {
        return BusinessResponseEntity.ok(this.workPaperService.getInvestWorkPaperColumns(condition));
    }

    public BusinessResponseEntity<List<Map<String, String>>> getInvestWorkPaperDatas(QueryCondition condition) {
        return BusinessResponseEntity.ok(this.workPaperService.getInvestWorkPaperDatas(condition));
    }

    public BusinessResponseEntity<Pagination<Map<String, Object>>> listPentrationDatas(QueryCondition condition) {
        return BusinessResponseEntity.ok(this.workPaperService.listPentrationDatas(condition));
    }
}

