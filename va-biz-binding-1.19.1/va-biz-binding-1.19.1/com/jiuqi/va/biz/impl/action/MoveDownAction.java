/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.va.biz.impl.action.ActionBase;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MoveDownAction
extends ActionBase {
    @Override
    public String getName() {
        return "item-move-down";
    }

    @Override
    public String getTitle() {
        return "\u4e0b\u79fb";
    }

    @Override
    public String getIcon() {
        return "";
    }

    @Override
    public String getActionPriority() {
        return "015";
    }

    @Override
    public void execute(Model model, Map<String, Object> params) {
        String tableName = Convert.cast(params.get("tableName"), String.class);
        DataImpl data = model.getPlugins().get(DataImpl.class);
        DataTableImpl table = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).get(tableName);
        List<DataRowImpl> rowList = table.getRowList();
        if (CollectionUtils.isEmpty(rowList)) {
            return;
        }
        Integer rowIndex = Convert.cast(params.get("rowIndex"), Integer.class);
        if (rowIndex == null || rowIndex < 0 || rowIndex >= rowList.size()) {
            return;
        }
        Integer endRowIndex = Convert.cast(params.get("endRowIndex"), Integer.class);
        int count = endRowIndex == null ? 1 : endRowIndex - rowIndex + 1;
        if (count <= 0 || rowIndex + count >= rowList.size()) {
            return;
        }
        int nextIndex = rowIndex + count;
        DataRowImpl nextRow = rowList.get(nextIndex);
        ArrayList<DataRowImpl> toMove = new ArrayList<DataRowImpl>(rowList.subList(rowIndex, nextIndex));
        ArrayList<DataRowImpl> newBlock = new ArrayList<DataRowImpl>();
        newBlock.add(nextRow);
        newBlock.addAll(toMove);
        rowList.subList(rowIndex, nextIndex + 1).clear();
        rowList.addAll(rowIndex, newBlock);
    }
}

