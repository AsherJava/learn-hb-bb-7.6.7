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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AfterDeleteBackWriteType
implements VaBillBackWriteTypeIntf {
    private static final Logger log = LoggerFactory.getLogger(AfterDeleteBackWriteType.class);

    @Override
    public String getType() {
        return VaBackWriteEnum.AFTER_DELETE.getName();
    }

    @Override
    public String getTitle() {
        return VaBackWriteEnum.AFTER_DELETE.getTitle();
    }

    @Override
    public String getTriggerType() {
        return VaBackWriteTriggerTypeEnum.AFTER_DELETE.getName();
    }

    @Override
    public Class<? extends Model> getDependModel() {
        return BillModelImpl.class;
    }

    @Override
    public boolean needExecute(BillModel model) {
        DataRow master = model.getMaster();
        int billstate = master.getInt("BILLSTATE");
        if (billstate == BillState.TEMPORARY.getValue()) {
            return false;
        }
        log.info("\u6267\u884c\u5220\u9664\u65f6\u53cd\u5199\u516c\u5f0f");
        return true;
    }
}

