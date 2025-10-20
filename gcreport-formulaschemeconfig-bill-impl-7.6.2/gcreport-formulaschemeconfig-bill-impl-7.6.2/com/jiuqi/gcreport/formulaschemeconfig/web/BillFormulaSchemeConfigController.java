/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.gcreport.formulaschemeconfig.client.BillFormulaSchemeConfigClient
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.BillFormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigCondition
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigQueryResultDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.formulaschemeconfig.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.gcreport.formulaschemeconfig.client.BillFormulaSchemeConfigClient;
import com.jiuqi.gcreport.formulaschemeconfig.dto.BillFormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn;
import com.jiuqi.gcreport.formulaschemeconfig.service.BillFormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigCondition;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigQueryResultDTO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class BillFormulaSchemeConfigController
implements BillFormulaSchemeConfigClient {
    @Autowired
    BillFormulaSchemeConfigService billFormulaSchemeConfigService;

    public BusinessResponseEntity<List<FormulaSchemeColumn>> getBillFormulaSchemeColumn(String tableCategory) {
        return BusinessResponseEntity.ok(this.billFormulaSchemeConfigService.createQueryColumns(tableCategory));
    }

    public BusinessResponseEntity<BillFormulaSchemeConfigQueryResultDTO> getShowTableByOrgId(BillFormulaSchemeConfigCondition formulaSchemeConfigCondition) {
        Assert.isNotNull((Object)formulaSchemeConfigCondition.getPage(), (String)"\u9875\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)formulaSchemeConfigCondition.getPageSize(), (String)"\u6bcf\u9875\u663e\u793a\u6570\u91cf\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        return BusinessResponseEntity.ok((Object)this.billFormulaSchemeConfigService.getShowTableByOrgId(formulaSchemeConfigCondition));
    }

    public BusinessResponseEntity<Object> saveStrategyFormulaSchemeConfig(@Valid List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        this.billFormulaSchemeConfigService.saveStrategyFormulaSchemeConfig(formulaSchemeConfigTableVOs);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> saveUnitFormulaSchemeConfig(@Valid List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        this.billFormulaSchemeConfigService.saveUnitFormulaSchemeConfig(formulaSchemeConfigTableVOs);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> recoverDefaultStrategy(@Valid BillFormulaSchemeConfigTableVO formulaSchemeConfigTableVO) {
        this.billFormulaSchemeConfigService.recoverDefaultStrategy(formulaSchemeConfigTableVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> deleteSelectSchemeConfig(@PathVariable(value="billId") String billId, @RequestBody List<String> ids) {
        this.billFormulaSchemeConfigService.deleteSelectSchemeConfig(billId, ids);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<Map<String, Object>> getFetchSchemesByBillId(@PathVariable(value="billId") String billId) {
        Map<String, Object> fetchScheme = this.billFormulaSchemeConfigService.getFetchSchemesByBillId(billId);
        return BusinessResponseEntity.ok(fetchScheme);
    }

    public BusinessResponseEntity<List<BillFormulaSchemeConfigTableVO>> getStrategyTabSchemeConfig(BillFormulaSchemeConfigCondition formulaSchemeConfigCondition) {
        return BusinessResponseEntity.ok(this.billFormulaSchemeConfigService.getStrategyTabSchemeConfig(formulaSchemeConfigCondition.getBillId()));
    }

    public BusinessResponseEntity<BillFormulaSchemeConfigDTO> getSchemeConfigByOrgId(@PathVariable String billId, @PathVariable String orgId) {
        return BusinessResponseEntity.ok((Object)this.billFormulaSchemeConfigService.getSchemeConfigByOrgId(billId, orgId));
    }
}

