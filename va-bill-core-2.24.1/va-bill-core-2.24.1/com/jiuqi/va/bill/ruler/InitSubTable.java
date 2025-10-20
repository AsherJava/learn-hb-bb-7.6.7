/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataDefine
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.data.DataTableType
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.ruler.intf.RulerFields
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 *  com.jiuqi.va.biz.utils.Utils
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.intf.RulerFields;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.biz.utils.Utils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InitSubTable
implements RulerItem {
    public String getName() {
        return "InitSubTable";
    }

    public String getTitle() {
        return "\u521d\u59cb\u5316\u5b50\u8868\u5b57\u6bb5\u503c";
    }

    public String getRulerType() {
        return "Fixed";
    }

    public Set<String> getTriggerTypes() {
        return Stream.of("after-add-row").collect(Collectors.toSet());
    }

    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        RulerFields rulerFields = RulerFields.build();
        data.getTables().stream().forEach(o -> {
            if (o.getName().equals(((DataTableDefine)data.getTables().getMasterTable()).getName())) {
                return;
            }
            rulerFields.noFields(o.getName(), true);
        });
        return rulerFields.fields();
    }

    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        RulerFields rulerFields = RulerFields.build();
        List<String> fields = Arrays.asList("VER", "BILLCODE");
        data.getTables().stream().forEach(o -> {
            if (o.getName().equals(((DataTableDefine)data.getTables().getMasterTable()).getName())) {
                return;
            }
            rulerFields.fields(o.getName(), fields, true);
        });
        return rulerFields.fields();
    }

    public void execute(Model model, Stream<TriggerEvent> events) {
        BillModelImpl billModel = (BillModelImpl)model;
        events.forEach(e -> {
            DataField fieldBILLCODE;
            if (e.getTable().getTableType() != DataTableType.DATA) {
                return;
            }
            DataRow dataRow = e.getRow();
            DataField fieldVER = (DataField)e.getTable().getFields().find("VER");
            if (fieldVER != null) {
                dataRow.setValue(fieldVER.getIndex(), (Object)System.currentTimeMillis());
            }
            if ((fieldBILLCODE = (DataField)e.getTable().getFields().find("BILLCODE")) != null) {
                dataRow.setValue(fieldBILLCODE.getIndex(), billModel.getMaster().getValue("BILLCODE", String.class));
            }
            billModel.getData().getTables().getDetailTables(e.getTable().getId()).stream().forEach(table -> {
                if (table.getDefine().isSingle() && table.getRows().size() == 0) {
                    table.appendRow(Utils.makeMap((Object[])new Object[]{"ID", UUID.randomUUID(), "MASTERID", dataRow.getMasterId(), "GROUPID", dataRow.getId()}));
                }
            });
        });
    }
}

