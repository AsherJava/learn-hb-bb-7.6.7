/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.financialcheckapi.checkconfig.FinancialCheckConfigClient
 *  com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.checkconfig.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.service.FinancialCheckConfigService;
import com.jiuqi.gcreport.financialcheckapi.checkconfig.FinancialCheckConfigClient;
import com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FinancialCheckConfigController
implements FinancialCheckConfigClient {
    @Autowired
    private FinancialCheckConfigService financialCheckConfigService;

    public BusinessResponseEntity<Object> save(FinancialCheckConfigVO financialCheckConfigVO) {
        this.financialCheckConfigService.save(financialCheckConfigVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<FinancialCheckConfigVO> query() {
        return BusinessResponseEntity.ok((Object)this.financialCheckConfigService.query());
    }
}

