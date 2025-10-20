/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import java.util.Map;
import java.util.UUID;

public abstract class ActionBase
implements Action {
    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public Class<? extends Model> getDependModel() {
        return Model.class;
    }

    protected DataTable getTable(Model model, Map<String, Object> params) {
        String tableName = (String)params.get("tableName");
        DataImpl data = model.getPlugins().get(DataImpl.class);
        DataTableImpl table = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).get(tableName);
        return table;
    }

    protected int getRowIndex(DataTable table, Map<String, Object> params) {
        UUID rowId = Convert.cast(params.get("rowId"), UUID.class);
        int rowIndex = rowId != null ? table.getRowById(rowId).getIndex() : Convert.cast(params.get("rowIndex"), Integer.TYPE).intValue();
        return rowIndex;
    }

    @Override
    public String getGroupId() {
        return "00";
    }

    @Override
    public String getActionPriority() {
        return "999";
    }
}

