/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 */
package com.jiuqi.va.extend.impl;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.extend.common.VaBackWriteEnum;
import com.jiuqi.va.extend.common.VaBackWriteTriggerTypeEnum;
import com.jiuqi.va.extend.intf.VaBillBackWriteTypeIntf;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class AfterRejectBackWriteType
implements VaBillBackWriteTypeIntf {
    @Override
    public String getType() {
        return VaBackWriteEnum.AFTER_REJECT.getName();
    }

    @Override
    public String getTitle() {
        return VaBackWriteEnum.AFTER_REJECT.getTitle();
    }

    @Override
    public String getTriggerType() {
        return VaBackWriteTriggerTypeEnum.AFTER_SAVE.getName();
    }

    @Override
    public Class<? extends Model> getDependModel() {
        return BillModelImpl.class;
    }

    @Override
    public boolean needExecute(BillModel model) {
        int bizstate;
        Object actionName = model.getContext().getContextValue("action");
        if (ObjectUtils.isEmpty(actionName) || !actionName.toString().equals("bill-reject")) {
            return false;
        }
        DataRow master = model.getMaster();
        DataRow originMaster = model.getMaster().getOriginRow();
        int billstate = master.getInt("BILLSTATE");
        int oldbillstate = originMaster.getInt("BILLSTATE");
        if (billstate == BillState.REJECT.getValue() && billstate != oldbillstate) {
            return true;
        }
        DataField bizStateDefine = (DataField)model.getMasterTable().getFields().find("BIZSTATE");
        if (bizStateDefine != null && ((bizstate = master.getInt("BIZSTATE")) == 43 || bizstate == 58)) {
            return false;
        }
        return billstate == BillState.SENDBACK.getValue() && billstate != oldbillstate;
    }
}

