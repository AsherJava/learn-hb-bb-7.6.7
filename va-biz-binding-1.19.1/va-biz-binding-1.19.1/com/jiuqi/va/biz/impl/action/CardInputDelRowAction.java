/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.va.biz.impl.action.ActionBase;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CardInputDelRowAction
extends ActionBase {
    @Override
    public String getName() {
        return "card-del-row";
    }

    @Override
    public String getTitle() {
        return "\u5220\u884c(\u5361\u7247\u5f55\u5165)";
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
    public String getActionPriority() {
        return "013";
    }

    @Override
    public void execute(Model model, Map<String, Object> params) {
        DataTable table = this.getTable(model, params);
        boolean leaf = params.containsKey("leaf") && (Boolean)params.get("leaf") != false;
        List<DataRow> rows = null;
        if (leaf) {
            rows = table.getRows().stream().collect(Collectors.toList());
        }
        int rowIndex = this.getRowIndex(table, params);
        int endRowIndex = Convert.cast(params.get("endRowIndex"), Integer.TYPE);
        if (endRowIndex == 0) {
            table.deleteRow(rowIndex);
        } else {
            ArrayList<Object> ids = new ArrayList<Object>();
            for (int i = endRowIndex; i >= rowIndex; --i) {
                ids.add(table.getRows().get(i).getId());
            }
            if (leaf) {
                this.defindLeaf(rows, rowIndex, endRowIndex);
            }
            for (Object e : ids) {
                table.deleteRowById(e);
            }
        }
        if (table.getRows().size() == 0) {
            table.insertRow(0);
        }
    }

    private void defindLeaf(List<DataRow> rows, int rowIndex, int endRowIndex) {
        DataRow beforeRow = null;
        DataRow currRow = null;
        DataRow afterRow = null;
        if (rowIndex - 1 > -1) {
            beforeRow = rows.get(rowIndex - 1);
        }
        currRow = rows.get(rowIndex);
        if (endRowIndex + 1 < rows.size()) {
            afterRow = rows.get(endRowIndex + 1);
        }
        if (beforeRow != null && currRow.getUUID("PARENTID").equals(beforeRow.getUUID("TREEID"))) {
            if (afterRow != null && afterRow.getUUID("PARENTID").equals(beforeRow.getUUID("TREEID"))) {
                beforeRow.setValue("LEAF", (Object)false);
                return;
            }
            beforeRow.setValue("LEAF", (Object)true);
        }
    }
}

