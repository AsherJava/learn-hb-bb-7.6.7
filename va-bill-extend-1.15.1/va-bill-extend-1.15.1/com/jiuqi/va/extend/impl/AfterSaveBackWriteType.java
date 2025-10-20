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
import org.springframework.util.ObjectUtils;

@Component
public class AfterSaveBackWriteType
implements VaBillBackWriteTypeIntf {
    private static final Logger log = LoggerFactory.getLogger(AfterSaveBackWriteType.class);
    private final String backWriteKey = "BACKWRITE_";

    @Override
    public String getType() {
        return VaBackWriteEnum.AFTER_SAVE.getName();
    }

    @Override
    public String getTitle() {
        return VaBackWriteEnum.AFTER_SAVE.getTitle();
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
        long version = master.getVersion();
        String newVersion = Long.toString(version);
        String billid = master.getString("ID");
        String key = "BACKWRITE_" + billid;
        Object oldVersion = model.getContext().getContextValue(key);
        if (!ObjectUtils.isEmpty(oldVersion) && oldVersion.equals(newVersion)) {
            log.info("\u5f53\u6b21\u4fdd\u5b58\u4e2d\u5df2\u6267\u884c\u8fc7\u4e00\u6b21\uff0c{}\u4e0d\u518d\u6267\u884c{}\u7c7b\u578b\u53cd\u5199\u516c\u5f0f", (Object)master.getString("BILLCODE"), (Object)this.getTitle());
            return false;
        }
        int billstate = master.getInt("BILLSTATE");
        int oldbillstate = master.getOriginRow().getInt("BILLSTATE");
        if (oldbillstate == BillState.COMMITTED.getValue() || oldbillstate == BillState.AUDITING.getValue()) {
            return false;
        }
        if (billstate == BillState.SAVED.getValue()) {
            model.getContext().setContextValue(key, (Object)newVersion);
            return true;
        }
        return false;
    }
}

