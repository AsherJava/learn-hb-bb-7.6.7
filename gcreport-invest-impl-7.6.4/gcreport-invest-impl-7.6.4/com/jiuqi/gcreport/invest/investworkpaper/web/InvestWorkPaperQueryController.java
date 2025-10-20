/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.investworkpaper.api.InvestWorkPaperQueryClient
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryResultVo
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.investworkpaper.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.invest.investworkpaper.service.InvestWorkPaperQueryService;
import com.jiuqi.gcreport.investworkpaper.api.InvestWorkPaperQueryClient;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvestWorkPaperQueryController
implements InvestWorkPaperQueryClient {
    @Autowired
    private InvestWorkPaperQueryService investWorkPaperQueryService;

    public BusinessResponseEntity<InvestWorkPaperQueryResultVo> getInvestWorkPaperColumnsAndDatas(InvestWorkPaperQueryCondition condition) {
        return BusinessResponseEntity.ok((Object)this.investWorkPaperQueryService.getInvestWorkPaperColumnsAndDatas(condition));
    }
}

