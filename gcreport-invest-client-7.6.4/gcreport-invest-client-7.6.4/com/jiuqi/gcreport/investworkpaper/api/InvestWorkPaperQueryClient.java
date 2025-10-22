/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.investworkpaper.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.investworkpaper.api.InvestWorkPaperQueryClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InvestWorkPaperQueryClient {
    public static final String WORKPAPER_API_BASE_PATH = "/api/gcreport/v1/investWorkpaperQuery";

    @PostMapping(value={"/api/gcreport/v1/investWorkpaperQuery/getInvestWorkPaperColumnsAndDatas"})
    public BusinessResponseEntity<InvestWorkPaperQueryResultVo> getInvestWorkPaperColumnsAndDatas(@RequestBody InvestWorkPaperQueryCondition var1);
}

