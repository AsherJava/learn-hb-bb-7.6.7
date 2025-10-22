/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.investbill.api.InvestBillClient
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.investbill.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.investbill.service.InvestBillService;
import com.jiuqi.gcreport.investbill.api.InvestBillClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvestBillController
implements InvestBillClient {
    @Autowired
    private InvestBillService investBillService;

    @RequiresPermissions(value={"gc:invest:base"})
    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listInvestBills(Map<String, Object> params) {
        return BusinessResponseEntity.ok(this.investBillService.listInvestBills(params));
    }

    @RequiresPermissions(value={"gc:invest:base"})
    public BusinessResponseEntity<String> batchDelete(List<String> ids) {
        this.investBillService.batchDelete(ids);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Map<String, Object>> checkInvestBillOffset(String unitType, String investBillId, String mergeId, String periodStr) {
        return BusinessResponseEntity.ok(this.investBillService.checkInvestBillOffset(unitType, mergeId, investBillId, periodStr));
    }

    public BusinessResponseEntity<String> updateDisPoseDate(Date disposeDate, List<String> investBillIds) {
        this.investBillService.updateDisPoseDate(disposeDate, investBillIds);
        return BusinessResponseEntity.ok();
    }

    public List<Map<String, Object>> showFvchFlag() {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> allow = new HashMap<String, Object>();
        allow.put("title", "\u662f");
        allow.put("key", 1);
        result.add(allow);
        HashMap<String, Object> notAllow = new HashMap<String, Object>();
        notAllow.put("title", "\u5426");
        notAllow.put("key", 0);
        result.add(notAllow);
        return result;
    }

    public Map<String, Object> getByUnitAndYear(String investUnitCode, String investedUnitCode, int acctYear) {
        return this.investBillService.getByUnitAndYear(investUnitCode, investedUnitCode, acctYear);
    }

    public List<DefaultTableEntity> getMastByInvestAndInvestedUnit(Set<String> investUnit, Set<String> investedUnit, int acctYear, int period) {
        return this.investBillService.getMastByInvestAndInvestedUnit(investUnit, investedUnit, acctYear, period);
    }

    public BusinessResponseEntity<Map<String, Object>> queryHistoryChangeRecord(Map<String, Object> params) {
        return BusinessResponseEntity.ok(this.investBillService.queryHistoryChangeRecord(params));
    }
}

