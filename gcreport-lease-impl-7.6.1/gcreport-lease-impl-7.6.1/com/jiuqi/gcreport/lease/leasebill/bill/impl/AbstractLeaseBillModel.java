/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.billcore.common.GcBillModelImpl
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataRowState
 */
package com.jiuqi.gcreport.lease.leasebill.bill.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.billcore.common.GcBillModelImpl;
import com.jiuqi.gcreport.lease.leasebill.bill.impl.LeaseSubBillCac;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import java.util.List;
import java.util.Map;

public abstract class AbstractLeaseBillModel
extends GcBillModelImpl {
    protected abstract String calcFn_initUnfinIncome();

    protected abstract String calcFn_initFinLeaseAsset();

    protected abstract String calcFn_unfinAmortize();

    protected abstract String calcFn_reaseFinBeginAmt();

    protected abstract String calcFn_reaseFinEndAmt();

    protected abstract String getSubTableName();

    public void autoAddSubBill(DataRow dataRow) {
        LeaseSubBillCac leaseSubBillCac = new LeaseSubBillCac(dataRow, this);
        int leaseHold = ConverterUtils.getAsIntValue((Object)dataRow.getString("LEASEHOLD"));
        if (leaseHold == 0) {
            return;
        }
        String collectAmtPeriod = dataRow.getString("COLLECTAMTPERIOD");
        if (LeaseSubBillCac.CollectAmtPeriodEnum.B.getCode().equals(collectAmtPeriod)) {
            return;
        }
        List<Map<String, Object>> subBillRecordList = leaseSubBillCac.doCalc(dataRow);
        ((DataTableImpl)this.getData().getTables().get(this.getSubTableName())).setRowsData(subBillRecordList);
    }

    protected void addLeaseBillLog(DataRow dataRow, String leaseBillName) {
        String unitCode = dataRow.getString("UNITCODE");
        String oppUnitCode = dataRow.getString("OPPUNITCODE");
        String buttonActionTitle = DataRowState.APPENDED.equals((Object)dataRow.getState()) ? "\u65b0\u589e" : "\u4fee\u6539";
        String operateTypeTitle = String.format("%1s-\u672c\u65b9\u5355\u4f4d%2s-\u5bf9\u65b9\u5355\u4f4d%4s", buttonActionTitle, unitCode, oppUnitCode);
        LogHelper.info((String)("\u5408\u5e76-" + leaseBillName), (String)operateTypeTitle, (String)String.format("\u6295\u8d44\u5355\u4f4d\uff1a%1s, \u88ab\u6295\u8d44\u5355\u4f4d\uff1a%2s", unitCode, oppUnitCode));
    }
}

