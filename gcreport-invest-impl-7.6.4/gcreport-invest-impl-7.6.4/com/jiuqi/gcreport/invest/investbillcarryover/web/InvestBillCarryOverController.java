/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.investcarryover.api.InvestBillCarryOverClient
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.investbillcarryover.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.invest.investbillcarryover.service.InvestBillGcCarryOverService;
import com.jiuqi.gcreport.investcarryover.api.InvestBillCarryOverClient;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class InvestBillCarryOverController
implements InvestBillCarryOverClient {
    private Logger logger = LoggerFactory.getLogger(InvestBillCarryOverController.class);
    @Autowired
    private InvestBillGcCarryOverService investBillGcCarryOverService;
    @Autowired
    private GcCarryOverProcessService carryOverProcessService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public BusinessResponseEntity<Object> doInvestCarryOver(QueryParamsVO queryParamsVO) {
        this.investBillGcCarryOverService.doInvestCarryOverTask(queryParamsVO);
        return BusinessResponseEntity.ok();
    }
}

