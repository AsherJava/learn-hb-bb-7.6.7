/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialUnitStatusVO
 *  com.jiuqi.gc.financialcubes.relatedtransactionstatus.api.RelatedTransactionStatusClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gc.financialcubes.relatedtransactionstatus.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialUnitStatusVO;
import com.jiuqi.gc.financialcubes.relatedtransactionstatus.api.RelatedTransactionStatusClient;
import com.jiuqi.gc.financialcubes.relatedtransactionstatus.service.RelatedTransactionUnitStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class RelatedTransactionUnitStatusWeb
implements RelatedTransactionStatusClient {
    @Autowired
    private RelatedTransactionUnitStatusService relatedTransactionUnitStatusService;

    public BusinessResponseEntity<String> openUnit(FinancialStatusParam param) {
        return BusinessResponseEntity.ok((Object)this.relatedTransactionUnitStatusService.openUnit(param));
    }

    public BusinessResponseEntity<String> closeUnit(FinancialStatusParam param) {
        return BusinessResponseEntity.ok((Object)this.relatedTransactionUnitStatusService.closeUnit(param));
    }

    public BusinessResponseEntity<PageInfo<FinancialUnitStatusVO>> listCloseUnit(FinancialStatusParam param) {
        return BusinessResponseEntity.ok(this.relatedTransactionUnitStatusService.listCloseUnit(param));
    }

    public BusinessResponseEntity<PageInfo<FinancialUnitStatusVO>> listOpenUnit(FinancialStatusParam param) {
        return BusinessResponseEntity.ok(this.relatedTransactionUnitStatusService.listOpenUnit(param));
    }
}

