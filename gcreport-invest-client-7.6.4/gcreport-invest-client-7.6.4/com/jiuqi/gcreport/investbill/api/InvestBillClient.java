/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.investbill.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.investbill.api.InvestBillClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InvestBillClient {
    public static final String API_BASE_PATH = "/api/gcreport/v1/investBill";

    @PostMapping(value={"/api/gcreport/v1/investBill/list"})
    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listInvestBills(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/investBill/batchDelete"})
    public BusinessResponseEntity<String> batchDelete(@RequestBody List<String> var1);

    @GetMapping(value={"/api/gcreport/v1/investBill/checkComUnit/{unitType}/checkOffset/{investBillId}"})
    public BusinessResponseEntity<Map<String, Object>> checkInvestBillOffset(@PathVariable(value="unitType") String var1, @PathVariable(value="investBillId") String var2, @RequestParam(value="mergeId") String var3, @RequestParam(value="periodStr") String var4);

    @PostMapping(value={"/api/gcreport/v1/investBill/updateDisPoseDate"})
    public BusinessResponseEntity<String> updateDisPoseDate(@RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date var1, @RequestBody List<String> var2);

    @GetMapping(value={"/api/gcreport/v1/investBill/showFvchFlag/getShow"})
    public List<Map<String, Object>> showFvchFlag();

    public Map<String, Object> getByUnitAndYear(String var1, String var2, int var3);

    public List<DefaultTableEntity> getMastByInvestAndInvestedUnit(Set<String> var1, Set<String> var2, int var3, int var4);

    @PostMapping(value={"/api/gcreport/v1/investBill/queryHistoryChangeRecord"})
    public BusinessResponseEntity<Map<String, Object>> queryHistoryChangeRecord(@RequestBody Map<String, Object> var1);
}

