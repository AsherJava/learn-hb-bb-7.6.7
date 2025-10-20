/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.invest.investbill.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.invest.investbill.service.FairValueBillService;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FairValueBillServiceImpl
implements FairValueBillService {
    @Autowired
    FairValueBillService fairValueBillService;
    @Autowired
    FairValueBillDao fairValueBillDao;
    @Autowired
    InvestBillDao investBillDao;

    @Override
    public Map<String, Object> queryFvchBillCode(String investBillId, String periodStr) {
        Map<String, Object> investBillMap = this.investBillDao.getInvestBillById(investBillId);
        String investUnit = (String)investBillMap.get("UNITCODE");
        String investedUnit = (String)investBillMap.get("INVESTEDUNIT");
        Integer acctYear = ConverterUtils.getAsInteger((Object)investBillMap.get("ACCTYEAR"));
        String mergeType = (String)investBillMap.get("MERGETYPE");
        if (InvestInfoEnum.INDIRECT.getCode().equals(mergeType)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investbill.cannot.enter.fairvalue"));
        }
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List list = InvestBillTool.listByWhere((String[])new String[]{"SRCID", "ACCTYEAR"}, (Object[])new Object[]{investBillMap.get("SRCID"), acctYear}, (String)"GC_FVCHBILL");
        if (!CollectionUtils.isEmpty(list)) {
            resultMap.put("billCode", ((Map)list.get(0)).get("BILLCODE"));
        }
        GcOrgCenterService tool = GcOrgPublicTool.getInstance();
        GcOrgCacheVO unitVO = tool.getOrgByCode(investUnit);
        GcOrgCacheVO oppUnitVO = tool.getOrgByCode(investedUnit);
        resultMap.put("investUnit", investUnit);
        resultMap.put("investedUnit", investedUnit);
        resultMap.put("unitVO", null == unitVO ? tool.getBaseOrgByCode(investUnit) : unitVO);
        resultMap.put("oppUnitVO", null == oppUnitVO ? tool.getBaseOrgByCode(investedUnit) : oppUnitVO);
        return resultMap;
    }

    @Override
    public Map<String, List<DefaultTableEntity>> getFvchItemBills(int acctYear, Set<String> investUnit, Map<String, String> fvchSrcId2IdMap) {
        List<DefaultTableEntity> fvchFixedItemBills = this.fairValueBillDao.getFvchFixedItemBills(null, null, null, acctYear);
        List<DefaultTableEntity> fvchOtherItemBills = this.fairValueBillDao.getFvchOtherItemBills(null, null, null, acctYear);
        List fvchMasterIds = fvchFixedItemBills.stream().map(item -> (String)item.getFieldValue("MASTERID")).collect(Collectors.toList());
        fvchMasterIds.addAll(fvchOtherItemBills.stream().map(item -> (String)item.getFieldValue("MASTERID")).collect(Collectors.toList()));
        List fvchList = InvestBillTool.listBillsByIds(fvchMasterIds, (String)"GC_FVCHBILL");
        fvchSrcId2IdMap.putAll(fvchList.stream().collect(Collectors.toMap(item -> (String)item.get("SRCID"), item -> (String)item.get("ID"), (v1, v2) -> v2)));
        HashMap<String, List<DefaultTableEntity>> unit2EOS = new HashMap<String, List<DefaultTableEntity>>();
        this.unit2ItemBills(fvchFixedItemBills, investUnit, unit2EOS, "GC_FVCH_FIXEDITEM");
        this.unit2ItemBills(fvchOtherItemBills, investUnit, unit2EOS, "GC_FVCH_OTHERITEM");
        return unit2EOS;
    }

    @Override
    public List<DefaultTableEntity> getFvchItemsByMasterSrcId(String masterSrcId, Integer acctYear) {
        Map<String, Object> fvchMasterMap = this.fairValueBillDao.getMasterByYearAndSrcId(acctYear, masterSrcId);
        List<String> masterIds = Arrays.asList((String)fvchMasterMap.get("ID"));
        List fvchSubItems = InvestBillTool.listItemByMasterId(masterIds, (String)"GC_FVCH_FIXEDITEM");
        fvchSubItems.addAll(InvestBillTool.listItemByMasterId(masterIds, (String)"GC_FVCH_OTHERITEM"));
        return fvchSubItems;
    }

    private void unit2ItemBills(List<DefaultTableEntity> fvchItemBills, Set<String> investUnit, Map<String, List<DefaultTableEntity>> unit2EOS, String fvchItemType) {
        if (!CollectionUtils.isEmpty(fvchItemBills)) {
            for (DefaultTableEntity fvchItemBill : fvchItemBills) {
                if ("GC_FVCH_FIXEDITEM".equals(fvchItemType)) {
                    fvchItemBill.getFields().put("fvchItemType", "GC_FVCH_FIXEDITEM");
                } else {
                    fvchItemBill.getFields().put("fvchItemType", "GC_FVCH_OTHERITEM");
                }
                List<DefaultTableEntity> fvchItemBillList = unit2EOS.get(fvchItemBill.getFieldValue("INVESTEDUNIT"));
                if (fvchItemBillList == null) {
                    fvchItemBillList = new ArrayList<DefaultTableEntity>();
                }
                fvchItemBillList.add(fvchItemBill);
                unit2EOS.put((String)fvchItemBill.getFieldValue("INVESTEDUNIT"), fvchItemBillList);
                investUnit.add((String)fvchItemBill.getFieldValue("UNITCODE"));
            }
        }
    }
}

