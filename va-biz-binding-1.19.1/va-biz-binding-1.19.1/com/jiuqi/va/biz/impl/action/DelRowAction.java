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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DelRowAction
extends ActionBase {
    @Override
    public String getName() {
        return "del-row";
    }

    @Override
    public String getTitle() {
        return "\u5220\u884c";
    }

    @Override
    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_shanhang";
    }

    @Override
    public String getActionPriority() {
        return "012";
    }

    @Override
    public void execute(Model model, Map<String, Object> params) {
        DataTable table = this.getTable(model, params);
        boolean leaf = params.containsKey("leaf") && (Boolean)params.get("leaf") != false;
        List<DataRow> rows = null;
        if (leaf) {
            rows = table.getRows().stream().collect(Collectors.toList());
        }
        if (params.containsKey("batch") && ((Boolean)params.get("batch")).booleanValue()) {
            List idList = (List)params.get("idList");
            if (idList != null && idList.size() > 0) {
                for (int i = 0; i < idList.size(); ++i) {
                    table.deleteRowById(Convert.cast(idList.get(i), UUID.class));
                }
            } else {
                List indexList = (List)params.get("indexList");
                Collections.sort(indexList, Comparator.reverseOrder());
                ArrayList<Object> ids = new ArrayList<Object>();
                for (Integer n : indexList) {
                    ids.add(table.getRows().get(n).getId());
                }
                for (int i = 0; i < ids.size(); ++i) {
                    Object e = ids.get(i);
                    table.deleteRowById(e);
                }
            }
            return;
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
                if (endRowIndex > rowIndex) {
                    this.defindLeaf(rows, rowIndex, endRowIndex);
                } else {
                    this.defindLeaf(rows, rowIndex, rowIndex);
                }
            }
            for (Object e : ids) {
                table.deleteRowById(e);
            }
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

