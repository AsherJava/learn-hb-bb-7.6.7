/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.common.GcBillModelImpl
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.bill.utils.VerifyUtils
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataRowState
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.investbill.bill.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.common.GcBillModelImpl;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.invest.common.InvestConst;
import com.jiuqi.gcreport.invest.investbill.dao.impl.FairValueBillDaoImpl;
import com.jiuqi.gcreport.invest.investbill.service.impl.InvestBillServiceImpl;
import com.jiuqi.gcreport.invest.offsetitem.impl.OffSetInitBillServiceImpl;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.VerifyUtils;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.transaction.annotation.Transactional;

public class FairValueModelImpl
extends GcBillModelImpl {
    @Transactional(rollbackFor={Exception.class})
    public void save() {
        DataRow dataRow = this.getMaster();
        if (!InvestBillTool.validateUserId((String)dataRow.getString("CREATEUSER"))) {
            dataRow.setValue("CREATEUSER", null);
        }
        String investUnitCode = dataRow.getString("UNITCODE");
        String investedUnitCode = dataRow.getString("INVESTEDUNIT");
        Integer acctYear = Integer.valueOf(dataRow.getString("ACCTYEAR"));
        this.addFvchLog(investUnitCode, investedUnitCode, acctYear);
        int fixedItemSize = this.handleFvchFixedItemBill(investUnitCode, investedUnitCode, acctYear);
        int otherItemSize = this.handleFvchOtherItemBill(investUnitCode, investedUnitCode, acctYear);
        VerifyUtils.verifyBill((BillModel)this, (int)2);
        int billState = this.getMaster().getInt("BILLSTATE");
        if (billState <= 1) {
            this.getMaster().setValue("BILLSTATE", (Object)BillState.SAVED);
        }
        this.getData().saveWithLock(Stream.of("ID", "VER").collect(Collectors.toSet()));
        int fvchItemSize = fixedItemSize + otherItemSize;
        InvestBillServiceImpl investBillService = (InvestBillServiceImpl)SpringContextUtils.getBean(InvestBillServiceImpl.class);
        int fairValueAdjustFlag = fvchItemSize > 0 ? InvestConst.FairValueAdjustStatus.DONE.getCode() : InvestConst.FairValueAdjustStatus.UN_DO.getCode();
        investBillService.updateFairValueAdjustFlag(investUnitCode, investedUnitCode, acctYear, fairValueAdjustFlag);
        if (fvchItemSize == 0) {
            OffSetInitBillServiceImpl offSetInitBillService = (OffSetInitBillServiceImpl)SpringContextUtils.getBean(OffSetInitBillServiceImpl.class);
            offSetInitBillService.deleteOffsetOfFvchBill(investUnitCode, investedUnitCode, acctYear);
            String masterId = dataRow.getString("ID");
            FairValueBillDaoImpl FairValueBillDao2 = (FairValueBillDaoImpl)SpringContextUtils.getBean(FairValueBillDaoImpl.class);
            FairValueBillDao2.deleteMaster(masterId);
        }
    }

    private int handleFvchFixedItemBill(String investUnitCode, String investedUnitCode, Integer acctYear) {
        HashSet assetTitleSet = new HashSet();
        List fixedItemMapList = ((DataTableImpl)this.getData().getTables().get("GC_FVCH_FIXEDITEM")).getRowsData();
        for (Map fixedItemMap : fixedItemMapList) {
            if (StringUtils.isEmpty((String)((String)fixedItemMap.get("ASSETTITLE")))) {
                throw new BillException("\u56fa\u5b9a/\u65e0\u5f62\u8d44\u4ea7\uff1a  \u8bf7\u586b\u5199\u8d44\u4ea7\u540d\u79f0");
            }
            if (assetTitleSet.contains(fixedItemMap.get("ASSETTITLE"))) {
                throw new BillException("\u56fa\u5b9a/\u65e0\u5f62\u8d44\u4ea7\uff1a  \u8d44\u4ea7\u540d\u79f0\u4e0d\u80fd\u91cd\u590d");
            }
            assetTitleSet.add(fixedItemMap.get("ASSETTITLE"));
            fixedItemMap.put("UNITCODE", investUnitCode);
            fixedItemMap.put("INVESTEDUNIT", investedUnitCode);
            fixedItemMap.put("ACCTYEAR", acctYear);
            fixedItemMap.put("SRCID", this.getMaster().getValue("SRCID"));
        }
        ((DataTableImpl)this.getData().getTables().get("GC_FVCH_FIXEDITEM")).updateRows(fixedItemMapList);
        return fixedItemMapList.size();
    }

    private int handleFvchOtherItemBill(String investUnitCode, String investedUnitCode, Integer acctYear) {
        HashSet assetTitleSet = new HashSet();
        List otherItemMapList = ((DataTableImpl)this.getData().getTables().get("GC_FVCH_OTHERITEM")).getRowsData();
        for (Map otherItemMap : otherItemMapList) {
            if (StringUtils.isEmpty((String)((String)otherItemMap.get("ASSETTITLE")))) {
                throw new BillException("\u5176\u5b83\u8d44\u4ea7\uff1a \u8bf7\u586b\u5199\u8d44\u4ea7\u540d\u79f0");
            }
            if (assetTitleSet.contains(otherItemMap.get("ASSETTITLE"))) {
                throw new BillException("\u5176\u5b83\u8d44\u4ea7\uff1a  \u8d44\u4ea7\u540d\u79f0\u4e0d\u80fd\u91cd\u590d");
            }
            assetTitleSet.add(otherItemMap.get("ASSETTITLE"));
            otherItemMap.put("UNITCODE", investUnitCode);
            otherItemMap.put("INVESTEDUNIT", investedUnitCode);
            otherItemMap.put("ACCTYEAR", acctYear);
            otherItemMap.put("SRCID", this.getMaster().getValue("SRCID"));
        }
        ((DataTableImpl)this.getData().getTables().get("GC_FVCH_OTHERITEM")).updateRows(otherItemMapList);
        return otherItemMapList.size();
    }

    private void addFvchLog(String investUnitCode, String investedUnitCode, Integer acctYear) {
        String buttonActionTitle = DataRowState.APPENDED.equals((Object)this.getMaster().getState()) ? "\u65b0\u589e" : "\u4fee\u6539";
        String operateTypeTitle = String.format("%1s-\u5e74\u5ea6%2s-\u6295\u8d44\u5355\u4f4d%3s-\u88ab\u6295\u8d44\u5355\u4f4d%4s", buttonActionTitle, acctYear, investUnitCode, investedUnitCode);
        LogHelper.info((String)"\u5408\u5e76-\u516c\u5141\u4ef7\u503c\u8c03\u6574\u53f0\u8d26", (String)operateTypeTitle, (String)String.format("\u6295\u8d44\u5355\u4f4d\uff1a%1s, \u88ab\u6295\u8d44\u5355\u4f4d\uff1a%2s", investUnitCode, investedUnitCode));
    }
}

