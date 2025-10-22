/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.investworkpaper.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO;
import com.jiuqi.gcreport.investworkpaper.vo.QueryCondition;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.investworkpaper.api.InvestWorkPaperClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InvestWorkPaperClient {
    public static final String WORKPAPER_API_BASE_PATH = "/api/gcreport/v1/investWorkpaper";

    @PostMapping(value={"/api/gcreport/v1/investWorkpaper/getInvestWorkPaperColumns"})
    public BusinessResponseEntity<List<InvestWorkPaperColumnVO>> getInvestWorkPaperColumns(@RequestBody QueryCondition var1);

    @PostMapping(value={"/api/gcreport/v1/investWorkpaper/getInvestWorkPaperDatas"})
    public BusinessResponseEntity<List<Map<String, String>>> getInvestWorkPaperDatas(@RequestBody QueryCondition var1);

    @PostMapping(value={"/api/gcreport/v1/investWorkpaper/listPentrationDatas"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> listPentrationDatas(@RequestBody QueryCondition var1);
}

