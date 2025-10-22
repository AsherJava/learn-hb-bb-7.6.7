/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.financialcheckapi.check;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckIniDataVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumConditionVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialClbrCodeInfoVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam;
import com.jiuqi.gcreport.financialcheckapi.check.vo.UnitCheckParam;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.financialcheckapi.check.FinancialCheckClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface FinancialCheckClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/financialcheck";

    @GetMapping(value={"/api/gcreport/v1/financialcheck/iniData"})
    public BusinessResponseEntity<FinancialCheckIniDataVO> getIniData();

    @PostMapping(value={"/api/gcreport/v1/financialcheck/autoCheck"})
    public BusinessResponseEntity<CheckResult> autoCheck(@RequestBody FinancialCheckQueryConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/manualCheck"})
    public BusinessResponseEntity<List<FinancialCheckQueryVO>> manualCheck(ManualCheckParam var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/cancelCheck"})
    public BusinessResponseEntity<String> cancelCheck(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/cancelCheckByCheckScheme"})
    public BusinessResponseEntity<String> cancelCheckByCheckSchemeIds(@RequestBody Map<String, Object> var1);

    @GetMapping(value={"/api/gcreport/v1/financialcheck/start/progress/{sn}"})
    public BusinessResponseEntity<FinancialCheckVO> querySnStartProgress(@PathVariable(value="sn") String var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/start/progress/{sn}"})
    public BusinessResponseEntity<Object> deleteSnStartProgress(@PathVariable(value="sn") String var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/saveUnCheckDesc"})
    public BusinessResponseEntity<String> saveUnCheckDesc(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/authUnCheckDesc"})
    public BusinessResponseEntity<String> authUnCheckDesc(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/deleteUnCheckDesc"})
    public BusinessResponseEntity<String> deleteUnCheckDesc(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/saveManualCheckData"})
    public BusinessResponseEntity saveManualCheckData(List<FinancialCheckQueryVO> var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/queryChecked"})
    public BusinessResponseEntity<PageInfo<FinancialCheckQueryVO>> queryChecked(@RequestBody FinancialCheckQueryConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/queryAmtSum"})
    public BusinessResponseEntity<FinancialCheckQueryAmtSumVO> queryAmtSum(@RequestBody FinancialCheckQueryAmtSumConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/queryUnchecked"})
    public BusinessResponseEntity<Object> queryUnchecked(@RequestBody FinancialCheckQueryConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/queryClbrCodeInfo"})
    public BusinessResponseEntity<FinancialClbrCodeInfoVO> queryClbrCodeInfo(@RequestParam(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/checkUnitState"})
    public BusinessResponseEntity<Object> checkUnitState(@RequestBody UnitCheckParam var1);

    @PostMapping(value={"/api/gcreport/v1/financialcheck/checkCanDoManualCheck"})
    public BusinessResponseEntity<Object> checkCanDoManualCheck(@RequestBody ManualCheckParam var1);
}

