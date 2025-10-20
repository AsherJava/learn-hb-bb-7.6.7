/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn
 *  javax.validation.Valid
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.formulaschemeconfig.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.formulaschemeconfig.dto.BillFormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigCondition;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigQueryResultDTO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.formulaschemeconfig.client.BillFormulaSchemeConfigClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface BillFormulaSchemeConfigClient {
    public static final String FORMULASCHEMECONFIG_API_BASE_PATH = "/api/gcreport/v1/formulaSchemeConfig";

    @GetMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getBillFormulaSchemeColumn/{tableCategory}"})
    public BusinessResponseEntity<List<FormulaSchemeColumn>> getBillFormulaSchemeColumn(@PathVariable String var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getBillShowTableByOrgId"})
    public BusinessResponseEntity<BillFormulaSchemeConfigQueryResultDTO> getShowTableByOrgId(@RequestBody BillFormulaSchemeConfigCondition var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/saveBillStrategyFormulaSchemeConfig"})
    public BusinessResponseEntity<Object> saveStrategyFormulaSchemeConfig(@Valid @RequestBody List<BillFormulaSchemeConfigTableVO> var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/saveBillUnitFormulaSchemeConfig"})
    public BusinessResponseEntity<Object> saveUnitFormulaSchemeConfig(@Valid @RequestBody List<BillFormulaSchemeConfigTableVO> var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/recoverBillDefaultStrategy"})
    public BusinessResponseEntity<Object> recoverDefaultStrategy(@Valid @RequestBody BillFormulaSchemeConfigTableVO var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/deleteBillSelectSchemeConfig/{billId}"})
    public BusinessResponseEntity<Object> deleteSelectSchemeConfig(@PathVariable(value="billId") String var1, @RequestBody List<String> var2);

    @GetMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getBillFetchScheme/{billId}"})
    public BusinessResponseEntity<Map<String, Object>> getFetchSchemesByBillId(@PathVariable(value="billId") String var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getBillStrategyTabSchemeConfig"})
    public BusinessResponseEntity<List<BillFormulaSchemeConfigTableVO>> getStrategyTabSchemeConfig(@RequestBody BillFormulaSchemeConfigCondition var1);

    @GetMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getSchemeConfigByOrgId/{billId}/{orgId}"})
    public BusinessResponseEntity<BillFormulaSchemeConfigDTO> getSchemeConfigByOrgId(@PathVariable String var1, @PathVariable String var2);
}

