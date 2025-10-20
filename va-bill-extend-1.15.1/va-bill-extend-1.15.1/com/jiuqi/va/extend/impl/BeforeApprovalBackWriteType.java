/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 */
package com.jiuqi.va.extend.impl;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.extend.common.VaBackWriteEnum;
import com.jiuqi.va.extend.common.VaBackWriteTriggerTypeEnum;
import com.jiuqi.va.extend.intf.VaBillBackWriteTypeIntf;
import org.springframework.stereotype.Component;

@Component
public class BeforeApprovalBackWriteType
implements VaBillBackWriteTypeIntf {
    @Override
    public String getType() {
        return VaBackWriteEnum.BEFORE_APPROVAL.getName();
    }

    @Override
    public String getTitle() {
        return VaBackWriteEnum.BEFORE_APPROVAL.getTitle();
    }

    @Override
    public String getTriggerType() {
        return VaBackWriteTriggerTypeEnum.BEFORE_SAVE.getName();
    }

    @Override
    public Class<? extends Model> getDependModel() {
        return BillModelImpl.class;
    }

    @Override
    public boolean needExecute(BillModel model) {
        DataRow master = model.getMaster();
        DataRow originMaster = model.getMaster().getOriginRow();
        int billstate = master.getInt("BILLSTATE");
        int oldbillstate = originMaster.getInt("BILLSTATE");
        return billstate == BillState.AUDITPASSED.getValue() && billstate != oldbillstate;
    }
}

