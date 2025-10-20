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
public class AfterCommitBackWriteType
implements VaBillBackWriteTypeIntf {
    private static final Logger log = LoggerFactory.getLogger(AfterCommitBackWriteType.class);

    @Override
    public String getType() {
        return VaBackWriteEnum.AFTER_COMMIT.getName();
    }

    @Override
    public String getTitle() {
        return VaBackWriteEnum.AFTER_COMMIT.getTitle();
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
        DataRow master = model.getMaster();
        DataRow originMaster = model.getMaster().getOriginRow();
        int billstate = master.getInt("BILLSTATE");
        int oldbillstate = originMaster.getInt("BILLSTATE");
        if (billstate == BillState.COMMITTED.getValue() && (oldbillstate == BillState.SAVED.getValue() || oldbillstate == BillState.SENDBACK.getValue())) {
            log.info("\u5355\u636e\u63d0\u4ea4\u65f6\u6267\u884c\u53cd\u5199\u516c\u5f0f" + billstate + ":" + oldbillstate);
            return true;
        }
        if (billstate == BillState.AUDITING.getValue() && oldbillstate == BillState.SENDBACK.getValue()) {
            log.info("\u5355\u636e\u63d0\u4ea4\u65f6\u6267\u884c\u53cd\u5199\u516c\u5f0f" + billstate + ":" + oldbillstate);
            return true;
        }
        if (billstate == BillState.AUDITING.getValue() && oldbillstate == BillState.SAVED.getValue()) {
            log.info("\u5355\u636e\u63d0\u4ea4\u65f6\u6267\u884c\u53cd\u5199\u516c\u5f0f" + billstate + ":" + oldbillstate);
            return true;
        }
        return false;
    }
}

