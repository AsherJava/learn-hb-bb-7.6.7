/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataTableNodeContainer
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.ruler.impl.RulerImpl
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.plugin.event.VaAttachmentDataPostEvent;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentSaveAction
extends BillActionBase {
    @Autowired
    ModelDefineService modelDefineService;
    @Autowired
    VaAttachmentDataPostEvent vaAttachmentDataPostEvent;
    @Autowired
    SaveAction saveAction;

    public String getName() {
        return "bill-attachment-save";
    }

    public String getTitle() {
        return "\u9644\u4ef6\u4fdd\u5b58";
    }

    public String getActionPriority() {
        return "030";
    }

    @Override
    public void execute(BillModel billModel, Map<String, Object> params) {
        this.vaAttachmentDataPostEvent.afterSave((DataImpl)billModel.getData());
        List update = (List)billModel.getContext().getContextValue("X--updateAttach");
        if (update != null) {
            this.updateAttach(billModel, update);
        }
    }

    private void updateAttach(BillModel billModel, List<Map<String, Object>> update) {
        billModel.getData().edit();
        ((RulerImpl)billModel.getRuler()).getRulerExecutor().setEnable(true);
        DataTableNodeContainer tables = billModel.getData().getTables();
        for (Map<String, Object> data : update) {
            String tableName = data.get("tableName").toString();
            String fieldName = data.get("fieldName").toString();
            Object value = data.get("value");
            Object id = data.get("id");
            DataTable dataTable = (DataTable)tables.get(tableName);
            DataRow rowById = dataTable.getRowById((Object)UUID.fromString(id.toString()));
            rowById.setValue(fieldName, value);
        }
        billModel.getData().save();
    }
}

