/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 */
package com.jiuqi.va.bill.plugin.event;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.service.BillChangeRecordService;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.bill.utils.SuperEditCondition;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(value={SuperEditCondition.class})
public class SuperEditDataPosEvent
implements DataPostEvent {
    @Autowired
    private BillChangeRecordService billChangeRecordService;

    public void beforeSave(DataImpl data) {
    }

    public void afterSave(DataImpl data) {
        BillContextImpl context = (BillContextImpl)data.getModel().getContext();
        Object contextValue = context.getContextValue("X--superEdit");
        if (contextValue == null || !((Boolean)contextValue).booleanValue()) {
            return;
        }
        Object sceneObj = context.getContextValue("X--superEditScene");
        String scene = sceneObj == null ? "" : sceneObj.toString();
        DataTableNodeContainerImpl tables = data.getTables();
        DataRowImpl master = (DataRowImpl)data.getMasterTable().getRows().get(0);
        Map dataUpdateMap = data.getChangeData();
        Map<String, Map<String, Object>> changeRecord = BillUtils.getUpdateData(dataUpdateMap, (DataTableNodeContainerImpl<DataTableImpl>)tables);
        this.billChangeRecordService.insertRecord(scene, master.getString("BILLCODE"), changeRecord);
    }

    public void beforeDelete(DataImpl data) {
    }

    public void afterDelete(DataImpl data) {
    }
}

