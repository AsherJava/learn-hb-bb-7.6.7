/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataDefine
 *  com.jiuqi.va.biz.intf.data.DataRowState
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.ruler.intf.RulerFields
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 *  com.jiuqi.va.biz.utils.Utils
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.intf.RulerFields;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.biz.utils.Utils;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnitCodeChangeListener2
implements RulerItem {
    Map<String, Map<String, Boolean>> triggerFields;
    private Map<String, Map<String, Boolean>> assignFields;

    public String getName() {
        return "UnitCodeChangeListener2";
    }

    public String getTitle() {
        return "\u76d1\u542c\u7ec4\u7ec7\u673a\u6784\u53d8\u53162";
    }

    public String getRulerType() {
        return "Fixed";
    }

    public Set<String> getTriggerTypes() {
        return Stream.of("after-set-value").collect(Collectors.toSet());
    }

    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine define) {
        if (this.triggerFields == null) {
            DataDefine data = (DataDefine)define.getPlugins().get("data");
            RulerFields rulerFields = RulerFields.build();
            data.getTables().stream().forEach(o -> o.getFields().stream().forEach(f -> {
                String unitField = null;
                Map shareFieldMapping = f.getShareFieldMapping();
                if (shareFieldMapping != null) {
                    unitField = (String)shareFieldMapping.get("UNITCODE");
                }
                if (unitField == null) {
                    unitField = f.getUnitField();
                }
                if (Utils.isNotEmpty(unitField)) {
                    rulerFields.field(o.getName(), unitField, true);
                }
            }));
            this.triggerFields = rulerFields.fields();
        }
        return this.triggerFields;
    }

    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine define) {
        if (this.assignFields == null) {
            DataDefine data = (DataDefine)define.getPlugins().get("data");
            RulerFields rulerFields = RulerFields.build();
            data.getTables().stream().forEach(o -> o.getFields().stream().forEach(f -> {
                String unitField = null;
                Map shareFieldMapping = f.getShareFieldMapping();
                if (shareFieldMapping != null) {
                    unitField = (String)shareFieldMapping.get("UNITCODE");
                }
                if (unitField == null) {
                    unitField = f.getUnitField();
                }
                if (Utils.isNotEmpty(unitField)) {
                    rulerFields.field(o.getName(), f.getName(), true);
                }
            }));
            this.assignFields = rulerFields.fields();
        }
        return this.assignFields;
    }

    public void execute(Model model, Stream<TriggerEvent> events) {
        events.forEach(o -> this.doExecute(model, (TriggerEvent)o));
    }

    private void doExecute(Model model, TriggerEvent event) {
        if (DataRowState.INITIAL == event.getRow().getState()) {
            return;
        }
        event.getTable().getFields().stream().forEach(o -> {
            String unitField = null;
            Map shareFieldMapping = o.getDefine().getShareFieldMapping();
            if (shareFieldMapping != null) {
                unitField = (String)shareFieldMapping.get("UNITCODE");
            }
            if (unitField == null) {
                unitField = o.getDefine().getUnitField();
            }
            if (o.getDefine().isCrossOrgSelection()) {
                return;
            }
            if (event.getField().getName().equals(unitField)) {
                event.getRow().setValue(o.getIndex(), null);
            }
        });
    }
}

