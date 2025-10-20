/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.va.biz.impl.action.ActionBase;
import com.jiuqi.va.biz.impl.action.SetValueAction;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SetValuesAction
extends ActionBase {
    @Override
    public String getName() {
        return "set-values";
    }

    @Override
    public String getTitle() {
        return "\u6279\u91cf\u8bbe\u7f6e\u5b57\u6bb5\u503c";
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public boolean isInner() {
        return true;
    }

    @Override
    public void execute(Model model, Map<String, Object> params) {
        ModelImpl modelImpl = (ModelImpl)model;
        DataTable table = this.getTable(model, params);
        String tableName = table.getName();
        int rowIndex = this.getRowIndex(table, params);
        List rows = (List)params.get("rows");
        if (rows != null) {
            for (Map row : rows) {
                this.executeSetValue(modelImpl, tableName, rowIndex, row);
                ++rowIndex;
            }
        } else {
            Map values = (Map)params.get("values");
            if (values == null || values.size() == 0) {
                return;
            }
            this.executeSetValue(modelImpl, tableName, rowIndex, values);
        }
    }

    private void executeSetValue(ModelImpl modelImpl, String tableName, int rowIndex, Map<String, Object> values) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            ActionRequest request = new ActionRequest();
            ActionResponse response = new ActionResponse();
            HashMap<String, Object> setValParam = new HashMap<String, Object>();
            setValParam.put("tableName", tableName);
            setValParam.put("fieldName", entry.getKey());
            setValParam.put("value", entry.getValue());
            setValParam.put("rowIndex", rowIndex);
            request.setParams(setValParam);
            modelImpl.executeAction((Action)ApplicationContextRegister.getBean(SetValueAction.class), request, response);
        }
    }
}

