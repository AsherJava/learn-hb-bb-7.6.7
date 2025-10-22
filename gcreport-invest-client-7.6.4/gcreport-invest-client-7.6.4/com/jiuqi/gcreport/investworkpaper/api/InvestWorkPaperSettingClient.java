/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.investworkpaper.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.investworkpaper.vo.Column;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.investworkpaper.api.InvestWorkPaperSettingClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InvestWorkPaperSettingClient {
    public static final String PAPERSETTING_API_BASE_PATH = "/api/gcreport/v1/investWorkpaperSetting";

    @PostMapping(value={"/api/gcreport/v1/investWorkpaperSetting/save"})
    public BusinessResponseEntity<String> save(@RequestBody InvestWorkPaperSettingVo var1);

    @GetMapping(value={"/api/gcreport/v1/investWorkpaperSetting/getSettingData/{taskId}/{systemId}"})
    public BusinessResponseEntity<InvestWorkPaperSettingVo> getSettingData(@PathVariable(value="taskId") String var1, @PathVariable(value="systemId") String var2, @RequestParam(required=false) String var3);

    @GetMapping(value={"/api/gcreport/v1/investWorkpaperSetting/listInvestAmtFields"})
    public BusinessResponseEntity<List<Column>> listInvestAmtFields();
}

