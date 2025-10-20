/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.va.biz.impl.action.ActionBase;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AddRowAction
extends ActionBase {
    @Override
    public String getName() {
        return "add-row";
    }

    @Override
    public String getTitle() {
        return "\u589e\u884c";
    }

    @Override
    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_zenghang";
    }

    @Override
    public String getActionPriority() {
        return "011";
    }

    @Override
    public void execute(Model model, Map<String, Object> params) {
        Map values;
        Map tableList;
        ModelContextImpl context = (ModelContextImpl)model.getContext();
        Object detailEnableFilter = context.getContextValue("X--detailFilterDataId");
        if (detailEnableFilter != null && !(tableList = (Map)detailEnableFilter).containsKey(params.get("tableName"))) {
            return;
        }
        Object directionObj = params.get("direction");
        String direction = directionObj == null ? "before" : String.valueOf(directionObj);
        DataTable table = this.getTable(model, params);
        int rowIndex = this.getRowIndex(table, params);
        if ("after".equals(direction)) {
            if (table.getRowsData().size() > 0) {
                ++rowIndex;
            }
        } else if ("last".equals(direction)) {
            rowIndex = table.getRowsData().size();
        }
        if (params.containsKey("batch") && ((Boolean)params.get("batch")).booleanValue()) {
            List list = (List)params.get("values");
            if (list == null) {
                return;
            }
            for (Map row : list) {
                if (row != null) {
                    table.insertRow(rowIndex, row);
                } else {
                    table.insertRow(rowIndex);
                }
                ++rowIndex;
            }
            return;
        }
        Map newValues = (Map)params.get("values");
        DataTableDefineImpl tableDefineImpl = (DataTableDefineImpl)table.getDefine();
        newValues.put("$UNSET", tableDefineImpl.isBlankRow());
        boolean leaf = params.containsKey("leaf") && (Boolean)params.get("leaf") != false;
        List<DataRow> rows = null;
        if (leaf) {
            rows = table.getRows().stream().collect(Collectors.toList());
        }
        if ((values = (Map)params.get("values")) != null) {
            if (leaf) {
                values.put("LEAF", this.isLeaf(rows, Convert.cast(values.get("TREEID"), UUID.class), Convert.cast(values.get("PARENTID"), UUID.class), rowIndex));
            }
            table.insertRow(rowIndex, values);
        } else {
            table.insertRow(rowIndex);
        }
    }

    private boolean isLeaf(List<DataRow> dataRows, UUID curId, UUID curPid, int i) {
        DataRow beforeRow;
        DataRow dataRow = beforeRow = i - 1 >= 0 ? dataRows.get(i - 1) : null;
        if (i == dataRows.size() - 1) {
            if (beforeRow != null && curPid.equals(beforeRow.getUUID("TREEID"))) {
                beforeRow.setValue("LEAF", (Object)false);
            }
            return true;
        }
        if (i == 0) {
            return true;
        }
        UUID pId = dataRows.get(i - 1).getUUID("PARENTID");
        if (beforeRow != null && beforeRow.getUUID("TREEID").equals(curPid)) {
            beforeRow.setValue("LEAF", (Object)false);
        }
        if (curId == null || pId == null) {
            return true;
        }
        return !curId.equals(pId);
    }
}

