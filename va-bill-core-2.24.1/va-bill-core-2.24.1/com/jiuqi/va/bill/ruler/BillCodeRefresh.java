/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataState
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.ruler.intf.RulerFields
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.intf.RulerFields;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BillCodeRefresh
implements RulerItem {
    public void execute(Model model, Stream<TriggerEvent> events) {
        BillModelImpl billModel = (BillModelImpl)model;
        if (billModel.getData().getState() != DataState.NEW) {
            return;
        }
        boolean generateFlag = false;
        List eventList = events.collect(Collectors.toList());
        for (TriggerEvent event : eventList) {
            if (generateFlag) break;
            DataRow master = event.getRow();
            Object unitCode = master.getValue("UNITCODE");
            Object billDate = master.getValue("BILLDATE");
            if (unitCode == null || billDate == null) continue;
            master.setValue("BILLCODE", (Object)billModel.createBillCode());
            generateFlag = true;
        }
    }

    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        String masterTableName = ((DataTableDefine)data.getTables().getMasterTable()).getName();
        List<String> fields = Arrays.asList("BILLCODE");
        return RulerFields.build().fields(masterTableName, fields, true).fields();
    }

    public String getName() {
        return "BillCodeRefresh";
    }

    public String getRulerType() {
        return "Fixed";
    }

    public String getTitle() {
        return "\u5355\u636e\u7f16\u53f7\u65b0\u589e\u65f6\u91cd\u65b0\u751f\u6210";
    }

    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        String masterTableName = ((DataTableDefine)data.getTables().getMasterTable()).getName();
        List<String> fields = Arrays.asList("UNITCODE", "BILLDATE");
        return RulerFields.build().fields(masterTableName, fields, true).fields();
    }

    public Set<String> getTriggerTypes() {
        return Stream.of("after-set-value").collect(Collectors.toSet());
    }
}

