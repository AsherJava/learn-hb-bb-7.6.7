/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.billcore.transaction.BillTransactionProxy
 *  com.jiuqi.gcreport.billcore.transaction.IBillTransaction
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.bill.utils.VerifyUtils
 *  com.jiuqi.va.biz.intf.data.DataRow
 */
package com.jiuqi.gcreport.lease.leasebill.bill.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.billcore.transaction.BillTransactionProxy;
import com.jiuqi.gcreport.billcore.transaction.IBillTransaction;
import com.jiuqi.gcreport.lease.leasebill.bill.impl.AbstractLeaseBillModel;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.VerifyUtils;
import com.jiuqi.va.biz.intf.data.DataRow;
import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LessorBillModelImpl
extends AbstractLeaseBillModel
implements IBillTransaction {
    public void save() {
        BillTransactionProxy.getInstance().save((IBillTransaction)this);
    }

    public void loadByCode(String billCode) {
        super.loadByCode(billCode);
        this.resetBillDate();
    }

    private void resetBillDate() {
        Integer acctYear = ConverterUtils.getAsInteger((Object)this.getContext().getContextValue("X--acctyear"));
        if (null == acctYear) {
            return;
        }
        Calendar curCalendar = Calendar.getInstance();
        int curYear = curCalendar.get(1);
        if (acctYear < curYear) {
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.set(acctYear, 11, 31);
            this.getMaster().setValue("BILLDATE", (Object)newCalendar.getTime());
            return;
        }
        this.getMaster().setValue("BILLDATE", (Object)curCalendar.getTime());
    }

    public void saveWithTrans() {
        VerifyUtils.verifyBill((BillModel)this, (int)2);
        DataRow dataRow = this.getMaster();
        int billState = this.getMaster().getInt("BILLSTATE");
        if (billState <= 1) {
            this.getMaster().setValue("BILLSTATE", (Object)BillState.SAVED);
        }
        this.getData().saveWithLock(Stream.of("ID", "VER").collect(Collectors.toSet()));
        this.addLeaseBillLog(dataRow, "\u51fa\u79df\u65b9\u53f0\u8d26");
        this.edit();
        this.autoAddSubBill(dataRow);
        this.getData().saveWithLock(Stream.of("ID", "VER").collect(Collectors.toSet()));
    }

    @Override
    protected String calcFn_initUnfinIncome() {
        return "IINITUNFININCOME";
    }

    @Override
    protected String calcFn_initFinLeaseAsset() {
        return "INITFINLEASEASSET";
    }

    @Override
    protected String calcFn_unfinAmortize() {
        return "UNFINAMORTIZE";
    }

    @Override
    protected String calcFn_reaseFinBeginAmt() {
        return "REASEFINBEGINAMT";
    }

    @Override
    protected String calcFn_reaseFinEndAmt() {
        return "REASEFINENDAMT";
    }

    @Override
    protected String getSubTableName() {
        return "GC_LESSORITEMBILL";
    }
}

