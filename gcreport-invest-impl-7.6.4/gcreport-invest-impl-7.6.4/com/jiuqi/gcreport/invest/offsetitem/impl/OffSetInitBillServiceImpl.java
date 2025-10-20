/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao
 *  com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitAssistService
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.invest.offsetitem.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.invest.common.InvestConst;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.invest.investbill.service.InvestBillService;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitAssistService;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OffSetInitBillServiceImpl
implements GcOffSetInitAssistService {
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private InvestBillService investBillService;
    @Autowired
    private GcOffSetVchrItemInitDao gcOffSetVchrItemInitDao;

    public void changeInvestmentOffsetStatus(List<GcOffSetVchrItemVO> offsetList, boolean isDone, boolean changeTz, boolean changeGy) {
        Object[] values;
        String[] columnNamesInDB;
        List<Map<String, Object>> investList;
        List creditConfigLists;
        if (CollectionUtils.isEmpty(offsetList)) {
            return;
        }
        UnionRuleVO ruleVO = this.unionRuleService.selectUnionRuleById(offsetList.get(0).getRuleId());
        String ruleType = ruleVO.getRuleType();
        List<String> ruleTypeList = Arrays.asList(RuleTypeEnum.DIRECT_INVESTMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT.getCode(), RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode());
        if (!ruleTypeList.contains(ruleType)) {
            return;
        }
        HashSet<String> unitCodeSet = new HashSet<String>();
        for (int i = 0; i < offsetList.size(); ++i) {
            unitCodeSet.add(offsetList.get(i).getUnitId());
            unitCodeSet.add(offsetList.get(i).getUnitCode());
            unitCodeSet.add(offsetList.get(i).getOppUnitId());
            unitCodeSet.add(offsetList.get(i).getOppUnitCode());
        }
        unitCodeSet.remove("");
        unitCodeSet.remove(null);
        if (unitCodeSet.size() < 2) {
            throw new BusinessRuntimeException("\u501f\u8d37\u65b9\u5355\u4f4d\u4e0d\u8db3\u4e24\u5bb6\uff01");
        }
        Iterator unitCodeSetIterator = unitCodeSet.iterator();
        String unitCode = (String)unitCodeSetIterator.next();
        String oppUnitCode = (String)unitCodeSetIterator.next();
        Map mapJson = (Map)JsonUtils.readValue((String)ruleVO.getJsonString(), HashMap.class);
        if (mapJson != null && !CollectionUtils.isEmpty((Collection)(creditConfigLists = (List)mapJson.get("creditItemList")))) {
            String investmentUnit = String.valueOf(((Map)creditConfigLists.get(0)).get("investmentUnit"));
            if (InvestmentUnitEnum.INVESTED_UNIT.getCode().equals(investmentUnit)) {
                String temp = unitCode;
                unitCode = oppUnitCode;
                oppUnitCode = temp;
            }
        }
        if (CollectionUtils.isEmpty(investList = this.investBillService.listByWhere(columnNamesInDB = new String[]{"UNITCODE", "INVESTEDUNIT", "ACCTYEAR", "DISPOSEFLAG"}, values = new Object[]{unitCode, oppUnitCode, offsetList.get(0).getAcctYear(), Integer.valueOf(InvestInfoEnum.DISPOSE_UNDO.getCode())}))) {
            values = new Object[]{oppUnitCode, unitCode, offsetList.get(0).getAcctYear(), Integer.valueOf(InvestInfoEnum.DISPOSE_UNDO.getCode())};
            investList = this.investBillService.listByWhere(columnNamesInDB, values);
        }
        String srcOffsetGroupId = CollectionUtils.isEmpty(investList) ? offsetList.get(0).getSrcOffsetGroupId() : (String)investList.get(0).get("SRCID");
        for (GcOffSetVchrItemVO item : offsetList) {
            item.setSrcOffsetGroupId(srcOffsetGroupId);
        }
        if (changeTz && RuleTypeEnum.DIRECT_INVESTMENT.getCode().equals(ruleVO.getRuleType()) || RuleTypeEnum.INDIRECT_INVESTMENT.getCode().equals(ruleVO.getRuleType())) {
            this.investBillService.updateOffsetStatus(srcOffsetGroupId, isDone ? InvestConst.FairValueAdjustStatus.DONE.getCode() : InvestConst.FairValueAdjustStatus.UN_DO.getCode());
        }
        if (changeGy && RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode().equals(ruleVO.getRuleType())) {
            this.investBillService.updateFairValueOffsetStatus(srcOffsetGroupId, isDone ? InvestConst.FairValueAdjustStatus.DONE.getCode() : InvestConst.FairValueAdjustStatus.UN_DO.getCode());
        }
    }

    public void deleteOffsetOfFvchBill(String investUnit, String investedUnit, Integer acctYear) {
        this.gcOffSetVchrItemInitDao.deleteOffsetOfFvchBill(investUnit, investedUnit, acctYear);
    }
}

