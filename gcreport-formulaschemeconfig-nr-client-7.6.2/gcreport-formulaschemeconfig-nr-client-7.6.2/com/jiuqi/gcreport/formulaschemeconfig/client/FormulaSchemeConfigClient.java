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
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.formulaschemeconfig.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigCondition;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigQueryResultDTO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.formulaschemeconfig.client.FormulaSchemeConfigClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface FormulaSchemeConfigClient {
    public static final String FORMULASCHEMECONFIG_API_BASE_PATH = "/api/gcreport/v1/formulaSchemeConfig";

    @GetMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getFormulaSchemeColumn/{tableCategory}/{taskId}"})
    public BusinessResponseEntity<List<FormulaSchemeColumn>> getFormulaSchemeColumn(@PathVariable String var1, @PathVariable String var2);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getShowTableByOrgId"})
    public BusinessResponseEntity<NrFormulaSchemeConfigQueryResultDTO> getShowTableByOrgId(@RequestBody NrFormulaSchemeConfigCondition var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/saveStrategyFormulaSchemeConfig"})
    public BusinessResponseEntity<Object> saveStrategyFormulaSchemeConfig(@Valid @RequestBody List<NrFormulaSchemeConfigTableVO> var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/saveUnitFormulaSchemeConfig"})
    public BusinessResponseEntity<Object> saveUnitFormulaSchemeConfig(@Valid @RequestBody List<NrFormulaSchemeConfigTableVO> var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/recoverDefaultStrategy"})
    public BusinessResponseEntity<Object> recoverDefaultStrategy(@Valid @RequestBody NrFormulaSchemeConfigTableVO var1);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/deleteSelectSchemeConfig/{schemeId}/{entityId}"})
    public BusinessResponseEntity<Object> deleteSelectSchemeConfig(@PathVariable(value="schemeId") String var1, @PathVariable(value="entityId") String var2, @RequestBody List<String> var3);

    @GetMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getReportScheme/{schemeId}"})
    public BusinessResponseEntity<Map<String, Object>> getFormulaSchemesBySchemeId(@PathVariable(value="schemeId") String var1);

    @GetMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getReportSchemeByEntityId"})
    public BusinessResponseEntity<Map<String, Object>> getFormulaSchemesBySchemeAndEntityId(@RequestParam(value="schemeId") String var1, @RequestParam(value="entityId") String var2);

    @PostMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getStrategyTabSchemeConfig"})
    public BusinessResponseEntity<List<NrFormulaSchemeConfigTableVO>> getStrategyTabSchemeConfig(@RequestBody NrFormulaSchemeConfigCondition var1);

    @GetMapping(value={"/api/gcreport/v1/formulaSchemeConfig/getCurrencyByOrgId"})
    public BusinessResponseEntity<Object> getCurrencyByOrgId(@RequestParam(value="schemeId") String var1, @RequestParam(value="orgId") String var2, @RequestParam(value="entityId") String var3);
}

