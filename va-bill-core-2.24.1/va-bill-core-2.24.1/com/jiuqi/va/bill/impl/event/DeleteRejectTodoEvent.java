/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.biz.intf.data.DataRow
 */
package com.jiuqi.va.bill.impl.event;

import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.ToDoUtils;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.data.DataRow;

public class DeleteRejectTodoEvent
implements DataPostEvent {
    public void afterSave(DataImpl billModel) {
        DataRow masterData = (DataRow)billModel.getMasterTable().getRows().get(0);
        if (masterData.getInt("BILLSTATE") == BillState.DELETED.getValue()) {
            String billcode = (String)masterData.getValue("BILLCODE", String.class);
            ToDoUtils.deleteRejectToDo(billcode, "BILL");
        }
    }

    public void afterDelete(DataImpl billModel) {
        int billState;
        DataRow masterData = (DataRow)billModel.getMasterTable().getRows().get(0);
        String billcode = (String)masterData.getValue("BILLCODE", String.class);
        if (billModel.getMasterTable().getFields().find("BILLSTATE") != null && ((billState = masterData.getInt("BILLSTATE")) == BillState.DELETED.getValue() || billState == BillState.SAVED.getValue() || billState == BillState.TEMPORARY.getValue())) {
            return;
        }
        ToDoUtils.deleteRejectToDo(billcode, "BILL");
    }

    public void beforeSave(DataImpl billModel) {
    }

    public void beforeDelete(DataImpl billModel) {
    }
}

