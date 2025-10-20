/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.financialcheckapi.scheme;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckBusinessRoleOptionVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckMatchSchemeVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeBaseDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeInitDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeNumVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeTreeVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.financialcheckapi.scheme.FinancialCheckSchemeClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface FinancialCheckSchemeClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/schemes";

    @PostMapping(value={"/api/gcreport/v1/schemes/initData"})
    public BusinessResponseEntity<FinancialCheckSchemeInitDataVO> initData();

    @PostMapping(value={"/api/gcreport/v1/schemes/addScheme"})
    public BusinessResponseEntity<FinancialCheckSchemeVO> addCheckScheme(@RequestBody FinancialCheckSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/updateScheme"})
    public BusinessResponseEntity<Object> updateCheckScheme(@RequestBody FinancialCheckSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/saveCheckScheme"})
    public BusinessResponseEntity<FinancialCheckSchemeTreeVO> saveCheckScheme(@RequestBody FinancialCheckSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/validCheckScheme/{id}"})
    public BusinessResponseEntity<Object> validCheckScheme(@PathVariable(name="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/deleteScheme/{id}"})
    public BusinessResponseEntity<Object> deleteCheckScheme(@PathVariable(name="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/treeCheckGroup"})
    public BusinessResponseEntity<List<FinancialCheckSchemeTreeVO>> treeCheckGroup(@RequestBody FinancialCheckGroupVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/treeEnableScheme"})
    public BusinessResponseEntity<List<FinancialCheckSchemeBaseDataVO>> treeEnableScheme(@RequestBody FinancialCheckGroupVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/singleEnableScheme"})
    public BusinessResponseEntity<FinancialCheckSchemeBaseDataVO> singleEnableScheme(@RequestBody FinancialCheckGroupVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/allEnableScheme"})
    public BusinessResponseEntity<List<FinancialCheckSchemeBaseDataVO>> allEnableScheme(@RequestBody FinancialCheckGroupVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/countScheme"})
    public BusinessResponseEntity<FinancialCheckSchemeNumVO> countScheme(@RequestBody FinancialCheckGroupVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/queryCheckScheme/{id}"})
    public BusinessResponseEntity<FinancialCheckSchemeVO> queryCheckScheme(@PathVariable(name="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/startFlag/{id}/{startFlag}"})
    public BusinessResponseEntity<Object> startCheckScheme(@PathVariable(name="id") String var1, @PathVariable(name="startFlag") boolean var2);

    @PostMapping(value={"/api/gcreport/v1/schemes/order/{id}/{step}"})
    public BusinessResponseEntity<Object> moveCheckScheme(@PathVariable(name="id") String var1, @PathVariable(name="step") double var2);

    @PostMapping(value={"/api/gcreport/v1/schemes/matchScheme"})
    public BusinessResponseEntity<Object> matchScheme(@RequestBody FinancialCheckMatchSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/cancelMatch"})
    public BusinessResponseEntity<Object> cancelMatch(@RequestBody FinancialCheckMatchSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/schemes/queryBusinessRoleOptions"})
    public BusinessResponseEntity<List<CheckBusinessRoleOptionVO>> queryBusinessRoleOptions(@RequestBody Map<String, String> var1);
}

