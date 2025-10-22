/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.billcore.offsetcheck.service.GcBillTracerService
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.invest.datatrace.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.billcore.offsetcheck.service.GcBillTracerService;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcBillTracerServiceImpl
implements GcBillTracerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FairValueBillDao fairValueBillDao;
    @Autowired
    private InvestBillDao investBillDao;

    public List<String> getAssociatedDataSrcGroupIds(GcDataTraceCondi condition) {
        ArrayList<String> fvchSrcGroupIds = new ArrayList<String>();
        if (GcDataTraceTypeEnum.INVEST.getType().equals(condition.getGcDataTraceType())) {
            List otherItemMapList;
            List fvchMasterList = InvestBillTool.listByWhere((String[])new String[]{"SRCID"}, (Object[])new Object[]{condition.getSrcId()}, (String)"GC_FVCHBILL");
            if (CollectionUtils.isEmpty((Collection)fvchMasterList)) {
                return fvchSrcGroupIds;
            }
            Map fairValueBillMasterData = (Map)fvchMasterList.get(0);
            List fixedItemMapList = InvestBillTool.getBillItemByMasterId((String)((String)fairValueBillMasterData.get("ID")), (String)"GC_FVCH_FIXEDITEM");
            if (!CollectionUtils.isEmpty((Collection)fixedItemMapList)) {
                fixedItemMapList.forEach(item -> fvchSrcGroupIds.add((String)item.get("ID")));
            }
            if (!CollectionUtils.isEmpty((Collection)(otherItemMapList = InvestBillTool.getBillItemByMasterId((String)((String)fairValueBillMasterData.get("ID")), (String)"GC_FVCH_OTHERITEM")))) {
                otherItemMapList.forEach(item -> fvchSrcGroupIds.add((String)item.get("ID")));
            }
        }
        return fvchSrcGroupIds;
    }

    public void resetBillCode(GcDataTraceCondi condition) {
        Boolean isFvchRule = (Boolean)condition.getExtendParams().get("isFvchRule");
        if (Boolean.TRUE.equals(isFvchRule)) {
            String fvchBillCode = condition.getBillCode();
            DefaultTableEntity fvchMasterEntity = InvestBillTool.getEntityByBillCode((String)fvchBillCode, (String)"GC_FVCHBILL");
            String srcId = (String)fvchMasterEntity.getFields().get("SRCID");
            Map<String, Object> investDataMap = this.investBillDao.getInvestBySrcIdAndYearAndPeriod(srcId, condition.getAcctYear(), condition.getAcctPeriod());
            if (investDataMap == null) {
                this.logger.info("\u901a\u8fc7\u516c\u5141\u53f0\u8d26srcid \u672a\u67e5\u8be2\u5230\u6295\u8d44\u53f0\u8d26\u6570\u636e");
            } else {
                condition.setBillCode((String)investDataMap.get("BILLCODE"));
            }
        }
    }
}

