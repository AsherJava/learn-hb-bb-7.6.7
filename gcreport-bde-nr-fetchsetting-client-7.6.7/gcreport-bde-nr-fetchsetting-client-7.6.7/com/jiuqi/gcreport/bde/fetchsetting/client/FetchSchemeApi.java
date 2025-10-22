/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.bde.fetchsetting.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodFetchDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FormulaSchemeConfigCond;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface FetchSchemeApi {
    @GetMapping(value={"/api/gcreport/v1/fetch/listFetchScheme/{formSchemeId}"})
    public BusinessResponseEntity<List<FetchSchemeVO>> listFetchScheme(@PathVariable(value="formSchemeId") String var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/queryFetchSchemeById/{fetchSchemeId}"})
    public BusinessResponseEntity<FetchSchemeVO> queryFetchSchemeById(@PathVariable(value="fetchSchemeId") String var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/listFetchSchemeByBizType/{bizType}"})
    public BusinessResponseEntity<List<FetchSchemeVO>> listFetchSchemeByBizType(@PathVariable(value="bizType") String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/saveFetchScheme"})
    public BusinessResponseEntity<Object> saveFetchScheme(@RequestBody FetchSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/copyFetchScheme"})
    public BusinessResponseEntity<Object> copyFetchScheme(@RequestBody FetchSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/updateFetchScheme"})
    public BusinessResponseEntity<Object> updateFetchScheme(@RequestBody FetchSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/deleteFetchScheme"})
    public BusinessResponseEntity<Object> deleteFetchScheme(@RequestBody FetchSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/getSchemeConfigByOrgAndAssistDim"})
    public BusinessResponseEntity<Object> getSchemeConfigByOrgAndAssistDim(@RequestBody FormulaSchemeConfigCond var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/canEditFetchScheme/{taskId}"})
    public BusinessResponseEntity<Boolean> canEditFetchScheme(@PathVariable(value="taskId") String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/saveAdjustPeriodSetting/{fetchSchemeId}"})
    public BusinessResponseEntity<String> saveAdjustPeriodSetting(@RequestBody List<AdjustPeriodSettingVO> var1, @PathVariable(value="fetchSchemeId") String var2);

    @PostMapping(value={"/api/gcreport/v1/fetch/isAdjustFetch"})
    public BusinessResponseEntity<Boolean> isAdjustFetch(@RequestBody AdjustPeriodFetchDTO var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/exchangeOrdinal"})
    public BusinessResponseEntity<Object> exchangeOrdinal(@RequestParam(value="srcId") String var1, @RequestParam(value="targetId") String var2);

    @GetMapping(value={"/api/gcreport/v1/fetch/updateIncludeAdjustVchr/{fetchSchemeId}/{includeAdjustVchr}"})
    public BusinessResponseEntity<String> updateIncludeAdjustVchrBySchemeId(@PathVariable(value="fetchSchemeId") String var1, @PathVariable(value="includeAdjustVchr") Integer var2);
}

