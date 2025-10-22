/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.init.carryover.enums.GcCarryOverInvestTypeEnum
 *  com.jiuqi.gcreport.offsetitem.init.carryover.service.GcCarryOverInvestService
 */
package com.jiuqi.gcreport.invest.investbillcarryover.service.impl;

import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.offsetitem.init.carryover.enums.GcCarryOverInvestTypeEnum;
import com.jiuqi.gcreport.offsetitem.init.carryover.service.GcCarryOverInvestService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcCarryOverInvestServiceImpl
implements GcCarryOverInvestService {
    @Autowired
    private InvestBillDao investBillDao;

    public Map<String, String> getSrcId2IdMap(Set<String> srcIds, GcCarryOverInvestTypeEnum ruleTypeEnum, int acctYear) {
        if (ruleTypeEnum.equals((Object)GcCarryOverInvestTypeEnum.INVESTMENT) || ruleTypeEnum.equals((Object)GcCarryOverInvestTypeEnum.PUBLIC_ADJUSTMENT)) {
            String[] columnNamesInDB = new String[]{"SRCID", "ACCTYEAR", "PERIOD"};
            Object[] values = new Object[]{srcIds, acctYear, 12};
            List<Map<String, Object>> investBillData = this.investBillDao.listByWhere(columnNamesInDB, values);
            Map<String, String> investSrcId2IDMap = investBillData.stream().collect(Collectors.toMap(item -> (String)item.get("SRCID"), item -> (String)item.get("ID"), (k1, k2) -> k1));
            return investSrcId2IDMap;
        }
        if (ruleTypeEnum.equals((Object)GcCarryOverInvestTypeEnum.PUBLIC_ADJUSTMENT)) {
            // empty if block
        }
        return null;
    }
}

