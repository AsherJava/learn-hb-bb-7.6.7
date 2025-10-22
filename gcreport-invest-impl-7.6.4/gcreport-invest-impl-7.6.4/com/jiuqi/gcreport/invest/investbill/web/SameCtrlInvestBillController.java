/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlInvestBillClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.investbill.web;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.investbill.service.InvestBillService;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlInvestBillClient;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SameCtrlInvestBillController
implements SameCtrlInvestBillClient {
    @Autowired
    private InvestBillService investBillService;

    public Map<String, Object> getByUnitAndYear(String investUnitCode, String investedUnitCode, int acctYear) {
        return this.investBillService.getByUnitAndYear(investUnitCode, investedUnitCode, acctYear);
    }

    public List<DefaultTableEntity> getMastByInvestAndInvestedUnit(Set<String> investUnit, Set<String> investedUnit, int acctYear, int period) {
        return this.investBillService.getMastByInvestAndInvestedUnit(investUnit, investedUnit, acctYear, period);
    }
}

