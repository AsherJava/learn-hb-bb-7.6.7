/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.gcreport.formulaschemeconfig.client.FormulaSchemeConfigClient
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigCondition
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigQueryResultDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.formulaschemeconfig.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.gcreport.formulaschemeconfig.client.FormulaSchemeConfigClient;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigCondition;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigQueryResultDTO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FormulaSchemeConfigController
implements FormulaSchemeConfigClient {
    @Autowired
    FormulaSchemeConfigService formulaSchemeConfigService;

    public BusinessResponseEntity<List<FormulaSchemeColumn>> getFormulaSchemeColumn(String tableCategory, String taskId) {
        return BusinessResponseEntity.ok(this.formulaSchemeConfigService.createQueryColumns(tableCategory, taskId));
    }

    public BusinessResponseEntity<NrFormulaSchemeConfigQueryResultDTO> getShowTableByOrgId(NrFormulaSchemeConfigCondition formulaSchemeConfigCondition) {
        Assert.isNotNull((Object)formulaSchemeConfigCondition.getPage(), (String)"\u9875\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)formulaSchemeConfigCondition.getPageSize(), (String)"\u6bcf\u9875\u663e\u793a\u6570\u91cf\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        return BusinessResponseEntity.ok((Object)this.formulaSchemeConfigService.getShowTableByOrgId(formulaSchemeConfigCondition));
    }

    public BusinessResponseEntity<Object> saveStrategyFormulaSchemeConfig(@Valid List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        this.formulaSchemeConfigService.saveStrategyFormulaSchemeConfig(formulaSchemeConfigTableVOs);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> saveUnitFormulaSchemeConfig(@Valid List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        this.formulaSchemeConfigService.saveUnitFormulaSchemeConfig(formulaSchemeConfigTableVOs);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> recoverDefaultStrategy(@Valid NrFormulaSchemeConfigTableVO formulaSchemeConfigTableVO) {
        this.formulaSchemeConfigService.recoverDefaultStrategy(formulaSchemeConfigTableVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> deleteSelectSchemeConfig(@PathVariable(value="schemeId") String schemeId, @PathVariable(value="entityId") String entityId, @RequestBody List<String> ids) {
        this.formulaSchemeConfigService.deleteSelectSchemeConfig(schemeId, entityId, ids);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<Map<String, Object>> getFormulaSchemesBySchemeId(@PathVariable(value="schemeId") String schemeId) {
        Map<String, Object> formulaScheme = this.formulaSchemeConfigService.getFormulaSchemesBySchemeId(schemeId);
        return BusinessResponseEntity.ok(formulaScheme);
    }

    public BusinessResponseEntity<Map<String, Object>> getFormulaSchemesBySchemeAndEntityId(@RequestParam(value="schemeId") String schemeId, @RequestParam(value="entityId") String entityId) {
        Map<String, Object> formulaScheme = this.formulaSchemeConfigService.getFormulaSchemesBySchemeId(schemeId, entityId);
        return BusinessResponseEntity.ok(formulaScheme);
    }

    public BusinessResponseEntity<List<NrFormulaSchemeConfigTableVO>> getStrategyTabSchemeConfig(NrFormulaSchemeConfigCondition formulaSchemeConfigCondition) {
        return BusinessResponseEntity.ok(this.formulaSchemeConfigService.getStrategyTabSchemeConfig(formulaSchemeConfigCondition.getSchemeId(), formulaSchemeConfigCondition.getEntityId()));
    }

    public BusinessResponseEntity<Object> getCurrencyByOrgId(@RequestParam(value="schemeId") String schemeId, @RequestParam(value="orgId") String orgId, @RequestParam(value="entityId") String entityId) {
        return BusinessResponseEntity.ok(this.formulaSchemeConfigService.getCurrencyByOrgId(schemeId, orgId, entityId));
    }
}

