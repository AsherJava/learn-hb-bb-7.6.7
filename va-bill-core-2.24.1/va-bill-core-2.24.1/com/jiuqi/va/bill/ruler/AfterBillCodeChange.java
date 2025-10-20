/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataDefine
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.data.DataTableNodeContainer
 *  com.jiuqi.va.biz.intf.data.DataTableType
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.ruler.intf.RulerFields
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.intf.RulerFields;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AfterBillCodeChange
implements RulerItem {
    public String getName() {
        return "AfterBillCodeChange";
    }

    public String getTitle() {
        return "\u76d1\u542c\u5355\u636e\u7f16\u53f7\u53d8\u5316";
    }

    public String getRulerType() {
        return "Fixed";
    }

    public Set<String> getTriggerTypes() {
        return Stream.of("after-set-value").collect(Collectors.toSet());
    }

    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        String masterTableName = ((DataTableDefine)data.getTables().getMasterTable()).getName();
        return RulerFields.build().field(masterTableName, "BILLCODE", true).fields();
    }

    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        DataTableNodeContainer tables = data.getTables();
        RulerFields rulerFields = RulerFields.build();
        for (int i = 1; i < tables.size(); ++i) {
            DataTableDefine itemTable = (DataTableDefine)tables.get(i);
            if (itemTable.getTableType() != DataTableType.DATA) continue;
            rulerFields.fields(itemTable.getName(), Arrays.asList("BILLCODE"), false);
        }
        return rulerFields.fields();
    }

    public void execute(Model model, Stream<TriggerEvent> events) {
        DataTableNodeContainer dataTable = ((Data)model.getPlugins().get(Data.class)).getTables();
        String billCode = ((DataRow)((DataTable)dataTable.get(0)).getRows().get(0)).getString("BILLCODE");
        for (int i = 1; i < dataTable.size(); ++i) {
            DataField field;
            DataTable itemTable = (DataTable)dataTable.get(i);
            if (itemTable.getTableType() != DataTableType.DATA || (field = (DataField)itemTable.getFields().find("BILLCODE")) == null) continue;
            itemTable.getRows().stream().forEach(o -> o.setValue(field.getIndex(), (Object)billCode));
        }
    }
}

