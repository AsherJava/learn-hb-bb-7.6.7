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
package com.jiuqi.common.rate.client.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CommonRateSchemeClient {
    public static final String COMMON_RATE_SCHEME_API = "/api/gcreport/v1/commonRate/rateScheme";

    @GetMapping(value={"/api/gcreport/v1/commonRate/rateScheme/query"})
    public BusinessResponseEntity<CommonRateSchemeVO> queryRateScheme(@RequestParam(value="code", required=false) String var1);

    @GetMapping(value={"/api/gcreport/v1/commonRate/rateScheme/list"})
    public BusinessResponseEntity<List<CommonRateSchemeVO>> listAllRateScheme();

    @PostMapping(value={"/api/gcreport/v1/commonRate/rateScheme/save"})
    public BusinessResponseEntity<Boolean> saveRateScheme(@RequestBody CommonRateSchemeVO var1);

    @GetMapping(value={"/api/gcreport/v1/commonRate/rateScheme/delete/{code}"})
    public BusinessResponseEntity<Boolean> deleteRateScheme(@PathVariable(value="code") String var1);
}

